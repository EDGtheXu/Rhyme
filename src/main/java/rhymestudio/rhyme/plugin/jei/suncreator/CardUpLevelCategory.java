package rhymestudio.rhyme.plugin.jei.suncreator;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.recipe.CardUpLevelRecipe;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import static rhymestudio.rhyme.plugin.jei.suncreator.ModJeiPlugin.ARROW_RIGHT;

public class CardUpLevelCategory implements IRecipeCategory<CardUpLevelRecipe> {
    public static final RecipeType<CardUpLevelRecipe> TYPE = RecipeType.create(Rhyme.MODID, "card_up_level", CardUpLevelRecipe.class);
    private static final Component TITLE = Component.translatable("container.rhyme.card_up_level");
    private final IDrawable icon;

    public CardUpLevelCategory(IJeiHelpers jeiHelpers) {
        this.icon = jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(ModBlocks.CARD_UP_LEVEL_BLOCK.get()));
    }

    @Override
    public @NotNull RecipeType<CardUpLevelRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return ModJeiPlugin.QUARTER_BACKGROUND ;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CardUpLevelRecipe recipe, @NotNull IFocusGroup focusGroup) {
        // input

        int x = 0;
        int y = 5;

        ItemStack res = recipe.getResultItem(null);

        var level = res.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get()).decreaseLevel();
        recipe.base.getItems()[0].set(ModDataComponentTypes.CARD_QUALITY, level);

        Ingredient i1 = Ingredient.of(recipe.am_template.getItems());
        Ingredient i2 = Ingredient.of(recipe.am_addition.getItems());
        ModJeiPlugin.addInput(builder, x, y, i1);
        ModJeiPlugin.addInput(builder, x+16, y,recipe.base);
        ModJeiPlugin.addInput(builder, x+32, y, i2);

        // output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, y).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(@NotNull CardUpLevelRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(ARROW_RIGHT, 60, 5, 0, 0, 22, 15, 22, 15);
    }
}
