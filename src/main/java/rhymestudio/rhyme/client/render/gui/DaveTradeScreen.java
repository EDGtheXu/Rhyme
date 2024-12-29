package rhymestudio.rhyme.client.render.gui;

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
import rhymestudio.rhyme.core.menu.DaveTradesMenu;
import rhymestudio.rhyme.utils.Computer;

import static rhymestudio.rhyme.client.render.gui.hud.CardHUD.cachedMoney;

public class DaveTradeScreen extends AbstractContainerScreen<DaveTradesMenu> {
    private static final ResourceLocation OUT_OF_STOCK_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/out_of_stock");
    private static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace(
            "container/villager/experience_bar_background"
    );
    private static final ResourceLocation EXPERIENCE_BAR_CURRENT_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/experience_bar_current");
    private static final ResourceLocation EXPERIENCE_BAR_RESULT_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/experience_bar_result");
    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/scroller_disabled");
    private static final ResourceLocation TRADE_ARROW_OUT_OF_STOCK_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/trade_arrow_out_of_stock");
    private static final ResourceLocation TRADE_ARROW_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/trade_arrow");
    private static final ResourceLocation DISCOUNT_STRIKETHRUOGH_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/discount_strikethrough");

    private static final ResourceLocation VILLAGER_LOCATION = ResourceLocation.withDefaultNamespace("textures/gui/container/villager.png");
    private static final int TEXTURE_WIDTH = 512;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int MERCHANT_MENU_PART_X = 99;
    private static final int PROGRESS_BAR_X = 136;
    private static final int PROGRESS_BAR_Y = 16;
    private static final int SELL_ITEM_1_X = 5;
    private static final int SELL_ITEM_2_X = 35;
    private static final int BUY_ITEM_X = 68;
    private static final int LABEL_Y = 6;
    private static final int NUMBER_OF_OFFER_BUTTONS = 7;
    private static final int TRADE_BUTTON_X = 5;
    private static final int TRADE_BUTTON_HEIGHT = 20;
    private static final int TRADE_BUTTON_WIDTH = 88;
    private static final int SCROLLER_HEIGHT = 27;
    private static final int SCROLLER_WIDTH = 6;
    private static final int SCROLL_BAR_HEIGHT = 139;
    private static final int SCROLL_BAR_TOP_POS_Y = 18;
    private static final int SCROLL_BAR_START_X = 94;
    private static final Component TRADES_LABEL = Component.translatable("dave.trades");

    private int shopItem;
    private final TradeOfferButton[] tradeOfferButtons = new TradeOfferButton[NUMBER_OF_OFFER_BUTTONS];
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
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k = j + 16 + 2;

        for (int l = 0; l < NUMBER_OF_OFFER_BUTTONS; l++) {
            this.tradeOfferButtons[l] = this.addRenderableWidget(new TradeOfferButton(i + 5, k, l, p -> {
                if (p instanceof TradeOfferButton) {
                    this.shopItem = ((TradeOfferButton) p).getIndex() + this.scrollOff;
                    menu.clickMenuButton(minecraft.player, shopItem);
                }
            }));
            k += 20;
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 49 + this.imageWidth / 2 - this.font.width(this.title) / 2, 6, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
        int l = this.font.width(TRADES_LABEL);
        guiGraphics.drawString(this.font, TRADES_LABEL, 5 - l / 2 + 48, 6, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(VILLAGER_LOCATION, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);
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
        int ii = (this.width - this.imageWidth) / 2;
        int jj = (this.height - this.imageHeight) / 2;
        this.renderScroller(guiGraphics, ii, jj);

        // 左侧物品
        var trades = menu.daveTrades.trades();
        int x = ii + 40;
        int y = jj;
        for (int l = 0; l < Math.min(trades.size(), NUMBER_OF_OFFER_BUTTONS); l++) {
            this.tradeOfferButtons[l].visible = true;
            this.tradeOfferButtons[l].render(guiGraphics, mouseX, mouseY, partialTick);
            var it = trades.get(l+scrollOff).give();
            y+=20;
            guiGraphics.renderItem(it, x , y );
            if(mouseX > x && mouseX < x+16 && mouseY > y && mouseY < y+16)
                guiGraphics.renderTooltip(this.font, it, mouseX, mouseY);
            guiGraphics.renderItemDecorations(this.font, it, x, y);
        }

        // 上面的材料物品
        var trade = trades.get(this.shopItem);
        var its = trade.requires();
        boolean canBuy = true;
        int x1 = ii + 200;
        int y1 = jj + 27;
        for (ItemStack it : its) {
            if(x1 < ii+200-20*2)
            {
                x1 = ii+200;
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
             menu.slots.get(0).set(trade.give().copy());
//            guiGraphics.renderItem(result, x2, y2);
//            guiGraphics.renderItemDecorations(this.font, result, x2, y2);
            guiGraphics.blitSprite(TRADE_ARROW_SPRITE, ii+200, jj+0, 0, 10, 9);
        }else{
            menu.slots.get(0).set(ItemStack.EMPTY);
            guiGraphics.blitSprite(TRADE_ARROW_OUT_OF_STOCK_SPRITE, ii+200, jj+40,  0, 10, 9);
        }
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private boolean canScroll(int numOffers) {
        return numOffers > NUMBER_OF_OFFER_BUTTONS;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int i = menu.daveTrades.trades().size();
        if (this.canScroll(i)) {
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
        this.isDragging = false;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (this.canScroll(5)
                && mouseX > (double)(i + 94)
                && mouseX < (double)(i + 94 + 6)
                && mouseY > (double)(j + 18)
                && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.isDragging = true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
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
