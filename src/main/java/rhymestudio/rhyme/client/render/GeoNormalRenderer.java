package rhymestudio.rhyme.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import rhymestudio.rhyme.client.model.GeoNormalModel;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GeoNormalRenderer<T extends Mob & GeoEntity> extends GeoEntityRenderer<T> {
    boolean ifRotX = false;
    float scale;
    float offsetY;
    boolean energy = false;
    public GeoNormalRenderer(EntityRendererProvider.Context renderManager, String name, boolean ifRotX) {
        this(renderManager, name, ifRotX,1,0);
    }
    public GeoNormalRenderer(EntityRendererProvider.Context renderManager, String name, boolean ifRotX, float scale, float offsetY) {
        super(renderManager, new GeoNormalModel<>(name));
        this.ifRotX = ifRotX;
        this.scale=scale;
        this.offsetY=offsetY;
    }
    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);

        poseStack.scale(scale, scale, scale);
        poseStack.translate(0, offsetY, 0);
        if(ifRotX) {
            double rad = animatable.yBodyRot * Math.PI / 180;
            poseStack.mulPose(Axis.of(new Vector3f((float) Math.cos(rad), 0, (float) Math.sin(rad))).rotationDegrees(animatable.xRotO));
            poseStack.translate(0, 0.5, 0);
        }

    }


    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        if(entity.getEntityData().get(AbstractPlant.DATA_CAFE_POSE_NAME).equals("ultimate")) {
            energy = true;
            poseStack.scale(1.3f, 1.1f, 1.3f);
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            energy = false;
        }
    }

    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        if(energy){
            return RenderType.energySwirl(
                    getTextureLocation(animatable),
                    (float) Math.sin(System.currentTimeMillis() / 1000d),
                    (float) Math.cos(System.currentTimeMillis() / 1000d));
        }else{
            return super.getRenderType(animatable, texture, bufferSource, partialTick);
        }
    }

}
