package rhymestudio.rhyme.client.render.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import rhymestudio.rhyme.client.ModRenderTypes;
import rhymestudio.rhyme.mixinauxiliary.IShaderInstance;

public class ShaderUtil {

    public static void drawFloatGlow(Matrix4f pose, ResourceLocation texture, float w,float h){
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.setShaderTexture(0, texture);

        RenderSystem.setShader(()->ModRenderTypes.Shaders.rectPolar);

        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(pose, 0, 0, 0).setUv(0, 0);
        bufferbuilder.addVertex(pose, 0, h, 0).setUv(0, 1);
        bufferbuilder.addVertex(pose, w, h, 0).setUv(1, 1);
        bufferbuilder.addVertex(pose, w, 0,0).setUv(1, 0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }
}
