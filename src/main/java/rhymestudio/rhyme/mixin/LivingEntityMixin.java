package rhymestudio.rhyme.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.mixinauxiliary.ILivingEntity;
import rhymestudio.rhyme.mixinauxiliary.SelfGetter;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ILivingEntity , SelfGetter<LivingEntity> {

    @Unique
    public int rhyme$frozenTime;
    public int rhyme$getFrozenTime() {
        return rhyme$frozenTime;
    }
    public void rhyme$setFrozenTime(int frozenTime) {
        this.rhyme$frozenTime = frozenTime;
    }
    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci) {
        rhyme$frozenTime--;
    }

//    @Inject(method = "doPush", at = @At("HEAD"), cancellable = true)
//    public void doPushMixin(Entity entity, CallbackInfo ci) {
//        if(rhyme$self() instanceof Player && entity instanceof AbstractPlant) {
//            ci.cancel();
//        }
//    }
}
