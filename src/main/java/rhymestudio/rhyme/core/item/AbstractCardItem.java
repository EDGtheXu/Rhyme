package rhymestudio.rhyme.core.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponentType;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModSounds;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static rhymestudio.rhyme.config.ServerConfig.PlantConsumeAdditionStep;
import static rhymestudio.rhyme.utils.Computer.getBlockPosCenter;
import static rhymestudio.rhyme.utils.Computer.getEyeBlockHitResult;

public class AbstractCardItem<T extends AbstractPlant> extends CustomRarityItem {
    public Supplier<EntityType<T>> entityType;

    public int consume;
    public int cd = 5*20;
    public AbstractCardItem(Properties properties, Supplier<EntityType<T>> entityType, int consume){
        super(properties);
        this.entityType = entityType;
        this.consume = consume;
    }

    public AbstractCardItem<T> setCd(int cd){
        this.cd = cd*20;
        return this;
    }

    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pStack.getTag() == null || pStack.getTag().get("card_quality") == null){
            var tag = pStack.getOrCreateTag();
            CardQualityComponentType.of(0).writeToNBT(tag);
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!player.canBeSeenAsEnemy()){ // 创造
            summon(player, level, itemstack);
            return InteractionResultHolder.success(itemstack);
        }
//            if(!Computer.tryCombineInventoryItem(player, MaterialItems.SUN_ITEM.get(), consume)){
//                return InteractionResultHolder.fail(itemstack);
//            }

        var data =player.getCapability(ModAttachments.PLANT_RECORDER_STORAGE);

        AtomicInteger consumeCount = new AtomicInteger();
        data.ifPresent(d-> consumeCount.set(d.ids.size() * PlantConsumeAdditionStep.get() + this.consume));
        AtomicBoolean flag = new AtomicBoolean(false);
        player.getCapability(ModAttachments.PLAYER_STORAGE).ifPresent(
                 d-> flag.set(d.consumeSun(player, consumeCount.get()))
        );

        if(!flag.get()) {
            if(!level.isClientSide)
                player.sendSystemMessage(Component.translatable("plantcard.not_enough_sun").withStyle(Style.EMPTY.withColor(0xff0000)));
            return InteractionResultHolder.fail(itemstack);
        }
        if(!summon(player, level, itemstack)) return InteractionResultHolder.fail(itemstack);

        if(itemstack.getTag() != null && itemstack.getTag().contains("Unbreakable")){
            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            if(itemstack.getDamageValue() >= itemstack.getMaxDamage())
                itemstack.shrink(1);
        }
        return InteractionResultHolder.success(itemstack);
    }

    public boolean summon(Player player, Level level,ItemStack stack){
        final BlockPos pos = getEyeBlockHitResult(player);

        if(!canPutPlant(level, player, pos)){
            if(!level.isClientSide)
                player.sendSystemMessage(Component.translatable("plantcard.cannot_put_plant").withStyle(Style.EMPTY.withColor(0xff0000)));
            return false;
        }

        var entity = entityType.get().create(level);
        entity.setOwner(player);
        entity.setPos(getBlockPosCenter(pos,player.getRandom()));

        var data = new CardQualityComponentType(stack);
        if(!data.isValid()) return false;
        int lvl = data.level;
        entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("209b13b1-9315-4500-8259-6d913210a2b1",0.5f*lvl,AttributeModifier.Operation.MULTIPLY_BASE));
        entity.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier("d7221e68-d651-4515-8288-1b50e645ccf0",0.5f*lvl,AttributeModifier.Operation.MULTIPLY_BASE));
        entity.setCardLevel(lvl);
        level.addFreshEntity(entity);
        entity.playSound(ModSounds.PLANT.get());
        entity.setHealth(entity.getMaxHealth());
        var playerData = player.getCapability(ModAttachments.PLANT_RECORDER_STORAGE);
        playerData.ifPresent(d->d.ids.add(entity.getId()));
        if(player.canBeSeenAsEnemy())
            player.getCooldowns().addCooldown(stack.getItem(), this.cd);
        if(!level.isClientSide){
            var data1 = new CardQualityComponentType(stack);
            player.sendSystemMessage(Component.translatable("plantcard.summon_success").append(Component.translatable("entity.rhyme."+ ForgeRegistries.ENTITY_TYPES.getKey(entityType.get())).withStyle(Style.EMPTY.withColor(data1.color))));
        }
        return true;
    }


    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var quality = new CardQualityComponentType(stack);
        var r = ForgeRegistries.ITEMS.getKey(quality.getQualityItem());
        if(r != null && r.getPath().split("/").length > 1)
            tooltipComponents.add(Component.translatable("plantcard.tooltip.card_quality").append(": ")
                .append(Component.translatable("plantcard.tooltip.card_quality."+ r.getPath().split("/")[1]).withStyle(Style.EMPTY.withColor(quality.color))));
        var data = Minecraft.getInstance().player.getCapability(ModAttachments.PLANT_RECORDER_STORAGE);
        data.ifPresent(d->{
            int consumeCount = d.ids.size() * PlantConsumeAdditionStep.get();
            tooltipComponents.add(Component.translatable("plantcard.tooltip.consumed_sun").append(": "+this.consume + " + "+consumeCount).withStyle(Style.EMPTY.withColor(0xffff00)));

        });
        float percent = (float)stack.getDamageValue()/(float)stack.getMaxDamage();
        int color = (int)((1-percent)*0x0000ff)<<8 | (int)(percent*0x0000ff)<<16;



        if(stack.getTag() != null && stack.getTag().contains("Unbreakable"))
            tooltipComponents.add(Component.translatable("plantcard.tooltip.damage").append(": ")
                .append(Component.literal((stack.getMaxDamage()-stack.getDamageValue())+"/"+stack.getMaxDamage()).withStyle(style -> style.withColor(color))));

    }

    public static boolean canPutPlant(Level level, Player player, BlockPos pos){
        if(!level.getBlockState(pos.below()).is(BlockTags.DIRT)) return false;
        if(!level.getBlockState(pos).is(Blocks.AIR)) return false;
        if(!level.getEntities(player,new AABB(pos).inflate(-0.2f),e->e instanceof LivingEntity).isEmpty()) return false;
        return true;
    }

    public static <T extends AbstractPlant> Builder<T> builder(Supplier<EntityType<T>> entityType, int consume){
        return new Builder<>(entityType, consume);
    }

    public static class Builder<T extends AbstractPlant> {
        private final Properties properties = new Properties();
        private final Supplier<EntityType<T>> entityType;
        private final int consume;
        private int cd = 5;
        private int durability = 10;

        public Builder(Supplier<EntityType<T>> entityType, int consume){
            this.entityType = entityType;
            this.consume = consume;

//            properties.component(ModDataComponentTypes.CARD_QUALITY.get(), CardQualityComponentType.COPPER);

        }
        public Builder<T> cd(int cd){
            this.cd = cd;
            return this;
        }
//        public Builder<T> rarity(ModRarity rarity){
////            properties.component(ModDataComponentTypes.MOD_RARITY.get(), rarity);
//            return this;
//        }
        public Builder<T> durability(int durability){
            this.durability = durability;
            return this;
        }
        public AbstractCardItem<T> build(){
            return new AbstractCardItem<>(properties.durability(durability), entityType, consume).setCd(cd);
        }
    }


}