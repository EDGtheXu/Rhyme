package rhymestudio.rhyme.core.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.registries.DeferredHolder;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.ModSounds;

import java.util.List;

import static rhymestudio.rhyme.config.ServerConfig.PlantConsumeAdditionStep;
import static rhymestudio.rhyme.utils.Computer.getBlockPosCenter;
import static rhymestudio.rhyme.utils.Computer.getEyeBlockHitResult;

public class AbstractCardItem<T extends AbstractPlant> extends CustomRarityItem {
    public DeferredHolder<EntityType<?>, EntityType<T>> entityType;

    public int consume;
    public int cd = 5*20;
    public AbstractCardItem(Properties properties, DeferredHolder<EntityType<?>, EntityType<T>> entityType, int consume){
        super(properties);
        this.entityType = entityType;
        this.consume = consume;
    }

    public AbstractCardItem<T> setCd(int cd){
        this.cd = cd*20;
        return this;
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
        var data =player.getData(ModAttachments.PLANT_RECORDER_STORAGE);
        int consumeCount = data.ids.size() * PlantConsumeAdditionStep.get() + this.consume;
        var flag = player.getData(ModAttachments.PLAYER_STORAGE).consumeSun(player,consumeCount);
        if(!flag) {
            if(!level.isClientSide)
                player.sendSystemMessage(Component.translatable("plantcard.not_enough_sun").withColor(0xff0000));
            return InteractionResultHolder.fail(itemstack);
        }
        if(!summon(player, level, itemstack)) return InteractionResultHolder.fail(itemstack);
        if(itemstack.get(DataComponents.UNBREAKABLE)==null){
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
                player.sendSystemMessage(Component.translatable("plantcard.cannot_put_plant").withColor(0xff0000));
            return false;
        }

        var entity = entityType.get().create(level);
        entity.setOwner(player);
        entity.setPos(getBlockPosCenter(pos,player.getRandom()));
        int lvl = stack.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get()).level();
        entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(Rhyme.space("card_health_modifier"),0.5f*lvl,AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        entity.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier(Rhyme.space("card_attack_damage_modifier"),0.5f*lvl,AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        entity.setCardLevel(lvl);
        level.addFreshEntity(entity);
        entity.playSound(ModSounds.PLANT.get());
        entity.setHealth(entity.getMaxHealth());
        var plantData = player.getData(ModAttachments.PLANT_RECORDER_STORAGE);
        plantData.ids.add(entity.getId());
        if(player.canBeSeenAsEnemy())
            player.getCooldowns().addCooldown(stack.getItem(), this.cd);
        if(!level.isClientSide){
            var data = stack.get(ModDataComponentTypes.CARD_QUALITY.get());
            player.sendSystemMessage(Component.translatable("plantcard.summon_success").append(Component.translatable("entity.rhyme."+ entityType.getId().getPath()).withColor(data.color())));
        }
        return true;
    }



    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var quality = stack.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        tooltipComponents.add(Component.translatable("plantcard.tooltip.card_quality").append(": ")
                .append(Component.translatable("plantcard.tooltip.card_quality."+BuiltInRegistries.ITEM.getKey(quality.getQualityItem()).getPath().split("/")[1]).withColor(quality.color())));


        var data = Minecraft.getInstance().player.getData(ModAttachments.PLANT_RECORDER_STORAGE);
        int consumeCount = data.ids.size() * PlantConsumeAdditionStep.get();
        tooltipComponents.add(Component.translatable("plantcard.tooltip.consumed_sun").append(": "+this.consume + " + "+consumeCount).withColor(0xffff00));
        float percent = (float)stack.getDamageValue()/(float)stack.getMaxDamage();
        int color = (int)((1-percent)*0x0000ff)<<8 | (int)(percent*0x0000ff)<<16;
        if(stack.get(DataComponents.UNBREAKABLE)==null)
            tooltipComponents.add(Component.translatable("plantcard.tooltip.damage").append(": ")
                .append(Component.literal((stack.getMaxDamage()-stack.getDamageValue())+"/"+stack.getMaxDamage()).withColor(color)));

    }

    public static boolean canPutPlant(Level level, Player player, BlockPos pos){
        if(!level.getBlockState(pos.below()).is(BlockTags.DIRT)) return false;
        if(!level.getBlockState(pos).is(BlockTags.AIR)) return false;
        if(!level.getEntities(player,new AABB(pos).inflate(-0.2f),e->e instanceof LivingEntity).isEmpty()) return false;
        return true;
    }

    public static <T extends AbstractPlant> Builder<T> builder(DeferredHolder<EntityType<?>, EntityType<T>> entityType, int consume){
        return new Builder<>(entityType, consume);
    }

    public static class Builder<T extends AbstractPlant> {
        private final Properties properties = new Properties();
        private final DeferredHolder<EntityType<?>, EntityType<T>> entityType;
        private final int consume;
        private int cd = 5;
        private int durability = 10;

        public Builder(DeferredHolder<EntityType<?>, EntityType<T>> entityType, int consume){
            this.entityType = entityType;
            this.consume = consume;
            properties.component(ModDataComponentTypes.CARD_QUALITY.get(), CardQualityComponent.COPPER);
        }
        public Builder<T> cd(int cd){
            this.cd = cd;
            return this;
        }
        public Builder<T> rarity(ModRarity rarity){
            properties.component(ModDataComponentTypes.MOD_RARITY.get(), rarity);
            return this;
        }
        public Builder<T> durability(int durability){
            this.durability = durability;
            return this;
        }
        public AbstractCardItem<T> build(){
            return new AbstractCardItem<>(properties.durability(durability), entityType, consume).setCd(cd);
        }
    }


}