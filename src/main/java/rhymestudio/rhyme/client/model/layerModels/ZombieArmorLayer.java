////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package rhymestudio.rhyme.client.model.layerModels;
//
//import com.google.common.collect.Maps;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import java.util.Map;
//
//import net.minecraft.client.model.HumanoidModel;
//import net.minecraft.client.model.Model;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.Sheets;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.client.renderer.texture.TextureAtlas;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.client.resources.model.ModelManager;
//import net.minecraft.core.Holder;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.armortrim.ArmorTrim;
//
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import net.minecraftforge.client.extensions.common.IClientItemExtensions;
//import rhymestudio.rhyme.client.model.zombieModels.NormalZombieModel;
//import rhymestudio.rhyme.client.render.util.ArmorLayerMixinUtil;
//import rhymestudio.rhyme.core.entity.AbstractMonster;
//
//@OnlyIn(Dist.CLIENT)
//public class ZombieArmorLayer<T extends AbstractMonster, M extends NormalZombieModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
//    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
//    private final A outerModel;
//    private final TextureAtlas armorTrimAtlas;
//
//    public ZombieArmorLayer(RenderLayerParent<T, M> renderer, A outerModel, ModelManager modelManager) {
//        super(renderer);
//        this.outerModel = outerModel;
//        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
//    }
//
//    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.CHEST, packedLight, this.getArmorModel(EquipmentSlot.CHEST), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
//        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.LEGS, packedLight, this.getArmorModel(EquipmentSlot.LEGS), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
//        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.FEET, packedLight, this.getArmorModel(EquipmentSlot.FEET), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
//        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.HEAD, packedLight, this.getArmorModel(EquipmentSlot.HEAD), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
//    }
//
//
//    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource bufferSource, T livingEntity, EquipmentSlot slot, int packedLight, A p_model, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
//        ItemStack itemstack = livingEntity.getItemBySlot(slot);
//        Item var15 = itemstack.getItem();
//        if (var15 instanceof ArmorItem armoritem) {
//            if (armoritem.getEquipmentSlot() == slot) {
//                (this.getParentModel()).copyPropertiesTo(p_model);
//                this.setPartVisibility(p_model, slot);
//                Model model = this.getArmorModelHook(livingEntity, itemstack, slot, p_model);
//                IClientItemExtensions extensions = IClientItemExtensions.of(itemstack);
////                extensions.setupModelAnimations(livingEntity, itemstack, slot, model, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
//
//
//                ArmorLayerMixinUtil.renderModelArmor(poseStack, bufferSource, livingEntity, slot, packedLight, p_model, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch,itemstack,
//                        this.getParentModel().head
//                );
///*
//                if(itemstack.getItem() instanceof IModelArmor){
//                    poseStack.pushPose();
//                    if(slot == EquipmentSlot.HEAD){
//                        if(getParentModel() instanceof NormalZombieModel<T> model1){
//
//                            System.out.println(model1.head.x);
//
//                            float s = 0.65f;
//                            poseStack.scale(s,s,s);
//                            poseStack.translate(0,-0.3 , model1.head.z*0.3);
//                            poseStack.mulPose(Axis.YP.rotation((float)3.14159 + model1.head.yRot));
//                            poseStack.mulPose(Axis.XN.rotation((float)3.14159 + model1.head.xRot));
//                        }
//
//                        Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.HEAD,packedLight,OverlayTexture.NO_OVERLAY,poseStack,bufferSource,livingEntity.level(),0);
//                    }
//                    poseStack.popPose();
//                }*/
//
//            }
//        }
//    }
//
//    protected void setPartVisibility(A model, EquipmentSlot slot) {
//        model.setAllVisible(false);
//        switch (slot) {
//            case HEAD:
//                model.head.visible = true;
//                model.hat.visible = true;
//                break;
//            case CHEST:
//                model.body.visible = true;
//                model.rightArm.visible = true;
//                model.leftArm.visible = true;
//                break;
//            case LEGS:
//                model.body.visible = true;
//                model.rightLeg.visible = true;
//                model.leftLeg.visible = true;
//                break;
//            case FEET:
//                model.rightLeg.visible = true;
//                model.leftLeg.visible = true;
//        }
//
//    }
//
//    private void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, A model, int dyeColor, ResourceLocation textureLocation) {
//        this.renderModel(poseStack, bufferSource, packedLight, (Model)model, dyeColor, textureLocation);
//    }
//
//    private void renderModel(PoseStack p_289664_, MultiBufferSource p_289689_, int p_289681_, Model p_289658_, int p_350798_, ResourceLocation p_324344_) {
//        VertexConsumer vertexconsumer = p_289689_.getBuffer(RenderType.armorCutoutNoCull(p_324344_));
//        p_289658_.renderToBuffer(p_289664_, vertexconsumer, p_289681_, OverlayTexture.NO_OVERLAY, p_350798_);
//    }
//
//    private void renderTrim(Holder<ArmorMaterial> armorMaterial, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ArmorTrim trim, A model, boolean innerTexture) {
//        this.renderTrim(armorMaterial, poseStack, bufferSource, packedLight, trim, (Model)model, innerTexture);
//    }
//
//    private void renderTrim(Holder<ArmorMaterial> p_323506_, PoseStack p_289687_, MultiBufferSource p_289643_, int p_289683_, ArmorTrim p_289692_, Model p_289663_, boolean p_289651_) {
//        TextureAtlasSprite textureatlassprite = this.armorTrimAtlas.getSprite(p_289651_ ? p_289692_.innerTexture(p_323506_) : p_289692_.outerTexture(p_323506_));
//        VertexConsumer vertexconsumer = textureatlassprite.wrap(p_289643_.getBuffer(Sheets.armorTrimsSheet(p_289692_.pattern().value().decal())));
//        p_289663_.renderToBuffer(p_289687_, vertexconsumer, p_289683_, OverlayTexture.NO_OVERLAY);
//    }
//
//    private void renderGlint(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, A model) {
//        this.renderGlint(poseStack, bufferSource, packedLight, (Model)model);
//    }
//
//    private void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, Model p_289659_) {
//        p_289659_.renderToBuffer(p_289673_, p_289654_.getBuffer(RenderType.armorEntityGlint()), p_289649_, OverlayTexture.NO_OVERLAY);
//    }
//
//    private A getArmorModel(EquipmentSlot slot) {
//        return this.outerModel;
//    }
//
//    private boolean usesInnerModel(EquipmentSlot slot) {
//        return slot == EquipmentSlot.LEGS;
//    }
//
//    protected Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
//        return ClientHooks.getArmorModel(entity, itemStack, slot, model);
//    }
//}
