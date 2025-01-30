package rhymestudio.rhyme.core.item.tool;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModSounds;

import static rhymestudio.rhyme.utils.Computer.getEyeTraceHitResult;

public class PlantShovel extends Item {
    public PlantShovel(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        EntityHitResult result = getEyeTraceHitResult(player,5);
        ItemStack itemStack = player.getItemInHand(usedHand);
        if(result != null){

            if(player.canBeSeenAsEnemy()){// 创造模式
                itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            }
            Entity plant = result.getEntity();
            doOnDetect(plant,level,player,itemStack);
        }else{

            doOnNotDetect(level,player,itemStack);
        }
        player.getCapability(ModAttachments.PLAYER_STORAGE).ifPresent(cap->{
            cap.consumeSun(player,2000);
        });
        return super.use(level, player, usedHand);
    }

    protected void doOnDetect(Entity entity, Level level, Player player, ItemStack itemStack){
        player.playSound(ModSounds.SHOVEL.get());
        entity.discard();
    }

    protected void doOnNotDetect(Level level, Player player, ItemStack itemStack){

    }
}
