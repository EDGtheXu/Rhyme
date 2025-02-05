package rhymestudio.rhyme.core.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.core.recipe.AbstractAmountRecipe;

public class AmountResultSlot extends Slot implements IHiddenSlot{
    protected final CraftingContainer crafting;
    protected @Nullable AbstractAmountRecipe recipe;
    private boolean hidden;
    private int code;
    public AmountResultSlot(CraftingContainer crafting, Container pContainer, int pSlot, int pX, int pY,int code) {
        super(pContainer, pSlot, pX, pY);
        this.crafting = crafting;
        this.code = code;
    }

    public AmountResultSlot(CraftingContainer crafting, Container pContainer, int pSlot, int pX, int pY) {
        this(crafting, pContainer, pSlot, pX, pY,0);
    }

    public void setCurrentRecipe(@Nullable AbstractAmountRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack pStack) {
        return false;
    }

    @Override
    public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
        if (recipe != null) {
            AbstractAmountRecipe.extractIngredients(crafting, recipe.getIngredients());
        }
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public boolean isActive() {
        return !hidden;
    }

    @Override
    public void setHide(int code) {
        setHidden(this.code != code);
    }
}
