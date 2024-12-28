package rhymestudio.rhyme.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rhymestudio.rhyme.mixinauxiliary.IPlayer;

@Mixin(Player.class)
public abstract class PlayerMixin implements IPlayer {
    @Unique
    private Entity rhyme$interactEntity;

    public Entity rhyme$getInteractEntity(){
        return rhyme$interactEntity;
    }
    public void rhyme$setInteractEntity(Entity entity){
        rhyme$interactEntity = entity;
    }

}
