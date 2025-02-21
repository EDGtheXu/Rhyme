package rhymestudio.rhyme.utils;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class AdapterUtils {

    public static void sendPacketToServer(CustomPacketPayload packet){
        PacketDistributor.sendToServer(packet);
    }

    public static void sendPacketToPlayer(CustomPacketPayload packet, ServerPlayer player){
        PacketDistributor.sendToPlayer(player, packet);
    }
}
