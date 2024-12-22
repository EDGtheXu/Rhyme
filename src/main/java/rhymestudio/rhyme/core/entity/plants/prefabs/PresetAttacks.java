package rhymestudio.rhyme.core.entity.plants.prefabs;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.BaseProj;
import rhymestudio.rhyme.core.entity.proj.LineProj;
import rhymestudio.rhyme.core.entity.proj.ThrowableProj;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class PresetAttacks {
    public final BiConsumer<AbstractPlant, LivingEntity> attack;
    public DeferredHolder<SoundEvent, SoundEvent> sound;
    public int shootCount = 1;

    public PresetAttacks(BiConsumer<AbstractPlant, LivingEntity> attack, DeferredHolder<SoundEvent, SoundEvent> sound, int shootCount) {
        this.attack = attack;
        this.sound = sound;
        this.shootCount = shootCount;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private BiConsumer<AbstractPlant, LivingEntity> attack;
        private DeferredHolder<SoundEvent, SoundEvent> sound;
        private int shootCount = 1;
        public Builder setShootCount(int shootCount) {
            this.shootCount = shootCount;
            return this;
        }
        public Builder setAttack(BiConsumer<AbstractPlant, LivingEntity> attack) {
            this.attack = attack;
            return this;
        }
        public Builder setSound(DeferredHolder<SoundEvent, SoundEvent> sound) {
            this.sound = sound;
            return this;
        }
        public PresetAttacks build() {
            return new PresetAttacks(attack, sound, shootCount);
        }
    }
    /**
     * 直线弹幕
     */
    private static final QuaConsumer<AbstractPlant, LivingEntity, DeferredHolder<EntityType<?>, EntityType<LineProj>>, Float> PEA_SHOOT_ATTACK_BASE = (me, tar, proj, offsetY) -> {
        Vec3 pos = tar.position().add(0,tar.getEyeHeight()/2,0);
        BaseProj proj1 = proj.get().create(me.level());
//        BaseProj proj1 = new LineProj(PlantEntities.ICE_PEA_PROJ.get(), me.level(),BaseProj.TextureLib.SNOW_PEA,new MobEffectInstance(ModEffects.FROZEN_EFFECT,20 * 5));
        proj1.setOwner(me);
        proj1.setPos(me.getEyePosition().add(0,offsetY,0));
        Vec3 dir = pos.subtract(me.getEyePosition());
        proj1.shoot(dir.x, dir.y, dir.z, me.builder.projSpeed, 1.0F);
        me.level().addFreshEntity(proj1);
    };

    //普通豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.PEA_PROJ, 0.1f);
    };
    //冰豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> SNOW_PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.ICE_PEA_PROJ, 0.1f);
    };
    //双豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> DOUBLE_PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT.accept(me, tar);

    };

    //小喷菇
    public static final BiConsumer<AbstractPlant, LivingEntity> SPORE_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.PUFF_SHROOM_PROJ, -0.45f);
    };

    /**
     * 投掷物弹幕
     */
    private static final  QuaConsumer<AbstractPlant, LivingEntity, DeferredHolder<EntityType<?>, EntityType<ThrowableProj>>, Float> THROWN_SHOOT = (me, tar, proj, offsetY) -> {
        Vec3 dir = tar.getDeltaMovement().normalize().scale(2.5f);
        Vec3 pos = tar.position().add(0,tar.getEyeHeight(),0).add(dir);

        ThrowableProj proj1 = proj.get().create(me.level()).setTargetPos(pos);
        proj1.setOwner(me);
        proj1.setPos(me.getEyePosition().add(0,offsetY,0));
        me.level().addFreshEntity(proj1);
    };

    //卷心菜
    public static final BiConsumer<AbstractPlant, LivingEntity> THROWN_PEA_SHOOT = (me, tar) -> {
        THROWN_SHOOT.accept(me, tar, MiscEntities.CABBAGE_PROJ, 1f);
    };



/**************************************************************************************************************************************/
    @FunctionalInterface
    public interface QuaConsumer<A,B,C,D>{
        void accept(A a, B b, C c, D d);
    }
    @FunctionalInterface
    public interface FifConsumer<A,B,C,D,E>{
        void accept(A a, B b, C c, D d, E e);
    }

}
