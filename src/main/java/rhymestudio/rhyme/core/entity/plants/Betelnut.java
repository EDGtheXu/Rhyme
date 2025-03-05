package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

public class Betelnut<T extends Betelnut<T>> extends AbstractPlant<T> {

    public Betelnut(EntityType<? extends AbstractPlant> type, Level level,
                   Builder builder) {
        super(type, level,builder);
        setTauntLevel(2);
    }

    @Override
    public void addSkills() {
        CircleMobSkill<T> idle = new CircleMobSkill<T>( "idle_test_betelnut",  999999999, 0)
                .onTick(a-> {

                });
        this.addSkill(idle);
    }
}
