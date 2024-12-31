package rhymestudio.rhyme.core.menu;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class HiddenSlot extends Slot implements IHiddenSlot{
    private boolean isHidden;
    private final int code;
    public HiddenSlot(Container container, int slot, int x, int y,int code) {
        super(container, slot, x, y);
        this.code = code;
    }

    @Override
    public boolean isActive() {
        return !isHidden;
    }
    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public void setHide(int code) {
        setHidden(this.code != code);
    }

    public boolean mayPlace(ItemStack stack) {
        return !isHidden;
    }
}
