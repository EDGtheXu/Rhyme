package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.registry.ModRecipes;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class SunCreatorRecipeProvider extends AbstractRecipeProvider {
    public SunCreatorRecipeProvider(PackOutput output) {
        super(output);
    }

    protected void run(){

        //向日葵
        gen(PlantItems.SUN_FLOWER)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.SOLID_SUN,4).build();

        //豌豆
        gen(PlantItems.PEA_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.PEA_GENE,6).build();

        gen(PlantItems.SNOW_PEA_ITEM)
                .add(PlantItems.PEA_ITEM)
                .add(MaterialItems.SNOW_GENE,6).build();

        gen(PlantItems.REPEATER_ITEM)
                .add(PlantItems.PEA_ITEM)
                .add(MaterialItems.PEA_GENE,6).build();

        //土豆
        gen(PlantItems.POTATO_MINE_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.HIDDEN_GENE,4).build();

        //蘑菇
        gen(PlantItems.PUFF_SHROOM_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.MUSHROOM_GENE,4).build();

        //坚果
        gen(PlantItems.NUT_WALL_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.NUT_GENE,4).build();


        //卷心菜
        gen(PlantItems.CABBAGE_PULT_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.THROWABLE_GENE,6).build();

        //大嘴花
        gen(PlantItems.CHOMPER_ITEM)
                .add(MaterialItems.GENERAL_SEED)
                .add(MaterialItems.STRONG_GENE,6).build();


    }

    protected String pathSuffix(){
        return "_sun_creator";
    }

    @Override
    public String getName() {
        return "Sun Creator Recipe Provider: "+ MODID;
    }

    protected void genRecipe(Supplier<ItemStack> result, List<AmountIngredient> ingredients, String suffix){
        JsonObject obj = new JsonObject();

        obj.addProperty("type",MODID + ":" + ModRecipes.SUN_CREATOR_ID);

        JsonArray arr = new JsonArray();
        ingredients.forEach(i -> arr.add(amountIngredientJson(i)));
        obj.add("ingredients", arr);

        JsonElement resit = parseCodec(ItemStack.CODEC.encodeStart(JsonOps.INSTANCE,result.get()));
        obj.add("result",resit);

        addJson(obj,result.get(),suffix);
//        futures.add(DataProvider.saveStable(cachedOutput,obj, getPath(result.get().getItemHolder().getKey().location())));
    }

    protected AmountIngredientBuilder gen(Supplier<ItemStack> result){
        return new AmountIngredientBuilder(result);
    }
    protected AmountIngredientBuilder gen(RegistryObject<Item> result){
        return gen(result.get()::getDefaultInstance);
    }
    protected AmountIngredientBuilder gen(RegistryObject<Item> result, int count){
        ItemStack stack = result.get().getDefaultInstance();
        stack.setCount(count);
        return gen(()->stack);
    }


    protected class AmountIngredientBuilder {
        Supplier<ItemStack> result;
        List<AmountIngredient> ingredients = new ArrayList<>();
        public AmountIngredientBuilder(Supplier<ItemStack> result){
            this.result = result;
        }


        public AmountIngredientBuilder add(RegistryObject<Item> ingredient){
            return add(ingredient.get());
        }
        public AmountIngredientBuilder add(RegistryObject<Item> ingredient, int amount){
            return add(ingredient.get(),amount);
        }
        public AmountIngredientBuilder add(ItemLike ingredient){
            return add(ingredient,1);
        }

        public AmountIngredientBuilder add(ItemLike ingredient, int amount){
            ItemStack stack = ingredient.asItem().getDefaultInstance();
            stack.setCount(amount);
            ingredients.add(AmountIngredient.of(stack));
            return this;
        }

        public AmountIngredientBuilder add(TagKey<Item> ingredient){
            return add(ingredient,1);
        }
        public AmountIngredientBuilder add(TagKey<Item> ingredient, int amount){
            ingredients.add(AmountIngredient.of(ingredient,amount));
            return this;
        }

        public void build(){
            build("");
        }
        public void build(String suffix){
            genRecipe(result,ingredients,suffix);
        }
    }
}
