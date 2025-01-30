package rhymestudio.rhyme.core.registry.entities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.core.entity.BaseProj;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import rhymestudio.rhyme.core.entity.misc.HelmetEntity;
import rhymestudio.rhyme.core.entity.misc.ModelPartEntity;
import rhymestudio.rhyme.core.entity.proj.LineProj;
import rhymestudio.rhyme.core.entity.proj.ThrowableProj;
import rhymestudio.rhyme.core.registry.ModEffects;
import rhymestudio.rhyme.core.registry.ModSounds;


import static rhymestudio.rhyme.Rhyme.MODID;
import static rhymestudio.rhyme.core.registry.ModEntities.Key;

public class MiscEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);



    public static final RegistryObject<EntityType<HelmetEntity>> HELMET_ENTITY = registerMisc("helmet_entity", HelmetEntity::new, 1,1);
    public static final RegistryObject<EntityType<ModelPartEntity>> MODEL_PART_ENTITY = registerMisc("model_part_entity",ModelPartEntity::new, 0.5F,0.3F);


    // tip 弹幕
        // tip 直线
    public static final RegistryObject<EntityType<LineProj>> PEA_PROJ = registerMisc("pea_proj",(e, l)->
            new LineProj(e,l, BaseProj.TextureLib.PEA).setHitSound(ModSounds.SPLAT),0.25F,0.25F);
    public static final RegistryObject<EntityType<LineProj>> ICE_PEA_PROJ = registerMisc("snow_pea_proj",(e,l)->
            new LineProj(e,l,BaseProj.TextureLib.SNOW_PEA, new MobEffectInstance(ModEffects.FROZEN_EFFECT.get(),20 * 5)).setHitSound(ModSounds.SNOW_PROJ_HIT),0.25F,0.25F);
    public static final RegistryObject<EntityType<LineProj>> PUFF_SHROOM_PROJ = registerMisc("puff_shroom_proj",(e,l)->
            new LineProj(e,l,BaseProj.TextureLib.PUFF_SHROOM_BULLET).setHitSound(ModSounds.SPLAT),0.15F,0.15F);

        // tip 投掷
    public static final RegistryObject<EntityType<ThrowableProj>> CABBAGE_PROJ = registerMisc("cabbage_proj",(e, l)->
            new ThrowableProj(e,l,ThrowableProj.TextureLib.CABBAGE_TEXTURE).setHitSound(ModSounds.SPLAT),0.5F,0.5F);


    // tip 阳光
    public static final RegistryObject<EntityType<SunItemEntity>> SUN_ITEM_ENTITY = registerMisc("sun", SunItemEntity::new, 1,1);


    private static <T extends Entity> RegistryObject<EntityType<T>> registerMisc(String name, EntityType.EntityFactory<T> entityFactory) {
        return registerMisc(name, entityFactory, 1,1);
    }
    private static <T extends Entity> RegistryObject<EntityType<T>>  registerMisc(String name, EntityType.EntityFactory<T> entityFactory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(entityFactory, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).sized(width,height).build(Key(name)));
    }

}
