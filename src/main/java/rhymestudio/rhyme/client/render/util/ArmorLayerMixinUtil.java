package rhymestudio.rhyme.client.render.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ArmorLayerMixinUtil {
    public static void renderModelArmor(PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity livingEntity, EquipmentSlot slot, int packedLight, HumanoidModel<? extends LivingEntity> model, ItemStack stack,
                                                         ModelPart head
    ){
        poseStack.pushPose();
        if(slot == EquipmentSlot.HEAD){

            float s = 0.65f;
            poseStack.scale(s,s,s);
//            poseStack.translate(0, offsetHeadY , head.z*0.2);
            poseStack.translate(0, head.y*0.01 , head.z*0.2);
            float lerpY = Mth.lerp( Minecraft.getInstance().getFrameTime(),livingEntity.yHeadRotO,livingEntity.yHeadRot);
            float lerpY2 = Mth.lerp( Minecraft.getInstance().getFrameTime(),livingEntity.yBodyRotO,livingEntity.yBodyRot);
            poseStack.mulPose(Axis.YP.rotationDegrees( lerpY-lerpY2 + 180));
            poseStack.mulPose(Axis.XP.rotation(3.1415927f - head.xRot));
            poseStack.translate(0, 0 , head.z*0.2);
            poseStack.translate(0,0.35+head.y*0.01 , 0);

//            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.HEAD,packedLight,OverlayTexture.NO_OVERLAY,poseStack,bufferSource,livingEntity.level(),0);

            Minecraft.getInstance().getItemRenderer().renderStatic(livingEntity,stack, ItemDisplayContext.HEAD,false,poseStack,bufferSource,livingEntity.level(),packedLight,OverlayTexture.NO_OVERLAY,0);
        }
        poseStack.popPose();
    }



}
