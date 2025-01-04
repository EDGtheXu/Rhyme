package rhymestudio.rhyme.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import rhymestudio.rhyme.core.block.SunCreatorBlock.SunCreatorBlockEntity;

public class SunCreatorBlockRenderer implements BlockEntityRenderer<SunCreatorBlockEntity> {
    public SunCreatorBlockRenderer(BlockEntityRendererProvider.Context pContext){

    }
    @Override
    public void render(SunCreatorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(1,0,0);
        BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
        BlockState state = Blocks.CHEST.defaultBlockState();
        blockRenderDispatcher.renderSingleBlock(state,pPoseStack,pBuffer,pPackedLight,pPackedOverlay);
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(0,1,0);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.DIAMOND);
        BakedModel bakedModel = itemRenderer.getModel(stack,pBlockEntity.getLevel(),null,0);
        itemRenderer.render(stack, ItemDisplayContext.FIXED,true,pPoseStack,pBuffer,pPackedLight,pPackedOverlay,bakedModel);
        pPoseStack.popPose();
    }

}
