package rhymestudio.rhyme.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import rhymestudio.rhyme.entity.AbstractPlant;
import rhymestudio.rhyme.network.s2c.SunCountPacketS2C;
import rhymestudio.rhyme.registry.ModAttachments;

@EventBusSubscriber(modid = "rhyme")
public class EntityEvent {

    @SubscribeEvent
    public static void onEntityJoinLevel(FinalizeSpawnEvent event) {
        if(event.getEntity() instanceof Monster monster ){
            if(monster instanceof NeutralMob) return;
            monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AbstractPlant.class, false));
        }else if(event.getEntity() instanceof Slime slime){

            slime.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(slime,  AbstractPlant.class, true));
        }
    }
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof ServerPlayer player){
            PacketDistributor.sendToPlayer(player, new SunCountPacketS2C(player.getData(ModAttachments.PLAYER_STORAGE).sunCount));
        }

    }
}
