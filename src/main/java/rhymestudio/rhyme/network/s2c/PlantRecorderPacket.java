package rhymestudio.rhyme.network.s2c;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.ModAttachments;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record PlantRecorderPacket (List<Integer> ids) implements CustomPacketPayload {

    public static final Type<PlantRecorderPacket> TYPE = new Type<>(Rhyme.space("plant_recorder_packet_s2c"));
    public static final StreamCodec<ByteBuf, PlantRecorderPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.INT.listOf()),PlantRecorderPacket::ids,
            PlantRecorderPacket::new
    );

    @Override
    public @NotNull Type<PlantRecorderPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().isLocalPlayer()) {
                context.player().getData(ModAttachments.PLANT_RECORDER_STORAGE).ids = Arrays.stream(ids.toArray()).mapToInt(obj->(int)obj).boxed().collect(Collectors.toList());
            }
        });
    }
}