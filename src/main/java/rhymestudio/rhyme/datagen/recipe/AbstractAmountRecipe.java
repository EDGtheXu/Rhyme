package rhymestudio.rhyme.datagen.recipe;

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
        // 创建一个 Map 来存储 pContainer 中每种物品的数量
        Map<Item, Integer> ingredientCount = new HashMap<>();

        for (int index = 0; index < pContainer.size(); index++) {
            ItemStack itemStack = pContainer.getItem(index);
            if (!itemStack.isEmpty()) {
                ingredientCount.put(itemStack.getItem(), ingredientCount.getOrDefault(itemStack.getItem(), 0) + itemStack.getCount());
            }
        }

        // 创建一个 Map 来存储 ingredients 所需的物品数量
        Map<Item, Integer> requiredCount = new HashMap<>();

        for (Ingredient ingredient : ingredients) {
            ItemStack[] items = ingredient.getItems();
            for (ItemStack item : items) {
                requiredCount.put(item.getItem(), requiredCount.getOrDefault(item.getItem(), 0) + item.getCount());
            }
        }
        // 比较可用数量和所需数量
        for (Map.Entry<Item, Integer> entry : requiredCount.entrySet()) {
            Item requiredItem = entry.getKey();
            int requiredAmount = entry.getValue();
            int availableAmount = ingredientCount.getOrDefault(requiredItem, 0);

            if (availableAmount < requiredAmount) {
                return false; // 如果可用数量小于所需数量，则返回 false
            }
        }

        return true; // 所有成分都满足条件
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput input, HolderLookup.@NotNull Provider registries) {
        extractIngredients(input, ingredients);
        return getResultItem(registries).copy();
    }

    private static void extractIngredients(RecipeInput pContainer, NonNullList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            for (int index = 0; index < pContainer.size(); index++) {
                ItemStack itemStack = pContainer.getItem(index);
                if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                    if (ingredient.getCustomIngredient() instanceof AmountIngredient amountIngredient) {
                        itemStack.shrink(amountIngredient.amount());
                    } else {
                        itemStack.shrink(1);
                    }
                    break;
                }
            }
        }
    }

    public static void extractIngredients(CraftingContainer pContainer, NonNullList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            for (int index = 0; index < pContainer.getContainerSize(); index++) {
                ItemStack itemStack = pContainer.getItem(index);
                if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                    if (ingredient.getCustomIngredient() instanceof AmountIngredient amountIngredient) {
                        itemStack.shrink(amountIngredient.amount());
                    } else {
                        itemStack.shrink(1);
                    }
                    break;
                }
            }
        }
    }

    public ItemStack assemble(RecipeInput container, Level level) {
        return assemble(container, level.registryAccess());
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
