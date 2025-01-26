package rhymestudio.rhyme.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import rhymestudio.rhyme.client.model.GeoNormalModel;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GeoNormalRenderer<T extends Mob & GeoEntity> extends GeoEntityRenderer<T> {
    boolean ifRotX;
    float scale;
    float offsetY;

    /**
     * @param path 实体文件位置 path.namespace/textures/entity/{name}.png
     */
    public GeoNormalRenderer(EntityRendererProvider.Context renderManager, ResourceLocation path) {
        this(renderManager, path, false,1,0);
    }
    public GeoNormalRenderer(EntityRendererProvider.Context renderManager, ResourceLocation path, boolean ifRotX) {
        this(renderManager, path, ifRotX,1,0);
    }
    public GeoNormalRenderer(EntityRendererProvider.Context renderManager, ResourceLocation path, boolean ifRotX, float scale, float offsetY) {
        super(renderManager, new GeoNormalModel<>(path));
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
//            poseStack.translate(0, 0, 0);
        }

    }

}
