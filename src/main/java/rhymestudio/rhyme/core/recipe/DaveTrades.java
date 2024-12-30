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
import java.util.*;
import java.util.function.Function;

public record DaveTrades(List<Trade> trades) {
//    public static Registry<DaveTrades> DAVE_SHOP_REGISTRY = new MappedRegistry<>(ResourceKey.createRegistryKey(Rhyme.space("dave_shop")), Lifecycle.stable());
//    public static DeferredRegister<DaveTrades> DAVE_SHOPS = DeferredRegister.create(DAVE_SHOP_REGISTRY, Rhyme.MODID);
    private static final List<Trade> allTrades = new ArrayList<>();
    public static Function<Integer,DaveTrades> RAND_TRADE = (num) -> {
        List<Trade> res = weightedRandomSubsequence(allTrades, num);
        Collections.shuffle(res);
        return new DaveTrades(res.subList(0, Math.min(num-1, res.size())));
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

    public static List<Trade> weightedRandomSubsequence(List<Trade> trades, int n) {
        if (trades == null || trades.isEmpty() || n <= 0 || n > trades.size()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        double totalWeight = trades.stream().mapToDouble(t -> t.weight).sum();
        List<Trade> result = new ArrayList<>();
        List<Trade> copyTrades = new ArrayList<>(trades);
        for (int i = 0; i < n; i++) {
            double randomValue = Minecraft.getInstance().level.random.nextDouble() * totalWeight;
            double cumulativeWeight = 0.0;
            for (Trade trade : copyTrades) {
                cumulativeWeight += trade.weight;
                if (cumulativeWeight >= randomValue) {
                    result.add(trade);
                    totalWeight -= trade.weight;
                    copyTrades.remove(trade);
                    break;
                }
            }
        }
        return result;
    }

    public static Codec<DaveTrades> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Trade.CODEC.listOf().fieldOf("trades").forGetter(DaveTrades::trades)
    ).apply(instance, DaveTrades::new));
    public static StreamCodec<RegistryFriendlyByteBuf, DaveTrades> STREAM_CODEC = StreamCodec.composite(
            Trade.LIST_STREAM_CODEC, DaveTrades::trades,
            DaveTrades::new
    );

    /**
     * 戴夫商店的一项
     * @param money 金币花费
     * @param requires 提供物品
     * @param result 获得物品
     * @param weight 权重，越大越容易被选中
     */
    public record Trade(int weight,int money, List<ItemStack> requires, ItemStack result) {
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
                Codec.INT.fieldOf("weight").forGetter(Trade::weight),
                Codec.INT.fieldOf("money").forGetter(Trade::money),
                ItemStack.CODEC.listOf().fieldOf("requires").forGetter(Trade::requires),
                ItemStack.CODEC.fieldOf("result").forGetter(Trade::result)
        ).apply(instance, Trade::new));
        public static StreamCodec<RegistryFriendlyByteBuf, Trade> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, Trade::weight,
                ByteBufCodecs.INT, Trade::money,
                ItemStack.LIST_STREAM_CODEC, Trade::requires,
                ItemStack.STREAM_CODEC, Trade::result,
                Trade::new
        );
        public static StreamCodec<RegistryFriendlyByteBuf, List<Trade>> LIST_STREAM_CODEC = STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity));
    }
}
