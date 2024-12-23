package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.icu.impl.Pair;
import com.mojang.serialization.JavaOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import rhymestudio.rhyme.config.Codec.ICodec;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.registry.ModRecipes;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class SunCreatorRecipeProvider implements DataProvider {
    private final PackOutput output;
    private List<Pair<JsonObject,ItemStack>> jsons = new ArrayList<>();
    private List<CompletableFuture<?>> futures = new ArrayList<>();
    public SunCreatorRecipeProvider(PackOutput output) {
        this.output = output;
    }
    void run(){

        //向日葵
        gen(PlantItems.SUN_FLOWER)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.SOLID_SUN,3).build();

        //豌豆
        gen(PlantItems.PEA_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.PEA_GENE,6).build();

        gen(PlantItems.SNOW_PEA_ITEM)
                .add(PlantItems.PEA_ITEM)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.SNOW_GENE,3).build();

        gen(PlantItems.REPEATER_ITEM)
                .add(PlantItems.PEA_ITEM)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.PEA_GENE,6).build();

        //土豆
        gen(PlantItems.POTATO_MINE_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.POTATO_GENE,4).build();

        //蘑菇
        gen(PlantItems.PUFF_SHROOM_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.SPORE_GENE,3).build();

        //坚果
        gen(PlantItems.NUT_WALL_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.NUT_GENE,4).build();

        //卷心菜
        gen(PlantItems.CABBAGE_PULT_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.PLANT_GENE)
                .add(MaterialItems.CABBAGE_GENE,6).build();


    }
    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        run();
        jsons.forEach(pair -> {
            var obj = pair.first;
            var result = pair.second;
            futures.add(DataProvider.saveStable(cachedOutput,obj, getPath(result.getItemHolder().getKey().location())));
        });
        return CompletableFuture.allOf( futures.toArray(CompletableFuture[]::new));
    }
    protected Path getPath(ResourceLocation loc) {
        return this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(loc.getNamespace()).resolve("recipe").resolve(loc.getPath() + "_gen_creator.json");
    }
    @Override
    public String getName() {
        return "Sun Creator Recipe Provider: "+ MODID;
    }

    public void genRecipe(Supplier<ItemStack> result, List<AmountIngredient> ingredients){
        JsonObject obj = new JsonObject();
        obj.addProperty("type",MODID + ":" + ModRecipes.SUN_CREATOR_ID);

        JsonArray arr = new JsonArray();
        for(AmountIngredient i : ingredients){
            JsonElement ingres;
            if(i.amount() > 1){
                var ing = AmountIngredient.CODEC.encoder().encodeStart(JavaOps.INSTANCE, i).result().get();
                ingres = JsonParser.parseString(ICodec.getGson().toJson(ing));
                ingres.getAsJsonObject().addProperty("type",MODID + ":" + ModRecipes.AMOUNT_INGREDIENT_ID);
            }else if(i.amount() == 1){
                var ing = Ingredient.CODEC.encodeStart(JavaOps.INSTANCE, i.ingredient()).result().get();
                ingres = JsonParser.parseString(ICodec.getGson().toJson(ing));
            }else continue;
            arr.add(ingres);
        }
        obj.add("ingredients", arr);

        var a = ItemStack.CODEC.encodeStart(JavaOps.INSTANCE,result.get()).result().get();
        JsonElement resit = JsonParser.parseString(ICodec.getGson().toJson(a));
        obj.add("result",resit);

        jsons.add(Pair.of(obj,result.get()));
//        futures.add(DataProvider.saveStable(cachedOutput,obj, getPath(result.get().getItemHolder().getKey().location())));
    }

    public AmountIngredientBuilder gen(Supplier<ItemStack> result){
        return new AmountIngredientBuilder(result);
    }
    public AmountIngredientBuilder gen(DeferredItem<Item> result){
        return new AmountIngredientBuilder(result.get()::getDefaultInstance);
    }
    public AmountIngredientBuilder gen(DeferredItem<Item> result, int count){
        return new AmountIngredientBuilder(()->result.toStack(count));
    }

    public class AmountIngredientBuilder {
        Supplier<ItemStack> result;
        List<AmountIngredient> ingredients = new ArrayList<>();
        public AmountIngredientBuilder(Supplier<ItemStack> result){
            this.result = result;
        }


        public AmountIngredientBuilder add(Ingredient ingredient){
            ingredients.add(new AmountIngredient(ingredient,1));
            return this;
        }
        public AmountIngredientBuilder add(Ingredient ingredient, int amount){
            ingredients.add(new AmountIngredient(ingredient,amount));
            return this;
        }
        public AmountIngredientBuilder add(ItemLike ingredient){
            ingredients.add(new AmountIngredient(Ingredient.of(ingredient),1));
            return this;
        }
        public AmountIngredientBuilder add(ItemLike ingredient, int amount){
            ingredients.add(new AmountIngredient(Ingredient.of(ingredient),amount));
            return this;
        }

        public void build(){
            genRecipe(result,ingredients);
        }
    }
}
