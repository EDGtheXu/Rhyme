package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JavaOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.config.Codec.ICodec;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SunCreatorRecipeProvider implements DataProvider {
    private final PackOutput output;
    private List<CompletableFuture<?>> futures = new ArrayList<>();
    public SunCreatorRecipeProvider(PackOutput output) {
        this.output = output;
    }
    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {


        // example recipe for sun creator
        genRecipe(
                PlantItems.REPEATER_ITEM::toStack,
                List.of(
                        new AmountIngredient( Ingredient.of(MaterialItems.SOLID_SUN.toStack()),5),
                        new AmountIngredient( Ingredient.of(MaterialItems.PLANT_GENE.toStack()),1)
                ), cachedOutput);



        return CompletableFuture.allOf( futures.toArray(CompletableFuture[]::new));
    }
    protected Path getPath(ResourceLocation loc) {
        return this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(loc.getNamespace()).resolve("recipe").resolve(loc.getPath() + "_gen_creator.json");
    }
    @Override
    public String getName() {
        return "Sun Creator Recipe Provider: "+ Rhyme.MODID;
    }

    public void genRecipe(Supplier<ItemStack> result, List<AmountIngredient> ingredients, CachedOutput cachedOutput){
        JsonObject obj = new JsonObject();
        obj.addProperty("type", ModRecipes.SUN_CREATOR_ID);

        JsonArray arr = new JsonArray();
        for(AmountIngredient i : ingredients){
            JsonElement ingres;
            if(i.amount() > 1){
                var ing = AmountIngredient.CODEC.encoder().encodeStart(JavaOps.INSTANCE, i).result().get();
                ingres = JsonParser.parseString(ICodec.getGson().toJson(ing));
                ingres.getAsJsonObject().addProperty("type", ModRecipes.AMOUNT_INGREDIENT_ID);
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

        futures.add(DataProvider.saveStable(cachedOutput,obj, getPath(result.get().getItemHolder().getKey().location())));
    }
}
