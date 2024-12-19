package rhymestudio.rhyme.client;


import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.model.plantModels.*;
import rhymestudio.rhyme.client.model.proj.CabbageProjModel;
import rhymestudio.rhyme.client.model.proj.PeaProjModel;
import rhymestudio.rhyme.client.model.zombieModels.NormalZombieModel;
import rhymestudio.rhyme.client.render.GeoNormalRenderer;
import rhymestudio.rhyme.client.render.entity.BasePlantRenderer;

import rhymestudio.rhyme.client.render.entity.SunRenderer;
import rhymestudio.rhyme.client.render.entity.misc.HelmetEntityRenderer;
import rhymestudio.rhyme.client.render.entity.proj.ProjRenderer;
import rhymestudio.rhyme.client.render.entity.zombie.NormalZombieRenderer;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.BaseProj;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;
import rhymestudio.rhyme.core.registry.entities.Zombies;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import static rhymestudio.rhyme.client.RegisterModel.getModelDefine;
import static rhymestudio.rhyme.core.registry.entities.PlantEntities.*;

@EventBusSubscriber(modid = Rhyme.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterRenderer {
    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PlantEntities.SUN_ITEM_ENTITY.get(), SunRenderer::new);
        // tip 植物
//        event.registerEntityRenderer(, dispatcher-> new BasePlantRenderer<SunFlower, HierarchicalModel<SunFlower>>(dispatcher,new SunflowerModel(dispatcher.bakeLayer(SunflowerModel.LAYER_LOCATION))));

        registerOne(event,SUN_FLOWER.get(),getRenderSup(SunflowerModel.class));
        registerOne(event,PEA.get(),getRenderSup(PeaModel.class));
        registerOne(event, SNOW_PEA.get(),getRenderSup(SnowPeaModel.class));
        registerOne(event,DOUBLE_PEA.get(),getRenderSup(DoublePeaModel.class));
        registerOne(event,WALL_NUT.get(),getRenderSup(WallNutModel.class),0.5f,1f);
        registerOne(event,POTATO_MINE.get(),getRenderSup(PotatoMineModel.class),0,1f);
        registerOne(event,PUFF_SHROOM.get(),getRenderSup(PuffShroomModel.class),0.2f,0.5f);
        registerOne(event,CABBAGE_PULT.get(),getRenderSup(CabbageModel.class));

        event.registerEntityRenderer(CHOMPER.get(), c -> new GeoNormalRenderer<>(c, "chomper",false));



        // tip 子弹
        registerProj(event,PEA_PROJ.get(),c->new PeaProjModel<>(c.bakeLayer(PeaProjModel.LAYER_LOCATION)),1,-0.6F);
        registerProj(event,ICE_PEA_PROJ.get(),c->new PeaProjModel<>(c.bakeLayer(PeaProjModel.LAYER_LOCATION)),1,-0.6F);
        registerProj(event,CABBAGE_PROJ.get(),c->new CabbageProjModel<>(c.bakeLayer(CabbageProjModel.LAYER_LOCATION)));


        // 僵尸
        event.registerEntityRenderer(Zombies.NORMAL_ZOMBIE.get(), c-> new NormalZombieRenderer<>(c, new NormalZombieModel<>(c.bakeLayer(NormalZombieModel.LAYER_LOCATION))));
        event.registerEntityRenderer(Zombies.CONE_ZOMBIE.get(), c-> new NormalZombieRenderer<>(c, new NormalZombieModel<>(c.bakeLayer(NormalZombieModel.LAYER_LOCATION))));
        event.registerEntityRenderer(Zombies.IRON_BUCKET_ZOMBIE.get(), c-> new NormalZombieRenderer<>(c, new NormalZombieModel<>(c.bakeLayer(NormalZombieModel.LAYER_LOCATION))));


        // 其他
        event.registerEntityRenderer(MiscEntities.HELMET_ENTITY.get(),HelmetEntityRenderer::new);

    }


    public static <T extends BaseProj>void registerProj(EntityRenderersEvent.RegisterRenderers event, EntityType<T> entityType, Function<EntityRendererProvider.Context, EntityModel<T>> model){
        event.registerEntityRenderer(entityType, (dispatcher)-> new ProjRenderer<>(dispatcher, model.apply(dispatcher),1,0));
    }

    public static <T extends BaseProj>void registerProj(EntityRenderersEvent.RegisterRenderers event, EntityType<T> entityType, Function<EntityRendererProvider.Context, EntityModel<T>> model,float size,float offsetY){
        event.registerEntityRenderer(entityType, (dispatcher)-> new ProjRenderer<>(dispatcher, model.apply(dispatcher),size,offsetY));
    }

    public static <T extends AbstractPlant>void registerOne(EntityRenderersEvent.RegisterRenderers event, EntityType<T> entityType, Function<EntityRendererProvider.Context, HierarchicalModel<T>> model){
        registerOne(event,entityType,model,0.35f,1f);
    }

    public static <T extends AbstractPlant>void registerOne(EntityRenderersEvent.RegisterRenderers event, EntityType<T> entityType, Function<EntityRendererProvider.Context, HierarchicalModel<T>> model,float shadowRadius,float scale){
        registerOne(event,entityType,model,shadowRadius,scale,false);
    }

    public static <T extends AbstractPlant>void registerOne(EntityRenderersEvent.RegisterRenderers event, EntityType<T> entityType, Function<EntityRendererProvider.Context, HierarchicalModel<T>> model,float shadowRadius,float scale,boolean rotY){
        event.registerEntityRenderer(entityType, (dispatcher)-> new BasePlantRenderer<>(dispatcher, model.apply(dispatcher), shadowRadius, scale, rotY));
    }


    public static <T extends AbstractPlant>Function<EntityRendererProvider.Context, HierarchicalModel<T>> getRenderSup(Class<? extends HierarchicalModel<T>>  clz){
        Constructor<? extends HierarchicalModel<T>> c;
        try{
            c = clz.getDeclaredConstructor(ModelPart.class);
        }catch (Exception e){ throw new RuntimeException();}

        Function<EntityRendererProvider.Context, HierarchicalModel<T>> res = cnt->
        {
            try {
                return c.newInstance(cnt.bakeLayer(getModelDefine(clz)));
            } catch (Exception e) {throw new RuntimeException(e);}
        };
        return res;
    }
}
