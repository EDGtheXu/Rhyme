package rhymestudio.rhyme.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.core.block.ZombieFlagBlock;

public class ZombieFlagBlockRenderer<E extends ZombieFlagBlock.ZombieFlagBlockEntity> implements BlockEntityRenderer<E> {

    public ZombieFlagBlockRenderer(BlockEntityRendererProvider.Context context) {

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

    @Override
    public void render(E blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        poseStack.pushPose();
        if (level != null) {
            poseStack.translate(0.5, 0, 0.5);
//            poseStack.mulPose(Axis.YP.rotation((level.getGameTime()%999999999 + partialTick)* 0.2f) );

            poseStack.translate(0, 0.5, 0);
            ModelPart modelpart = Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.BANNER);
            ModelPart flag = modelpart.getChild("flag");
            var registry = blockEntity.getLevel().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN);
            BannerRenderer.renderPatterns(poseStack, buffer, packedLight, packedOverlay, flag, ModelBakery.BANNER_BASE, true,
                    DyeColor.PURPLE, new BannerPatternLayers.Builder()
//                            .addIfRegistered(registry, BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN)
                            .addIfRegistered(registry, BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
                            .addIfRegistered(registry, BannerPatterns.STRIPE_CENTER, DyeColor.GRAY)
//                            .addIfRegistered(registry, BannerPatterns.BORDER, DyeColor.LIGHT_GRAY)
                            .addIfRegistered(registry, BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)

                            .addIfRegistered(registry, BannerPatterns.CREEPER, DyeColor.GREEN)
//                            .addIfRegistered(registry, BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK)

//                            .addIfRegistered(registry, BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
                            .addIfRegistered(registry, BannerPatterns.BORDER, DyeColor.RED)

                    .build());
            poseStack.translate(0, -0.5, 0);


            poseStack.scale(2,3,1);
            poseStack.translate(-0.5, 0, -0.5);
        }
//        poseStack.translate(-1, 0, -1);

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                Blocks.TORCH.defaultBlockState(), poseStack, buffer, packedLight, packedOverlay
        );


        poseStack.popPose();


    }
}
