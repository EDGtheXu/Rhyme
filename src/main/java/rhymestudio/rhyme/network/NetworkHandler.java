package rhymestudio.rhyme.network;


import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.network.c2s.DaveShopPacket;
import rhymestudio.rhyme.network.s2c.PlantRecorderPacket;
import rhymestudio.rhyme.network.s2c.ProjHitPacket;
import rhymestudio.rhyme.network.s2c.SunCountPacketS2C;


public final class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        Rhyme.space("main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++,  PlantRecorderPacket.class,  PlantRecorderPacket::encode,  PlantRecorderPacket::decode,  PlantRecorderPacket::handle);
        CHANNEL.registerMessage(packetId++,  ProjHitPacket.class,  ProjHitPacket::encode,  ProjHitPacket::decode,  ProjHitPacket::handle);
        CHANNEL.registerMessage(packetId++,  SunCountPacketS2C.class,  SunCountPacketS2C::encode,  SunCountPacketS2C::decode,  SunCountPacketS2C::handle);
        CHANNEL.registerMessage(packetId++,  DaveShopPacket.class,  DaveShopPacket::encode,  DaveShopPacket::decode,  DaveShopPacket::handle);


    }
}
