package rhymestudio.rhyme.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rhymestudio.rhyme.core.item.IItemExtension;
import rhymestudio.rhyme.mixinauxiliary.SelfGetter;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements SelfGetter<ItemStack> {
    @Shadow public abstract CompoundTag getOrCreateTag();

    @Shadow @Nullable private CompoundTag tag;

    @Shadow public abstract Item getItem();

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    void init(CompoundTag pCompoundTag, CallbackInfo ci) {
        if(this.getItem() instanceof IItemExtension extension){
            extension.onStackInit(rhyme$self());
        }
    }
    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    void init(ItemLike p_41604_, int p_41605_, CompoundTag p_41606_, CallbackInfo ci) {
        if(this.getItem() instanceof IItemExtension extension){
            extension.onStackInit(rhyme$self());
        }
    }
    @Inject(method = "<init>(Ljava/lang/Void;)V", at = @At("RETURN"))
    void init(Void p_282703_, CallbackInfo ci) {
        if(this.getItem() instanceof IItemExtension extension){
            extension.onStackInit(rhyme$self());
        }
    }
}
