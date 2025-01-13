package rhymestudio.rhyme.core.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.ModRecipes;

public class CardUpLevelRecipe extends SmithingTransformRecipe {
    public final AmountIngredient am_template;
    public final AmountIngredient am_addition;

    public CardUpLevelRecipe(AmountIngredient template, Ingredient base, AmountIngredient addition, ItemStack result) {
        super(template.ingredient(), base, addition.ingredient(), result);
        this.am_template = template;
        this.am_addition = addition;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        if (!(this.template.test(input.template()) && this.base.test(input.base()) && this.addition.test(input.addition())))
            return false;
        var data = input.getItem(1).getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        var dataRes = this.getResultItem(null).getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        return data != null && dataRes != null && data.level() == dataRes.level() - 1;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider var2){
        ItemStack result = super.assemble(input, var2);
        int damage = result.getComponents().get(DataComponents.MAX_DAMAGE).intValue();
        damage += 5;
        result.set(DataComponents.MAX_DAMAGE, damage);
        return result;
    }

    @Override
    public @NotNull RecipeSerializer<CardUpLevelRecipe> getSerializer() {
        return ModRecipes.CARD_UP_LEVEL_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.CARD_UP_LEVEL_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CardUpLevelRecipe> {
        private static final MapCodec<CardUpLevelRecipe> CODEC = RecordCodecBuilder.mapCodec((p_340782_) -> {
            return p_340782_.group(AmountIngredient.CODEC.fieldOf("template").forGetter((p_301310_) -> {
                return p_301310_.am_template;
            }), Ingredient.CODEC.fieldOf("base").forGetter((p_300938_) -> {
                return p_300938_.base;
            }), AmountIngredient.CODEC.fieldOf("addition").forGetter((p_301153_) -> {
                return p_301153_.am_addition;
            }), ItemStack.STRICT_CODEC.fieldOf("result").forGetter((p_300935_) -> {
                return p_300935_.getResultItem(null);
            })).apply(p_340782_, CardUpLevelRecipe::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, CardUpLevelRecipe> STREAM_CODEC = StreamCodec.of(CardUpLevelRecipe.Serializer::toNetwork, CardUpLevelRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        public MapCodec<CardUpLevelRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, CardUpLevelRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CardUpLevelRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            AmountIngredient ingredient = AmountIngredient.STREAM_CODEC.decode(buffer);
            Ingredient ingredient1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            AmountIngredient ingredient2 = AmountIngredient.STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            return new CardUpLevelRecipe(ingredient, ingredient1, ingredient2, itemstack);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CardUpLevelRecipe recipe) {
            AmountIngredient.STREAM_CODEC.encode(buffer, recipe.am_template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            AmountIngredient.STREAM_CODEC.encode(buffer, recipe.am_addition);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.getResultItem(null));
        }
    }
}
