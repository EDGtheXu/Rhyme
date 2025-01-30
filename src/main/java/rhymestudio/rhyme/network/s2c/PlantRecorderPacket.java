package rhymestudio.rhyme.network.s2c;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import rhymestudio.rhyme.core.registry.ModAttachments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PlantRecorderPacket{
    List<Integer> ids;

    public PlantRecorderPacket(List<Integer> ids) {
        this.ids = ids;
    }

    public PlantRecorderPacket(FriendlyByteBuf buf) {
        ids = new ArrayList<>();
        int i = buf.readInt();
        for (int j = 0; j < i; j++) {
            ids.add(buf.readInt());
        }
    }

    public static PlantRecorderPacket decode(FriendlyByteBuf buffer) {
        return new PlantRecorderPacket(buffer);
    }

    public static void encode(PlantRecorderPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.ids.size());
        for (var data : packet.ids)
            buf.writeInt(data);
    }

    public static void handle(PlantRecorderPacket packet, Supplier<Context> cxt) {
        Context context = cxt.get();
        context.enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ModAttachments.PLANT_RECORDER_STORAGE).ifPresent(provider -> {
                provider.ids = Arrays.stream(packet.ids.toArray()).mapToInt(obj->(int)obj).boxed().collect(Collectors.toList());
            });
        });
    }
}