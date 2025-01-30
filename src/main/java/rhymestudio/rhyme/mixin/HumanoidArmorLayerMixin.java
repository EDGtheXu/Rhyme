package rhymestudio.rhyme.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rhymestudio.rhyme.client.render.util.ArmorLayerMixinUtil;
import rhymestudio.rhyme.core.item.armor.IModelArmor;
@SuppressWarnings("all")
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @Unique
    protected RenderLayerParent<T, M> Rhyme$renderer;
    @Inject(method = "<init>", at = @At("RETURN"))
    public void initMixin(RenderLayerParent<T, M> renderer, HumanoidModel<T> innerModel, HumanoidModel<T> outerModel, ModelManager modelManager, CallbackInfo ci) {
        this.Rhyme$renderer = renderer;
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;usesInnerModel(Lnet/minecraft/world/entity/EquipmentSlot;)Z"), cancellable = true)
    public void renderMixin(PoseStack poseStack, MultiBufferSource bufferSource, T livingEntity, EquipmentSlot slot, int packedLight, A model, CallbackInfo ci) {
        ItemStack itemstack = livingEntity.getItemBySlot(slot);
        if(itemstack.getItem() instanceof IModelArmor){
            ArmorLayerMixinUtil.renderModelArmor(poseStack, bufferSource, livingEntity, slot, packedLight, model,  itemstack,
                    Rhyme$renderer.getModel().head
            );
            ci.cancel();
        }
    }
}
