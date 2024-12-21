package rhymestudio.rhyme.core.registry.entities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.core.entity.BaseProj;
import rhymestudio.rhyme.core.entity.SunItemEntity;
import rhymestudio.rhyme.core.entity.misc.HelmetEntity;
import rhymestudio.rhyme.core.entity.misc.ModelPartEntity;
import rhymestudio.rhyme.core.entity.proj.LineProj;
import rhymestudio.rhyme.core.entity.proj.ThrowableProj;
import rhymestudio.rhyme.core.registry.ModEffects;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class MiscEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);



    public static final DeferredHolder<EntityType<?>, EntityType<HelmetEntity>> HELMET_ENTITY = registerMisc("helmet_entity", HelmetEntity::new, 1,1);
    public static final DeferredHolder<EntityType<?>, EntityType<ModelPartEntity>> MODEL_PART_ENTITY = registerMisc("model_part_entity",ModelPartEntity::new, 0.5F,0.3F);


    // tip 弹幕
        // tip 直线
    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> PEA_PROJ = registerMisc("pea_proj",(e, l)->
            new LineProj(e,l, BaseProj.TextureLib.PEA),0.25F,0.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> ICE_PEA_PROJ = registerMisc("snow_pea_proj",(e,l)->
            new LineProj(e,l,BaseProj.TextureLib.SNOW_PEA, new MobEffectInstance(ModEffects.FROZEN_EFFECT,20 * 5)),0.25F,0.25F);
    public static final DeferredHolder<EntityType<?>, EntityType<LineProj>> PUFF_SHROOM_PROJ = registerMisc("puff_shroom_proj",(e,l)->
            new LineProj(e,l,BaseProj.TextureLib.PUFF_SHROOM_BULLET),0.15F,0.15F);

        // tip 投掷
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableProj>> CABBAGE_PROJ = registerMisc("cabbage_proj",(e, l)->
            new ThrowableProj(e,l,ThrowableProj.TextureLib.CABBAGE_TEXTURE),0.5F,0.5F);


    // tip 阳光
    public static final Supplier<EntityType<SunItemEntity>> SUN_ITEM_ENTITY = registerMisc("sun_entity", SunItemEntity::new, 0.25F,0.25F);


    private static <T extends Entity>DeferredHolder<EntityType<?>, EntityType<T>> registerMisc(String name, EntityType.EntityFactory<T> entityFactory) {
        return registerMisc(name, entityFactory, 1,1);
    }
    private static <T extends Entity>DeferredHolder<EntityType<?>, EntityType<T>> registerMisc(String name, EntityType.EntityFactory<T> entityFactory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(entityFactory, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).sized(width,height).build(Key(name)));
    }

    public static String Key(String key){
        return MODID + ":" + key;
    }
}
