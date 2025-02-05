package rhymestudio.rhyme.core.entity.plants.prefabs;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.BaseProj;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import rhymestudio.rhyme.core.entity.proj.LineProj;
import rhymestudio.rhyme.core.entity.proj.ThrowableProj;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;
import rhymestudio.rhyme.core.registry.items.MaterialItems;

import java.util.function.BiConsumer;
import java.util.function.Function;


/**
 * 弹幕预设，调整发射初始位置、弹幕类型
 */
public class PresetAttacks {
    public DeferredHolder<SoundEvent, SoundEvent> sound;
    public Function<AbstractPlant,Integer> shootCountSetter;
    public Function<AbstractPlant,BiConsumer<AbstractPlant, LivingEntity>> attackSetter;


    public BiConsumer<AbstractPlant, LivingEntity> getAttack(AbstractPlant plant){
        return attackSetter.apply(plant);
    }
    public int getShootCount(AbstractPlant plant){
        if(shootCountSetter!=null) return shootCountSetter.apply(plant);
        return 1;
    }

    public PresetAttacks(Function<AbstractPlant,BiConsumer<AbstractPlant, LivingEntity>> attackGetter, DeferredHolder<SoundEvent, SoundEvent> sound, Function<AbstractPlant,Integer> shootCountSetter) {
        this.attackSetter = attackGetter;
        this.sound = sound;
        this.shootCountSetter = shootCountSetter;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Function<AbstractPlant,BiConsumer<AbstractPlant, LivingEntity>> attack;
        private DeferredHolder<SoundEvent, SoundEvent> sound;
        private Function<AbstractPlant,Integer> shootCountSetter;
        public Builder setShootCount(int shootCount) {
            this.shootCountSetter = (p) -> shootCount;
            return this;
        }
        public Builder setShootCount(Function<AbstractPlant,Integer> shootCount) {
            this.shootCountSetter = shootCount;
            return this;
        }
        public Builder setAttack(BiConsumer<AbstractPlant, LivingEntity> attack) {
            this.attack = (p)->attack;
            return this;
        }
        public Builder setAttack(Function<AbstractPlant,BiConsumer<AbstractPlant, LivingEntity>> attack) {
            this.attack = attack;
            return this;
        }
        public Builder setSound(DeferredHolder<SoundEvent, SoundEvent> sound) {
            this.sound = sound;
            return this;
        }
        public PresetAttacks build() {
            var preset = new PresetAttacks(attack, sound, shootCountSetter);
            preset.shootCountSetter = shootCountSetter;
            return preset;
        }
    }
    /**
     * 直线弹幕
     */
    public static final QuaConsumer<AbstractPlant, LivingEntity, DeferredHolder<EntityType<?>, EntityType<LineProj>>, Float> PEA_SHOOT_ATTACK_BASE = (me, tar, proj, offsetY) -> {

        Vec3 dir;
        if(tar!=null) {
            Vec3 pos = tar.position().add(0,tar.getEyeHeight()* 0.75f,0);
            dir = pos.subtract(me.getEyePosition());
//            dir = me.calculateViewVector(me.getXRot(), me.yHeadRot);
        } else dir = me.calculateViewVector(me.getXRot(), me.yHeadRot);
        BaseProj proj1 = proj.get().create(me.level());
//        BaseProj proj1 = new LineProj(PlantEntities.ICE_PEA_PROJ.get(), me.level(),BaseProj.TextureLib.SNOW_PEA,new MobEffectInstance(ModEffects.FROZEN_EFFECT,20 * 5));
        proj1.setOwner(me);
        if(!me.builder.shouldRotX)dir = dir.multiply(1,0,1);
        proj1.setPos(me.getEyePosition().add(0,offsetY,0));
        proj1.shoot(dir.x, dir.y, dir.z, me.builder.projSpeed, 1.0F);
        me.level().addFreshEntity(proj1);
    };



    //普通豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.PEA_PROJ, 0.1f);
    };
    //冰豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> SNOW_PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.SNOW_PEA_PROJ, 0.1f);
    };
    //冻结豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> FROZEN_PEA_SHOOT_1 = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.FROZEN_PEA_PROJ_1, 0.1f);
    };
    public static final BiConsumer<AbstractPlant, LivingEntity> FROZEN_PEA_SHOOT_2 = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.FROZEN_PEA_PROJ_2, 0.1f);
    };



    // 小喷菇
    public static final BiConsumer<AbstractPlant, LivingEntity> SPORE_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.PUFF_SHROOM_PROJ, -0.45f);
    };
    // 大喷菇
    public static final BiConsumer<AbstractPlant, LivingEntity> FUME_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, MiscEntities.FUME_SHROOM_PROJ, 0f);
    };


    /**
     * 投掷物弹幕
     */
    private static final  QuaConsumer<AbstractPlant, LivingEntity, DeferredHolder<EntityType<?>, EntityType<ThrowableProj>>, Float> THROWN_SHOOT = (me, tar, proj, offsetY) -> {
        Vec3 pos = tar.position().add(0,tar.getEyeHeight(),0);
        if(tar instanceof Mob mob && !mob.getNavigation().isDone()){
            Vec3 dir = tar.getDeltaMovement().normalize().scale(2.5f);
            pos = pos.add(dir);
        }
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

    // 其他静态方法
    public static void produceSun(AbstractPlant plant, int count){
        SunItemEntity entity = new SunItemEntity(plant.level(), plant.position().add(0,0.5,0));
        entity.setDeltaMovement(plant.getRandom().nextFloat()*0.05f,0.05f,plant.getRandom().nextFloat()*0.05f);
        ItemStack stack = new ItemStack(MaterialItems.SOLID_SUN.get());
        stack.set(DataComponents.MAX_DAMAGE, count);
        entity.setItem(stack);
        plant.level().addFreshEntity(entity);
    }
}
