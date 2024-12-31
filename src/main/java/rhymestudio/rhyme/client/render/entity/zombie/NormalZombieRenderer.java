package rhymestudio.rhyme.client.render.entity.zombie;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.model.layerModels.ZombieArmorLayer;
import rhymestudio.rhyme.client.model.zombieModels.NormalZombieModel;
import rhymestudio.rhyme.core.entity.AbstractMonster;
import rhymestudio.rhyme.core.entity.zombies.NormalZombie;


public class NormalZombieRenderer<T extends NormalZombie, M extends NormalZombieModel<T>> extends MobRenderer<T, M> {
    private boolean rotY;
    private float scale;
    public NormalZombieRenderer(EntityRendererProvider.Context renderManager, M model) {
        this(renderManager, model,0.3f,1);
    }

    public NormalZombieRenderer(EntityRendererProvider.Context renderManager, M model, float shadowRadius, float scale) {
        this(renderManager, model,shadowRadius,scale,false);
    }

    public NormalZombieRenderer(EntityRendererProvider.Context context, M model, float shadowRadius, float scale, boolean rotY) {
        super(context, model,shadowRadius);
        this.rotY = rotY;
        this.scale = scale;

        this.addLayer(new ZombieArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.ZOMBIE)),
//                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.ZOMBIE)),
                context.getModelManager()
        ));

//        this.addLayer(new CustomHeadLayer(this, context.getModelSet(), scale, scale, scale, context.getItemInHandRenderer()));
//        this.addLayer(new ElytraLayer(this, context.getModelSet()));
//        this.addLayer(new ItemInHandLayer(this, context.getItemInHandRenderer()));
    }

    protected void setupRotations(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        if(rotY) poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        poseStack.scale(this.scale, this.scale, this.scale);
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
    }
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        ModelPart arm = null;
        ModelPart head = null;
        for(ModelPart part : model.root().getAllParts().toList()){
            if(part.hasChild("left_arm")){
                arm = part.getChild("left_arm");
            }
            if(part.hasChild("head")){
                head = part.getChild("head");
            }
        }

        if(arm!= null) {
            arm.visible = !entity.isDropArm;
        }
        if(head != null) {
            head.visible = !entity.isDropHead;
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        if(arm!= null) {
            arm.visible = true;
        }
        if(head != null) {
            head.visible = true;
        }
    }


        @Override
    public ResourceLocation getTextureLocation(T entity) {
        String s = "textures/entity/zombies/"+entity.getNamePath()+".png";
        return Rhyme.space(s);
    }
/*
    @Nullable
    protected RenderType getRenderType(T livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
        ResourceLocation resourcelocation = this.getTextureLocation(livingEntity);
        RenderType.CompositeRenderType rendertype = (RenderType.CompositeRenderType) ModRenderTypes.cthRenderType(resourcelocation);
        ShaderInstance shader = rendertype.state.shaderState.shader.get().get();
//        RenderSystem._setShaderTexture(3, Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
//        ((IShaderInstance)shader).getRhyme$TEST().set((float) Math.sin(livingEntity.tickCount / 10f) * 0.2f + 0.8f);
        ((IShaderInstance)shader).getRhyme$TEST().set(0.5F);
        return rendertype;
    }
    */
}
