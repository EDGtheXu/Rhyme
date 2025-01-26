package rhymestudio.rhyme.client.render.entity.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.model.GeoNormalModel;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SunRenderer extends GeoEntityRenderer<SunItemEntity> {
    public SunRenderer(EntityRendererProvider.Context context) {
        super(context, new GeoNormalModel<>(Rhyme.space("sun")));
    }

    public void render(SunItemEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(0.8F, 0.8F, 0.8F);
        poseStack.translate(0, 0, 0);
        float f = Math.min((entity.tickCount + partialTick)/20.0F, 1.0F);
        if (f < 1F) poseStack.scale(f,f,f);
        float f1 = Math.max((entity.tickCount + partialTick - entity.touchTick)/10F, 0.0F);

        float f2 = 1.2f - f1;
        if(f2 < 1) {
//            poseStack.translate(0,1,0);
            poseStack.scale(f2,f2,f2);
//            poseStack.translate(0,-1,0);
        }
        poseStack.mulPose(Axis.YP.rotation((float) (Mth.lerp(partialTick, entity.cachedYawO, entity.cachedYaw) + Math.sin(entity.tickCount/5F)*0.2f)));

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

    }

}
