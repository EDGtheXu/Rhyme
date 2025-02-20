package rhymestudio.rhyme.client.render.buffer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import rhymestudio.rhyme.Rhyme;

import java.util.HashMap;
import java.util.Map;


/**
 * 用于显示Debug方块的帮助类
 */
public class FakeBlocksHelper extends AbstractBufferManager{
    Map<BlockPos, BlockState> blocks = new HashMap<>();
    BlockPos targetPos;
    int maxX, maxY, maxZ;
    private final int continueTick;
    private Long lastTime;
    protected VertexBuffer lineVertexBuffer;
    protected VertexBuffer blockBuffer;
    private static final FakeBlocksHelper instance = new FakeBlocksHelper(50,100);

    public void addBlock(BlockPos pos, BlockState state) {
        blocks.put(pos, state);
        lastTime = System.currentTimeMillis();
    }

    public void setTargetPos(BlockPos pos, int maxX, int maxY, int maxZ) {
        targetPos = pos;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void clear(BlockPos pos) {
        blocks.remove(pos);
    }

    public static FakeBlocksHelper Singleton() {
        return instance;
    }

    /**
     * @param refreshTime 刷新时间 单位：毫秒
     * @param continueTick 持续时间 单位：tick
     */
    FakeBlocksHelper(int refreshTime, int continueTick) {
        super(refreshTime);
        this.continueTick = continueTick;
    }

    public void refresh(PoseStack poseStack){
        super.refresh(poseStack);
//        if(lastTime!= null && lastTime + continueTick * 20L < System.currentTimeMillis()) {
//            clear();
//        }
    }

    public void clear(){
        blocks.clear();
        lastTime = null;
    }

    @Override
    protected boolean shouldRender() {
        if(blocks.isEmpty()) {
            if (blockBuffer != null)
                blockBuffer.close();
            if (lineVertexBuffer != null)
                lineVertexBuffer.close();
            if (vertexBuffer != null)
                vertexBuffer.close();
            return false;
        }
        boolean flag = Minecraft.getInstance().player.distanceToSqr(Vec3.atLowerCornerOf(blocks.keySet().stream().toList().getFirst())) > 1000 * 1000;
        if(flag){
            clear();
            return false;
        }
        return true;
    }

    @Override
    protected void beforeRender() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
//        GameRenderer.getPositionTexShader().apply();
//        RenderSystem.disableCull();
    }

