package rhymestudio.rhyme.core.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import rhymestudio.rhyme.core.registry.ModMenus;

public class ChapterMenu extends AbstractContainerMenu {

    public ChapterMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.CHAPTER_MENU.get(), containerId);

    }


    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }




}
