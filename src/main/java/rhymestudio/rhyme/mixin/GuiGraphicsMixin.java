package rhymestudio.rhyme.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rhymestudio.rhyme.registry.ModDataComponentTypes;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {


    @Shadow @Final private PoseStack pose;

    @Shadow public abstract void renderItem(ItemStack stack, int x, int y);

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;


    @Shadow public abstract void flush();

    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V"),cancellable = true)
    private void renderItemMixin(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset, CallbackInfo ci) {
        var data = stack.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        if(data!=null){
            ItemStack qualityItem = data.getQualityItem().getDefaultInstance();
            BakedModel bakedmodel = this.minecraft.getItemRenderer().getModel(qualityItem, level, entity, seed);


            //            pose.scale(1.2f, 1.2f, 1.2f);
            pose.translate(0, 0, -1f);
            minecraft.getItemRenderer().render(qualityItem, ItemDisplayContext.GUI, false, this.pose,  this.bufferSource, 15728880, OverlayTexture.NO_OVERLAY,bakedmodel);
            pose.translate(0, 0, 1f);
//            pose.scale(1/1.2f, 1/1.2f, 1/1.2f);


/*
            PeaModel model = PeaModel.getInstance();
            if(model==null) return;

            pose.translate(0,1,0);

            model.defaultAnimate(pose);
            model.renderToBuffer(pose, this.bufferSource.getBuffer(RenderType.entityTranslucent(Rhyme.space("textures/entity/pea_shooter.png"))), 15728880, OverlayTexture.NO_OVERLAY);
            this.flush();

            pose.popPose();
            ci.cancel();
*/
        }
    }
}
