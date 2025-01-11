package rhymestudio.rhyme.mixin;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rhymestudio.rhyme.mixinauxiliary.IShaderInstance;

import javax.annotation.Nullable;

@Mixin(ShaderInstance.class)
public abstract class ShaderInstanceMixin implements IShaderInstance {
    @Shadow @Nullable public abstract Uniform getUniform(String name);

    @Unique private  Uniform rhyme$TEST;
    public Uniform getRhyme$TEST(){return rhyme$TEST;}
    @Unique private  Uniform rhyme$Time;
    public Uniform getRhyme$Time(){return rhyme$Time;}
    @Unique private  Uniform rhyme$Radius;
    public Uniform getRhyme$Radius(){return rhyme$Radius;}


    @Inject(method = "<init>(Lnet/minecraft/server/packs/resources/ResourceProvider;Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/blaze3d/vertex/VertexFormat;)V", at = @At("RETURN"))
    public void ShaderInstance(ResourceProvider p_173336_, ResourceLocation shaderLocation, VertexFormat p_173338_, CallbackInfo ci) {

        rhyme$TEST = this.getUniform("TEST");
        rhyme$Time = this.getUniform("Time");
        rhyme$Radius = this.getUniform("Radius");
    }


}
