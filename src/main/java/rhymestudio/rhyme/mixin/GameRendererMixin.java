package rhymestudio.rhyme.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {


    @Inject(method = "renderLevel", at = @At("RETURN"))
    public void renderLevelReturnMixin(float p_109090_, long p_109091_, PoseStack p_109092_, CallbackInfo ci ){

//        PostUtil.bloom.apply();
//        PostUtil.backUp();

    }


}
