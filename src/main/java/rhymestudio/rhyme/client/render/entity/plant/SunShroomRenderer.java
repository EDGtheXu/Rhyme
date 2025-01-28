package rhymestudio.rhyme.client.render.entity.plant;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.client.render.GeoPlantRenderer;
import rhymestudio.rhyme.core.entity.plants.shroom.SunShroom;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class SunShroomRenderer<T extends SunShroom> extends GeoPlantRenderer<T> {


    public SunShroomRenderer(EntityRendererProvider.Context renderManager, ResourceLocation path) {
        super(renderManager, path);
    }

    public SunShroomRenderer(EntityRendererProvider.Context renderManager, ResourceLocation path, boolean ifRotX, float scale, float offsetY) {
        super(renderManager, path, ifRotX, scale, offsetY);
    }

    public SunShroomRenderer(EntityRendererProvider.Context renderManager, ResourceLocation path, boolean ifRotX) {
        super(renderManager, path, ifRotX);
    }
    @Override
    public void preRender(PoseStack poseStack, T entity, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {

        float rate = 1 / 160.0f;
        float size=1;
        float progress = (Math.min(entity.tickCount - entity.lastGrowthTick, 40) + partialTick);
        if(entity.stage == 1){
            if(entity.growth <= 40) size += progress * rate;
            else size += 40 * rate;
        }else if(entity.stage == 2){
            progress += 40;
            if(entity.growth <= 40) size += progress * rate;
            else size += 80 * rate;
        }

        poseStack.scale(size, size, size);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);

    }

}
