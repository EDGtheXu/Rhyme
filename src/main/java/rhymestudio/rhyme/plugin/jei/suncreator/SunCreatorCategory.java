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
import rhymestudio.rhyme.core.recipe.SunCreatorRecipe;
import rhymestudio.rhyme.core.registry.ModBlocks;

import static rhymestudio.rhyme.plugin.jei.suncreator.ModJeiPlugin.ARROW_RIGHT;

public class SunCreatorCategory implements IRecipeCategory<SunCreatorRecipe> {
    public static final RecipeType<SunCreatorRecipe> TYPE = RecipeType.create(Rhyme.MODID, "sun_creator", SunCreatorRecipe.class);
    private static final Component TITLE = Component.translatable("container.rhyme.sun_creator");
    private final IDrawable icon;

    public SunCreatorCategory(IJeiHelpers jeiHelpers) {
        this.icon = jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(ModBlocks.SUN_CREATOR_BLOCK.get()));
    }

    @Override
    public @NotNull RecipeType<SunCreatorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return ModJeiPlugin.HALF_BACKGROUND;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull SunCreatorRecipe recipe, @NotNull IFocusGroup focusGroup) {
        // input
        int size = recipe.getIngredients().size();
        int line = size / 3;
        boolean remain = size % 3 != 0;
        int x = 0;
        int y = 0;
        if (line == 0) {
            y = 24;
        } else if (line == 1) {
            y = remain ? 16 : 24;
        } else if (line == 2) {
            y = remain ? 8 : 16;
        } else if (line == 3) {
            y = remain ? 0 : 8;
        }
        for (Ingredient ingredient : recipe.getIngredients()) {
            ModJeiPlugin.addInput(builder, x, y, ingredient);
            x += 16;
            if (x == 48) {
                x = 0;
                y += 16;
            }
        }
        // output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 24).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(@NotNull SunCreatorRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(ARROW_RIGHT, 50, 25, 0, 0, 22, 15, 22, 15);
    }
}
