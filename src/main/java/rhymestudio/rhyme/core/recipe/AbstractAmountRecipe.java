package rhymestudio.rhyme.core.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAmountRecipe implements Recipe<Container> {
    protected final ResourceLocation id;
    protected final ItemStack result;
    protected final NonNullList<Ingredient> ingredients;

    protected AbstractAmountRecipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.result = pResult;
        this.ingredients = pIngredients;
    }

    @Override
    public boolean matches(@NotNull Container pContainer, @NotNull Level pLevel) {
        // pContainer
        Map<Item, Integer> ingredientCount = new HashMap<>();
        for (int index = 0; index < pContainer.getContainerSize(); index++) {
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
    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        extractIngredients(pContainer, ingredients);
        return getResultItem(pRegistryAccess).copy();
    }

    public static void extractIngredients(Container pContainer, NonNullList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            for (int index = 0; index < pContainer.getContainerSize(); index++) {
                ItemStack itemStack = pContainer.getItem(index);
                if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                    if(ingredient instanceof AmountIngredient am)
                        pContainer.removeItem(index, am.getCount());
                    else pContainer.removeItem(index, 1);
                    break;
                }
            }
        }
    }

    public ItemStack assemble(Container container, Level level) {
        return assemble(container, level.registryAccess());
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@Nullable RegistryAccess registryAccess) {
        return result;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    protected abstract int maxIngredientSize();

    public static abstract class Serializer<R extends AbstractAmountRecipe> implements RecipeSerializer<R> {
        protected abstract R newInstance(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients);

        @Override
        public @NotNull R fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject json) {
            ItemStack result = ItemStack.CODEC.decode(JsonOps.INSTANCE, json.get("result")).result().get().getFirst();
            JsonArray jr = json.getAsJsonArray("ingredients");
//            List<Ingredient> ingredients = AmountIngredient.;ItemStack.CODEC.listOf().decode(JsonOps.INSTANCE, pJson.get("ingredients")).result().get().getFirst();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(jr.size(), AmountIngredient.EMPTY);
//            HashSet<Item> items = new HashSet<>();
            for (int i = 0; i < jr.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(jr.get(i).getAsJsonObject());
                nonNullList.set(i, ingredient);
            }
            if (nonNullList.isEmpty()) throw new JsonParseException("No ingredients for " + pRecipeId);
            R recipe = newInstance(pRecipeId, result, nonNullList);
            return recipe;
        }

        @Override
        public @Nullable R fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
            int size = pBuffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, AmountIngredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack result = pBuffer.readItem();
            return newInstance(pRecipeId, result, ingredients);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, @NotNull R pRecipe) {
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for (Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.result);
        }
    }
}