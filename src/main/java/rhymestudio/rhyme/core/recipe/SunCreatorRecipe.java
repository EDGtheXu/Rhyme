package rhymestudio.rhyme.core.recipe;

import net.minecraft.core.NonNullList;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.ModRecipes;

public class SunCreatorRecipe extends AbstractAmountRecipe {
    public SunCreatorRecipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pResult, pIngredients);
    }

    @Override
    protected int maxIngredientSize() {
        return 12;
    }

    @Override
    public @NotNull String getGroup() {
        return "sun_creator";
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.SUN_CREATOR_BLOCK.get().asItem());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.SUN_CREATOR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.SUN_CREATOR_TYPE.get();
    }

    public static class Serializer extends AbstractAmountRecipe.Serializer<SunCreatorRecipe> {
        @Override
        protected SunCreatorRecipe newInstance(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
            return new SunCreatorRecipe(pId, pResult, pIngredients);
        }
    }

    public static class Type implements RecipeType<SunCreatorRecipe> {
        @Override
        public String toString() {
            return "rhyme:sun_creator";
        }
    }
}
