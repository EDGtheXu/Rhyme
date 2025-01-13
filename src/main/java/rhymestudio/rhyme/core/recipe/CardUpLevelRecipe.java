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

public class CardUpLevelRecipe implements Recipe<CardUpLevelRecipe.CardUpLevelRecipeInput> {

    public final AmountIngredient am_addition;
    public final Ingredient base;
    public final AmountIngredient am_addition1;
    public final AmountIngredient am_template;

    public ItemStack result;

    public CardUpLevelRecipe(AmountIngredient addition, Ingredient base, AmountIngredient addition1,AmountIngredient template, ItemStack result) {

        this.base = base;
        this.result = result;
        this.am_template = template;
        this.am_addition = addition;
        this.am_addition1 = addition1;
    }

    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width >= 4 && height >= 1;
    }

    @Override
    public boolean matches(CardUpLevelRecipeInput input, Level level) {
        if (!(
                this.am_template.test(input.template()) &&
                        this.base.test(input.base()) &&
                        this.am_addition.test(input.addition()) &&
                        this.am_addition1.test(input.addition2())
        ))
            return false;
        var data = input.getItem(1).getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        var dataRes = this.getResultItem(null).getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        return data != null && dataRes != null && data.level() == dataRes.level() - 1 || dataRes.level() == 0 && input.base().get(DataComponents.UNBREAKABLE)==null;
    }

    @Override
    public ItemStack assemble(CardUpLevelRecipeInput input, HolderLookup.Provider var2){
        ItemStack result = input.base().transmuteCopy(this.result.getItem(), this.result.getCount());
        result.applyComponents(this.result.getComponentsPatch());

        ItemStack itemstack = input.base().transmuteCopy(input.base().getItem(), input.base().getCount());
        itemstack.applyComponents(result.getComponentsPatch());
        int damage = itemstack.getComponents().get(DataComponents.MAX_DAMAGE).intValue();
        damage += 5;
        itemstack.set(DataComponents.MAX_DAMAGE, damage);
        return itemstack;
    }
    public boolean isTemplateIngredient(ItemStack stack) {
        return this.am_template.test(stack);
    }

    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack);
    }

    public boolean isAdditionIngredient(ItemStack stack) {
        return this.am_addition.test(stack);
    }

    public boolean isAddition1Ingredient(ItemStack stack) {
        return this.am_addition1.test(stack);
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
        private static final MapCodec<CardUpLevelRecipe> CODEC = RecordCodecBuilder.mapCodec(ins -> ins.group(
                AmountIngredient.CODEC.fieldOf("addition").forGetter((p_301310_) -> p_301310_.am_addition),
                Ingredient.CODEC.fieldOf("base").forGetter((p_300938_) -> p_300938_.base),
                AmountIngredient.CODEC.fieldOf("addition1").forGetter((p_301153_) -> p_301153_.am_addition1),
                AmountIngredient.CODEC.fieldOf("template").forGetter((p_301156_) -> p_301156_.am_template),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter((p_300935_) -> p_300935_.getResultItem(null))
        ).apply(ins, CardUpLevelRecipe::new));
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
            AmountIngredient ingredient3 = AmountIngredient.STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            return new CardUpLevelRecipe(ingredient, ingredient1, ingredient2,ingredient3, itemstack);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CardUpLevelRecipe recipe) {
            AmountIngredient.STREAM_CODEC.encode(buffer, recipe.am_addition);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            AmountIngredient.STREAM_CODEC.encode(buffer, recipe.am_addition1);
            AmountIngredient.STREAM_CODEC.encode(buffer, recipe.am_template);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.getResultItem(null));
        }
    }



    public record CardUpLevelRecipeInput(ItemStack addition, ItemStack base, ItemStack addition2,ItemStack template) implements RecipeInput {
        public CardUpLevelRecipeInput(ItemStack addition, ItemStack base, ItemStack addition2, ItemStack template) {
            this.template = template;
            this.base = base;
            this.addition = addition;
            this.addition2 = addition2;
        }

        public ItemStack getItem(int p_346205_) {
            ItemStack var10000;
            switch (p_346205_) {
                case 0 -> var10000 = this.addition;
                case 1 -> var10000 = this.base;
                case 2 -> var10000 = this.addition2;
                case 3 -> var10000 = this.template;
                default -> throw new IllegalArgumentException("Recipe does not contain slot " + p_346205_);
            }

            return var10000;
        }

        public int size() {
            return 4;
        }

        public boolean isEmpty() {
            return this.template.isEmpty() && this.base.isEmpty() && this.addition.isEmpty();
        }

        public ItemStack template() {
            return this.template;
        }

        public ItemStack base() {
            return this.base;
        }

        public ItemStack addition() {
            return this.addition;
        }

        public ItemStack addition2() {
            return this.addition2;
        }
    }
}
