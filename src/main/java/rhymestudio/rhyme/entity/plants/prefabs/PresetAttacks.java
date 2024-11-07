package rhymestudio.rhyme.entity.plants.prefabs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.function.TriConsumer;
import rhymestudio.rhyme.entity.AbstractPlant;
import rhymestudio.rhyme.entity.BaseProj;
import rhymestudio.rhyme.entity.proj.LineProj;
import rhymestudio.rhyme.registry.ModEntities;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class PresetAttacks {

    //普通豌豆
    public static final  QuaConsumer<AbstractPlant, LivingEntity, DeferredHolder<EntityType<?>, EntityType<LineProj>>, Float> PEA_SHOOT_ATTACK_BASE = (me, tar, proj, offsetY) -> {
        Vec3 pos = tar.position().add(0,tar.getEyeHeight()/2,0);
        BaseProj arrow = proj.get().create(me.level());
        arrow.setOwner(me);
        arrow.setPos(me.getEyePosition().add(0,offsetY,0));
        Vec3 dir = pos.subtract(me.getEyePosition());
        arrow.shoot(dir.x, dir.y, dir.z, me.builder.projSpeed, 1.0F);
        me.level().addFreshEntity(arrow);
    };

    //普通豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, ModEntities.PEA_PROJ, 0.1f);
    };
    //冰豌豆
    public static final BiConsumer<AbstractPlant, LivingEntity> ICE_PEA_SHOOT = (me, tar) -> {
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, ModEntities.ICE_PEA_PROJ, 0.1f);
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
        PEA_SHOOT_ATTACK_BASE.accept(me, tar, ModEntities.PEA_PROJ, -0.3f);
    };


    @FunctionalInterface
    public interface QuaConsumer<A,B,C,D>{
        void accept(A a, B b, C c, D d);
    }

}
