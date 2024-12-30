package rhymestudio.rhyme.client.render.gui;

import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import org.joml.Quaternionf;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.item.AbstractCardItem;
import rhymestudio.rhyme.core.menu.CardUpLevelMenu;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;
import software.bernie.geckolib.animatable.GeoEntity;

import javax.annotation.Nullable;
import java.util.Optional;

public class CardUpLevelScreen extends ItemCombinerScreen<CardUpLevelMenu> {
    private static final ResourceLocation ERROR_SPRITE = ResourceLocation.withDefaultNamespace("container/smithing/error");
    private static final ResourceLocation MENU_RESOURCE = Rhyme.space("textures/gui/smithing1.png");
    private static final Component MISSING_TEMPLATE_TOOLTIP = Component.translatable("card_up_level.missing_base_tooltip");
    private static final Component ERROR_TOOLTIP = Component.translatable("card_up_level.error_tooltip");

    private final CyclingSlotBackground templateIcon = new CyclingSlotBackground(0);
    private final CyclingSlotBackground baseIcon = new CyclingSlotBackground(1);
    private final CyclingSlotBackground additionalIcon = new CyclingSlotBackground(2);
    @Nullable

    private AbstractPlant entity;
    public CardUpLevelScreen(CardUpLevelMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, MENU_RESOURCE);
        this.titleLabelX = 44;
        this.titleLabelY = 15;

//        entity = PlantEntities.CABBAGE_PULT.get().create(Minecraft.getInstance().level);

    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        if (slotId == 1) {
            if(this.menu.getSlot(slotId).getItem().getItem() instanceof AbstractCardItem<?> cardItem){
                entity = cardItem.entityType.get().create(Minecraft.getInstance().level);
                entity.setYRot(25.0F);
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
    private Optional<SmithingTemplateItem> getBaseCard() {
        ItemStack itemstack = this.menu.getSlot(1).getItem();
        return !itemstack.isEmpty() && itemstack.getItem() instanceof SmithingTemplateItem smithingtemplateitem
                ? Optional.of(smithingtemplateitem)
                : Optional.empty();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderOnboardingTooltips(guiGraphics, mouseX, mouseY);

        if (entity != null) {
            guiGraphics.pose().pushPose();
            float scale = 30f;
            guiGraphics.pose().translate((float)(this.leftPos+140 ), (float)(this.topPos+70), 50.0);
            guiGraphics.pose().mulPose(new Quaternionf().rotationXYZ(2.8f, 4f, 0));
            guiGraphics.pose().scale(scale, scale, scale);
            entity.tickCount = (int) minecraft.level.getLevelData().getGameTime();

            entity.animState.curAnimState.updateTime(entity.tickCount,1);
            Lighting.setupForEntityInInventory();
            Minecraft.getInstance().getEntityRenderDispatcher().render(entity,0,0,0,0,1f,guiGraphics.pose(),guiGraphics.bufferSource(),15728880);
            guiGraphics.pose().popPose();
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
