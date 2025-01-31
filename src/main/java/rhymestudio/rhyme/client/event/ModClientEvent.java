package rhymestudio.rhyme.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.Rhyme;


import rhymestudio.rhyme.client.model.ModelUtils;
import rhymestudio.rhyme.client.render.gui.CardUpLevelScreen;
import rhymestudio.rhyme.client.render.gui.SunCreatorScreen;
import rhymestudio.rhyme.core.registry.ModMenus;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static rhymestudio.rhyme.client.model.ModelUtils.HEAD_MODEL_ITEMS;

@Mod.EventBusSubscriber(modid = Rhyme.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ModClientEvent {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // todo
//            ClientConfig.load();
//
            MenuScreens.register(ModMenus.SUN_CREATOR_MENU.get(), SunCreatorScreen::new);
            MenuScreens.register(ModMenus.CARD_UP_LEVEL_MENU.get(), CardUpLevelScreen::new);
//            MenuScreens.register(ModMenus.DAVE_TRADES_MENU.get(), DaveTradeScreen::new);
//            PostUtil.init();
        });

    }
    @SubscribeEvent
    public static void registerAdditionalModel(ModelEvent.RegisterAdditional event) {

        ForgeRegistries.ITEMS.forEach(item->{
            ResourceManager provider = Minecraft.getInstance().getResourceManager();
            try{
                ResourceLocation location1 = ForgeRegistries.ITEMS.getKey(item);
                for(int i=1;i<=3;i++){
                    String name = new StringBuilder("models/item/").append(location1.getPath()).append("_head").append(i>1?"_"+i:"").append(".json").toString();
                    ResourceLocation location = ModelUtils.getHeadModelResourceLocation(item, i);
                    provider.getResourceOrThrow(new ResourceLocation(location.getNamespace(), name));
                    event.register(new ModelResourceLocation( location, "inventory"));
                    if(HEAD_MODEL_ITEMS.get(item)==null){
                        List<ResourceLocation> list = new ArrayList<>();
                        list.add(location);
                        HEAD_MODEL_ITEMS.put(item, list);
                    }else{
                        HEAD_MODEL_ITEMS.get(item).add(location);
                    }

                }

            } catch (FileNotFoundException e) {
                Rhyme.LOGGER.warn(e.getMessage());
            }
        });

    }

}
