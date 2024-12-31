package rhymestudio.rhyme.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import rhymestudio.rhyme.core.dataSaver.attactment.PlantRecorderAttachment;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.CrazyDave;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.datagen.tag.ModTags;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.mixinauxiliary.IPlayer;
import rhymestudio.rhyme.network.s2c.PlantRecorderPacket;

import static rhymestudio.rhyme.Rhyme.MODID;

@EventBusSubscriber(modid = MODID,bus = EventBusSubscriber.Bus.GAME)
public class EntityEvent {

    // 怪物生成添加攻击植物goal
    @SubscribeEvent
    public static void onEntitySpawn(FinalizeSpawnEvent event) {
        if (event.getEntity() instanceof Monster monster) {
            if (monster instanceof NeutralMob) return;
            monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AbstractPlant.class, false));
        }
    }

    // 玩家加入世界同步数据
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_STORAGE);
            data.sendSunCountUpdate(player);
            PlantRecorderAttachment plantRecorder = player.getData(ModAttachments.PLANT_RECORDER_STORAGE);
            PacketDistributor.sendToPlayer(player,new PlantRecorderPacket(plantRecorder.ids));
        }


    }

    public static void onLivingHurt(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Monster monster) {
            if(event.getSource().is(DamageTypeTags.NO_KNOCKBACK))
                event.getEntity().hurtTime = 2;
        }
    }

    // 自然生成阳光
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

    // 死亡掉落金币
    @SubscribeEvent
    public static void livingDead(LivingDeathEvent event) {
        if(!event.getEntity().level().isClientSide && event.getEntity() instanceof Monster){
            ItemEntity ite = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), MaterialItems.SILVER_COIN.toStack());
            event.getEntity().level().addFreshEntity(ite);


        }
    }

    // 玩家捡到金币，消失并同步数据
    @SubscribeEvent
    public static void pickupItem(ItemEntityPickupEvent.Pre event) {
        if(event.getPlayer() instanceof ServerPlayer sp){
            var originalStack = event.getItemEntity().getItem();

            if(!event.getItemEntity().hasPickUpDelay() &&
                    (originalStack.is(MaterialItems.SILVER_COIN)
                        || originalStack.is(MaterialItems.GOLD_COIN))
            ){
                var coin_data = originalStack.getComponents().get(ModDataComponentTypes.ITEM_DAT_MAP.get());
                if(coin_data!= null){
                    int coin_capacity = coin_data.getInt("money","value");

                    var data = sp.getData(ModAttachments.PLAYER_STORAGE);
                    data.moneys += coin_capacity * originalStack.getCount();
                    data.sendSunCountUpdate(sp);

                    originalStack.setCount(0);
                }

            }
        }
    }

    // 打开戴夫商店
    @SubscribeEvent
    public static void interactEntity(PlayerInteractEvent.EntityInteract event) {
        if(event.getTarget() instanceof CrazyDave dave) {
            ((IPlayer) event.getEntity()).rhyme$setDaveTrades(dave.daveTrades);
            ((IPlayer) event.getEntity()).rhyme$setInteractingEntity(dave);
        }
    }
}