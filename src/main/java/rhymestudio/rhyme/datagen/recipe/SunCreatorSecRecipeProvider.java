package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonElement;
import com.mojang.serialization.JavaOps;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.recipe.SunCreatorSecRecipe;
import rhymestudio.rhyme.core.registry.ModRecipes;
import rhymestudio.rhyme.core.registry.items.MaterialItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;


public class SunCreatorSecRecipeProvider extends SunCreatorRecipeProvider {

    public SunCreatorSecRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void run() {
        genSec(MaterialItems.SOLID_SUN,4)
                .left(Ingredient.of(Items.SUNFLOWER))
                .right(Ingredient.of(Items.BONE_MEAL))
                .build();
        genSec(MaterialItems.SOLID_SUN)
                .left(Ingredient.of(MaterialItems.GENERAL_SEED))
                .right(Ingredient.of(Items.BONE_MEAL))
                .build();

    }

    private Builder genSec(Supplier<ItemStack> result){
        return new Builder(result);
    }

    private Builder genSec(DeferredItem<Item> result){
        return genSec(result,1);
    }

    private Builder genSec(DeferredItem<Item> result, int amount){
        return genSec(()->result.toStack(amount));
    }

    private void genSecJson(SunCreatorSecRecipe recipe, String suffix){
        JsonElement res = parseCodec(SunCreatorSecRecipe.Serializer.CODEC.encoder().encodeStart(JavaOps.INSTANCE,recipe));
        res.getAsJsonObject().addProperty("type",MODID + ":" + ModRecipes.SUN_CREATOR_SEC_ID);
        addJson(res.getAsJsonObject(),recipe.getResultItem(null),suffix);
    }

    private void genSecJson(SunCreatorSecRecipe recipe){
        genSecJson(recipe,"");
    }

    public class Builder {
        Supplier<ItemStack> result;
        List<Ingredient> ingredients;
        public Builder(Supplier<ItemStack> result){
            this.result = result;
            ingredients = new ArrayList<>();
            ingredients.add(Ingredient.EMPTY);
            ingredients.add(Ingredient.EMPTY);
        }

        public Builder left(Ingredient ing){
            ingredients.set(0,ing);
            return this;
        }
        public Builder left(Ingredient ing, int amount){
            ingredients.set(0,new Ingredient(new AmountIngredient(ing,amount)));
            return this;
        }
        public Builder right(Ingredient ing){
            ingredients.set(1,ing);
            return this;
        }
        public Builder right(Ingredient ing, int amount){
            ingredients.set(1,new Ingredient(new AmountIngredient(ing,amount)));
            return this;
        }

        public void build(){
            build("");
        }
        public void build(String suffix){
            genSecJson(new SunCreatorSecRecipe(result.get(),ingredients.get(0),ingredients.get(1)),suffix);
        }
    }

    @Override
    protected String pathSuffix() {
        return "_sun_creator_sec";
    }

    @Override
    public String getName() {
        return "Sun Creator Sec Recipe Provider";
    }

}