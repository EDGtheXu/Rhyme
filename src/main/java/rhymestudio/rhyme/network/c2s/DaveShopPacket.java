package rhymestudio.rhyme.network.c2s;


import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import rhymestudio.rhyme.core.entity.CrazyDave;
import rhymestudio.rhyme.core.recipe.DaveTrades.Trade;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.mixinauxiliary.IPlayer;
import rhymestudio.rhyme.utils.Computer;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class DaveShopPacket{
    private final Trade trade;
    public DaveShopPacket(Trade ids) {
        this.trade = ids;
    }

    public DaveShopPacket(FriendlyByteBuf buffer) {
        this.trade = Trade.CODEC.decode(
                JsonOps.INSTANCE,
                JsonParser.parseString(buffer.readBytes(buffer.readInt()).toString(StandardCharsets.UTF_8))
        ).result().get().getFirst();
    }

    public static DaveShopPacket decode(FriendlyByteBuf buffer) {
        return new DaveShopPacket(buffer);
    }

    public static void encode(DaveShopPacket packet, FriendlyByteBuf buf) {
        byte[] bytes = Trade.CODEC.encodeStart(JsonOps.INSTANCE, packet.trade).result().get().toString().getBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
  
    public static void handle(DaveShopPacket packet, Supplier<NetworkEvent.Context> cxt) {
        NetworkEvent.Context context = cxt.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if(player == null) return;
            Trade trade = packet.trade;
            if(trade.canTrade(player) && player instanceof ServerPlayer sp){
                var data = player.getCapability(ModAttachments.PLAYER_STORAGE).resolve().get();
                data.moneys -= trade.money();
                if(((IPlayer)player).rhyme$getInteractingEntity() instanceof CrazyDave dave)
                    dave.addMoney(trade.money());
                data.sendSunCountUpdate(sp);
                for (var item : trade.requires())
                    Computer.tryCombineInventoryItem(player, item.getItem(), item.getCount());
                ItemStack result = trade.result();
                player.addItem(result.copy());
            }else{
                player.sendSystemMessage(Component.translatable("rhyme.trade.not_enough_items"));
            }

        }).exceptionally(e -> null);
    }
}
