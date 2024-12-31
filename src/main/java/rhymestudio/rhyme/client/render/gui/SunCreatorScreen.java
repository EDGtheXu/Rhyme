package rhymestudio.rhyme.client.render.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.menu.IHiddenSlot;
import rhymestudio.rhyme.core.menu.SunCreatorMenu;

public class SunCreatorScreen extends AbstractContainerScreen<SunCreatorMenu> {

    private Button switchButton;
    int state;
    private AbstractContainerWidget craftWidget;
    private AbstractContainerWidget creatorWidget;

    public SunCreatorScreen(SunCreatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

    }

    @Override
    protected void init() {
        super.init();

        this.titleLabelX = imageWidth - font.width(title) - 8;
        this.inventoryLabelX = imageWidth - font.width(playerInventoryTitle) - 8;
        this.creatorWidget = new SunCreatorCreatorWidget(this,leftPos,topPos,imageWidth,imageHeight,Component.literal("creator"));
        this.craftWidget = new SunCreatorCraftWidget(this,leftPos,topPos,imageWidth,imageHeight,Component.literal("craft"));

        switchButton = Button.builder(Component.literal("switch"), (press) -> {
            if(state == 1){
                state = 2;
                this.removeWidget(craftWidget);
                this.addRenderableWidget(creatorWidget);
                menu.slots.forEach(slot -> {
                    if(slot instanceof IHiddenSlot slot1)
                        slot1.setHide(state);
                });

            }else{
                state = 1;
                this.removeWidget(creatorWidget);
                this.addRenderableWidget(craftWidget);
                menu.slots.forEach(slot -> {
                    if(slot instanceof IHiddenSlot slot1)
                        slot1.setHide(state);
                });
            }
        }).pos(leftPos,topPos-20).size(25,16).build();

        this.addRenderableWidget(craftWidget);
//        this.addRenderableWidget(creatorWidget);
        this.addRenderableWidget(switchButton);

        state = 1;
        menu.slots.forEach(slot -> {
            if(slot instanceof IHiddenSlot slot1)
                slot1.setHide(state);
        });

    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);


    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {


    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        for(var widget : this.children()){
            if(widget instanceof AbstractContainerWidget widget1){
                if(widget1.isHovered() && widget1.mouseClicked(pMouseX, pMouseY, pButton))
                    return true;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

}
