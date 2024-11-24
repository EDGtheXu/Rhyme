package rhymestudio.rhyme.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import rhymestudio.rhyme.client.model.plantModels.PotatoMineModel;
import rhymestudio.rhyme.client.model.plantModels.SunflowerModel;
import rhymestudio.rhyme.client.model.proj.PeaProjModel;
import rhymestudio.rhyme.client.model.zombieModels.NormalZombieModel;

import static rhymestudio.rhyme.registry.Entities.PlantEntities.registerAbstractPlants;
import static rhymestudio.rhyme.registry.Entities.PlantEntities.registerNutWalls;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class RegisterModel {
    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(PeaProjModel.LAYER_LOCATION, PeaProjModel::createBodyLayer);
        evt.registerLayerDefinition(SunflowerModel.LAYER_LOCATION, SunflowerModel::createBodyLayer);
//        evt.registerLayerDefinition(PeaModel.LAYER_LOCATION, PeaModel::createBodyLayer);
//        evt.registerLayerDefinition(IcePeaModel.LAYER_LOCATION, IcePeaModel::createBodyLayer);
//        evt.registerLayerDefinition(DoublePeaModel.LAYER_LOCATION, DoublePeaModel::createBodyLayer);

        registerAbstractPlants.forEach(r->evt.registerLayerDefinition(r.getModelDefine(), r.getLayerDefinition()));
        registerNutWalls.forEach(r->evt.registerLayerDefinition(r.getModelDefine(), r.getLayerDefinition()));

        evt.registerLayerDefinition(PotatoMineModel.LAYER_LOCATION, PotatoMineModel::createBodyLayer);
//        evt.registerLayerDefinition(PotatoMineUnderModel.LAYER_LOCATION, PotatoMineUnderModel::createBodyLayer);


        // 僵尸模型
        evt.registerLayerDefinition(NormalZombieModel.LAYER_LOCATION,NormalZombieModel::createBodyLayer);

    }


    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event){
        event.enqueueWork(()->{
//            BlockEntityRenderers.register(ModBlocks.SUN_CREATOR_BLOCK_ENTITY.get(), SunCreatorBlockRenderer::new);
        });
    }

}
