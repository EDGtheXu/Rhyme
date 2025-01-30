package rhymestudio.rhyme.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.CrazyDave;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;

import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.datagen.tag.ModTags;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.mixinauxiliary.IPlayer;
import rhymestudio.rhyme.network.NetworkHandler;
import rhymestudio.rhyme.network.s2c.PlantRecorderPacket;

import static rhymestudio.rhyme.Rhyme.MODID;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvent {

    // 怪物生成添加攻击植物goal
    @SubscribeEvent
    public static void onEntitySpawn(MobSpawnEvent event) {
        if (event.getEntity() instanceof Monster monster) {
            if (monster instanceof NeutralMob) return;
            monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AbstractPlant.class, false));
        }
    }

    // 玩家加入世界同步数据
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(ModAttachments.PLAYER_STORAGE).ifPresent(d->d.sendSunCountUpdate(player));
            player.getCapability(ModAttachments.PLANT_RECORDER_STORAGE).ifPresent(d->
                    NetworkHandler.CHANNEL.sendTo(new PlantRecorderPacket(d.ids), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT)
            );
        }
    }

    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Monster monster) {
            if(event.getSource().is(ModTags.DamageTypes.PLANT_PROJ) || event.getSource().is(ModTags.DamageTypes.PLANT_EXPLORE))
                event.getEntity().hurtTime = 2;
        }
    }

    // 自然生成阳光
    @SubscribeEvent
    public static void onLevelTickEvent(TickEvent.LevelTickEvent event){
        Level level = event.level;
        if(level instanceof ServerLevel serverLevel){
            float f = event.level.getDayTime();
            if(level.isDay() && f % (20 * 15) == 0){
                SunItemEntity.summon(serverLevel);
            }
        }
    }

    @SubscribeEvent
    public static void livingIncomingDamage(LivingDamageEvent event) {
        DamageSource damageSource = event.getSource();

        if(damageSource.is(ModTags.DamageTypes.PLANT_PROJ) || damageSource.is(ModTags.DamageTypes.PLANT_EXPLORE)){
            event.getEntity().invulnerableTime = 0;
            // todo
//            event.getContainer().setPostAttackInvulnerabilityTicks(0);
        }
    }

    // 死亡掉落金币
    @SubscribeEvent
    public static void livingDead(LivingDeathEvent event) {
        if(!event.getEntity().level().isClientSide && event.getEntity() instanceof Monster){
            ItemStack stack = MaterialItems.SILVER_COIN.get().getDefaultInstance();
            if(stack.getItem() == MaterialItems.SILVER_COIN.get())
                stack.getOrCreateTag().putInt("money", 5);
            else if(stack.getItem() == MaterialItems.GOLD_COIN.get())
                stack.getOrCreateTag().putInt("money", 10);
            ItemEntity ite = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
            event.getEntity().level().addFreshEntity(ite);

        }
    }

    // 玩家捡到金币，消失并同步数据
    @SubscribeEvent
    public static void pickupItem(PlayerEvent.ItemPickupEvent event) {
        if(event.getEntity() instanceof ServerPlayer sp){
            ItemStack stack = event.getStack();
            if(
                    (stack.is(MaterialItems.SILVER_COIN.get())
                        || stack.is(MaterialItems.GOLD_COIN.get()))
            ){
                CompoundTag tag = stack.getTag();
                int m;
                if(tag!=null && tag.contains("money")) {
                    m = tag.getInt("money");
                    sp.getCapability(ModAttachments.PLAYER_STORAGE).ifPresent(d -> {
                        d.moneys += m * stack.getCount();
                        d.sendSunCountUpdate(sp);
                        stack.setCount(0);
                    });
                }
                // todo 不能删除
                event.getOriginalEntity().discard();
                stack.setCount(0);

            }
        }
    }

    // 打开戴夫商店
    @SubscribeEvent
    public static void interactEntity(PlayerInteractEvent.EntityInteract event) {
        if(event.getTarget() instanceof CrazyDave dave) {
//            ((IPlayer) event.getEntity()).rhyme$setDaveTrades(dave.daveTrades);
            ((IPlayer) event.getEntity()).rhyme$setInteractingEntity(dave);
        }
    }

}