package rhymestudio.rhyme.client.render.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;
import rhymestudio.rhyme.core.dataSaver.attactment.StructureStaffAttachment;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;
import rhymestudio.rhyme.core.menu.StaffMenu;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.network.c2s.GenerateStructurePacket;
import rhymestudio.rhyme.utils.AdapterUtils;

public class StaffScreen extends AbstractContainerScreen<StaffMenu> {
    ImageButton saveBt;
    EditBox editBox;
    int hoverIndex = -1;
    public StaffScreen(StaffMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    private static final WidgetSprites WEBSITE_LINK_SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("icon/link"), ResourceLocation.withDefaultNamespace("icon/link_highlighted")
    );

    protected void init() {
        super.init();
        var data = minecraft.player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
        if(data.structureStaffComponents.isEmpty()){
            data.loadStructures();
        }
        this.editBox = new EditBox(this.font, 50, 5, 100, 20, Component.empty());
        this.saveBt = new ImageButton(0,0,50,50,WEBSITE_LINK_SPRITES, p->{
            StructureStaffAttachment data1;
            if (minecraft != null && minecraft.player != null) {
                data1 = minecraft.player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
                if(!editBox.getValue().isEmpty())
                    data1.saveStructure(editBox.getValue());
            }
        });

        this.addRenderableWidget(this.saveBt);
        this.addRenderableWidget(this.editBox);

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.editBox.render(guiGraphics, mouseX, mouseY, partialTick);
        this.saveBt.render(guiGraphics, mouseX, mouseY, partialTick);
        StructureStaffAttachment data;
        if (minecraft != null && minecraft.player != null) {
            data = minecraft.player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
            var components = data.structureStaffComponents;
            int x = 75;
            int y = 0;
            hoverIndex = -1;
            var values = components.values().stream().toList();
            for(int i = 0; i < values.size(); i++) {
                var component = values.get(i);

                if (component != null) {
                    PoseStack pose = guiGraphics.pose();

                    var blocks = component.blocks();
                    pose.pushPose();

                    pose.translate(x, y + 50, 500);

                    int row = i / 3;
                    int col = i % 3;

                    guiGraphics.renderOutline(-35, -40, 75, 75, 0xFFFFFFFF);

                    x += 75;
                    if(i % 3 == 2){
                        x = 75;
                        y += 75;
                    }

                    int marginTop = 10 + row * 75;
                    int marginLeft = 40 + col * 75;

                    int hoverIndex = -1;
                    if(mouseX >= marginLeft && mouseX <= marginLeft + 75 && mouseY >= marginTop && mouseY <= marginTop + 75){
                        hoverIndex = i;
                        this.hoverIndex = hoverIndex;
                    }
                    if(hoverIndex < 0){
                        pose.popPose();
                        continue;
                    }


                    int xx = component.maxx() / 2;
                    int yy = component.maxy() / 2;
                    int zz = component.maxz() / 2;

                    int max = Math.max(xx, Math.max(yy, zz));
                    float size = 5;

                    int allowMax = 5;
                    if(max > allowMax){
                        size = (float) (5 * allowMax) / max;
                    }

                    pose.scale(size, -size, size);
                    pose.mulPose(Axis.XP.rotationDegrees(20));
                    pose.mulPose(Axis.YP.rotationDegrees(30 + (minecraft.player.tickCount + partialTick) * 2));

                    for (int j = 0; j < blocks.size(); j++) {
                        pose.pushPose();
                        StructureStaffComponent.Tuple tuple = blocks.get(j);
                        BlockPos pos = tuple.position();
                        pose.translate(pos.getX() - xx, pos.getY() - yy, pos.getZ() - zz);
                        BlockState block = tuple.block();
                        minecraft.getBlockRenderer().renderSingleBlock(block, guiGraphics.pose(), minecraft.renderBuffers().bufferSource(), 0xF000F0, OverlayTexture.pack(OverlayTexture.u(0), 10));
                        pose.popPose();
                    }
                    pose.popPose();
                }

            }
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(this.hoverIndex >= 0){
            var data = minecraft.player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
            if(data.structureStaffComponents.size() > this.hoverIndex){
                data.selectedStructureStaffComponent = data.structureStaffComponents.values().stream().toList().get(this.hoverIndex);
                var list = data.selectedStructureStaffComponent.split(50);
                for(var c : list){
                    AdapterUtils.sendPacketToServer(new GenerateStructurePacket(c, minecraft.player.blockPosition()));
                }

            }

        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
    }
}
