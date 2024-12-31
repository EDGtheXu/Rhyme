package rhymestudio.rhyme.client.render.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.menu.SunCreatorMenu;

import java.util.List;

public class SunCreatorCraftWidget extends AbstractContainerWidget {

    private final SunCreatorMenu menu;
    private final SunCreatorScreen screen;
    private static final ResourceLocation BACKGROUND = Rhyme.space("textures/gui/sun_creator.png");
    private boolean upButtonClicked = false;
    private ItemStack upItem = null;
    private boolean downButtonClicked = false;
    private ItemStack downItem = null;
    protected int left;
    protected int top;


    public SunCreatorCraftWidget(SunCreatorScreen screen, int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
        this.left = x;
        this.top = y;
        this.screen = screen;
        this.menu = screen.getMenu();

    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float v) {
        int imageWidth = 176;
        int imageHeight = 166;
        pGuiGraphics.blit(BACKGROUND, left, top, 0, 0, imageWidth, imageHeight);
        if (upButtonClicked) {
            pGuiGraphics.blit(BACKGROUND, left + 128, top + 25, 177, 0, 10, 7);
        } else if (downButtonClicked) {
            pGuiGraphics.blit(BACKGROUND, left + 128, top + 54, 177, 8, 10, 7);
        }

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(this.left, this.top, 0.0F);

        Font font = Minecraft.getInstance().font;

        if (isOverUpButton(pMouseX , pMouseY )) {
            if (upItem == null) this.upItem = menu.getUpResult();
            pGuiGraphics.renderFakeItem(upItem, 125, 35);
            this.downItem = null;
        } else if (isOverDownButton(pMouseX , pMouseY )) {
            if (downItem == null) this.downItem = menu.getDownResult();
            pGuiGraphics.renderFakeItem(downItem, 125, 35);
            this.upItem = null;
        } else {
            pGuiGraphics.renderFakeItem(menu.getSlot(0).getItem(),  125,  35);
            this.upItem = null;
            this.downItem = null;
        }

        String text = menu.getRecipesAmount() == 0 ? "0/0" : menu.getCurrentIndex() + 1 + "/" + menu.getRecipesAmount();
        pGuiGraphics.drawString(font, text, 147, 40, 0xFFFFFF);

        pGuiGraphics.pose().popPose();
    }


    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        Minecraft minecraft = Minecraft.getInstance();
        if (isOverUpButton((int) pMouseX - left, (int) pMouseY - top)) {
            int upIndex = menu.getUpIndex();
            if (menu.clickMenuButton(minecraft.player, upIndex)) {
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, upIndex);
                this.upButtonClicked = true;
                this.downButtonClicked = false;
                this.upItem = null;
                return true;
            }
            return false;
        } else if (isOverDownButton((int) pMouseX - left, (int) pMouseY - top)) {
            int downIndex = menu.getDownIndex();
            if (menu.clickMenuButton(minecraft.player, downIndex)) {
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, downIndex);
                this.upButtonClicked = false;
                this.downButtonClicked = true;
                this.downItem = null;
                return true;
            }
            return false;
        }
        else {
            super.mouseClicked(pMouseX, pMouseY, pButton);
            return false;
        }
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }


    private static boolean isOverUpButton(int x, int y) {
        return x >= 128 && x <= 138 && y >= 25 && y <= 32;
    }

    private static boolean isOverDownButton(int x, int y) {
        return x >= 128 && x <= 138 && y >= 54 && y <= 61;
    }


}
