package rhymestudio.rhyme.client.render.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.menu.SunCreatorMenu;

import java.util.List;

public class SunCreatorCreatorWidget extends AbstractContainerWidget {
    public static final ResourceLocation BACKGROUND = Rhyme.space("textures/gui/sun_creator_2.png");

    private final SunCreatorMenu menu;
    private final SunCreatorScreen screen;
    protected int left;
    protected int top;

    public SunCreatorCreatorWidget(SunCreatorScreen screen, int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
        this.menu = screen.getMenu();
        this.screen = screen;
        this.left = x;
        this.top = y;
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

        return false;
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int imageWidth = 176;
        int imageHeight = 166;

        guiGraphics.blit(BACKGROUND, left, top, 0, 0, imageWidth, imageHeight);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.left, this.top, 0.0F);

        Font font = Minecraft.getInstance().font;

        guiGraphics.drawString(font, menu.access.get(0) +"/"+menu.access.get(1),  115, 18, 4210752, false);

        guiGraphics.pose().popPose();


        int h = 30;
        float percent = (float)menu.access.get(0)/menu.access.get(1);
        int realH = (int) (percent*h);
        int realY = top + 32 + h - realH;
        guiGraphics.blit(BACKGROUND, left + 39, realY, imageWidth, 0 + h - realH, 70,realH);

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
