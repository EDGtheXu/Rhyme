package rhymestudio.rhyme.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.entity.AbstractPlant;

import javax.annotation.Nullable;


public class BasePlantRenderer<T extends AbstractPlant,U extends EntityModel<T>> extends MobRenderer<T, U> {
    private boolean rotY;
    private float scale;
    private boolean energy = false;
    public BasePlantRenderer(EntityRendererProvider.Context renderManager,U model) {
        this(renderManager, model,0.3f,1);
    }

    public BasePlantRenderer(EntityRendererProvider.Context renderManager,U model,float shadowRadius,float scale) {
        this(renderManager, model,shadowRadius,scale,false);
    }

    public BasePlantRenderer(EntityRendererProvider.Context renderManager,U model,float shadowRadius,float scale,boolean rotY) {
        super(renderManager, model,shadowRadius);
        this.rotY = rotY;
        this.scale = scale;
    }

    @Override
    protected void setupRotations(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        if(rotY) poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        poseStack.scale(this.scale, this.scale, this.scale);
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractPlant abstractPlant) {
        String s = "textures/entity/"+abstractPlant.namePath+".png";
        return Rhyme.space(s);
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

    @Nullable
    protected RenderType getRenderType(T entity, boolean bodyVisible, boolean translucent, boolean glowing) {

//        ResourceLocation resourcelocation = this.getTextureLocation(livingEntity);
//        RenderType.CompositeRenderType rendertype = (RenderType.CompositeRenderType) ModRenderTypes.cthRenderType(resourcelocation);
//        ShaderInstance shader = rendertype.state.shaderState.shader.get().get();
////        RenderSystem._setShaderTexture(3, Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
////        ((IShaderInstance)shader).getRhyme$TEST().set((float) Math.sin(livingEntity.tickCount / 10f) * 0.2f + 0.8f);
//        ((IShaderInstance)shader).getRhyme$TEST().set(0.5F);

        if(energy){
            return RenderType.energySwirl(
                         getTextureLocation(entity),
                         (float) Math.sin(System.currentTimeMillis() / 1000d),
                         (float) Math.cos(System.currentTimeMillis() / 1000d));
        }else{
            return super.getRenderType(entity, bodyVisible, translucent, glowing);
        }
    }

}
