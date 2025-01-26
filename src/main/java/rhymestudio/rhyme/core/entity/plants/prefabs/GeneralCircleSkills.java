package rhymestudio.rhyme.core.entity.plants.prefabs;

import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;

import java.util.function.Supplier;

public class GeneralCircleSkills {
    public static final Supplier<CircleSkill<AbstractPlant>> SROOM_SLEEP_SKILLS = () -> new CircleSkill<>( "sleep",  999999999, 0)
            .onTick(e->{
                if(!e.level().isClientSide() && e.level().isNight())
                    e.skills.forceEnd();
            });
}
