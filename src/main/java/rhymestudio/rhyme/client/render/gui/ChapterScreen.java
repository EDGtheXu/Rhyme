package rhymestudio.rhyme.client.render.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;
import rhymestudio.rhyme.core.checkpoint.CheckPointManager;
import rhymestudio.rhyme.core.checkpoint.ModCheckPoints;
import rhymestudio.rhyme.core.menu.ChapterMenu;
import rhymestudio.rhyme.core.registry.ModAttachments;

import java.awt.*;
import java.util.List;

public class ChapterScreen extends AbstractContainerScreen<ChapterMenu> {

    private final GridLayout layout = new GridLayout(60, 33);
    private final GridLayout rowLayout = new GridLayout(0, 20);


    public ChapterScreen(ChapterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    protected void init() {
        super.init();
        // 关卡
        var data = minecraft.player.getData(ModAttachments.PLAYER_PROGRESS_STORAGE);
        List<Integer> chapter = data.getChapter(ModCheckPoints.SIMPLE_CHECKPOINT);
        int count = CheckPointManager.getCount(ModCheckPoints.SIMPLE_CHECKPOINT);
//        int count = 50;


        layout.defaultCellSetting().paddingHorizontal(20).paddingBottom(10).alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = layout.createRowHelper(5);
        for(int i=1;i<=count;i++){
            rowHelper.addChild(new StringWidget(chapter.contains(i) ?
                    Component.literal("√").withStyle(Style.EMPTY.withColor(Color.green.getRGB())) :
                    Component.literal("X"), this.font));

        }

        this.layout.arrangeElements();
        this.layout.visitWidgets(this::addRenderableWidget);


        // 章节
        rowLayout.defaultCellSetting().paddingHorizontal(20).paddingBottom(20).alignVerticallyMiddle().alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper1 = rowLayout.createRowHelper(1);
        rowHelper1.addChild(new StringWidget(Component.translatable(ModCheckPoints.SIMPLE_CHECKPOINT.getTranslationKey()).withStyle(Style.EMPTY.withColor(Color.cyan.getRGB())), this.font));


        rowLayout.arrangeElements();
        rowLayout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
//        this.renderTransparentBackground(guiGraphics);


    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        var data = minecraft.player.getData(ModAttachments.PLAYER_PROGRESS_STORAGE);
        var chapter = data.getChapter(ModCheckPoints.SIMPLE_CHECKPOINT);
        int count = CheckPointManager.getCount(ModCheckPoints.SIMPLE_CHECKPOINT);

        int passed = chapter.size();

        guiGraphics.drawString(this.font, Component.literal(passed + "/" + count), 30, 8, Color.LIGHT_GRAY.getRGB(), false);


    }

    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
//        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
//        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }


}
