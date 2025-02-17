package rhymestudio.rhyme.network.c2s;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

public record SyncStructureStaffComponentPacket(StructureStaffComponent staff, ItemStack itemStack) implements CustomPacketPayload {

    
    public static final Type<SyncStructureStaffComponentPacket> TYPE = new Type<>(Rhyme.space("sync_structure_staff_component_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncStructureStaffComponentPacket> STREAM_CODEC = StreamCodec.composite(
            StructureStaffComponent.STREAM_CODEC, SyncStructureStaffComponentPacket::staff,
            ItemStack.STREAM_CODEC, SyncStructureStaffComponentPacket::itemStack,
            SyncStructureStaffComponentPacket::new
    );

    @Override
    public @NotNull Type<SyncStructureStaffComponentPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            itemStack.set(ModDataComponentTypes.STRUCTURE_STAFF, staff);
        }).exceptionally(e ->{
            System.err.println("Failed to handle packet: " + e.getMessage());
            return null;
        } );
    }
}
