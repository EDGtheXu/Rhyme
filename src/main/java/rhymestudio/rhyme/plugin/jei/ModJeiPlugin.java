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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.ModRecipes;

import java.util.Arrays;
import java.util.List;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = Rhyme.space("jei_plugin");
    public static final JeiBackGround HALF_BACKGROUND = new JeiBackGround(128, 64, null);
    public static final JeiBackGround QUARTER_BACKGROUND = new JeiBackGround(128, 24, null);
    public static final ResourceLocation ARROW_RIGHT = Rhyme.space("textures/gui/gray_arrow.png");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        registration.addRecipeCategories(new SunCreatorCategory(jeiHelpers));
        registration.addRecipeCategories(new SunCreatorSecCategory(jeiHelpers));
        registration.addRecipeCategories(new CardUpLevelCategory(jeiHelpers));

    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        RecipeManager recipeManager = level.getRecipeManager();
        registration.addRecipes(SunCreatorCategory.TYPE, recipeManager.getAllRecipesFor(ModRecipes.SUN_CREATOR_TYPE.get()).stream().toList());
        registration.addRecipes(SunCreatorSecCategory.TYPE, recipeManager.getAllRecipesFor(ModRecipes.SUN_CREATOR_SEC_TYPE.get()).stream().toList());
        registration.addRecipes(CardUpLevelCategory.TYPE, recipeManager.getAllRecipesFor(ModRecipes.CARD_UP_LEVEL_TYPE.get()).stream().toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.SUN_CREATOR_BLOCK.get().asItem().getDefaultInstance(), SunCreatorCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.SUN_CREATOR_BLOCK.get().asItem().getDefaultInstance(), SunCreatorSecCategory.TYPE);
        registration.addRecipeCatalyst(ModBlocks.CARD_UP_LEVEL_BLOCK.get().asItem().getDefaultInstance(), CardUpLevelCategory.TYPE);
    }

    public static void addInput(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient) {
        if (!ingredient.isEmpty()) {
            if (ingredient instanceof AmountIngredient amountIngredient) {
                List<ItemStack> stacks = Arrays.stream(amountIngredient.getItems()).toList();
                for (ItemStack stack : stacks) {
                    stack.setCount(amountIngredient.getCount());
                }
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(VanillaTypes.ITEM_STACK, stacks);
            } else {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
            }
        }
    }
}
