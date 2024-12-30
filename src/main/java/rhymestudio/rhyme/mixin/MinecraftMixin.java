package rhymestudio.rhyme.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecraft.class)

public abstract  class MinecraftMixin {
//    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/MusicManager;isPlayingMusic(Lnet/minecraft/sounds/Music;)Z"),locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
//    private void getCustomMusic(CallbackInfoReturnable<Music> cir, Music music, Holder<Biome> holder) {
//        if(music==null && Config.IsOpenBgm.get()){
//            cir.setReturnValue(new Music(ModSounds.BGM,300,3000,true));
//            cir.cancel();
//
//        }else if(music!=null && music.getEvent() == ModSounds.BGM){
//            if(!Config.IsOpenBgm.get()){
//                cir.setReturnValue(null);
//                cir.cancel();
//            }
//        }
//    }
}
