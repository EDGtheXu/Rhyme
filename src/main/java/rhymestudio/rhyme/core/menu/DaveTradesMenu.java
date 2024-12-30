package rhymestudio.rhyme.core.menu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.core.registry.ModMenus;
import rhymestudio.rhyme.mixinauxiliary.IPlayer;
import rhymestudio.rhyme.network.c2s.DaveShopPacket;

public class DaveTradesMenu extends AbstractContainerMenu {
    protected static final int PAYMENT1_SLOT = 0;
    protected static final int PAYMENT2_SLOT = 1;
    protected static final int RESULT_SLOT = 2;
    private static final int INV_SLOT_START = 3;
    private static final int INV_SLOT_END = 30;
    private static final int USE_ROW_SLOT_START = 30;
    private static final int USE_ROW_SLOT_END = 39;
    private static final int SELLSLOT1_X = 136;
    private static final int SELLSLOT2_X = 162;
    private static final int BUYSLOT_X = 220;
    private static final int ROW_Y = 37;
//    private final Merchant trader;
    private final SimpleContainer container;
    public DaveTrades daveTrades = null;

    public int selectedMerchantIndex = -1;
    private boolean showProgressBar;

    public DaveTradesMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, null);
    }

    public DaveTradesMenu(int containerId, Inventory playerInventory, DaveTrades daveTrades) {
        super(ModMenus.DAVE_TRADES_MENU.get(), containerId);
        this.daveTrades = daveTrades;
        if(daveTrades == null) this.daveTrades = ((IPlayer)playerInventory.player).rhyme$getDaveTrades();

        this.container = new SimpleContainer(1);
        this.addSlot(new Slot(this.container, 0, 238, 37){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
            @Override
            public void onTake(Player player, ItemStack stack){
                var d  = ((IPlayer)playerInventory.player).rhyme$getDaveTrades();
                if(selectedMerchantIndex >= 0 && selectedMerchantIndex < d.trades().size())
                    PacketDistributor.sendToServer(new DaveShopPacket(d.trades().get(selectedMerchantIndex)));
                super.onTake(player, stack);
            }
        });

        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 108 + j * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 108 + k * 18, 142));
        }

    }

    @Override
    public boolean clickMenuButton(@NotNull Player pPlayer, int pId) {
        selectedMerchantIndex = pId;
        return true;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public void slotsChanged(Container inventory) {
//        this.tradeContainer.updateSellItem();
        super.slotsChanged(inventory);
    }



    public boolean stillValid(Player player) {
        return true;
    }

    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return false;
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
                this.playTradeSound();
            } else if (index != 0 && index != 1) {
                if (index >= 3 && index < 30) {
                    if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    private void playTradeSound() {


    }

    public void removed(Player player) {
        super.removed(player);

    }

    public boolean showProgressBar() {
        return this.showProgressBar;
    }


}
