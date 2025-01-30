package rhymestudio.rhyme.client.render.entity.proj;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.entity.BaseProj;

public class ProjRenderer<T extends BaseProj> extends EntityRenderer<T> {
    private final EntityModel<T> bulletModel;
    float size;
    float offsetY;
    public ProjRenderer(EntityRendererProvider.Context pContext, EntityModel<T> pModel,float size,float offsetY) {
        super(pContext);
        bulletModel = pModel;
        this.size = size;
        this.offsetY = offsetY;
    }

    public ProjRenderer(EntityRendererProvider.Context pContext, EntityModel<T> pModel) {
        super(pContext);
        bulletModel = pModel;
    }

    @Override
    public ResourceLocation getTextureLocation(BaseProj pEntity) {
        if(pEntity.getTexture() == null)return Rhyme.space("textures/entity/missing_texture.png");
        return pEntity.getTexture();
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        pPoseStack.scale(size,size,size);
        pPoseStack.translate(0,offsetY,0);
        VertexConsumer buffer = pBuffer.getBuffer(this.bulletModel.renderType(this.getTextureLocation(pEntity)));
        this.bulletModel.renderToBuffer(pPoseStack,buffer,pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }

}
