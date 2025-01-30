//package rhymestudio.rhyme.plugin.jei;
//
//import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
//import mezz.jei.api.helpers.IJeiHelpers;
//import mezz.jei.api.recipe.IFocusGroup;
//import mezz.jei.api.recipe.RecipeIngredientRole;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import org.jetbrains.annotations.NotNull;
//import rhymestudio.rhyme.Rhyme;
//import rhymestudio.rhyme.client.render.gui.SunCreatorCreatorWidget;
//import rhymestudio.rhyme.core.recipe.SunCreatorSecRecipe;
//import rhymestudio.rhyme.core.registry.ModBlocks;
//
//import java.util.List;
//
//public class SunCreatorSecCategory implements IRecipeCategory<SunCreatorSecRecipe> {
//    public static final RecipeType<SunCreatorSecRecipe> TYPE = RecipeType.create(Rhyme.MODID, "sun_creator_sec", SunCreatorSecRecipe.class);
//    private static final Component TITLE = Component.translatable("container.rhyme.sun_creator");
//    private final IDrawable icon;
//
//    public SunCreatorSecCategory(IJeiHelpers jeiHelpers) {
//        this.icon = jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(ModBlocks.SUN_CREATOR_BLOCK.get()));
//    }
//
//    @Override
//    public @NotNull RecipeType<SunCreatorSecRecipe> getRecipeType() {
//        return TYPE;
//    }
//
//    @Override
//    public @NotNull Component getTitle() {
//        return TITLE;
//    }
//
//    @Override
//    public @NotNull IDrawable getBackground() {
//        return ModJeiPlugin.HALF_BACKGROUND;
//    }
//
//    @Override
//    public @NotNull IDrawable getIcon() {
//        return icon;
//    }
//
//    @Override
//    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull SunCreatorSecRecipe recipe, @NotNull IFocusGroup focusGroup) {
//        // input
//        List<Ingredient> ings = recipe.getIngredients();
//        ModJeiPlugin.addInput(builder, 37, 39, ings.get(0));
//        ModJeiPlugin.addInput(builder, 75, 39, ings.get(1));
//
//
//        // output
//        builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 15).addItemStack(recipe.getResultItem(null));
//    }
//
//    @Override
//    public void draw(@NotNull SunCreatorSecRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        ResourceLocation bg = SunCreatorCreatorWidget.BACKGROUND;
//        guiGraphics.blit(bg, 30, 10, 40, 20, 70, 50);
//        int h = 30;
//        float percent = System.currentTimeMillis() % 5000 / 5000f;
//        int realH = (int) (percent*h);
//        int realY = 22+ h - realH;
//        guiGraphics.blit(bg, 30, realY, 177, h - realH, 70, realH);
//
//    }
//}
