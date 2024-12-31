package rhymestudio.rhyme.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.mixinauxiliary.ILivingEntity;

public record ProjHitPacket (int id, int frozenTick) implements CustomPacketPayload {

    public static final Type<ProjHitPacket> TYPE = new Type<>(Rhyme.space("frozen_tick_packet_s2c"));
    public static final StreamCodec<ByteBuf, ProjHitPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ProjHitPacket::id,
            ByteBufCodecs.INT, ProjHitPacket::frozenTick,
            ProjHitPacket::new
    );

    @Override
    public @NotNull Type<ProjHitPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().isLocalPlayer()) {
                ((ILivingEntity)context.player().level().getEntity(id)).rhyme$setFrozenTime(frozenTick);
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }
}
