package rhymestudio.rhyme.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.render.post.DIYShaderInstance;


import java.io.IOException;
import java.util.OptionalDouble;

import static net.minecraft.client.renderer.RenderStateShard.*;
import static rhymestudio.rhyme.Rhyme.MODID;


public final class ModRenderTypes {
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Shaders {
        public static ShaderInstance entityDynamic;
        public static DIYShaderInstance diy_blit;//直接输出到屏幕
        public static DIYShaderInstance gaussian_blur;
        public static ShaderInstance rectPolar;// 矩形极坐标



        @SubscribeEvent
        public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
            ResourceProvider resourceProvider = event.getResourceProvider();

            ShaderInstance ins = new ShaderInstance(resourceProvider,
                    Rhyme.space("rendertype_entity_dynamic"),
                    DefaultVertexFormat.NEW_ENTITY);
//            var mx = (((IShaderInstance)ins).getRhyme$TEST());
            event.registerShader(ins, shader -> entityDynamic = shader);

            event.registerShader(new DIYShaderInstance(
                            resourceProvider,
                            Rhyme.space("diy_blit"),
                            DefaultVertexFormat.BLIT_SCREEN,null
//                            um->um.createUniform("colorMask")
                    ),
                    shader -> diy_blit = (DIYShaderInstance) shader
            );

            event.registerShader(new DIYShaderInstance(
                            resourceProvider,
                            Rhyme.space("gaussian_blur"),
                            DefaultVertexFormat.BLIT_SCREEN,
                            um->{
                                um.createUniform("hor");
                            }
                    ),
                    shader -> gaussian_blur = (DIYShaderInstance) shader
            );

            event.registerShader(new ShaderInstance(resourceProvider,
                            Rhyme.space("rect_polar"),
                            DefaultVertexFormat.POSITION_TEX),
                    shader -> {
                        rectPolar = shader;
                    }
            );
        }
    }
    public static final RenderStateShard.ShaderStateShard CTH_SHADER = new RenderStateShard.ShaderStateShard(() -> Shaders.entityDynamic);

    public static RenderType cthRenderType(ResourceLocation tex) {
        return RenderType.create(MODID + "rendertype_entity_dynamic",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                true,
                false,
                RenderType.CompositeState.builder()
                        .setShaderState(CTH_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(tex, false, false))
                        .setCullState(CULL)
                        .setLightmapState(LIGHTMAP)
//                        .setTransparencyState(GLINT_TRANSPARENCY)
                        .setOverlayState(OVERLAY).setLineState(new LineStateShard(OptionalDouble.of(5)))
                        //.setOutputState(RenderStateShard.OUTLINE_TARGET)
                        .createCompositeState(false)
        );

    }

}
