package rhymestudio.rhyme.client.render.buffer;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Quaternionf;
import rhymestudio.rhyme.client.render.GeoNormalRenderer;
import rhymestudio.rhyme.client.render.entity.BasePlantRenderer;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.item.AbstractCardItem;
import rhymestudio.rhyme.core.item.tool.PlantPutter;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.utils.Computer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.awt.*;


/**
 * 用于显示Debug实体的帮助类
 */
public class DebugEntityHelper{
    private static final DebugEntityHelper instance = new DebugEntityHelper();

    public static DebugEntityHelper Singleton() {
        return instance;
    }

    Color color = Color.white;
    AbstractPlant e;
    BlockPos pos;

    DebugEntityHelper() {
    }

    private boolean shouldRender() {
        ItemStack it = Minecraft.getInstance().player.getMainHandItem();
        if(it.getItem() instanceof AbstractCardItem<?> card) {
            BlockPos p = Computer.getEyeBlockHitResult(Minecraft.getInstance().player);
            if(!AbstractCardItem.canPutPlant(Minecraft.getInstance().level, Minecraft.getInstance().player, p)) {
                return false;
            }
            color = Color.WHITE;
            if(!p.equals(pos) || card.entityType.get() != e.getType()){
                pos = p;
                e = card.entityType.get().create(Minecraft.getInstance().level);
                e.setPos(p.getX() + 0.5, p.getY() + 1.5, p.getZ() + 0.5);
                e.animState.playDefaultAnim(0);
                return true;
            }
            return true;
        }else if(it.getItem() instanceof PlantPutter putter){
            BlockPos p = Computer.getEyeBlockHitResult(Minecraft.getInstance().player);
            boolean canPut = AbstractCardItem.canPutPlant(Minecraft.getInstance().level, Minecraft.getInstance().player, p);
            if(!canPut) {
                color = Color.MAGENTA;
            }else color = Color.WHITE;
            var data = it.get(ModDataComponentTypes.ITEM_ENTITY_TAG);
            if(data != null){
                var type = BuiltInRegistries.ENTITY_TYPE.get(data.type());
                if(!p.equals(pos) || type != e.getType()){
                    pos = p;
                    if(type.create(Minecraft.getInstance().level) instanceof AbstractPlant plant){
                        e = plant ;
                        e.setPos(p.getX() + 0.5, p.getY() + 1.5 + (canPut?0:-1), p.getZ() + 0.5);
                        e.animState.playDefaultAnim(0);
                        return true;
                    }
                }
                return true;
            }

        }
        return false;
    }

    public void render(RenderLevelStageEvent event){

        if(!shouldRender()){
            return;
        }
        PoseStack poseStack = event.getPoseStack();
        Vec3 playerPos = event.getCamera().getPosition();
        var render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(e);
        if(render instanceof BasePlantRenderer renderer){

            var buf = Minecraft.getInstance().renderBuffers().bufferSource();
            var model = renderer.getModel();
            model.setupAnim(e,0,0,0,0,0);
            RenderType rendertype = RenderType.entityCutoutNoCull(renderer.getTextureLocation(e));
            VertexConsumer vertexconsumer = buf.getBuffer(rendertype);
            poseStack.pushPose();
            poseStack.translate(e.getX() - playerPos.x(), e.getY() - playerPos.y(), e.getZ() - playerPos.z());
            poseStack.scale(1,-1,1);
            poseStack.mulPose(new Quaternionf().rotateY((float) Math.PI));

            model.renderToBuffer(poseStack, vertexconsumer, 15728880,
                    OverlayTexture.pack(OverlayTexture.u(0.6f), 15), color.getRGB());
            poseStack.popPose();
        }else if(render instanceof GeoEntityRenderer renderer){
            poseStack.pushPose();
            poseStack.translate(e.getX() - playerPos.x(), e.getY() - playerPos.y() - 1.5f , e.getZ() - playerPos.z());

            if(renderer instanceof GeoNormalRenderer<?> normalRenderer) {
                normalRenderer.consumedOverlay = OverlayTexture.pack(OverlayTexture.u(0.6f), 15);
                normalRenderer.consumedColor = software.bernie.geckolib.util.Color.ofOpaque(color.getRGB());
            }
            renderer.render(e, 0, 0, poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), 15<<20| 15 << 4);

            poseStack.popPose();
        }

    }
}
