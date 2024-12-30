package rhymestudio.rhyme.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rhymestudio.rhyme.mixinauxiliary.SelfGetter;

@Mixin(Entity.class)
public abstract class EntityMixin implements SelfGetter<Entity> {

    @Inject(method = "interact", at = @At("HEAD"))
    public void interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {

    }

}
