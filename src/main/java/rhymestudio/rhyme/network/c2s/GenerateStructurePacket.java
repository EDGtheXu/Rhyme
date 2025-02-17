package rhymestudio.rhyme.network.c2s;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;

public record GenerateStructurePacket(StructureStaffComponent component, BlockPos eyePos) implements CustomPacketPayload {

    public static final Type<GenerateStructurePacket> TYPE = new Type<>(Rhyme.space("generate_structure_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, GenerateStructurePacket> STREAM_CODEC = StreamCodec.composite(
            StructureStaffComponent.STREAM_CODEC, GenerateStructurePacket::component,
            BlockPos.STREAM_CODEC, GenerateStructurePacket::eyePos,
            GenerateStructurePacket::new
    );

    @Override
    public @NotNull Type<GenerateStructurePacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {

        context.enqueueWork(() -> {
            Player player = context.player();
            if (component != null) {
                component.generate(player.level(), eyePos);
            }
        }).exceptionally(e -> null);
    }
}
