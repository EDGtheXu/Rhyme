package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.config.Codec.ICodec;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.registry.ModRecipes;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static rhymestudio.rhyme.Rhyme.MODID;

public abstract class AbstractRecipeProvider implements DataProvider {
    protected PackOutput output;
    private  final List<tuple> jsons;
    private final List<CompletableFuture<?>> futures;
    public AbstractRecipeProvider(PackOutput output) {
        this.output = output;
        this.jsons = new ArrayList<>();
        this.futures = new ArrayList<>();
    }
    private record tuple(JsonObject json, ItemStack result, String suffix) {}
    abstract protected void run();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        run();
        this.jsons.forEach(pair -> {
            var obj = pair.json;
            var result = pair.result;
            var suffix = pair.suffix;
            ResourceLocation loc = ForgeRegistries.ITEMS.getKey(result.getItemHolder().value());
            Path path = getPath(loc, suffix);
            this.futures.add(DataProvider.saveStable(cachedOutput, obj, path));
        });
        return CompletableFuture.allOf(this.futures.toArray(CompletableFuture[]::new));
    }
    protected void addJson(JsonObject json, ItemStack result, String suffix) {
        int sameCount = 0;
        for(var pair : this.jsons){
            if(pair.result.getItem() == result.getItem() && pair.suffix.equals(suffix)){
                sameCount++;
                suffix = "_" + sameCount;
            }
        }
        this.jsons.add(new tuple(json, result, suffix));
    }
    protected Path getPath(ResourceLocation loc, String nameSuffix) {
        return getRoot(loc).resolve(loc.getPath() +"_gen"+pathSuffix()+ nameSuffix+".json");
    }

    protected abstract String pathSuffix();

    protected Path getRoot(ResourceLocation loc){
        return this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(loc.getNamespace()).resolve("recipes");
    }


    protected JsonElement amountIngredientJson(AmountIngredient i){
        JsonElement ingres;
        if(i.getCount() > 1){
            var ing = i.toJson();
//            ing.getAsJsonObject().addProperty("type",amountIngredientType());
            ingres = ing;
        }else if(i.getCount() == 1){
            ingres = i.toJsonIngredient();
        }else ingres = new JsonObject();
        return ingres;
    }
    protected String amountIngredientType(){return MODID + ":" + ModRecipes.AMOUNT_INGREDIENT_ID;}
    protected JsonElement parseCodec(DataResult<?> result){
        return JsonParser.parseString(ICodec.getGson().toJson(result.result().get()));
    }
}
