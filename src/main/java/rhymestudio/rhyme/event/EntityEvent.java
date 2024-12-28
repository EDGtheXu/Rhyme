package rhymestudio.rhyme.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.datagen.tag.ModTags;
import rhymestudio.rhyme.core.registry.ModAttachments;

import static rhymestudio.rhyme.Rhyme.MODID;

@EventBusSubscriber(modid = MODID,bus = EventBusSubscriber.Bus.GAME)
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
            var data = player.getData(ModAttachments.PLAYER_STORAGE);
            data.sendSunCountUpdate(player);
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

    @SubscribeEvent
    public static void livingDead(LivingDeathEvent event) {
        if(!event.getEntity().level().isClientSide){
            ItemEntity ite = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), MaterialItems.SILVER_COIN.toStack());
            event.getEntity().level().addFreshEntity(ite);


        }
    }

    @SubscribeEvent
    public static void pickupItem(ItemEntityPickupEvent.Pre event) {
        if(event.getPlayer() instanceof ServerPlayer sp){
            var originalStack = event.getItemEntity().getItem();
            if(originalStack.is(MaterialItems.SILVER_COIN)
                || originalStack.is(MaterialItems.GOLD_COIN)
            ){
                event.getPlayer().getData(ModAttachments.PLAYER_STORAGE).moneys +=
                        originalStack.getCount() * (originalStack.is(MaterialItems.GOLD_COIN)? 10 : 5);

                var data = sp.getData(ModAttachments.PLAYER_STORAGE);
                data.sendSunCountUpdate(sp);

                originalStack.setCount(0);
            }
        }


    }
}