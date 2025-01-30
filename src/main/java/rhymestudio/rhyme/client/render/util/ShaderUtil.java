package rhymestudio.rhyme.client.render.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import rhymestudio.rhyme.client.ModRenderTypes;

public class ShaderUtil {

    public static void drawFloatGlow(Matrix4f pose, ResourceLocation texture, float w,float h){
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.setShaderTexture(0, texture);

        RenderSystem.setShader(()->ModRenderTypes.Shaders.rectPolar);

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//        .begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(pose, 0, 0, 0).uv(0, 0).endVertex();
        bufferbuilder.vertex(pose, 0, h, 0).uv(0, 1).endVertex();
        bufferbuilder.vertex(pose, w, h, 0).uv(1, 1).endVertex();
        bufferbuilder.vertex(pose, w, 0,0).uv(1, 0).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }
}
