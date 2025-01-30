package rhymestudio.rhyme.client.render.util;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rhymestudio.rhyme.mixinauxiliary.ILivingEntity;

public class LivingEntityRenderMixinUtil {
    public static int setOverlay(LivingEntity livingEntity, float u, CallbackInfoReturnable<Integer> cir) {
        if(livingEntity.hurtTime > 0)
            return OverlayTexture.pack(OverlayTexture.u(u), OverlayTexture.v(true));
        return OverlayTexture.pack(
                OverlayTexture.u(0f),
                15);
    }

    public static int modifyOverlay(LivingEntity entity,int x) {

        // todo

//        if (ClientConfig.IsOpenEffectOverLay.get() && ((ILivingEntity)entity).rhyme$getFrozenTime() > 0){
//            return 0x8080ff;
//        }

        return x;
    }
}
