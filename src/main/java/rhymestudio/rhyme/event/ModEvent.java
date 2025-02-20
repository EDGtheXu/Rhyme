package rhymestudio.rhyme.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;
import rhymestudio.rhyme.core.registry.ModRegistry;
import rhymestudio.rhyme.network.c2s.ClientEventBoundPacket;
import rhymestudio.rhyme.network.c2s.DaveShopPacket;
import rhymestudio.rhyme.network.c2s.GenerateStructurePacket;
import rhymestudio.rhyme.network.c2s.SyncStructureStaffComponentPacket;
import rhymestudio.rhyme.network.s2c.PlantRecorderPacket;
import rhymestudio.rhyme.network.s2c.ProjHitPacket;
import rhymestudio.rhyme.network.s2c.SunCountPacketS2C;

import static rhymestudio.rhyme.Rhyme.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvent {

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(SunCountPacketS2C.TYPE, SunCountPacketS2C.STREAM_CODEC, SunCountPacketS2C::handle);
        registrar.playToClient(ProjHitPacket.TYPE, ProjHitPacket.STREAM_CODEC, ProjHitPacket::handle);
        registrar.playToClient(PlantRecorderPacket.TYPE, PlantRecorderPacket.STREAM_CODEC, PlantRecorderPacket::handle);


        registrar.playToServer(DaveShopPacket.TYPE, DaveShopPacket.STREAM_CODEC, DaveShopPacket::handle);
        registrar.playToServer(SyncStructureStaffComponentPacket.TYPE, SyncStructureStaffComponentPacket.STREAM_CODEC, SyncStructureStaffComponentPacket::handle);
        registrar.playToServer(GenerateStructurePacket.TYPE, GenerateStructurePacket.STREAM_CODEC, GenerateStructurePacket::handle);
        registrar.playToServer(ClientEventBoundPacket.TYPE, ClientEventBoundPacket.STREAM_CODEC, ClientEventBoundPacket::handle);

    }

    @SubscribeEvent
    public static void registerEvent(RegisterEvent event) {
        if(event.getRegistry() == ModRegistry.CHECK_POINT_REGISTRY){

//            Registries.rec
//            ModCheckPoints.registerFromJson();
//            ResourceManager
//            ModCheckPoints.CHECK_POINTS.register("lvl_2", ()-> (spawn)-> WaveManager.builder(spawn).build());

        }
    }



}
