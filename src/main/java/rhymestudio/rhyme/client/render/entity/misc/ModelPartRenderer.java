package rhymestudio.rhyme.client.render.entity.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import rhymestudio.rhyme.core.entity.misc.ModelPartEntity;

public class ModelPartRenderer extends EntityRenderer<ModelPartEntity> {

    public ModelPartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    protected float getShadowRadius(ModelPartEntity entity) {
        return 0F;
    }
    public void render(ModelPartEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        LivingEntity living = entity.owner;
        if (living == null) return;
        var render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(living);
        var model = (HierarchicalModel<?>)((LivingEntityRenderer<?, ?>)render).getModel();
        ModelPart root = model.root();
        ModelPart tar = null;
        for(ModelPart part :root.getAllParts().toList()){
            if(part.hasChild(entity.name)){
                tar = part.getChild(entity.name);
                tar.resetPose();
            }
        }

        if (tar != null) {
            poseStack.pushPose();
            if(entity.onGround()){
                float y = Mth.lerp((entity.tickCount+partialTick - 80)/20.0f, 0, 1);
                if(y>0) poseStack.translate(0, -y, 0);
            }
            poseStack.translate(-0.35, -1, -0.4);
            poseStack.translate(0.5, 1.1, 0.5);
            poseStack.mulPose(Axis.YN.rotationDegrees(entity.cachedYaw+180));
            if(entity.name.equals("head")){
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
                poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            }

            poseStack.translate(-0.5, 0, -0.5);
            poseStack.mulPose(Axis.XN.rotationDegrees(90));
//            poseStack.translate(-0.5, 1, 0.1);

            poseStack.translate(0, 1, 0);
            tar.render(poseStack, bufferSource.getBuffer(model.renderType(render.getTextureLocation(living))), packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(ModelPartEntity helmetEntity) {
        return null;
    }
}
