package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;
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
                .left(Ingredient.of(MaterialItems.GENERAL_SEED.get()))
                .right(Ingredient.of(Items.BONE_MEAL))
                .build();

    }

    private Builder genSec(Supplier<ItemStack> result){
        return new Builder(result);
    }

    private Builder genSec(RegistryObject<Item> result){
        return genSec(result,1);
    }

    private Builder genSec(RegistryObject<Item> result, int amount){
        ItemStack stack = result.get().getDefaultInstance();
        stack.setCount(amount);
        return genSec(()->stack);
    }

    private void genSecJson(Ingredient left, Ingredient right, ItemStack result, String suffix){
        JsonObject res = new JsonObject();
        res.add("left",left.toJson());
        res.add("right",right.toJson());
        res.addProperty("type",MODID + ":" + ModRecipes.SUN_CREATOR_SEC_ID);
        res.add("result",parseCodec(ItemStack.CODEC.encodeStart(JsonOps.INSTANCE,result)));
        addJson(res,result,suffix);
    }

    private void genSecJson(Ingredient left, Ingredient right, ItemStack result){
        genSecJson(left,right,result,"");
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
        public Builder right(Ingredient ing){
            ingredients.set(1,ing);
            return this;
        }
        public void build(){
            build("");
        }
        public void build(String suffix){
            genSecJson(ingredients.get(0),ingredients.get(1),result.get(),suffix);
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