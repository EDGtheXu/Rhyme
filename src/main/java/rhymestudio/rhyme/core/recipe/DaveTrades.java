package rhymestudio.rhyme.core.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.utils.Computer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record DaveTrades(List<Trade> trades) {
//    public static Registry<DaveTrades> DAVE_SHOP_REGISTRY = new MappedRegistry<>(ResourceKey.createRegistryKey(Rhyme.space("dave_shop")), Lifecycle.stable());
//    public static DeferredRegister<DaveTrades> DAVE_SHOPS = DeferredRegister.create(DAVE_SHOP_REGISTRY, Rhyme.MODID);
    public static List<Trade> allTrades = new ArrayList<>();
    public static Supplier<DaveTrades> RAND_TRADE = () -> {
        List<Trade> res = new ArrayList<>(allTrades);
        Collections.shuffle(res);
        return new DaveTrades(res.subList(0, Math.min(3, res.size())));
    };

    public static void readTradesFromJson() {
        Map<ResourceLocation, Resource> jsons = Minecraft.getInstance().getResourceManager().listResources("dave_shop", r -> r.getPath().endsWith(".json"));
        jsons.forEach((k,v)->{
            try {
                Reader reader = Minecraft.getInstance().getResourceManager().openAsReader(k);
                JsonObject jsonobject = GsonHelper.parse(reader);
                allTrades.add(Trade.CODEC.decode(JsonOps.INSTANCE, jsonobject).result().get().getFirst());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Codec<DaveTrades> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Trade.CODEC.listOf().fieldOf("trades").forGetter(DaveTrades::trades)
    ).apply(instance, DaveTrades::new));
    public static StreamCodec<RegistryFriendlyByteBuf, DaveTrades> STREAM_CODEC = StreamCodec.composite(
            Trade.LIST_STREAM_CODEC, DaveTrades::trades,
            DaveTrades::new
    );

    public record Trade(int money, List<ItemStack> requires, ItemStack give) {
//        public static Registry<Trade> DAVE_TRADE_REGISTRY = new MappedRegistry<>(ResourceKey.createRegistryKey(Rhyme.space("dave_trade")), Lifecycle.stable());
//        public static DeferredRegister<Trade> DAVE_TRADES = DeferredRegister.create(DAVE_TRADE_REGISTRY, Rhyme.MODID);


        public boolean canTrade(Player player) {
            if (player.getData(ModAttachments.PLAYER_STORAGE).moneys < money)
                return false;
            for (ItemStack it : requires) {
                if (Computer.getInventoryItemCount(player, it.getItem()) < it.getCount()) {
                    return false;
                }
            }
            return true;
        }

        public static Codec<Trade> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("money").forGetter(Trade::money),
                ItemStack.CODEC.listOf().fieldOf("requires").forGetter(Trade::requires),
                ItemStack.CODEC.fieldOf("give").forGetter(Trade::give)
        ).apply(instance, Trade::new));
        public static StreamCodec<RegistryFriendlyByteBuf, Trade> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, Trade::money,
                ItemStack.LIST_STREAM_CODEC, Trade::requires,
                ItemStack.STREAM_CODEC, Trade::give,
                Trade::new
        );
        public static StreamCodec<RegistryFriendlyByteBuf, List<Trade>> LIST_STREAM_CODEC = STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity));
    }
}
