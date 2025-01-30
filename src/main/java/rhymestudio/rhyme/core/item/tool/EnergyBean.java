package rhymestudio.rhyme.core.item.tool;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeMod;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.utils.Computer;

public class EnergyBean extends CustomRarityItem {

    public EnergyBean(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide){
            EntityHitResult hit = Computer.getEyeTraceHitResult(player,player.getAttributeBaseValue(ForgeMod.ENTITY_REACH.get()));
            if(hit ==null) return super.use(level, player, usedHand);
            Entity e = hit.getEntity();
            if(e instanceof AbstractPlant plant && plant.haveUltimate() && !plant.isUltimating){
                plant.triggerUltimate();
                if(player.canBeSeenAsEnemy())
                    player.getItemInHand(usedHand).shrink(1);
            }
        }
        return super.use(level, player, usedHand);
    }

}
