package rhymestudio.rhyme.core.entity;


import net.minecraft.core.particles.*;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.particle.options.BrokenProjOptions;
import rhymestudio.rhyme.core.registry.ModEffects;
import rhymestudio.rhyme.datagen.tag.ModTags;
import rhymestudio.rhyme.network.s2c.ProjHitPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public abstract class BaseProj extends AbstractHurtingProjectile{
    private final long starttime = System.currentTimeMillis();
    public float damage;
    private List<Integer> hitList = new ArrayList<>();
    public int penetration =1;
    protected MobEffectInstance effect;
    public ResourceLocation texture;
    protected DeferredHolder<SoundEvent,SoundEvent> hitSound;
    public Consumer<BaseProj> clientTickCallback;

    public BaseProj(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel, MobEffectInstance pEffect) {
        super(pEntityType, pLevel);
        this.effect = pEffect;
    }
    public <T extends BaseProj> T setHitSound(DeferredHolder<SoundEvent,SoundEvent> hitSound){
        this.hitSound = hitSound;
        return (T) this;
    }

    public float getDamage() {return damage;}
    public void addDamage(int damage) {this.damage += damage;}
    public <T extends BaseProj> T setPenetrate(int penetration){
        this.penetration = penetration;
        return (T) this;
    }
    public <T extends BaseProj> T setClientTickCallback(Consumer<BaseProj> clientTickCallback){
        this.clientTickCallback = clientTickCallback;
        return (T) this;
    }
    public void setTexture(ResourceLocation texture){this.texture = texture;}
    public ResourceLocation getTexture(){return texture;}
    public abstract int waveDur();
    public boolean shouldBeSaved(){
        return false;
    }
    public void doAABBHurt(){
        //包围盒检测造成伤害
        var entities=level().getEntities(this,this.getBoundingBox());
        if(!entities.isEmpty() && penetration > 0){
            for (var e:entities) {
                int id = e.getId();
                if(canHitEntity(e) && !hitList.contains(id)) {
                    hitList.add(id);
                    if(e instanceof LivingEntity living) {
                        doHurt(living);
                        //doKnockBack(living);

                    }
                }
            }
        }
    }

    protected void doKnockBack(LivingEntity entity) {
        double d1 = Math.max(0.0, 1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(((LivingEntity)getOwner()).getAttributeBaseValue(Attributes.ATTACK_KNOCKBACK) * 0.6 * d1);
        if (vec3.lengthSqr() > 0.0) {
            entity.push(vec3.x, 0.1, vec3.z);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }


    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide){
            if(System.currentTimeMillis()-starttime > waveDur() * 50L) {
                discard();
                return;
            }
            doAABBHurt();
        }else if(clientTickCallback!= null){
            clientTickCallback.accept(this);
        }

    }

    //弹幕设置
    @Override//取消射击惯性
    public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * (float) (Math.PI / 180.0)) * Mth.cos(pX * (float) (Math.PI / 180.0));
        float f1 = -Mth.sin((pX + pZ) * (float) (Math.PI / 180.0));
        float f2 = Mth.cos(pY * (float) (Math.PI / 180.0)) * Mth.cos(pX * (float) (Math.PI / 180.0));
        this.shoot(f, f1, f2, pVelocity, pInaccuracy);
    }
    @Override
    public void onAddedToLevel(){
        super.onAddedToLevel();
        if(!level().isClientSide()){
            if(getOwner()==null){
                discard();
                return;
            }
            this.damage = (int) ((LivingEntity)getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        }
    }
    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        Entity hurter = pResult.getEntity();
        if(!hitList.contains(hurter.getId()) && hurter instanceof LivingEntity living && canHitEntity(living))
            doHurt(living);
        super.onHitEntity(pResult);
    }

    protected void doHurt(LivingEntity hurter){
        Entity entity = this.getOwner();
        if(effect!= null && hurter != entity){
            if(effect.getEffect().is(ModEffects.SLOWDOWN_EFFECT.getId())){
                PacketDistributor.sendToAllPlayers(new ProjHitPacket(hurter.getId(),effect.getDuration()));
            }
            hurter.addEffect(effect);
        }
        if(hitSound != null)
            level().playSound(this,this.blockPosition(), hitSound.get(), SoundSource.AMBIENT, 1.0f, 1.0f);
        if(entity!= null)
            hurter.hurt(entity.damageSources().source(ModTags.DamageTypes.PLANT_PROJ), getDamage());
        else if(hurter!= null)
            hurter.hurt(this.damageSources().source(ModTags.DamageTypes.PLANT_PROJ), getDamage());
        Vec3 pos = hurter.position();

        if(this.level() instanceof ServerLevel serverlevel){
            if(this.texture != null)
                serverlevel.sendParticles(new BrokenProjOptions(this.texture.getPath()),
                        pos.x,
                        pos.y+1,
                        pos.z,
                        20, 0.2, 0, 0.2, 0.1F);
            penetration--;
            if(penetration <= 0) {
                discard();
            }
        }
    }


    @Nullable
    @Override//设置粒子效果
    protected ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity target) {

        return target.canBeHitByProjectile() &&
                target != getOwner() &&
                !(target instanceof AbstractPlant) &&
                !(target instanceof Player);
    }

    @Override//流体阻力
    protected float getLiquidInertia() {
        return 1;
    }

    @Override//火焰效果
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override//空气阻力
    protected float getInertia() {
        return 1;
    }
    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if(!this.level().isClientSide()) discard();
    }


    public static class TextureLib{
        public static final ResourceLocation PEA = Rhyme.space("textures/entity/pea_bullet.png");
        public static final ResourceLocation SNOW_PEA = Rhyme.space("textures/entity/snow_pea_bullet.png");
        public static final ResourceLocation PUFF_SHROOM_BULLET = Rhyme.space("textures/entity/puff_shroom_bullet.png");
        public static final ResourceLocation FIRE_PEA = Rhyme.space("textures/entity/fire_pea_bullet.png");
        public static final ResourceLocation EMPTY = null;

    }

}
