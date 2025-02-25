package rhymestudio.rhyme.client.render.block;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.render.buffer.AbstractBufferManager;
import rhymestudio.rhyme.client.render.buffer.RenderUtil;
import rhymestudio.rhyme.core.block.ZombieBannerBlock;

public class ZombieFlagBlockRenderer<E extends ZombieBannerBlock.ZombieFlagBlockEntity> implements BlockEntityRenderer<E> {

    OutlineBuffer outlineBuffer;
    public ZombieFlagBlockRenderer(BlockEntityRendererProvider.Context context) {
        outlineBuffer = new OutlineBuffer(null, 0.5f);
    }



    @Override
    public boolean shouldRenderOffScreen(E pBlockEntity) {
        return false;
    }

    @Override
    public int getViewDistance() {
        return 64;
    }

    @Override
    public boolean shouldRender(E blockEntity, Vec3 cameraPos) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).closerThan(cameraPos, this.getViewDistance());
    }

    @Override
    public AABB getRenderBoundingBox(E blockEntity) {
        return AABB.INFINITE;
    }

    public class OutlineBuffer extends AbstractBufferManager{


        BlockPos targetPos;
        float size;
        public OutlineBuffer(BlockPos center,float size) {
            super(0);
            this.targetPos = center;
            this.size = size;
        }

        @Override
        protected boolean shouldRender() {
            return targetPos!=null;
        }

        @Override
        protected void beforeRender() {

        }

        @Override
        protected void afterRender(PoseStack poseStack) {

        }

        @Override
        protected void buildBuffer(BufferBuilder buffer, PoseStack poseStack) {
            if(targetPos!=null){

//            RenderSystem.setShaderTexture(0, Rhyme.space("textures/block/card_up_level_block.png"));
                float random = System.currentTimeMillis() % 1000000 * 0.00008f;
                double offsetU = Math.sin(random) * 1f + random * 2;
                double offsetV = Math.cos(random) * 1f  + Math.cos(random * 0.3f) * 0.01f;

                RenderUtil.renderAABB(buffer,
                        targetPos.getX() -0.1F, targetPos.getY() -0.1F, targetPos.getZ() -0.1F,targetPos.getX() + size, targetPos.getY() + size, targetPos.getZ() + size,
                        255,255,255,100,
                        5, (float)offsetU, (float)offsetV
                );
            }
        }
    }

    @Override
    public void render(E blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        outlineBuffer.targetPos = blockEntity.getBlockPos();
//        outlineBuffer.render(poseStack, buffer, packedLight, packedOverlay);

//        poseStack.pushPose();
//        if (level != null) {
//            poseStack.translate(0.5, 0, 0.5);
////            poseStack.mulPose(Axis.YP.rotation((level.getGameTime()%999999999 + partialTick)* 0.2f) );
//
//            poseStack.translate(0, 0.5, 0);
//            ModelPart modelpart = Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.BANNER);
//            ModelPart flag = modelpart.getChild("flag");
//            var registry = blockEntity.getLevel().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN);
//            BannerRenderer.renderPatterns(poseStack, buffer, packedLight, packedOverlay, flag, ModelBakery.BANNER_BASE, true,
//                    DyeColor.PURPLE, new BannerPatternLayers.Builder()
////                            .addIfRegistered(registry, BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN)
//                            .addIfRegistered(registry, BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
//                            .addIfRegistered(registry, BannerPatterns.STRIPE_CENTER, DyeColor.GRAY)
////                            .addIfRegistered(registry, BannerPatterns.BORDER, DyeColor.LIGHT_GRAY)
//                            .addIfRegistered(registry, BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)
//
//                            .addIfRegistered(registry, BannerPatterns.CREEPER, DyeColor.GREEN)
////                            .addIfRegistered(registry, BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK)
//
////                            .addIfRegistered(registry, BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
//                            .addIfRegistered(registry, BannerPatterns.BORDER, DyeColor.RED)
//
//                    .build());
//            poseStack.translate(0, -0.5, 0);
//
//
//            poseStack.scale(2,3,1);
//            poseStack.translate(-0.5, 0, -0.5);
//        }
////        poseStack.translate(-1, 0, -1);
//
//        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
//                Blocks.TORCH.defaultBlockState(), poseStack, buffer, packedLight, packedOverlay
//        );
//
//
//        poseStack.popPose();


    }
}
