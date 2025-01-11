package rhymestudio.rhyme.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import rhymestudio.rhyme.Rhyme;

import static rhymestudio.rhyme.client.render.util.ShaderUtil.drawFloatGlow;

public class hot_swap {
    public static void render(PoseStack pose){
//        pose.pushPose();
        RenderSystem.disableCull();
//        RenderSystem.enableDepthTest();
//        RenderSystem.depthFunc(519);
        pose.translate(-0.25,0,0);
        pose.scale(1/32f,1/32f,1);

        drawFloatGlow(pose.last().pose(), Rhyme.space("textures/gui/float_glow.png"), 16,14);

//        pose.popPose();
    }
}
