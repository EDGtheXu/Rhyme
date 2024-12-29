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

public class DaveShopProvider extends AbstractRecipeProvider {

    public DaveShopProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void run() {

        gen(MaterialItems.GENERAL_SEED,10)
                .cost(10)
                .add(MaterialItems.PEA_GENE,3)
                .add(MaterialItems.NUT_GENE,2)
                .add(MaterialItems.GENERAL_SEED,1)
                .build();
        gen(MaterialItems.GENERAL_SEED,9)
                .cost(10)
                .add(MaterialItems.PEA_GENE,4)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(MaterialItems.GENERAL_SEED,8)
                .cost(10)
                .add(MaterialItems.PEA_GENE,5)
                .add(MaterialItems.NUT_GENE,2)
                .add(MaterialItems.GENERAL_SEED,1)
                .add(MaterialItems.CABBAGE_GENE,1)
                .build();
        gen(MaterialItems.GENERAL_SEED,7)
                .cost(10)
                .add(MaterialItems.PEA_GENE,6)
                .add(MaterialItems.NUT_GENE,2)
                .build();
        gen(MaterialItems.GENERAL_SEED,6)
                .cost(10)
                .add(MaterialItems.PEA_GENE,7)
                .add(MaterialItems.NUT_GENE,2)
                .build();

    }

    private void genRecipe(DaveTrades.Trade trade){
        JsonElement res = parseCodec(DaveTrades.Trade.CODEC.encodeStart(JavaOps.INSTANCE,trade));
        addJson(res.getAsJsonObject(),trade.give(),"");
    }

    private Builder gen(Supplier<ItemStack> result){
        return new Builder(result);
    }
    private Builder gen(DeferredItem<Item> result, int amount){
        return new Builder(() -> result.toStack(amount));
    }
    private Builder gen(DeferredItem<Item> result){
        return gen(result,1);
    }
    private class Builder {
        int money;
        private final Supplier<ItemStack> result;
        List<ItemStack> ingredients = new ArrayList<>();
        public Builder(Supplier<ItemStack> result){
            this.result = result;
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
            genRecipe(new DaveTrades.Trade(money,ingredients,result.get()));
        }

    }

    @Override
    protected String pathSuffix() {
        return "";
    }

    @Override
    public String getName() {
        return "Dve Shop Recipes";
    }

    protected Path getRoot(ResourceLocation loc){
        return this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(loc.getNamespace()).resolve("dave_shop");
    }
}