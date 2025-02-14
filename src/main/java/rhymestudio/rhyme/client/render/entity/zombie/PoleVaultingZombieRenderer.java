package rhymestudio.rhyme.client.render.entity.zombie;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3d;
import rhymestudio.rhyme.client.render.GeoNormalRenderer;
import rhymestudio.rhyme.core.entity.zombies.PoleVaultingZombie;
import rhymestudio.rhyme.core.registry.items.ToolItems;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class PoleVaultingZombieRenderer extends GeoNormalRenderer<PoleVaultingZombie> {

    public PoleVaultingZombieRenderer(EntityRendererProvider.Context renderManager, ResourceLocation path) {
        super(renderManager, path);
    }

    public void render(PoleVaultingZombie entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        if(!entity.havePole()) return;

        ItemStack stack = ToolItems.POLE.get().getDefaultInstance();
        poseStack.pushPose();
        var mod = this.getGeoModel();
        var arm = mod.getBone("right_arm").get();
//        poseStack.mulPose(Axis.YP.rotation((float) (Math.PI + entityYaw * 0.017453292F )));
        Vector3d armPos = arm.getLocalPosition();
        poseStack.translate((float) armPos.x() , (float) armPos.y(), (float) armPos.z());
        Vector3d armRotate = arm.getRotationVector();

        poseStack.mulPose(Axis.YP.rotation((float)Math.PI -(entityYaw * 0.017453292F )));

        poseStack.mulPose(Axis.XP.rotation((float) armRotate.x));
        poseStack.mulPose(Axis.YP.rotation(- (float) armRotate.y));
        poseStack.mulPose(Axis.ZP.rotation((float) armRotate.z));

//        poseStack.mulPose(Axis.YP.rotation(-(float) (Math.PI + entityYaw * 0.017453292F )));
        poseStack.translate(0 , -0.3f,  0.5);


//        poseStack.mulPose(new Quaternionf().rotateXYZ((float) armRotate.x(), (float) armRotate.y(), (float) armRotate.z()));

//        poseStack.translate(arm.getModelPosition().x()/4, arm.getModelPosition().y()/4, arm.getModelPosition().z()/4);
//        poseStack.mulPose(arm.getLocalSpaceMatrix());


        Minecraft.getInstance().getItemRenderer().renderStatic(entity,stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,false,poseStack,bufferSource,entity.level(),packedLight, OverlayTexture.NO_OVERLAY,0);

        poseStack.popPose();
    }

    public void actuallyRender(PoseStack poseStack, PoleVaultingZombie entity, BakedGeoModel model, @Nullable RenderType renderType,
                                MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick,
                                int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        super.actuallyRender(poseStack, entity, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);


    }


}
