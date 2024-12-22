package rhymestudio.rhyme.core.item.tool;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.registry.ModSounds;

import static rhymestudio.rhyme.utils.Computer.getEyeTraceHitResult;

public class PlantShovel extends Item {
    public PlantShovel(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide){
            EntityHitResult result = getEyeTraceHitResult(player,5);
            if(result != null){
                AbstractPlant plant = (AbstractPlant) result.getEntity();
                plant.playSound(ModSounds.SHOVEL.get());

                plant.discard();
            }
        }
        return super.use(level, player, usedHand);
    }
}
