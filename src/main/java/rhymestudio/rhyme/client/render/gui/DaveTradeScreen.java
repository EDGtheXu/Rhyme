package rhymestudio.rhyme.client.render.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.menu.DaveTradesMenu;
import rhymestudio.rhyme.mixinauxiliary.IPlayer;
import rhymestudio.rhyme.utils.Computer;

import static rhymestudio.rhyme.client.render.gui.hud.CardHUD.cachedMoney;

public class DaveTradeScreen extends AbstractContainerScreen<DaveTradesMenu> {
    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/scroller_disabled");
    private static final ResourceLocation MENU_LOCATION = Rhyme.space("textures/gui/dave_shop_menu.png");
    private static final int NUMBER_OF_OFFER_BUTTONS = 7;
    private static final Component TRADES_LABEL = Component.translatable("dave.trades");

    private int shopItem = -1;
    private int hoveredItem = -1;
    private int row;
    private final int col = 4;
    private int offsetX;
    private int offsetY;
    private int intervalX = 20;

    int scrollOff;
    private boolean isDragging;

    public DaveTradeScreen(DaveTradesMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 276;
        this.inventoryLabelX = 107;
    }

    @Override
    protected void init() {
        super.init();
        if (menu.daveTrades == null)
            menu.daveTrades = ((IPlayer)Minecraft.getInstance().player).rhyme$getDaveTrades();

        offsetX = (this.width - this.imageWidth) / 2 + 10;
        offsetY = (this.height - this.imageHeight) / 2 + 20;

        this.row = menu.daveTrades.trades().size() / 3;
        if (menu.daveTrades.trades().size() % 3 != 0)
            this.row++;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 49 + this.imageWidth / 2 - this.font.width(this.title) / 2, 6, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle,90 + this.imageWidth / 2, this.inventoryLabelY, 4210752, false);
        int l = this.font.width(TRADES_LABEL);
        guiGraphics.drawString(this.font, TRADES_LABEL, 5 - l / 2 + 48, 6, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(MENU_LOCATION, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);
    }

