package rhymestudio.rhyme.core.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.core.dataSaver.dataComponent.EntitySaverComponent;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.ModSounds;
import rhymestudio.rhyme.utils.Computer;

import java.util.List;

import static rhymestudio.rhyme.core.item.AbstractCardItem.canPutPlant;

public class PlantPutter extends PlantShovel {
    public PlantPutter(Properties properties) {
        super(properties);
    }

    @Override
    protected void doOnDetect(Entity entity, Level level, Player player, ItemStack itemStack){

        var data = itemStack.get(ModDataComponentTypes.ITEM_ENTITY_TAG);
        if(data == null){

            CompoundTag tag = new CompoundTag();
            entity.save(tag);
            itemStack.set(ModDataComponentTypes.ITEM_ENTITY_TAG.get(), new EntitySaverComponent(tag, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType())));
            entity.discard();
            player.playSound(ModSounds.SHOVEL.get());
            if(player.canBeSeenAsEnemy())
                player.getCooldowns().addCooldown(this, 20 * 15);
        }

//        player.playSound(ModSounds.SHOVEL.get());


    }
    @Override
    protected void doOnNotDetect(Level level, Player player, ItemStack itemStack){

        var data = itemStack.get(ModDataComponentTypes.ITEM_ENTITY_TAG);
        if(data != null){
            final BlockPos pos = Computer.getEyeBlockHitResult(player);
            if(!canPutPlant(level,player,pos)) {
                if(!level.isClientSide)
                    player.sendSystemMessage(Component.translatable("plantcard.cannot_put_plant").withColor(0xff0000));
                return;
            }

            CompoundTag tag = data.tag();
            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(data.type());
            Entity e =  type.create(level);

            e.load(tag);

            e.setPos(Computer.getBlockPosCenter(pos,player.getRandom()));
            level.addFreshEntity(e);
            itemStack.remove(ModDataComponentTypes.ITEM_ENTITY_TAG.get());
            player.playSound(ModSounds.PLANT.get());
            if(player.canBeSeenAsEnemy())
                player.getCooldowns().addCooldown(this, 20 * 15);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        var data = stack.get(ModDataComponentTypes.ITEM_ENTITY_TAG);
        if(data != null){
            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(data.type());
            CompoundTag tag = data.tag();
            int lvl = tag.getInt("cardLevel");
            tooltipComponents.add(Component.translatable("tooltip.rhyme.plant_putter.entity_picked")
                    .append(Component.translatable(type.getDescriptionId()))
            );

            var quality = CardQualityComponent.of(lvl);
            tooltipComponents.add(Component.translatable("plantcard.tooltip.card_quality").append(": ")
                    .append(Component.translatable("plantcard.tooltip.card_quality."+BuiltInRegistries.ITEM.getKey(quality.getQualityItem()).getPath().split("/")[1]).withColor(quality.color())));

        }
    }
}
