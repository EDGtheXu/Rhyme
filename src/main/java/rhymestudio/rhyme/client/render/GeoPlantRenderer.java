package rhymestudio.rhyme.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponentType;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.object.Color;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class GeoPlantRenderer<T extends AbstractPlant & GeoEntity> extends GeoNormalRenderer<T> {
    boolean energy = false;
    public int consumedOverlay = -1;
    public Color consumedColor;
    public GeoPlantRenderer(EntityRendererProvider.Context renderManager, ResourceLocation name, boolean ifRotX) {
        super(renderManager, name,ifRotX);
    }
    public GeoPlantRenderer(EntityRendererProvider.Context renderManager, ResourceLocation name, boolean ifRotX, float scale, float offsetY) {
        super(renderManager, name,ifRotX, scale, offsetY);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        if(Minecraft.getInstance().player.distanceTo(entity) < 2){
            poseStack.pushPose();
            poseStack.translate(0, 1, 0);
            poseStack.scale(0.02f, -0.02f, 0.02f);
            poseStack.translate(0, -15 - (entity.getEyeHeight()-1) * 50,0);
            float f = Minecraft.getInstance().player.yHeadRot;
            Quaternionf q = new Quaternionf().rotateY(-(float) Math.toRadians(f + 180));
            q = q.rotateX((float) Math.toRadians(Minecraft.getInstance().player.getXRot()));
            poseStack.mulPose(q);
            poseStack.translate(-10, 0,0);

// todo

            int lvl = entity.getCardLevel();
            CardQualityComponentType quality = CardQualityComponentType.of(lvl);
            int color = quality.color;
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
