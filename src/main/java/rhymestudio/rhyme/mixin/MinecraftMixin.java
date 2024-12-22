package rhymestudio.rhyme.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rhymestudio.rhyme.core.registry.ModSounds;

@Mixin(Minecraft.class)

public abstract  class MinecraftMixin {
    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/MusicManager;isPlayingMusic(Lnet/minecraft/sounds/Music;)Z"),locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getCustomMusic(CallbackInfoReturnable<Music> cir, Music music, Holder<Biome> holder) {
        if(music==null){
            cir.setReturnValue(new Music(ModSounds.BGM,300,3000,true));
            cir.cancel();
        }
    }
}
