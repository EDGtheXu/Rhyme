package rhymestudio.rhyme.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import rhymestudio.rhyme.datagen.tag.ModTags;
import rhymestudio.rhyme.mixinauxiliary.ILivingEntity;
import rhymestudio.rhyme.network.s2c.SunCountPacketS2C;
import rhymestudio.rhyme.core.registry.ModAttachments;

@EventBusSubscriber(modid = "rhyme",bus = EventBusSubscriber.Bus.GAME)
public class EntityEvent {

    @SubscribeEvent
    public static void onEntitySpawn(FinalizeSpawnEvent event) {
        if (event.getEntity() instanceof Monster monster) {
            if (monster instanceof NeutralMob) return;
            monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AbstractPlant.class, false));
        } else if (event.getEntity() instanceof Slime slime) {

            slime.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(slime, AbstractPlant.class, true));
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new SunCountPacketS2C(player.getData(ModAttachments.PLAYER_STORAGE).sunCount));
        }


    }

    public static void onLivingHurt(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Monster monster) {
            if(event.getSource().is(DamageTypeTags.NO_KNOCKBACK))
                event.getEntity().hurtTime = 2;
        }
    }

    @SubscribeEvent
    public static void onLevelTickEvent(LevelTickEvent.Pre event){
        Level level = event.getLevel();
        if(level instanceof ServerLevel serverLevel){
            float f = event.getLevel().getDayTime();
            if(level.isDay() && f % (20 * 15) == 0){
                SunItemEntity.summon(serverLevel);
            }
        }
    }

    @SubscribeEvent
    public static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        DamageSource damageSource = event.getSource();

        if(damageSource.is(ModTags.DamageTypes.PLANT_PROJ) || damageSource.is(ModTags.DamageTypes.PLANT_EXPLORE)){
            event.getContainer().setPostAttackInvulnerabilityTicks(0);
        }
    }
}