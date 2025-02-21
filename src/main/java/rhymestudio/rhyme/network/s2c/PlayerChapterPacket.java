package rhymestudio.rhyme.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.attactment.PlayerProgressAttachment;
import rhymestudio.rhyme.core.registry.ModAttachments;

public record PlayerChapterPacket(PlayerProgressAttachment attachment)  implements CustomPacketPayload {

    public static final Type< PlayerChapterPacket> TYPE = new Type<>(Rhyme.space("player_chapter_packet_s2c"));
    public static final StreamCodec<ByteBuf, PlayerChapterPacket> STREAM_CODEC = ByteBufCodecs.fromCodec(
            CompoundTag.CODEC.xmap(tag->{
                PlayerProgressAttachment attachment1 = new PlayerProgressAttachment();
                attachment1.deserializeNBT(null,tag);
                return new PlayerChapterPacket(attachment1);
            }, packet -> packet.attachment().serializeNBT(null)
            ));

    @Override
    public @NotNull Type< PlayerChapterPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().isLocalPlayer()) {
                context.player().setData(ModAttachments.PLAYER_PROGRESS_STORAGE, attachment);
            }
        });
    }
}