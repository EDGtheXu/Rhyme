package rhymestudio.rhyme.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.render.gui.CardUpLevelScreen;
import rhymestudio.rhyme.client.render.gui.SunCreatorScreen;
import rhymestudio.rhyme.client.model.ModelUtils;
import rhymestudio.rhyme.client.render.post.PostUtil;
import rhymestudio.rhyme.core.registry.ModMenus;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static rhymestudio.rhyme.client.model.ModelUtils.HEAD_MODEL_ITEMS;

@EventBusSubscriber(modid = Rhyme.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ModClientEvent {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

            PostUtil.init();
        });

    }
    @SubscribeEvent
    public static void registerAdditionalModel(ModelEvent.RegisterAdditional event) {

        BuiltInRegistries.ITEM.forEach(item->{
            ResourceManager provider = Minecraft.getInstance().getResourceManager();
            try{
                ResourceLocation location1 = BuiltInRegistries.ITEM.getKey(item);
                for(int i=1;i<=3;i++){
                    String name = new StringBuilder("models/item/").append(location1.getPath()).append("_head").append(i>1?"_"+i:"").append(".json").toString();
                    ResourceLocation location = ModelUtils.getHeadModelResourceLocation(item, i);
                    ResourceLocation location2 = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), name);
                    provider.getResourceOrThrow(location2);
                    var modelResourceLocation = ModelResourceLocation.standalone(location);
                    event.register(modelResourceLocation);
                    if(HEAD_MODEL_ITEMS.get(item)==null){
                        List<ResourceLocation> list = new ArrayList<>();
                        list.add(location);
                        HEAD_MODEL_ITEMS.put(item, list);
                    }else{
                        HEAD_MODEL_ITEMS.get(item).add(location);
                    }

                }

            } catch (FileNotFoundException e) {
                System.out.println("not exist");
            }

        });
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.SUN_CREATOR_MENU.get(), SunCreatorScreen::new);
        event.register(ModMenus.CARD_UP_LEVEL_MENU.get(), CardUpLevelScreen::new);
    }
}
