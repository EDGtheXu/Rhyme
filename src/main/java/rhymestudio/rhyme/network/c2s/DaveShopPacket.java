package rhymestudio.rhyme.network.c2s;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.utils.Computer;

public record DaveShopPacket(DaveTrades.Trade trade) implements CustomPacketPayload {

    
    public static final Type<DaveShopPacket> TYPE = new Type<>(Rhyme.space("dave_shop_packet_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DaveShopPacket> STREAM_CODEC = StreamCodec.composite(
            DaveTrades.Trade.STREAM_CODEC,
            DaveShopPacket::trade,
            DaveShopPacket::new
    );

    @Override
    public @NotNull Type<DaveShopPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if(trade.canTrade(context.player()) && context.player() instanceof ServerPlayer sp){
                var data = context.player().getData(ModAttachments.PLAYER_STORAGE);
                data.moneys -= trade.money();
                data.sendSunCountUpdate(sp);
                for (var item : trade.requires())
                    Computer.tryCombineInventoryItem(context.player(), item.getItem(), item.getCount());
                ItemStack result = trade.result();
                context.player().addItem(result.copy());
            }else{
                context.player().sendSystemMessage(Component.translatable("rhyme.trade.not_enough_items"));
            }

        }).exceptionally(e -> null);
    }
}
