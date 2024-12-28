package rhymestudio.rhyme.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.ModAttachments;

public record SunCountPacketS2C(int count,int money) implements CustomPacketPayload {
    public int count() {
        return count;
    }
    public static final Type<SunCountPacketS2C> TYPE = new Type<>(Rhyme.space("sun_count_packet_s2c"));
    public static final StreamCodec<ByteBuf, SunCountPacketS2C> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SunCountPacketS2C::count,
            ByteBufCodecs.INT, SunCountPacketS2C::money,
            SunCountPacketS2C::new
    );

    @Override
    public @NotNull Type<SunCountPacketS2C> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().isLocalPlayer()) {
                context.player().getData(ModAttachments.PLAYER_STORAGE).sunCount = count;
                context.player().getData(ModAttachments.PLAYER_STORAGE).moneys = money;
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }
}
