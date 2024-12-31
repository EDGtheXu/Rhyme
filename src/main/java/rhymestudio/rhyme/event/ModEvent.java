package rhymestudio.rhyme.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.network.c2s.DaveShopPacket;
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


        DaveTrades.readTradesFromJson();
    }
}
