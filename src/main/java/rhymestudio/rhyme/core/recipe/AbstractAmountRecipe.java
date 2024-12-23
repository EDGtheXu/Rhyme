package rhymestudio.rhyme.core.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractAmountRecipe implements Recipe<RecipeInput> {
    protected final ItemStack result;
    protected final NonNullList<Ingredient> ingredients;

    protected AbstractAmountRecipe(ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.result = pResult;
        this.ingredients = pIngredients;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@Nullable Provider registries) {
        return result;
    }

    @Override
    public boolean matches(@NotNull RecipeInput pContainer, @NotNull Level pLevel) {
        // pContainer
        Map<Item, Integer> ingredientCount = new HashMap<>();
        for (int index = 0; index < pContainer.size(); index++) {
            ItemStack itemStack = pContainer.getItem(index);
            if (!itemStack.isEmpty()) {
                ingredientCount.put(itemStack.getItem(), ingredientCount.getOrDefault(itemStack.getItem(), 0) + itemStack.getCount());
            }
        }
        // ingredients
        Map<Item, Integer> requiredCount = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            ItemStack[] items = ingredient.getItems();
            for (ItemStack item : items) {
                requiredCount.put(item.getItem(), requiredCount.getOrDefault(item.getItem(), 0) + item.getCount());
            }
        }
        // 比较
        for (Map.Entry<Item, Integer> entry : requiredCount.entrySet()) {
            Item requiredItem = entry.getKey();
            int requiredAmount = entry.getValue();
            int availableAmount = ingredientCount.getOrDefault(requiredItem, 0);

            if (availableAmount < requiredAmount) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput input, HolderLookup.@NotNull Provider registries) {
        extractIngredients(input, ingredients);
        return getResultItem(registries).copy();
    }

    public ItemStack assemble(RecipeInput container, Level level) {
        return assemble(container, level.registryAccess());
    }

    private static void extractIngredients(RecipeInput pContainer, NonNullList<Ingredient> ingredients) {
        consumeIngredients(pContainer.size(), pContainer::getItem, ingredients);
    }

    public static void extractIngredients(CraftingContainer pContainer, NonNullList<Ingredient> ingredients) {
        consumeIngredients(pContainer.getContainerSize(), pContainer::getItem, ingredients);
    }

    private static void consumeIngredients(int pContainerSize, Function<Integer, ItemStack> getItemStackCallback, NonNullList<Ingredient> ingredients) {
        // 计算所有需要的原料数量
        Map<ItemStack, Integer> requiredIngredients = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            int requiredAmount = ingredient.getCustomIngredient() instanceof AmountIngredient amountIngredient
                    ? amountIngredient.amount() : 1;
            ItemStack[] items = ingredient.getItems();
            for (ItemStack item : items) {
                requiredIngredients.put(item, requiredIngredients.getOrDefault(item, 0) + requiredAmount);
            }
        }
        // 逐个消耗
        for (Map.Entry<ItemStack, Integer> entry : requiredIngredients.entrySet()) {
            ItemStack requiredItem = entry.getKey();
            int amountToConsume = entry.getValue();
            for (int index = 0; index < pContainerSize; index++) {
                ItemStack itemStack = getItemStackCallback.apply(index);
                if (!itemStack.isEmpty() && requiredItem.getItem() == itemStack.getItem()) {
                    int availableAmount = itemStack.getCount();
                    // 实际消耗
                    int amountToShrink = Math.min(availableAmount, amountToConsume);
                    itemStack.shrink(amountToShrink);
                    amountToConsume -= amountToShrink;
                    if (amountToConsume <= 0) break;
                }
            }
        }
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    protected abstract int maxIngredientSize();

    public static abstract class Serializer<R extends AbstractAmountRecipe> implements RecipeSerializer<R> {
        protected abstract R newInstance(ItemStack pResult, NonNullList<Ingredient> pIngredients);
    }
}
