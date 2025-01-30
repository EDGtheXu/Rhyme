package rhymestudio.rhyme.event;



import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import rhymestudio.rhyme.network.NetworkHandler;

import static rhymestudio.rhyme.Rhyme.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvent {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
//            Config.init();
            NetworkHandler.register();
        });
    }

}
