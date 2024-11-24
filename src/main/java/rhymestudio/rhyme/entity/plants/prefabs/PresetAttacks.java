package rhymestudio.rhyme.entity.plants.prefabs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import rhymestudio.rhyme.entity.AbstractPlant;
import rhymestudio.rhyme.entity.BaseProj;
import rhymestudio.rhyme.entity.proj.LineProj;
import rhymestudio.rhyme.entity.proj.ThrowableProj;
import rhymestudio.rhyme.registry.Entities.PlantEntities;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class PresetAttacks {

    //普通豌豆
    public static final QuaConsumer<AbstractPlant, LivingEntity, DeferredHolder<EntityType<?>, EntityType<LineProj>>, Float> PEA_SHOOT_ATTACK_BASE = (me, tar, proj, offsetY) -> {
        Vec3 pos = tar.position().add(0,tar.getEyeHeight()/2,0);
        BaseProj proj1 = proj.get().create(me.level());
        proj1.setOwner(me);
        proj1.setPos(me.getEyePosition().add(0,offsetY,0));
        Vec3 dir = pos.subtract(me.getEyePosition());
        proj1.shoot(dir.x, dir.y, dir.z, me.builder.projSpeed, 1.0F);
        me.level().addFreshEntity(proj1);
    };

    //普通豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, PlantEntities.PEA_PROJ, 0.1f);
    };
    //冰豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> ICE_PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, PlantEntities.ICE_PEA_PROJ, 0.1f);
    };
    //双豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> DOUBLE_PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT.accept(me, tar);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                PEA_SHOOT.accept(me, tar);
            }
        }, 300);
    };

    //小喷菇
    public static final BiConsumer<AbstractPlant, LivingEntity> SPORE_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, PlantEntities.PEA_PROJ, -0.3f);
    };

    //投手豌豆
    public static final  QuaConsumer<AbstractPlant, LivingEntity, DeferredHolder<EntityType<?>, EntityType<ThrowableProj>>, Float> THROWN_SHOOT = (me, tar, proj, offsetY) -> {
        Vec3 dir = tar.getDeltaMovement().normalize().scale(2.5f);
        Vec3 pos = tar.position().add(0,tar.getEyeHeight()/2,0).add(dir);

        ThrowableProj proj1 = new ThrowableProj(proj.get(),me.level(),pos);
        proj1.setOwner(me);
        proj1.setPos(me.getEyePosition().add(0,offsetY,0));
        me.level().addFreshEntity(proj1);
    };

    //普通豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> THROWN_PEA_SHOOT = (me, tar) -> {
        THROWN_SHOOT.accept(me, tar, PlantEntities.THROWN_PEA_PROJ, 1f);
    };


    @FunctionalInterface
    public interface QuaConsumer<A,B,C,D>{
        void accept(A a, B b, C c, D d);
    }

}
