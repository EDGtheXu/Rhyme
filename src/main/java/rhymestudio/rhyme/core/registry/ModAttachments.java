package rhymestudio.rhyme.core.registry;


import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.attactment.PlantRecorderAttachment;
import rhymestudio.rhyme.core.dataSaver.attactment.SunCountAttachment;

@Mod.EventBusSubscriber(modid = Rhyme.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModAttachments {

    public static final Capability<PlantRecorderAttachment> PLANT_RECORDER_STORAGE = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<SunCountAttachment> PLAYER_STORAGE = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlantRecorderAttachment.class);
        event.register(SunCountAttachment.class);
    }
}
