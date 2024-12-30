package rhymestudio.rhyme.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.ModRecipes;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = Rhyme.space("jei_plugin");
    public static final ResourceLocation ARROW_RIGHT = Rhyme.space("textures/gui/arrow.png");
    public static final JeiBackGround HALF_BACKGROUND = new JeiBackGround(128, 64, null);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        registration.addRecipeCategories(new SunCreatorCategory(jeiHelpers));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        RecipeManager recipeManager = level.getRecipeManager();
        registration.addRecipes(SunCreatorCategory.TYPE, recipeManager.getAllRecipesFor(ModRecipes.SUN_CREATOR_TYPE.get()).stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(BuiltInRegistries.ITEM.get(ModBlocks.SUN_CREATOR_BLOCK_ENTITY.getId()).getDefaultInstance(), SunCreatorCategory.TYPE);
    }

    public static void drawArrowRight(GuiGraphics guiGraphics, int x, int y, boolean usable) {
        guiGraphics.blit(ARROW_RIGHT, x, y+3, 0, usable ? 0 : 21, 22, 15, 22, 15);
    }

    public static void addInput(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient) {
        if (!ingredient.isEmpty()) {
            if (ingredient.getCustomIngredient() instanceof AmountIngredient amountIngredient) {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(VanillaTypes.ITEM_STACK, amountIngredient.getItems().toList());
            } else {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
            }
        }
    }
}
