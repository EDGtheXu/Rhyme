package rhymestudio.rhyme.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.mixinauxiliary.IPlayer;

@Mixin(Player.class)
public abstract class PlayerMixin implements IPlayer {

    @Unique
    private DaveTrades rhyme$daveTrades;
    @Unique
    private Entity rhyme$interactingEntity;

    public DaveTrades rhyme$getDaveTrades() {
        return rhyme$daveTrades;
    }
    public void rhyme$setDaveTrades(DaveTrades daveTrades) {
        rhyme$daveTrades = daveTrades;
    }

    public Entity rhyme$getInteractingEntity(){
        return rhyme$interactingEntity;
    } // getRhyme$dave
    public void rhyme$setInteractingEntity(Entity entity){
        this.rhyme$interactingEntity = entity;
    } // setRhyme$dave

}
