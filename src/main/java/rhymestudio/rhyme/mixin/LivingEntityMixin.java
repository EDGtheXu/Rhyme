package rhymestudio.rhyme.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rhymestudio.rhyme.mixinauxiliary.ILivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ILivingEntity {

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
}
