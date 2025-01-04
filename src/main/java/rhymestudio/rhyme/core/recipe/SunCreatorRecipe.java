package rhymestudio.rhyme.core.recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.registry.ModRecipes;

public class SunCreatorRecipe extends AbstractAmountRecipe {
    public SunCreatorRecipe(ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pResult, pIngredients);
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
    public @NotNull RecipeSerializer<SunCreatorRecipe> getSerializer() {
        return ModRecipes.SUN_CREATOR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<SunCreatorRecipe> getType() {
        return ModRecipes.SUN_CREATOR_TYPE.get();
    }

    public static class Serializer extends AbstractAmountRecipe.Serializer<SunCreatorRecipe> {
        public static final MapCodec<SunCreatorRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(list -> {
                    Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                    if (ingredients.length == 0) {
                        return DataResult.error(() -> "No ingredients for workshop recipe");
                    } else {
                        return ingredients.length > 12 ? DataResult.error(() -> "Too many ingredients for workshop recipe. The maximum is: 12") : DataResult.success(NonNullList.of(AmountIngredient.EMPTY, ingredients));
                    }
                }, DataResult::success).forGetter(p_300975_ -> p_300975_.ingredients)
        ).apply(instance, SunCreatorRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SunCreatorRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

//        @Override
        protected SunCreatorRecipe newInstance(ItemStack pResult, NonNullList<Ingredient> pIngredients) {
            return new SunCreatorRecipe(pResult, pIngredients);
        }

        @Override
        public @NotNull MapCodec<SunCreatorRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SunCreatorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SunCreatorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(size, AmountIngredient.EMPTY);
            nonnulllist.replaceAll(ignore -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            return new SunCreatorRecipe(itemstack, nonnulllist);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SunCreatorRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }
    }
}
