package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;

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

        gen(200,MaterialItems.GENERAL_SEED,1)
                .cost(10)
                .build();

        gen(100, PlantItems.SUN_FLOWER, 1)
                .cost(100)
                .build();

        gen(100, PlantItems.PEA_ITEM, 1)
                .cost(100)
                .build();

        gen(80, PlantItems.SNOW_PEA_ITEM, 1)
                .cost(125)
                .build();

        gen(80, PlantItems.REPEATER_ITEM, 1)
                .cost(200)
                .build();

        gen(100, PlantItems.PUFF_SHROOM_ITEM, 1)
                .cost(25)
                .build();

        gen(100, PlantItems.POTATO_MINE_ITEM, 1)
                .cost(25)
                .build();

        gen(100, PlantItems.NUT_WALL_ITEM, 1)
                .cost(50)
                .build();

        gen(80, PlantItems.CABBAGE_PULT_ITEM, 1)
                .cost(100)
                .build();

        gen(100, PlantItems.CHOMPER_ITEM, 1)
                .cost(150)
                .build();
    }
    
    private void genRecipe(DaveTrades.Trade trade){
        JsonElement res = parseCodec(DaveTrades.Trade.CODEC.encodeStart(JsonOps.INSTANCE,trade));
        addJson(res.getAsJsonObject(),trade.result(),"");
    }

    private Builder gen(int weight,Supplier<ItemStack> result){
        return new Builder(result,weight);
    }
    private Builder gen(int weight,RegistryObject<Item> result, int amount){
        ItemStack stack = result.get().getDefaultInstance();
        stack.setCount(amount);
        return new Builder(() -> stack,weight);
    }
    private Builder gen(int weight,RegistryObject<Item> result){
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

        public Builder add(RegistryObject<Item> it, int amount){
            ItemStack stack = it.get().getDefaultInstance();
            stack.setCount(amount);
            ingredients.add(stack);
            return this;
        }
        public Builder add(RegistryObject<Item> it){
            return add(it,1);
        }

        public void build(){
            genRecipe(new DaveTrades.Trade(weight,money,ingredients,result.get()));
        }

    }

    @Override
    protected String pathSuffix() {
        return "_dave_shop";
    }

    @Override
    public String getName() {
        return "Dve Shop Recipes";
    }

    protected Path getRoot(ResourceLocation loc){
        return this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(loc.getNamespace()).resolve("dave_shop");
    }
}