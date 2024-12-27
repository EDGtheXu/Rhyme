package rhymestudio.rhyme.client.render.entity.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import rhymestudio.rhyme.core.entity.misc.HelmetEntity;

public class HelmetEntityRenderer extends EntityRenderer<HelmetEntity> {

    public HelmetEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    protected float getShadowRadius(HelmetEntity entity) {
        return 0F;
    }
    public void render(HelmetEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ItemStack stack = entity.helmetStack;
        LivingEntity living = entity.owner;
        if (stack!=null && !stack.isEmpty() && living!= null) {
            poseStack.pushPose();
            poseStack.scale(0.65f, 0.65f, 0.65f);
            poseStack.translate(0.0D, 0.5D, 0.0D);
            if(entity.onGround()){
                float y = Mth.lerp((entity.tickCount+partialTick - 80)/20.0f, 0, 1);
                if(y>0) poseStack.translate(0, -y, 0);
                poseStack.mulPose(Axis.XN.rotationDegrees(entity.cachedPitch));
                poseStack.mulPose(Axis.YN.rotationDegrees(entity.cachedYaw));
            }else{
//                float ly = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
                float lx = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());

                poseStack.mulPose(Axis.XN.rotationDegrees(lx));
                poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw));
            }

            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.HEAD,packedLight, OverlayTexture.NO_OVERLAY,poseStack,bufferSource,living.level(),0);

            poseStack.popPose();
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(HelmetEntity helmetEntity) {
        return null;
    }
}
