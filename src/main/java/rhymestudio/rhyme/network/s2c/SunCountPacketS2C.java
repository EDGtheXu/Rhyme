package rhymestudio.rhyme.network.s2c;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;


import net.minecraftforge.network.NetworkEvent;
import rhymestudio.rhyme.core.registry.ModAttachments;

import java.util.function.Supplier;

public class SunCountPacketS2C{
    public int count;
    public int money;
    public int additionSunCount;

    public SunCountPacketS2C(int count, int money, int additionSunCount) {
        this.count = count;
        this.money = money;
        this.additionSunCount = additionSunCount;
    }

    public static SunCountPacketS2C decode(FriendlyByteBuf buffer) {
        int count = buffer.readInt();
        int money = buffer.readInt();
        int additionSunCount = buffer.readInt();
        return new SunCountPacketS2C(count, money, additionSunCount);
    }

    public static void encode(SunCountPacketS2C packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.count);
        buf.writeInt(packet.money);
        buf.writeInt(packet.additionSunCount);
    }


    public static void handle(SunCountPacketS2C packet, Supplier<NetworkEvent.Context> cxt) {
        NetworkEvent.Context context = cxt.get();
        context.enqueueWork(() -> {
            var data = Minecraft.getInstance().player.getCapability(ModAttachments.PLAYER_STORAGE);
            data.ifPresent(d->{
                d.sunCount = packet.count;
                d.moneys = packet.money;
                d.additionalSunCount = packet.additionSunCount;
            });


        }).exceptionally(e -> null);
    }
}
