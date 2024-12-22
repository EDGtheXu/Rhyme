package rhymestudio.rhyme.core.item;

import com.google.common.collect.HashMultimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.ModSounds;

import java.util.List;

import static rhymestudio.rhyme.utils.Computer.playSound;

public class AbstractCardItem<T extends AbstractPlant> extends CustomRarityItem {
    public DeferredHolder<EntityType<?>, EntityType<T>> entityType;

    public int consume;
    public AbstractCardItem(Properties properties, DeferredHolder<EntityType<?>, EntityType<T>> entityType, int consume){
        super(properties);
        this.entityType = entityType;
        this.consume = consume;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

//        if (!level.isClientSide()) {

            ItemStack itemstack = player.getItemInHand(hand);

            if(!player.canBeSeenAsEnemy()){ // 创造
                summon(player, level, itemstack);
                return InteractionResultHolder.success(itemstack);
            }
//            if(!Computer.tryCombineInventoryItem(player, MaterialItems.SUN_ITEM.get(), consume)){
//                return InteractionResultHolder.fail(itemstack);
//            }
            var flag = player.getData(ModAttachments.PLAYER_STORAGE).consumeSun(consume);
            if(!flag) return InteractionResultHolder.fail(itemstack);
            if(!summon(player, level, itemstack)) return InteractionResultHolder.fail(itemstack);
            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            if(itemstack.getDamageValue() >= itemstack.getMaxDamage())
                itemstack.shrink(1);
            return InteractionResultHolder.success(itemstack);
//        }
//        return super.use(level, player, hand);
    }

    public boolean summon(Player player, Level level,ItemStack stack){
        final BlockHitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        final BlockHitResult raytraceResult = result.withPosition(result.getBlockPos().above());
        final BlockPos pos = raytraceResult.getBlockPos();

        if(!level.getBlockState(pos.below()).is(BlockTags.DIRT)) return false;
        if(!level.getBlockState(pos).is(BlockTags.AIR)) return false;
        if(!level.getEntities(player,new AABB(pos).inflate(-0.2f)).isEmpty()) return false;

        var entity = entityType.get().create(level);
        entity.setOwner(player);
        entity.setPos(new Vec3(pos.getX() + 0.5+player.getRandom().nextFloat()*0.1f, pos.getY(), pos.getZ() + 0.5+player.getRandom().nextFloat()*0.1f));
        int lvl = stack.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get()).level();
        HashMultimap<Holder<Attribute>, AttributeModifier> hashmultimap = HashMultimap.create();
        entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(Rhyme.space("card_health_modifier"),0.5f*lvl,AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        entity.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier(Rhyme.space("card_attack_damage_modifier"),0.5f*lvl,AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        level.addFreshEntity(entity);
        entity.playSound(ModSounds.PLANT.get());
        entity.setHealth(entity.getMaxHealth());
//        CardQualityComponent.tryUpLevel(stack);
        return true;
    }



    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var quality = stack.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        tooltipComponents.add(Component.translatable("plantcard.tooltip.card_quality").append(": ")
                .append(Component.translatable("plantcard.tooltip.card_quality."+BuiltInRegistries.ITEM.getKey(quality.getQualityItem()).getPath().split("/")[1]).withColor(quality.color())));

        tooltipComponents.add(Component.translatable("plantcard.tooltip.consumed_sun").append(": "+this.consume).withColor(0xffff00));
    }

}