package rhymestudio.rhyme.mixinauxiliary;

import net.minecraft.world.entity.Entity;
import rhymestudio.rhyme.core.recipe.DaveTrades;

public interface IPlayer {

    DaveTrades rhyme$getDaveTrades(); // getRhyme$daveTrades
    void rhyme$setDaveTrades(DaveTrades daveTrades); // setRhyme$daveTrades

    Entity rhyme$getInteractingEntity(); // getRhyme$dave
    void rhyme$setInteractingEntity(Entity entity); // setRhyme$dave
}
