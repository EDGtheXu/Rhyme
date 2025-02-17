package rhymestudio.rhyme.utils;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public class AdapterUtils {

    public static void sendPacketToServer(CustomPacketPayload packet){
        PacketDistributor.sendToServer(packet);
    }
}
