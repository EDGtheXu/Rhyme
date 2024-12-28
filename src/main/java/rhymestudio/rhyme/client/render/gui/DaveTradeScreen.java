package rhymestudio.rhyme.client.render.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import rhymestudio.rhyme.core.menu.DaveTradesMenu;
import rhymestudio.rhyme.utils.Computer;

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
    /**
     * The GUI texture for the villager merchant GUI.
     */
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
    private static final Component TRADES_LABEL = Component.translatable("merchant.trades");
    private static final Component DEPRECATED_TOOLTIP = Component.translatable("merchant.deprecated");
    /**
     * The integer value corresponding to the currently selected merchant recipe.
     */
    private int shopItem;
    private final DaveTradeScreen.TradeOfferButton[] tradeOfferButtons = new DaveTradeScreen.TradeOfferButton[NUMBER_OF_OFFER_BUTTONS];
    int scrollOff;
    private boolean isDragging;

    public DaveTradeScreen(DaveTradesMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.imageWidth = 276;
        this.inventoryLabelX = 107;
    }

    private void postButtonClick() {

        this.minecraft.getConnection().send(new ServerboundSelectTradePacket(this.shopItem));
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k = j + 16 + 2;

        for (int l = 0; l < NUMBER_OF_OFFER_BUTTONS; l++) {
            this.tradeOfferButtons[l] = this.addRenderableWidget(new DaveTradeScreen.TradeOfferButton(i + 5, k, l, p_99174_ -> {
                if (p_99174_ instanceof DaveTradeScreen.TradeOfferButton) {
                    this.shopItem = ((DaveTradeScreen.TradeOfferButton)p_99174_).getIndex() + this.scrollOff;
                    this.postButtonClick();
                    menu.clickMenuButton(minecraft.player,shopItem);
                }
            }));
            k += 20;
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int i = 2;
        if (i > 0 && i <= 5 && this.menu.showProgressBar()) {
            Component component = Component.translatable("merchant.title", this.title, Component.translatable("merchant.level." + i));
            int j = this.font.width(component);
            int k = 49 + this.imageWidth / 2 - j / 2;
            guiGraphics.drawString(this.font, component, k, 6, 4210752, false);
        } else {
            guiGraphics.drawString(this.font, this.title, 49 + this.imageWidth / 2 - this.font.width(this.title) / 2, 6, 4210752, false);
        }

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

    private void renderProgressBar(GuiGraphics guiGraphics, int posX, int posY, MerchantOffer merchantOffer) {
//        int i = this.menu.getTraderLevel();
//        int j = this.menu.getTraderXp();
//        if (i < 5) {
//            guiGraphics.blitSprite(EXPERIENCE_BAR_BACKGROUND_SPRITE, posX + 136, posY + 16, 0, 102, 5);
//            int k = VillagerData.getMinXpPerLevel(i);
//            if (j >= k && VillagerData.canLevelUp(i)) {
//                int l = 102;
//                float f = 102.0F / (float)(VillagerData.getMaxXpPerLevel(i) - k);
//                int i1 = Math.min(Mth.floor(f * (float)(j - k)), 102);
//                guiGraphics.blitSprite(EXPERIENCE_BAR_CURRENT_SPRITE, 102, 5, 0, 0, posX + 136, posY + 16, 0, i1, 5);
//                int j1 = this.menu.getFutureTraderXp();
//                if (j1 > 0) {
//                    int k1 = Math.min(Mth.floor((float)j1 * f), 102 - i1);
//                    guiGraphics.blitSprite(EXPERIENCE_BAR_RESULT_SPRITE, 102, 5, i1, 0, posX + 136 + i1, posY + 16, 0, k1, 5);
//                }
//            }
//        }
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

        this.renderScroller(guiGraphics, 75, 38);
        var trades = menu.daveTrades.trades();

        for (int l = 0; l < 7; l++) {
            this.tradeOfferButtons[l].visible = true;
            this.tradeOfferButtons[l].render(guiGraphics, mouseX, mouseY, partialTick);
            var trade = trades.get(l+scrollOff);
            int x = 86 ;
            int y = 57 ;
            var it = trade.give();
            guiGraphics.renderItem(it, x  + 20  , y + 20 * l);

            guiGraphics.renderItemDecorations(this.font, it, x + 20, y + 20 * l);
        }


        var trade = trades.get(this.shopItem);
        int m = trade.money();
        var its = trade.requires();
        int x = 200 ;
        int y = 74 ;
        String numberText = m + " + ";
        guiGraphics.drawString(this.font, numberText, x, y + 5 , 4210752, false);

        boolean canBuy = true;
        for(int j = 0; j < its.size(); j++){
            var it = its.get(j);
            int x1 = x + numberText.length() * 5 + 20 * j;
            if(Computer.getInventoryItemCount(minecraft.player, it.getItem()) < it.getCount()){
                guiGraphics.setColor(1,0.5f,0.5f,1);
                canBuy = false;
            }

            guiGraphics.renderItem(it, x1, y );
            guiGraphics.renderItemDecorations(this.font, it, x1, y);
            guiGraphics.setColor(1,1,1,1);

        }

        if(canBuy){
             menu.slots.get(0).set(trade.give().copy());
//            guiGraphics.renderItem(result, x2, y2);
//            guiGraphics.renderItemDecorations(this.font, result, x2, y2);

            guiGraphics.blitSprite(TRADE_ARROW_SPRITE, 270, 78, 0, 10, 9);
        }else{
            menu.slots.get(0).set(ItemStack.EMPTY);
            guiGraphics.blitSprite(TRADE_ARROW_OUT_OF_STOCK_SPRITE, 270, 78, 0, 10, 9);

        }


        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderButtonArrows(GuiGraphics guiGraphics, MerchantOffer merchantOffers, int posX, int posY) {
        RenderSystem.enableBlend();
        if (merchantOffers.isOutOfStock()) {
            guiGraphics.blitSprite(TRADE_ARROW_OUT_OF_STOCK_SPRITE, posX + 5 + 35 + 20, posY + 3, 0, 10, 9);
        } else {
            guiGraphics.blitSprite(TRADE_ARROW_SPRITE, posX + 5 + 35 + 20, posY + 3, 0, 10, 9);
        }
    }

    private void renderAndDecorateCostA(GuiGraphics guiGraphics, ItemStack realCost, ItemStack baseCost, int x, int y) {
        guiGraphics.renderFakeItem(realCost, x, y);
        if (baseCost.getCount() == realCost.getCount()) {
            guiGraphics.renderItemDecorations(this.font, realCost, x, y);
        } else {
            guiGraphics.renderItemDecorations(this.font, baseCost, x, y, baseCost.getCount() == 1 ? "1" : null);
            // Forge: fixes Forge-8806, code for count rendering taken from GuiGraphics#renderGuiItemDecorations
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
            String count = realCost.getCount() == 1 ? "1" : String.valueOf(realCost.getCount());
            font.drawInBatch(count, (float) (x + 14) + 19 - 2 - font.width(count), (float)y + 6 + 3, 0xFFFFFF, true, guiGraphics.pose().last().pose(), guiGraphics.bufferSource(), net.minecraft.client.gui.Font.DisplayMode.NORMAL, 0, 15728880, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 0.0F, 300.0F);
            guiGraphics.blitSprite(DISCOUNT_STRIKETHRUOGH_SPRITE, x + 7, y + 12, 0, 9, 2);
            guiGraphics.pose().popPose();
        }
    }

    private boolean canScroll(int numOffers) {
        return numOffers > 7;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int i = menu.daveTrades.trades().size();
        if (this.canScroll(i)) {
            int j = i - 7;
            this.scrollOff = Mth.clamp((int)((double)this.scrollOff - scrollY), 0, j);
        }

        return true;
    }

    /**
     * Called when the mouse is dragged within the GUI element.
     * <p>
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     *
     * @param mouseX the X coordinate of the mouse.
     * @param mouseY the Y coordinate of the mouse.
     * @param button the button that is being dragged.
     * @param dragX  the X distance of the drag.
     * @param dragY  the Y distance of the drag.
     */
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);

    }

    /**
     * Called when a mouse button is clicked within the GUI element.
     * <p>
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     *
     * @param mouseX the X coordinate of the mouse.
     * @param mouseY the Y coordinate of the mouse.
     * @param button the button that was clicked.
     */
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
    class TradeOfferButton extends Button {
        final int index;

        public TradeOfferButton(int x, int y, int index, Button.OnPress onPress) {
            super(x, y, 88, 20, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
            this.index = index;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }


    }
}
