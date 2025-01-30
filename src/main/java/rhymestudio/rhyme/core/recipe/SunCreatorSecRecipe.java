package rhymestudio.rhyme.core.recipe;

import net.minecraft.core.NonNullList;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
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
        return 12;
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

    }
}
