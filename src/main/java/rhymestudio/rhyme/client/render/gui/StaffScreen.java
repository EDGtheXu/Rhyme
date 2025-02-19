package rhymestudio.rhyme.client.render.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterSpriteSourceTypesEvent;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.render.buffer.FakeBlocksHelper;
import rhymestudio.rhyme.core.dataSaver.attactment.StructureStaffAttachment;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;
import rhymestudio.rhyme.core.menu.StaffMenu;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.network.c2s.GenerateStructurePacket;
import rhymestudio.rhyme.utils.AdapterUtils;
import rhymestudio.rhyme.utils.Computer;

import java.awt.*;

public class StaffScreen extends AbstractContainerScreen<StaffMenu> {
    ImageButton saveBt;
    EditBox editBox;
    ImageButton generateBt;
    ImageButton chooseBt;
    ImageButton deleteBt;
    int hoverIndex = -1;
    int selectIndex = -1;


    StructureStaffAttachment attachment;
    public StaffScreen(StaffMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    private static final WidgetSprites SAVE_SPRITES = new WidgetSprites(
            Rhyme.space("down"),  Rhyme.space("down_highlight")
    );
    private static final WidgetSprites GEN_SPRITES = new WidgetSprites(
            Rhyme.space("gen"),  Rhyme.space("gen_highlight")
    );
    private static final WidgetSprites TEST_SPRITES = new WidgetSprites(
            Rhyme.space("generate"),  Rhyme.space("generate_highlight")
    );
    private static final WidgetSprites DELETE_SPRITES = new WidgetSprites(
            Rhyme.space("delete"),  Rhyme.space("delete_highlight")
    );

    protected void init() {
        super.init();


        attachment = minecraft.player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
        if(attachment.structureStaffComponents.isEmpty()){
            attachment.loadStructures();
        }
        this.editBox = new EditBox(this.font, 50, 5, 100, 20, Component.empty());

        this.saveBt = new ImageButton(5,5,30,30, SAVE_SPRITES, p->{
            // 保存文件名
            if(!editBox.getValue().isEmpty())
                attachment.saveStructure(editBox.getValue());
        });
        this.chooseBt = new ImageButton(5,35,30,30, TEST_SPRITES, p->{
            // 生成虚影
            attachment.targetPos = Computer.getEyeBlockHitResult(minecraft.player);
            if(attachment.selectedStructureStaffComponent!= null) {
                var d = attachment.selectedStructureStaffComponent.blocks();
                FakeBlocksHelper.Singleton().clear();
                for (var block : d) {
                    BlockPos pos = block.position().offset(attachment.targetPos);
                    FakeBlocksHelper.Singleton().addBlock(pos, block.block());
                    FakeBlocksHelper.Singleton().setTargetPos(attachment.targetPos,
                            attachment.selectedStructureStaffComponent.maxx(),
                            attachment.selectedStructureStaffComponent.maxy(),
                            attachment.selectedStructureStaffComponent.maxz()
                    );
                }
            }
            FakeBlocksHelper.Singleton().setRebuild(true);

        });
        this.generateBt = new ImageButton(5,65,30,30, GEN_SPRITES, p->{
            // 生成地形
            if(attachment.selectedStructureStaffComponent!= null && attachment.targetPos!= null) {
                var list = attachment.selectedStructureStaffComponent.split(50);
                for (var c : list) {
                    AdapterUtils.sendPacketToServer(new GenerateStructurePacket(c, attachment.targetPos));
                }
            }
            FakeBlocksHelper.Singleton().clear();
            attachment.selectedStructureStaffComponent = null;
            attachment.targetPos = null;

        });
        this.deleteBt = new ImageButton(5,95,30,30, DELETE_SPRITES, p->{
            FakeBlocksHelper.Singleton().clear();
        });



        this.addRenderableWidget(this.saveBt);
        this.addRenderableWidget(this.editBox);
        this.addRenderableWidget(this.generateBt);
        this.addRenderableWidget(this.chooseBt);
        this.addRenderableWidget(this.deleteBt);

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.editBox.render(guiGraphics, mouseX, mouseY, partialTick);
        this.saveBt.render(guiGraphics, mouseX, mouseY, partialTick);
        this.generateBt.render(guiGraphics, mouseX, mouseY, partialTick);
        this.chooseBt.render(guiGraphics, mouseX, mouseY, partialTick);
        this.deleteBt.render(guiGraphics, mouseX, mouseY, partialTick);
        StructureStaffAttachment data;
        if (minecraft != null && minecraft.player != null) {
            data = minecraft.player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
            var components = data.structureStaffComponents;
            int x = 75;
            int y = 0;
            hoverIndex = -1;
            var entries = components.entrySet().stream().toList();
            for(int i = 0; i < entries.size(); i++) {
                var component = entries.get(i).getValue();
                var name = entries.get(i).getKey();

                if (component != null) {
                    PoseStack pose = guiGraphics.pose();

                    var blocks = component.blocks();
                    pose.pushPose();

                    pose.translate(x, y + 70, 500);

                    int row = i / 3;
                    int col = i % 3;

                    guiGraphics.renderOutline(-35, -40, 75, 75, 0xFFFFFFFF);

                    x += 75;
                    if(i % 3 == 2){
                        x = 75;
                        y += 75;
                    }

                    int marginTop = 30 + row * 75;
                    int marginLeft = 40 + col * 75;


                    int hoverIndex = -1;
                    if(mouseX >= marginLeft && mouseX <= marginLeft + 75 && mouseY >= marginTop && mouseY <= marginTop + 75){
                        hoverIndex = i;
                        this.hoverIndex = hoverIndex;
                    }
                    if(hoverIndex < 0 && this.selectIndex != i){
                        // 未选中，渲染文件名
                        guiGraphics.drawCenteredString(this.font, name, 0,-5, 0xFFFFFFFF);
                        pose.popPose();
                        continue;
                    }

                    guiGraphics.fillGradient(RenderType.guiOverlay(),
                            -35, -40,  40, 35,
                            Color.BLUE.getRGB() + 0x50000000, Color.cyan.getRGB(), 0);


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

    // 选择
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(this.hoverIndex >= 0){
            this.selectIndex = this.hoverIndex;
            var data = minecraft.player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
            if(data.structureStaffComponents.size() > this.hoverIndex){
                data.selectedStructureStaffComponent = data.structureStaffComponents.values().stream().toList().get(this.hoverIndex);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
//                var list = data.selectedStructureStaffComponent.split(50);
//                for(var c : list){
//                    AdapterUtils.sendPacketToServer(new GenerateStructurePacket(c, minecraft.player.blockPosition()));
//                }
            }

        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
    }
}
