package rhymestudio.rhyme.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponentType;

import static rhymestudio.rhyme.client.model.ModelUtils.HEAD_MODEL_ITEMS;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow public abstract void renderStatic(@Nullable LivingEntity entity, ItemStack itemStack, ItemDisplayContext diplayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, @Nullable Level level, int combinedLight, int combinedOverlay, int seed);

    @Shadow @Final private ItemModelShaper itemModelShaper;

    @Shadow public abstract void render(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model);

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", at = @At("HEAD"),cancellable = true)
    private void renderStaticMixin(LivingEntity entity, ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, Level level, int combinedLight, int combinedOverlay, int seed, CallbackInfo ci) {
        Item item = itemStack.getItem();
        if(HEAD_MODEL_ITEMS.containsKey(item)){
            if(displayContext == ItemDisplayContext.HEAD) {
                ResourceLocation location;
                if(entity != null && entity.isAlive()){
                    float additionHp = entity.getMaxHealth() - 35;
                    float percentage = (entity.getMaxHealth() - entity.getHealth()) / additionHp;
                    if(percentage > 0.66f){
                        location = HEAD_MODEL_ITEMS.get(item).get(2);
                    }else if(percentage > 0.33f){
                        location = HEAD_MODEL_ITEMS.get(item).get(1);
                    }else{
                        location = HEAD_MODEL_ITEMS.get(item).get(0);
                    }
                }else{
                    location = HEAD_MODEL_ITEMS.get(item).get(HEAD_MODEL_ITEMS.get(item).size()-1);
                }

                BakedModel bakedmodel = itemModelShaper.getModelManager().getModel(new ModelResourceLocation(location, "inventory"));
                if(bakedmodel!=itemModelShaper.getModelManager().getMissingModel()) {
                    this.render(itemStack, displayContext, leftHand, poseStack, bufferSource, combinedLight, combinedOverlay, bakedmodel);
                    ci.cancel();
                }
            }
        }

        var data = new CardQualityComponentType(itemStack);
        if(data.isValid()){
            ItemStack qualityItem = data.getQualityItem().getDefaultInstance();
            renderStatic(entity, qualityItem, displayContext, leftHand, poseStack, bufferSource, level, combinedLight, combinedOverlay, seed);
        }
    }
            /*
    @Inject(method = "getModel", at = @At(value = "HEAD"),cancellable = true)
    public void getModel(ItemStack stack, Level level, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {

        BakedModel bakedmodel;
        if(stack.is(ArmorItems.CONE_HELMET)){
            bakedmodel = itemModelShaper.getItemModel(Items.LEATHER_HELMET);
            ClientLevel clientlevel = level instanceof ClientLevel ? (ClientLevel)level : null;
            BakedModel bakedmodel1 = bakedmodel.getOverrides().resolve(bakedmodel, stack, clientlevel, entity, seed);
            bakedmodel1 = bakedmodel1 == null ? itemModelShaper.getModelManager().getMissingModel() : bakedmodel1;

            cir.setReturnValue(bakedmodel1);
            cir.cancel();
        }else{
            return;
        }
    }
    */



}
