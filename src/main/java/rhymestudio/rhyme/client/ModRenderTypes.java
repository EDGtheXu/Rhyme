package rhymestudio.rhyme.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rhymestudio.rhyme.Rhyme;


import java.io.IOException;
import java.util.OptionalDouble;

import static net.minecraft.client.renderer.RenderStateShard.*;
import static rhymestudio.rhyme.Rhyme.MODID;


public final class ModRenderTypes {
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Shaders {
        public static ShaderInstance rectPolar;// 矩形极坐标

        @SubscribeEvent
        public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
            ResourceProvider resourceProvider = event.getResourceProvider();

            event.registerShader(new ShaderInstance(resourceProvider,
                            Rhyme.space("rect_polar"),
                            DefaultVertexFormat.POSITION_TEX),
                    shader -> {
                        rectPolar = shader;
                    }
            );
        }
    }
}
