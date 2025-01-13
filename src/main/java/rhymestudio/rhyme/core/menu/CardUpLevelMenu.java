package rhymestudio.rhyme.core.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rhymestudio.rhyme.core.recipe.CardUpLevelRecipe;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.ModMenus;
import rhymestudio.rhyme.core.registry.ModRecipes;

import javax.annotation.Nullable;
import java.util.List;
import java.util.OptionalInt;

public class CardUpLevelMenu extends ItemCombinerMenu {
    public static final int TEMPLATE_SLOT = 0;
    public static final int BASE_SLOT = 1;
    public static final int ADDITIONAL_SLOT = 2;
    public static final int RESULT_SLOT = 3;
    public static final int TEMPLATE_SLOT_X_PLACEMENT = 8;
    public static final int BASE_SLOT_X_PLACEMENT = 26;
    public static final int ADDITIONAL_SLOT_X_PLACEMENT = 44;
    private static final int RESULT_SLOT_X_PLACEMENT = 98;
    public static final int SLOT_Y_PLACEMENT = 48;
    private final Level level;
    @Nullable
    private RecipeHolder<CardUpLevelRecipe> selectedRecipe;
    private final List<RecipeHolder<CardUpLevelRecipe>> recipes;

    public CardUpLevelMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public CardUpLevelMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenus.CARD_UP_LEVEL_MENU.get(), containerId, playerInventory, access);
        this.level = playerInventory.player.level();
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(ModRecipes.CARD_UP_LEVEL_TYPE.get());
    }

    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 8, 48, slot -> this.recipes.stream().anyMatch(it -> (it.value()).isAdditionIngredient(slot)))
                .withSlot(1, 26, 48, slot -> this.recipes.stream().anyMatch(it-> (it.value()).isBaseIngredient(slot)))
                .withSlot(2, 44, 48, slot -> this.recipes.stream().anyMatch(it -> (it.value()).isAddition1Ingredient(slot)))
                .withSlot(3, 71, 28, slot -> this.recipes.stream().anyMatch(it -> (it.value()).isTemplateIngredient(slot)))
                .withResultSlot(4, 98, 48).build();
    }

    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.CARD_UP_LEVEL_BLOCK.get());
    }

    protected boolean mayPickup(Player player, boolean hasStack) {
        return this.selectedRecipe != null && (this.selectedRecipe.value()).matches(this.createRecipeInput(), this.level);
    }

    protected void onTake(Player player, ItemStack stack) {
        stack.onCraftedBy(player.level(), player, stack.getCount());
        this.resultSlots.awardUsedRecipes(player, this.getRelevantItems());
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        this.shrinkStackInSlot(3);
        this.access.execute((level, pos) -> {
            level.levelEvent(1044, pos, 0);
        });
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(this.inputSlots.getItem(0), this.inputSlots.getItem(1), this.inputSlots.getItem(2), this.inputSlots.getItem(3));
    }

    private CardUpLevelRecipe.CardUpLevelRecipeInput createRecipeInput() {
        return new CardUpLevelRecipe.CardUpLevelRecipeInput(this.inputSlots.getItem(0), this.inputSlots.getItem(1), this.inputSlots.getItem(2),this.inputSlots.getItem(3));
    }

    private void shrinkStackInSlot(int index) {
        ItemStack itemstack = this.inputSlots.getItem(index);
        if (!itemstack.isEmpty()) {
            CardUpLevelRecipe recipe = selectedRecipe.value();
            if (index == 0) {
                itemstack.shrink(recipe.am_addition.amount());
            } else if (index == 1) {
                itemstack.shrink(recipe.base.getItems()[0].getCount());
            } else if (index == 2) {
                itemstack.shrink(recipe.am_addition1.amount());
            } else if(index == 3){
                itemstack.shrink(recipe.am_template.amount());
            }

            this.inputSlots.setItem(index, itemstack);
        }
    }

    public void createResult() {
        CardUpLevelRecipe.CardUpLevelRecipeInput input = this.createRecipeInput();
        List<RecipeHolder<CardUpLevelRecipe>> list = this.level.getRecipeManager().getRecipesFor(ModRecipes.CARD_UP_LEVEL_TYPE.get(), input, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            RecipeHolder<CardUpLevelRecipe> recipeholder = list.get(0);
            ItemStack itemstack = recipeholder.value().assemble(input, this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.selectedRecipe = recipeholder;
                this.resultSlots.setRecipeUsed(recipeholder);
                this.resultSlots.setItem(0, itemstack);
            }
        }

    }

    public int getSlotToQuickMoveTo(ItemStack stack) {
        return this.findSlotToQuickMoveTo(stack).orElse(0);
    }

    private static OptionalInt findSlotMatchingIngredient(CardUpLevelRecipe recipe, ItemStack stack) {
        if (recipe.isBaseIngredient(stack)) {
            return OptionalInt.of(1);
        } else if (recipe.isAddition1Ingredient(stack)) {
            return OptionalInt.of(2);
        }else if (recipe.isTemplateIngredient(stack)) {
            return OptionalInt.of(3);
        } else {
            return recipe.isAdditionIngredient(stack) ? OptionalInt.of(0) : OptionalInt.empty();
        }
    }

    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public boolean canMoveIntoInputSlots(ItemStack stack) {
        return this.findSlotToQuickMoveTo(stack).isPresent();
    }

    private OptionalInt findSlotToQuickMoveTo(ItemStack stack) {
        return this.recipes.stream()
                .flatMapToInt((recipeHolder) -> findSlotMatchingIngredient(recipeHolder.value(), stack).stream())
                .filter((i) -> !this.getSlot(i).hasItem())
                .findFirst();
    }
}
