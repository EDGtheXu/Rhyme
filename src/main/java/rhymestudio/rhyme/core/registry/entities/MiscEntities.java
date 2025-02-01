package rhymestudio.rhyme.core.registry.entities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.core.entity.BaseProj;
import rhymestudio.rhyme.core.entity.misc.HelmetEntity;
import rhymestudio.rhyme.core.entity.misc.ModelPartEntity;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import rhymestudio.rhyme.core.entity.proj.LineProj;
import rhymestudio.rhyme.core.entity.proj.ThrowableProj;
import rhymestudio.rhyme.core.registry.ModEffects;
import rhymestudio.rhyme.core.registry.ModParticles;
import rhymestudio.rhyme.core.registry.ModSounds;

import java.util.List;
import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;
import static rhymestudio.rhyme.core.registry.ModEntities.Key;

public class MiscEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);



    public static final DeferredHolder<EntityType<?>, EntityType<HelmetEntity>> HELMET_ENTITY = registerMisc("helmet_entity", HelmetEntity::new, 1,1);
    public static final DeferredHolder<EntityType<?>, EntityType<ModelPartEntity>> MODEL_PART_ENTITY = registerMisc("model_part_entity",ModelPartEntity::new, 0.5F,0.3F);


    // tip 弹幕
        // tip 直线
    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> PEA_PROJ = registerMisc("pea_proj",(e, l)->
            new LineProj(e,l, BaseProj.TextureLib.PEA).setHitSound(ModSounds.SPLAT),0.25F,0.25F);

    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> SNOW_PEA_PROJ = registerMisc("snow_pea_proj",(e, l)->
            new LineProj(e,l,BaseProj.TextureLib.SNOW_PEA, new MobEffectInstance(ModEffects.SLOWDOWN_EFFECT,20 * 5)).setHitSound(ModSounds.SNOW_PROJ_HIT),0.25F,0.25F);

    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> FROZEN_PEA_PROJ_1 = registerMisc("frozen_pea_proj_1",(e, l)->
            new LineProj(
                    e, l,
                    BaseProj.TextureLib.SNOW_PEA,
                    List.of(new MobEffectInstance(ModEffects.FROZEN_EFFECT, 20 * 2), new MobEffectInstance(ModEffects.SLOWDOWN_EFFECT,20 * 5)
                    )
            ).setHitSound(ModSounds.SNOW_PROJ_HIT),0.25F,0.25F);

    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> FROZEN_PEA_PROJ_2 = registerMisc("frozen_pea_proj_2",(e, l)->
            new LineProj(
                    e, l,
                    BaseProj.TextureLib.SNOW_PEA,
                    List.of(new MobEffectInstance(ModEffects.FROZEN_EFFECT,20 * 5), new MobEffectInstance(ModEffects.SLOWDOWN_EFFECT,20 * 5)
                    )
            ).setHitSound(ModSounds.SNOW_PROJ_HIT).setDamage(12.0f),0.25F,0.25F);

    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> PUFF_SHROOM_PROJ = registerMisc("puff_shroom_proj",(e,l)->
            new LineProj(e,l,BaseProj.TextureLib.PUFF_SHROOM_BULLET).setHitSound(ModSounds.SPLAT),0.15F,0.15F);

    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> FUME_SHROOM_PROJ = registerMisc("fume_shroom_proj",(e,l)->
            new LineProj(e,l,BaseProj.TextureLib.EMPTY).setExistTick(10).setPenetrate(99).setHitSound(ModSounds.SPLAT).setClientTickCallback(p->{
                    for(int i=0;i<5;i++)
                        p.level().addParticle(ModParticles.PUFF_PROJ_PARTICLE.get(),p.getX(),p.getY()+0.5,p.getZ(),0.0D,0,0);
            }), 1F,1F);

        // tip 投掷
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableProj>> CABBAGE_PROJ = registerMisc("cabbage_proj",(e, l)->
            new ThrowableProj(e,l,ThrowableProj.TextureLib.CABBAGE_TEXTURE).setHitSound(ModSounds.SPLAT),0.5F,0.5F);


    // tip 阳光
    public static final Supplier<EntityType<SunItemEntity>> SUN_ITEM_ENTITY = registerMisc("sun", SunItemEntity::new, 1,1);


    private static <T extends Entity>DeferredHolder<EntityType<?>, EntityType<T>> registerMisc(String name, EntityType.EntityFactory<T> entityFactory) {
        return registerMisc(name, entityFactory, 1,1);
    }
    private static <T extends Entity>DeferredHolder<EntityType<?>, EntityType<T>> registerMisc(String name, EntityType.EntityFactory<T> entityFactory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(entityFactory, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).sized(width,height).build(Key(name)));
    }

}
