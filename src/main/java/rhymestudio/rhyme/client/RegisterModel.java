package rhymestudio.rhyme.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import rhymestudio.rhyme.client.model.plantModels.*;
import rhymestudio.rhyme.client.model.proj.CabbageProjModel;
import rhymestudio.rhyme.client.model.proj.PeaProjModel;
import rhymestudio.rhyme.client.model.zombieModels.NormalZombieModel;

import java.lang.reflect.Field;
import java.util.function.Supplier;



@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class RegisterModel {
    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions evt) {

        // 弹幕模型
        register(evt, PeaProjModel.class);
        register(evt, CabbageProjModel.class);


        // 注册植物模型
        register(evt, SunflowerModel.class);
        register(evt, PeaModel.class);
        register(evt, PotatoMineModel.class);
        register(evt, SnowPeaModel.class);
        register(evt, RepeaterModel.class);
        register(evt, PuffShroomModel.class);
        register(evt, WallNutModel.class);
        register(evt, CabbageModel.class);

        // 僵尸模型
        register(evt, NormalZombieModel.class);

        // 疯狂戴夫模型
//        register(evt, CrazyDaveModel.class);
    }





    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event){
        event.enqueueWork(()->{
//            BlockEntityRenderers.register(ModBlocks.SUN_CREATOR_BLOCK_ENTITY.get(), SunCreatorBlockRenderer::new);
        });
    }

    public static ModelLayerLocation getModelDefine(Class<? extends Model> clz){
        Field field2;
        try{
            field2  = clz.getDeclaredField("LAYER_LOCATION");
        }catch (Exception e){ throw new RuntimeException();}
        field2.setAccessible(true);

        try{
            return (ModelLayerLocation) field2.get(null);
        }catch (Exception e){ throw new RuntimeException();}

    }

    public static Supplier<LayerDefinition> getLayerDefinition(Class<? extends Model> clz){
        return  ()-> {
            try {
                return (LayerDefinition) clz.getMethod("createBodyLayer").invoke(null);
            } catch (Exception e) {throw new RuntimeException(e);}
        };
    }

    public static void register(EntityRenderersEvent.RegisterLayerDefinitions evt, Class<? extends Model> clz){
        evt.registerLayerDefinition(getModelDefine(clz), getLayerDefinition(clz));
    }
}