    private void renderScroller(GuiGraphics guiGraphics, int posX, int posY) {

        int i = menu.daveTrades.trades().size() - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int l = 113;
            int i1 = Math.min(113, this.scrollOff * k);
            if (this.scrollOff == i - 1) {
                i1 = 113;
            }
            guiGraphics.blitSprite(SCROLLER_SPRITE, posX + 94, posY + 18 + i1, 0, 6, 27);
        } else {
            guiGraphics.blitSprite(SCROLLER_DISABLED_SPRITE, posX + 94, posY + 18, 0, 6, 27);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (menu.daveTrades == null){
//            menu.daveTrades = ((IPlayer)Minecraft.getInstance().player).rhyme$getDaveTrades();
            return;
        }
        int ii = (this.width - this.imageWidth) / 2;
        int jj = (this.height - this.imageHeight) / 2;
        this.renderScroller(guiGraphics, ii, jj);

        // 左侧物品
        if(this.hoveredItem >= 0 && this.hoveredItem < menu.daveTrades.trades().size()){
            int x = offsetX + hoveredItem % col * intervalX;
            int y = offsetY + hoveredItem / col * 20;
            renderSlotHighlight(guiGraphics,x,y, 20);
        }
        // 左侧物品
        if(this.shopItem >= 0 && this.shopItem < menu.daveTrades.trades().size()){
            int x = offsetX + shopItem % col * intervalX;
            int y = offsetY + shopItem / col * 20;
            renderSlotHighlight(guiGraphics,x,y, 20);
        }
        var trades = menu.daveTrades.trades();
        int x = offsetX;
        int y = offsetY;
        for (int l = 0; l < Math.min(row, NUMBER_OF_OFFER_BUTTONS); l++) {
            for(int k = 0; k < col; k++){
                int index = k+(l+ scrollOff) * col;
                if(index >= trades.size()) break;
                var it = trades.get(index).result();

                guiGraphics.renderItem(it, x , y );
                if(mouseX > x && mouseX < x+16 && mouseY > y && mouseY < y+16)
                    guiGraphics.renderTooltip(this.font, it, mouseX, mouseY);
                guiGraphics.renderItemDecorations(this.font, it, x, y);
                x+=intervalX;
            }
            x = offsetX;
            y += 20;
        }
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        // 上面的材料物品
        if(shopItem < 0 ||shopItem >= trades.size()){
            return;
        }

        var trade = trades.get(this.shopItem);
        var its = trade.requires();
        boolean canBuy = true;
        int x1 = ii + 190;
        int y1 = jj + 27;
        for (ItemStack it : its) {
            if(x1 < 1 + ii + 190 - 20 * 4)
            {
                x1 = ii + 195;
                y1 += 20;
            }
            x1 -= 20;
            if(mouseX > x1 && mouseX < x1+16 && mouseY > y1 && mouseY < y1+16) {
                guiGraphics.setColor(1, 1, 1, 1);
                guiGraphics.renderTooltip(this.font, it, mouseX, mouseY);
            }

            if (Computer.getInventoryItemCount(minecraft.player, it.getItem()) < it.getCount()) {
                guiGraphics.setColor(1, 0.5f, 0.5f, 1);
                canBuy = false;
            }
            guiGraphics.renderItem(it, x1, y1);
            guiGraphics.renderItemDecorations(this.font, it, x1, y1);
            guiGraphics.setColor(1, 1, 1, 1);
        }

        // 花费金币
        int m = trade.money();
        String numberText = m + " ￥ ";
        if(cachedMoney >= m)guiGraphics.setColor(0.4F, 1, 0.4F, 1);
        else {
            guiGraphics.setColor(0.8F, 0.2F, 0.2F, 1);
            canBuy = false;
        }
        guiGraphics.drawString(this.font, numberText, ii+215, jj+65 , 0xFFFFFF, false);
        guiGraphics.setColor(1, 1, 1, 1);

        // 能否购买
        if(canBuy){
            menu.slots.get(0).set(trade.result().copy());
            guiGraphics.blit(MENU_LOCATION,ii+203,jj+35,276,0,35,17,512,256);
        }else{
            menu.slots.get(0).set(ItemStack.EMPTY);
            guiGraphics.blit(MENU_LOCATION,ii+203,jj+35,276,17,35,17,512,256);
        }

    }

    private boolean canScroll() {
        return row > NUMBER_OF_OFFER_BUTTONS;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int i = row;
        if (this.canScroll()) {
            int j = i - NUMBER_OF_OFFER_BUTTONS;
            this.scrollOff = Mth.clamp((int)((double)this.scrollOff - scrollY), 0, j);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (
                mouseX > (double)(i + 238) && mouseX <= (double)(i + 238 + 16) &&
                mouseY > (double)(j + 36)&& mouseY <= (double)(j + 36 + 16)
        ) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        this.shopItem = hoveredItem;
        menu.selectedMerchantIndex = shopItem;
        if(menu.selectedMerchantIndex <0) menu.slots.get(0).set(ItemStack.EMPTY);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {

        int x = (int) (mouseX - offsetX);
        int y = (int) (mouseY - offsetY);
        int i = x / intervalX;
        int j = y / 20;
        if (i >= 0 && i < col && j >= 0 && j < row
                && x % intervalX < 16 && y % 20 < 16
                && x >= 0 && y >= 0
        ) {
            int index = i + (j + scrollOff) * col;
            if (index < menu.daveTrades.trades().size()) {
                this.hoveredItem = index;

            }
        }else {
            this.hoveredItem = -1;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class TradeOfferButton extends Button {
        final int index;
        public TradeOfferButton(int x, int y, int index, OnPress onPress) {
            super(x, y, 88, 20, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
            this.index = index;
            this.visible = false;
        }
        public int getIndex() {
            return this.index;
        }
    }
}
