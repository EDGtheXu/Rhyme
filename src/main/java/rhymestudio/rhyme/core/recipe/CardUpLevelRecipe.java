package rhymestudio.rhyme.core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.registry.ModRecipes;

public class CardUpLevelRecipe implements Recipe<Container> {
    public ResourceLocation id;
    public final AmountIngredient am_addition;
    public final Ingredient base;
    public final AmountIngredient am_addition1;
    public final AmountIngredient am_template;
    public ItemStack result;

    public CardUpLevelRecipe(ResourceLocation pId, AmountIngredient addition, Ingredient base, AmountIngredient addition1,AmountIngredient template, ItemStack result) {
         this.id = pId;
        this.base = base;
        this.result = result;
        this.am_template = template;
        this.am_addition = addition;
        this.am_addition1 = addition1;
    }

    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width >= 4 && height >= 1;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean matches(Container input, Level level) {
        if (!(
                this.am_template.test(input.getItem(0)) &&
                        this.base.test(input.getItem(1)) &&
                        this.am_addition.test(input.getItem(2)) &&
                        this.am_addition1.test(input.getItem(3))
        ))
            return false;

        var data = input.getItem(1).getTag();
        var dataRes = this.getResultItem(null).getTag();
        return data != null && dataRes != null && data.getInt("level") == dataRes.getInt("level") - 1
//                || dataRes.getInt("level") == 0 && input.base().get(DataComponents.UNBREAKABLE)==null
                ;
    }

    @Override
    public ItemStack assemble(Container input, RegistryAccess registryAccess){
        ItemStack result = input.getItem(1).copyWithCount(this.result.getCount());

        ItemStack itemstack = result.copy();
        if(itemstack.hasTag() && itemstack.getTag().contains("level"))
            itemstack.getTag().putInt("level", input.getItem(1).getTag().getInt("level") + 1);
//        itemstack.applyComponents(result.getComponentsPatch());
//        int damage = itemstack.getComponents().get(DataComponents.MAX_DAMAGE).intValue();
//        damage += 5;
//        itemstack.set(DataComponents.MAX_DAMAGE, damage);
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
        public CardUpLevelRecipe fromJson(ResourceLocation id, JsonObject json) {
            AmountIngredient addition = AmountIngredient.fromJson(json,"addition");
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(json, "base"));
            AmountIngredient addition1 = AmountIngredient.fromJson(json,"addition2");
            AmountIngredient template = AmountIngredient.fromJson(json,"template");
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new CardUpLevelRecipe(id, addition, base, addition1, template, itemstack);
        }

        public CardUpLevelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            AmountIngredient template = AmountIngredient.fromNetwork(buf);
            Ingredient base = Ingredient.fromNetwork(buf);
            AmountIngredient addition = AmountIngredient.fromNetwork(buf);
            AmountIngredient addition1 = AmountIngredient.fromNetwork(buf);
            ItemStack itemstack = buf.readItem();
            return new CardUpLevelRecipe(id, addition, base, addition1, template, itemstack);
        }

        public void toNetwork(FriendlyByteBuf buf, CardUpLevelRecipe recipe) {
            recipe.am_template.toNetwork(buf);
            recipe.base.toNetwork(buf);
            recipe.am_addition.toNetwork(buf);
            recipe.am_addition1.toNetwork(buf);
            buf.writeItem(recipe.result);
        }

    }

}
