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

public class SunCreatorSecRecipe extends AbstractAmountRecipe {
    public Ingredient left;
    public Ingredient right;
    public SunCreatorSecRecipe(ItemStack pResult, Ingredient left, Ingredient right) {
        super(pResult, NonNullList.of(AmountIngredient.EMPTY, left, right));
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
        public static final MapCodec<SunCreatorSecRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                Ingredient.CODEC_NONEMPTY.fieldOf("left").forGetter(recipe -> recipe.left),
                Ingredient.CODEC_NONEMPTY.fieldOf("right").forGetter(recipe -> recipe.right)
        ).apply(instance, SunCreatorSecRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SunCreatorSecRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);


        @Override
        public @NotNull MapCodec<SunCreatorSecRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SunCreatorSecRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SunCreatorSecRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient ig1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient ig2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            return new SunCreatorSecRecipe(itemstack, ig1, ig2);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SunCreatorSecRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.left);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.right);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }
    }
}
