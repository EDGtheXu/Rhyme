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
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.component.Unbreakable;
//import net.minecraft.world.item.crafting.Ingredient;
//import org.jetbrains.annotations.NotNull;
//import rhymestudio.rhyme.Rhyme;
//import rhymestudio.rhyme.core.recipe.CardUpLevelRecipe;
//import rhymestudio.rhyme.core.registry.ModBlocks;
//import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
//
//import static rhymestudio.rhyme.plugin.jei.ModJeiPlugin.ARROW_RIGHT;
//
//public class CardUpLevelCategory implements IRecipeCategory<CardUpLevelRecipe> {
//    public static final RecipeType<CardUpLevelRecipe> TYPE = RecipeType.create(Rhyme.MODID, "card_up_level", CardUpLevelRecipe.class);
//    private static final Component TITLE = Component.translatable("container.rhyme.card_up_level");
//    private final IDrawable icon;
//
//    public CardUpLevelCategory(IJeiHelpers jeiHelpers) {
//        this.icon = jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(ModBlocks.CARD_UP_LEVEL_BLOCK.get()));
//    }
//
//    @Override
//    public @NotNull RecipeType<CardUpLevelRecipe> getRecipeType() {
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
//        return ModJeiPlugin.HALF_BACKGROUND ;
//    }
//
//    @Override
//    public @NotNull IDrawable getIcon() {
//        return icon;
//    }
//
//    @Override
//    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CardUpLevelRecipe recipe, @NotNull IFocusGroup focusGroup) {
//        // input
//
//        int x = 0;
//        int y = 25;
//
//        ItemStack res = recipe.getResultItem(null);
//        if(res.get(ModDataComponentTypes.CARD_QUALITY.get()).level()==0){
//            if(!focusGroup.getItemStackFocuses().toList().isEmpty()){
//                res = focusGroup.getItemStackFocuses().toList().get(0).getTypedValue().getIngredient();
//                res.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
//            }
//
//        }else{
//            var level = res.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get()).decreaseLevel();
//            recipe.base.getItems()[0].set(ModDataComponentTypes.CARD_QUALITY, level);
//        }
//
//        Ingredient i1 = Ingredient.of(recipe.am_addition.getItems());
//        Ingredient i2 = Ingredient.of(recipe.am_addition1.getItems());
//        Ingredient i3 = Ingredient.of(recipe.am_template.getItems());
//        ModJeiPlugin.addInput(builder, x, y, i1);
//        ModJeiPlugin.addInput(builder, x+20, y,recipe.base);
//        ModJeiPlugin.addInput(builder, x+40, y, i2);
//        ModJeiPlugin.addInput(builder, x+67, y-20, i3);
//
//        // output
//        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, y).addItemStack(res);
//    }
//
//    @Override
//    public void draw(@NotNull CardUpLevelRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        guiGraphics.blit(ARROW_RIGHT, 65, 25, 0, 0, 22, 15, 22, 15);
//    }
//}