    @Override
    protected void afterRender(PoseStack poseStack) {
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    @Override
    protected void buildBuffer(BufferBuilder buffer, PoseStack poseStack) {

        if(targetPos!=null){
            int x = maxX;
            int y = maxY;
            int z = maxZ;

//            RenderSystem.setShaderTexture(0, Rhyme.space("textures/block/card_up_level_block.png"));
            float random = System.currentTimeMillis() % 1000000 * 0.00008f;
            double offsetU = Math.sin(random) * 1f + random * 2;
            double offsetV = Math.cos(random) * 1f  + Math.cos(random * 0.3f) * 0.01f;

            RenderUtil.renderAABB(buffer,
                    targetPos.getX() -0.1F, targetPos.getY() -0.1F, targetPos.getZ() -0.1F,targetPos.getX() + x+0.1F, targetPos.getY() + y+0.1F, targetPos.getZ() + z+0.1F,
                    255,255,255,100,
                    5, (float)offsetU, (float)offsetV
            );
        }
    }

    public void buildBlocks(PoseStack poseStack){
        if(blockBuffer != null)
            blockBuffer.close();
        blockBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder blockBufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        BlockPos pos = targetPos;
        if(pos!=null){
            poseStack.pushPose();
            for (Map.Entry<BlockPos,BlockState> entry : blocks.entrySet()){
               poseStack.pushPose();
                BlockPos pos1 = entry.getKey();
                BlockState state = entry.getValue();

                poseStack.translate(pos1.getX(), pos1.getY(), pos1.getZ());
                Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateBlock(
                        Minecraft.getInstance().level,
                        Minecraft.getInstance().getBlockRenderer().getBlockModel(state),
                        state,
                        pos1,
                        poseStack, blockBufferBuilder,
                        false,
                        Minecraft.getInstance().player.getRandom(),
                        0,
                        OverlayTexture.NO_OVERLAY);
                poseStack.popPose();
            }
            poseStack.popPose();
        }

        var build = blockBufferBuilder.build();
        if(build != null) {
            blockBuffer.bind();
            blockBuffer.upload(build);
            VertexBuffer.unbind();
        }
    }

    boolean reBuild = true;
    public void setRebuild(boolean reBuild) {
        this.reBuild = reBuild;
    }

    public void render(PoseStack poseStack, Matrix4f modelMatrix, Vec3 playerPos, Matrix4f projectMatrix){

        if(!shouldRender()) return;
        RenderSystem.enableCull();
        // 渲染方块虚影
        poseStack.pushPose();
//        poseStack.mulPose(modelMatrix);
//        poseStack.translate(-playerPos.x(), -playerPos.y(), -playerPos.z());

        if(reBuild){
            buildBlocks(poseStack);
            reBuild = false;
        }
//        buildBlocks(poseStack);
        // 取消遮挡
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
        poseStack.popPose();

//        RenderSystem.setShaderTexture(0, te);
        ;
        if(shouldRefresh()){
            // 刷新选择框
            refreshLineBuffer(poseStack);

            updateTime();
        }
        // 刷新AABB
        refresh(poseStack);
        beforeRender();

        if (vertexBuffer != null && lineVertexBuffer!= null && blockBuffer!= null) {
            // 渲染选择框

            poseStack.pushPose();

            poseStack.mulPose(modelMatrix);
            poseStack.translate(-playerPos.x(), -playerPos.y(), -playerPos.z());

            afterRender(poseStack);
            blockBuffer.bind();
            blockBuffer.drawWithShader(poseStack.last().pose(), projectMatrix, RenderSystem.getShader());

            beforeRender();
            RenderSystem.disableCull();
            RenderSystem.setShaderTexture(0, Rhyme.space("textures/gui/mask.png"));

//            minecraft.getBlockRenderer().renderSingleBlock(minecraft.level.getBlockState(BlockPos.containing(playerPos.subtract(0,-1,0))),poseStack,minecraft.renderBuffers().bufferSource(),15, OverlayTexture.NO_OVERLAY);

            vertexBuffer.bind();
            vertexBuffer.drawWithShader(poseStack.last().pose(), projectMatrix, RenderSystem.getShader());

            lineVertexBuffer.bind();
            lineVertexBuffer.drawWithShader(poseStack.last().pose(), projectMatrix, GameRenderer.getPositionColorShader());

            VertexBuffer.unbind();

            poseStack.popPose();
        }

        afterRender(poseStack);
    }

    public void refreshLineBuffer(PoseStack poseStack) {

        if(lineVertexBuffer != null)
            lineVertexBuffer.close();
        lineVertexBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder lineBuffer = Tesselator.getInstance().begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);


        BlockPos pos = targetPos;
        if(pos!=null){
            int x = maxX;
            int y = maxY;
            int z = maxZ;

            poseStack.pushPose();
            poseStack.translate(pos.getX(), pos.getY(), pos.getZ());


            RenderUtil.renderAABBOutLine(lineBuffer,
                    pos.getX() -0.1F, pos.getY() -0.1F, pos.getZ() -0.1F,pos.getX() + x+0.1F, pos.getY() + y+0.1F, pos.getZ() + z+0.1F,
                    255,255,255,100,
                    5
            );
            poseStack.popPose();
        }

        var build = lineBuffer.build();
        if(build != null) {
            lineVertexBuffer.bind();
            lineVertexBuffer.upload(build);
            VertexBuffer.unbind();
        }
    }


}
