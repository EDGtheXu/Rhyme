package rhymestudio.rhyme.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import rhymestudio.rhyme.client.model.GeoNormalModel;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.Color;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class GeoNormalRenderer<T extends Mob & GeoEntity> extends GeoEntityRenderer<T> {
    boolean ifRotX = false;
    float scale;
    float offsetY;
    boolean energy = false;
    public int consumedOverlay = -1;
    public Color consumedColor;
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

        if(entity instanceof AbstractPlant plant&& Minecraft.getInstance().player.distanceTo(entity) < 2){
            poseStack.pushPose();
            poseStack.translate(0, 1, 0);
            poseStack.scale(0.02f, -0.02f, 0.02f);
            poseStack.translate(0, -15 - (entity.getEyeHeight()-1) * 50,0);
            float f = Minecraft.getInstance().player.yHeadRot;
            Quaternionf q = new Quaternionf().rotateY(-(float) Math.toRadians(f + 180));
            q = q.rotateX((float) Math.toRadians(Minecraft.getInstance().player.getXRot()));
            poseStack.mulPose(q);
            poseStack.translate(-10, 0,0);


            int lvl = plant.getCardLevel();
            CardQualityComponent quality = CardQualityComponent.of(lvl);
            int color = quality.color();
            Minecraft.getInstance().font.drawInBatch("lvl:"+lvl,0,0,color,false,poseStack.last().pose(),buffer, Font.DisplayMode.SEE_THROUGH,0xf000f0,packedLight);

            poseStack.popPose();
        }

        if(entity.getEntityData().get(AbstractPlant.DATA_CAFE_POSE_NAME).equals("ultimate")) {
            energy = true;
            poseStack.scale(1.3f, 1.1f, 1.3f);
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            energy = false;
        }

        consumedOverlay = -1;
        consumedColor = null;
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

    public int getPackedOverlay(T animatable, float u, float partialTick) {
        return consumedOverlay == -1? getOverlayCoords(animatable, u) : consumedOverlay;
    }

    public Color getRenderColor(T animatable, float partialTick, int packedLight) {
        return consumedColor==null? super.getRenderColor(animatable, partialTick, packedLight) : consumedColor;
    }

}
