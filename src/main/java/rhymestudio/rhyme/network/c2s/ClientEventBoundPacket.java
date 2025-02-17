package rhymestudio.rhyme.network.c2s;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;
import rhymestudio.rhyme.core.menu.StaffMenu;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

public record ClientEventBoundPacket(int code) implements CustomPacketPayload {

    public static final Type<ClientEventBoundPacket> TYPE = new Type<>(Rhyme.space("client_event_bound_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientEventBoundPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientEventBoundPacket::code,
            ClientEventBoundPacket::new
    );

    @Override
    public @NotNull Type<ClientEventBoundPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if(code == 0){
                player.openMenu(new SimpleMenuProvider(
                        (id, inventory, ignored) -> new StaffMenu(id,inventory),
                        Component.literal("Structure Staff")
                ));
            }


        }).exceptionally(e -> null);
    }
}
