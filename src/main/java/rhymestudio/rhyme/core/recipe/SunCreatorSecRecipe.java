package rhymestudio.rhyme.core.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.NonNullList;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.registry.ModRecipes;

public class SunCreatorSecRecipe extends AbstractAmountRecipe {
    public Ingredient left;
    public Ingredient right;
    public SunCreatorSecRecipe(ResourceLocation id,ItemStack pResult, Ingredient left, Ingredient right) {
        super(id,pResult, NonNullList.of(AmountIngredient.EMPTY, left, right));
        this.left = left;
        this.right = right;
    }

    @Override
    protected int maxIngredientSize() {
        return 2;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return super.getToastSymbol();
    }

    @Override
    public @NotNull RecipeSerializer<SunCreatorSecRecipe> getSerializer() {
        return ModRecipes.SUN_CREATOR_SEC_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<SunCreatorSecRecipe> getType() {
        return ModRecipes.SUN_CREATOR_SEC_TYPE.get();
    }

    public static class Serializer extends AbstractAmountRecipe.Serializer<SunCreatorSecRecipe> {

        @Override
        protected SunCreatorSecRecipe newInstance(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
            return new SunCreatorSecRecipe(pId, pResult, pIngredients.get(0), pIngredients.get(1));
        }


        public SunCreatorSecRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient left = Ingredient.fromJson(json.get("left").getAsJsonObject(),false);
            Ingredient right = Ingredient.fromJson(json.get("right").getAsJsonObject(),false);
            ItemStack res = ItemStack.CODEC.decode(JsonOps.INSTANCE,GsonHelper.getAsJsonObject(json, "result")).result().get().getFirst();
            return new SunCreatorSecRecipe(id, res, left, right);
        }

        public SunCreatorSecRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient left = AmountIngredient.fromNetwork(buf);
            Ingredient right = AmountIngredient.fromNetwork(buf);
            ItemStack res = buf.readItem();
            return new SunCreatorSecRecipe(id, res, left, right);
        }

        public void toNetwork(FriendlyByteBuf buf, SunCreatorSecRecipe recipe) {
            recipe.left.toNetwork(buf);
            recipe.right.toNetwork(buf);
            buf.writeItem(recipe.result);
        }

    }
}
