package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonElement;
import com.mojang.serialization.JavaOps;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.core.registry.items.MaterialItems;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 生成戴夫商店的单个配方，随机组成戴夫商店
 * @see DaveTrades.Trade
 */
public class DaveShopProvider extends AbstractRecipeProvider {

    public DaveShopProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void run() {

        gen(10,MaterialItems.GENERAL_SEED,10)
                .cost(10)
                .add(MaterialItems.PEA_GENE,3)
                .add(MaterialItems.NUT_GENE,2)
                .add(MaterialItems.GENERAL_SEED,1)
                .build();
        gen(20,MaterialItems.GENERAL_SEED,9)
                .cost(10)
                .add(MaterialItems.PEA_GENE,4)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(30,MaterialItems.GENERAL_SEED,8)
                .cost(10)
                .add(MaterialItems.PEA_GENE,5)
                .add(MaterialItems.NUT_GENE,2)
                .add(MaterialItems.GENERAL_SEED,1)
                .add(MaterialItems.THROWABLE_GENE,1)
                .build();
        gen(40,MaterialItems.GENERAL_SEED,7)
                .cost(10)
                .add(MaterialItems.PEA_GENE,6)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(50,MaterialItems.GENERAL_SEED,6)
                .cost(10)
                .add(MaterialItems.PEA_GENE,7)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(60,MaterialItems.GENERAL_SEED,5)
                .cost(10)
                .add(MaterialItems.PEA_GENE,8)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(70,MaterialItems.GENERAL_SEED,4)
                .cost(10)
                .add(MaterialItems.PEA_GENE,9)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(80,MaterialItems.GENERAL_SEED,3)
                .cost(10)
                .add(MaterialItems.PEA_GENE,10)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(90,MaterialItems.GENERAL_SEED,2)
                .cost(10)
                .add(MaterialItems.PEA_GENE,11)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(100,MaterialItems.GENERAL_SEED,1)
                .cost(10)
                .add(MaterialItems.PEA_GENE,12)
                .add(MaterialItems.NUT_GENE,2)
                .build();

    }

    private void genRecipe(DaveTrades.Trade trade){
        JsonElement res = parseCodec(DaveTrades.Trade.CODEC.encodeStart(JavaOps.INSTANCE,trade));
        addJson(res.getAsJsonObject(),trade.result(),"");
    }

    private Builder gen(int weight,Supplier<ItemStack> result){
        return new Builder(result,weight);
    }
    private Builder gen(int weight,DeferredItem<Item> result, int amount){
        return new Builder(() -> result.toStack(amount),weight);
    }
    private Builder gen(int weight,DeferredItem<Item> result){
        return gen(weight,result,1);
    }
    private class Builder {
        private int money;
        private final int weight;
        private final Supplier<ItemStack> result;
        private final List<ItemStack> ingredients = new ArrayList<>();
        public Builder(Supplier<ItemStack> result, int weight){
            this.result = result;
            this.weight = weight;
        }

        public Builder cost(int money){
            this.money = money;
            return this;
        }

        public Builder add(ItemStack it){
            ingredients.add(it);
            return this;
        }

        public Builder add(DeferredItem<Item> it, int amount){
            ingredients.add(it.toStack(amount));
            return this;
        }
        public Builder add(DeferredItem<Item> it){
            return add(it,1);
        }

        public void build(){
            genRecipe(new DaveTrades.Trade(weight,money,ingredients,result.get()));
        }

    }

    @Override
    protected String pathSuffix() {
        return "dave_shop";
    }

    @Override
    public String getName() {
        return "Dve Shop Recipes";
    }

    protected Path getRoot(ResourceLocation loc){
        return this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(loc.getNamespace()).resolve("dave_shop");
    }
}