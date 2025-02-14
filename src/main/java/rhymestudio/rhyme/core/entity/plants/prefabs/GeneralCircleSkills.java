package rhymestudio.rhyme.core.entity.plants.prefabs;

import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

import java.util.function.Supplier;

public class GeneralCircleSkills {
    public static final Supplier<CircleMobSkill<AbstractPlant>> SROOM_SLEEP_SKILLS = () -> new CircleMobSkill<AbstractPlant>( "sleep",  999999999, 0)
            .onTick(e->{
                if(!e.level().isClientSide() && e.level().isNight())
                    e.skills.forceEnd();
            });
}
