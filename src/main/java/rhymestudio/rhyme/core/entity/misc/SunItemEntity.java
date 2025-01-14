package rhymestudio.rhyme.core.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.core.registry.ModSounds;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.ModAttachments;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SunItemEntity extends ItemEntity implements GeoEntity {
    public int touchTick;
    private Player followingPlayer;
    public float cachedYaw;
    public float cachedYawO;

    public SunItemEntity(EntityType<SunItemEntity> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        this.lifespan = 20*20;
        this.touchTick = lifespan;
    }

    public SunItemEntity(Level level, Vec3 pos) {
        this(MiscEntities.SUN_ITEM_ENTITY.get(), level);
        setPos(pos);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide){
            float f = this.tickCount * 0.1f;
            this.cachedYawO = this.cachedYaw;
            this.cachedYaw = f;
            this.setYRot(cachedYaw);
        }
        BlockPos pos = this.blockPosition();
        if(this.tickCount < 80 ){
            float h = 0;
            while(pos.getY() > -60 && level().getBlockState(pos).isAir()){
                pos = pos.below();
                h++;
            }
            if( h > 2){
                this.setDeltaMovement(new Vec3(0,-Math.min(3,h/30f),0));
            }
        }

        if (this.tickCount % 20 == 1) {
            this.scanForEntities();
        }

        if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying() || this.followingPlayer.distanceToSqr(this) > 64.0)) {
            this.followingPlayer = null;
        }

        if (this.followingPlayer != null) {
            Vec3 vec3 = new Vec3(this.followingPlayer.getX() - this.getX(), this.followingPlayer.getY() + (double)this.followingPlayer.getEyeHeight() / 2.0 - this.getY(), this.followingPlayer.getZ() - this.getZ());
            double d0 = vec3.lengthSqr();
            if (d0 < 64.0) {
                double d1 = 1.0 - Math.sqrt(d0) / 8.0;
                vec3 = vec3.normalize().scale(d1 * d1 * 0.02);
                if(touchTick<tickCount){
                    this.setDeltaMovement(vec3.scale(10));
                }else
                    this.setDeltaMovement(this.getDeltaMovement().add(vec3));
            }
        }

        this.addDeltaMovement(new Vec3(0, Math.sin(tickCount/7f )* 0.005 ,0));
        Vec3 v = this.getDeltaMovement().scale(0.95);
        this.setDeltaMovement(v);

        if(tickCount-touchTick>10){
            this.discard();
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    private void scanForEntities() {
        if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0) {
            this.followingPlayer = level().getNearestPlayer(this, 16.0);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    public void playerTouch(Player entity) {
        if(this.tickCount<20)return;
        if (touchTick>tickCount) {
            if (entity.takeXpDelay == 0 && entity instanceof ServerPlayer serverplayer ) {
                var data = serverplayer.getData(ModAttachments.PLAYER_STORAGE);
                data.sunCount += this.getItem().getCount() * 25;
                data.sunCount = Math.min(data.sunCount, data.getMaxSunCount());
                data.sendSunCountUpdate(serverplayer);
                playSound(ModSounds.POINTS.get());
            }
            touchTick = tickCount;
        }

    }

    public static void summon(ServerLevel serverLevel) {
        if (!(serverLevel.dimension().equals(Level.OVERWORLD) && serverLevel.getDayTime() % 24000 > 12000 && serverLevel.getGameTime() % 600 == 0)) {
            RandomSource random = serverLevel.random;
            for (ServerPlayer serverPlayer : serverLevel.players()) {
                int offsetX = (random.nextBoolean() ? 1 : -1) * random.nextInt(20);
                int offsetZ = (random.nextBoolean() ? 1 : -1) * random.nextInt(20);
                BlockPos pos = serverPlayer.getOnPos().offset(offsetX, 0, offsetZ).atY(256);
                if (serverLevel.isLoaded(pos)) {
                    var entity = new SunItemEntity(serverLevel, pos.getCenter());
                    entity.setItem(new ItemStack(MaterialItems.SOLID_SUN.get()));
                    serverLevel.addFreshEntity(entity);
                }
            }
        }
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
