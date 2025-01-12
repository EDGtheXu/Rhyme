package rhymestudio.rhyme.client.render.gui;

import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.ModRenderTypes;
import rhymestudio.rhyme.client.render.util.ShaderUtil;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.item.AbstractCardItem;
import rhymestudio.rhyme.core.menu.CardUpLevelMenu;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.mixinauxiliary.IShaderInstance;
import software.bernie.geckolib.animatable.GeoEntity;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;

public class CardUpLevelScreen extends ItemCombinerScreen<CardUpLevelMenu> {
    private static final ResourceLocation MENU_RESOURCE = Rhyme.space("textures/gui/card_up_level_menu.png");
    private static final Component MISSING_TEMPLATE_TOOLTIP = Component.translatable("card_up_level.missing_base_tooltip");
    private static final Component ERROR_TOOLTIP = Component.translatable("card_up_level.error_tooltip");

    private final CyclingSlotBackground templateIcon = new CyclingSlotBackground(0);
    private final CyclingSlotBackground baseIcon = new CyclingSlotBackground(1);
    private final CyclingSlotBackground additionalIcon = new CyclingSlotBackground(2);

    @Nullable
    private AbstractPlant entity;
    public CardUpLevelScreen(CardUpLevelMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title.copy().setStyle(Style.EMPTY.withBold(true)).withColor(Color.MAGENTA.getRGB()), MENU_RESOURCE);
        this.titleLabelX = 70;
        this.titleLabelY = 15;
    }

    @Override
    public void slotChanged(AbstractContainerMenu containerToSend, int slotId, ItemStack stack) {
        if (slotId == 1) {
            if(this.menu.getSlot(slotId).getItem().getItem() instanceof AbstractCardItem<?> cardItem){
                entity = cardItem.entityType.get().create(Minecraft.getInstance().level);
//                entity.setXRot(25.0F);
                if(entity instanceof GeoEntity geo){
                    geo.triggerAnim("base_controller","misc.idle");
                }
                entity.animState.playDefaultAnim(0);
            }else{
                entity = null;
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderOnboardingTooltips(guiGraphics, mouseX, mouseY);

        if (entity != null) {
            guiGraphics.pose().pushPose();
            float scale = 25f;
            guiGraphics.pose().translate((float)(this.leftPos+145 ), (float)(this.topPos+63), 50.0);
            guiGraphics.pose().mulPose(new Quaternionf().rotationXYZ(2.8f, 3.6f, 0));
            guiGraphics.pose().scale(scale, scale, scale);
            entity.tickCount = (int) minecraft.level.getLevelData().getGameTime();

            entity.animState.curAnimState.updateTime(entity.tickCount,1);
            Lighting.setupForEntityInInventory();
            Minecraft.getInstance().getEntityRenderDispatcher().render(entity,0,0,0,0,1f,guiGraphics.pose(),guiGraphics.bufferSource(),15728880);
            guiGraphics.pose().popPose();

            if(menu.slots.get(3).hasItem()){
                var data = menu.slots.get(3).getItem().getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
                if(data!= null){
                    int color = data.color();
                    float r = (float) ((color >> 16) & 0xFF) / 255.0F;
                    float g = (float) ((color >> 8) & 0xFF) / 255.0F;
                    float b = (float) (color & 0xFF) / 255.0F;
                    guiGraphics.setColor(r, g, b, 1.0f);
                }

                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate((float)(this.leftPos+121.5 ), (float)(this.topPos+10), 0);
                double seconds =  System.currentTimeMillis() % 100000000 / 1000f; // seconds
                ((IShaderInstance) ModRenderTypes.Shaders.rectPolar).getRhyme$Time().set((float) seconds);
                ((IShaderInstance) ModRenderTypes.Shaders.rectPolar).getRhyme$Radius().set(1.0f);

                ShaderUtil.drawFloatGlow(guiGraphics.pose().last().pose(),Rhyme.space("textures/gui/pixel_glow.png"),46.9f,63.88f);
                guiGraphics.setColor(1,1,1, 1.0f);
                guiGraphics.pose().popPose();
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        this.templateIcon.render(this.menu, guiGraphics, partialTick, this.leftPos, this.topPos);
        this.baseIcon.render(this.menu, guiGraphics, partialTick, this.leftPos, this.topPos);
        this.additionalIcon.render(this.menu, guiGraphics, partialTick, this.leftPos, this.topPos);

    }

    @Override
    protected void renderErrorIcon(GuiGraphics guiGraphics, int x, int y) {
        if (this.hasRecipeError()) {
            guiGraphics.blit(MENU_RESOURCE, x + 68, y + 49, 180,0, 28, 21);
        }
    }

    private void renderOnboardingTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Optional<Component> optional = Optional.empty();
        if (this.hasRecipeError() && this.isHovering(65, 46, 28, 21, mouseX, mouseY)) {
            optional = Optional.of(ERROR_TOOLTIP);
        }
        if (this.hoveredSlot != null) {
            ItemStack itemstack = this.menu.getSlot(1).getItem();
            if (itemstack.isEmpty() && this.hoveredSlot.index == 1) {
                optional = Optional.of(MISSING_TEMPLATE_TOOLTIP);
            }
        }
        optional.ifPresent(cmp -> guiGraphics.renderTooltip(this.font, this.font.split(cmp, 115), mouseX, mouseY));
    }

    private boolean hasRecipeError() {
        return this.menu.getSlot(0).hasItem()
                && this.menu.getSlot(1).hasItem()
                && this.menu.getSlot(2).hasItem()
                && !this.menu.getSlot(this.menu.getResultSlot()).hasItem();
    }
}
