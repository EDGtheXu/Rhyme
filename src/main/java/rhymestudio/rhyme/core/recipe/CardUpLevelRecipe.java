package rhymestudio.rhyme.core.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponentType;
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
        return this.result.copy();
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
                this.am_template.test(input.getItem(3)) &&

                        this.base.test(input.getItem(1)) &&
                        this.am_addition.test(input.getItem(0)) &&
                        this.am_addition1.test(input.getItem(2))
        ))
            return false;

        var data = input.getItem(1).getTag();
        var dataRes = this.getResultItem(null).getTag();
        boolean accept = data != null && dataRes != null && data.getCompound("card_quality").getInt("level") == dataRes.getCompound("card_quality").getInt("level") - 1
            ||dataRes != null && dataRes.getCompound("card_quality").getInt("level") == 0 && data != null && data.get("Unbreakable")==null;
        return accept
//
                ;
    }

    @Override
    public ItemStack assemble(Container input, RegistryAccess registryAccess){
        ItemStack result = input.getItem(1).copyWithCount(this.result.getCount());

        ItemStack itemstack = result.copy();
        var data = new CardQualityComponentType(getResultItem(null));
        if(data.level == 0){
            itemstack.getOrCreateTag().putBoolean("Unbreakable", true);
            return itemstack;
        }
        result.getOrCreateTag().putInt("max_damage", result.getMaxDamage());
        data.writeToNBT(itemstack.getOrCreateTag());
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
            AmountIngredient addition1 = AmountIngredient.fromJson(json,"addition1");
            AmountIngredient template = AmountIngredient.fromJson(json,"template");
            ItemStack res = ItemStack.CODEC.decode(JsonOps.INSTANCE,GsonHelper.getAsJsonObject(json, "result")).result().get().getFirst();
            return new CardUpLevelRecipe(id, addition, base, addition1, template, res);
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
