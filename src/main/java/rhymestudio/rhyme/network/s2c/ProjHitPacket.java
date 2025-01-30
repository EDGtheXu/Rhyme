package rhymestudio.rhyme.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkEvent.Context;
import rhymestudio.rhyme.mixinauxiliary.ILivingEntity;

import java.util.function.Supplier;

public class ProjHitPacket{
    int id;
    int frozenTick;
    public ProjHitPacket(int id, int frozenTick) {
        this.id = id;
        this.frozenTick = frozenTick;
    }

    public static ProjHitPacket decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();
        int frozenTick = buffer.readInt();
        return new ProjHitPacket(id, frozenTick);
    }

    public static void encode(ProjHitPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.id);
        buf.writeInt(packet.frozenTick);
    }


    public static void handle(ProjHitPacket packet, Supplier<Context> cxt) {
        Context context = cxt.get();
        context.enqueueWork(() -> {
            if (context.getSender().isLocalPlayer()) {
                ((ILivingEntity)context.getSender().level().getEntity(packet.id)).rhyme$setFrozenTime(packet.frozenTick);
            }
        }).exceptionally(e -> {
            return null;
        });
    }
}
