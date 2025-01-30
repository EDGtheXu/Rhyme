package rhymestudio.rhyme.core.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponentType;
import rhymestudio.rhyme.core.dataSaver.dataComponent.EntitySaverComponentType;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ItemDataMapComponent;
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

        var data = new ItemDataMapComponent(itemStack);
        if(data.isValid()){

            CompoundTag tag = new CompoundTag();
            entity.save(tag);
            new EntitySaverComponentType(tag, ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())).writeToNBT(tag);
            entity.discard();
            player.playSound(ModSounds.SHOVEL.get());
            if(player.canBeSeenAsEnemy())
                player.getCooldowns().addCooldown(this, 20 * 15);
        }

        player.playSound(ModSounds.SHOVEL.get());


    }
    @Override
    protected void doOnNotDetect(Level level, Player player, ItemStack itemStack){
        var data = new EntitySaverComponentType(itemStack);
        if(data.isValid()){
            final BlockPos pos = Computer.getEyeBlockHitResult(player);
            if(!canPutPlant(level,player,pos)) {
                if(!level.isClientSide)
                    player.sendSystemMessage(Component.translatable("plantcard.cannot_put_plant").withStyle(Style.EMPTY.withColor(0xff0000)));
                return;
            }

            CompoundTag tag = data.tag;
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(data.type);
            Entity e;
            if (type != null) {
                e = type.create(level);
            }else return;

            if (e != null) {
                e.load(tag);
            }else return;

            e.setPos(Computer.getBlockPosCenter(pos,player.getRandom()));
            level.addFreshEntity(e);
            if (itemStack.getTag() != null) {
                itemStack.getTag().remove(data.name());
            }
            player.playSound(ModSounds.PLANT.get());
            if(player.canBeSeenAsEnemy())
                player.getCooldowns().addCooldown(this, 20 * 15);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        var data = new EntitySaverComponentType(stack);
        if(data.isValid()){
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(data.type);
            CompoundTag tag = data.tag;
            int lvl = tag.getInt("cardLevel");
            tooltipComponents.add(Component.translatable("tooltip.rhyme.plant_putter.entity_picked")
                    .append(Component.translatable(type.getDescriptionId()))
            );

            var quality = CardQualityComponentType.of(lvl);
            var r = ForgeRegistries.ITEMS.getKey(quality.getQualityItem());
            if(r != null)
                tooltipComponents.add(Component.translatable("plantcard.tooltip.card_quality").append(": ")
                        .append(Component.translatable("plantcard.tooltip.card_quality."+ r.getPath().split("/")[1]).withStyle(Style.EMPTY.withColor(quality.color))));

        }
    }
}
