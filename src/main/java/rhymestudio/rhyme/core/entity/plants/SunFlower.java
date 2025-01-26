package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;

import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks.produceSun;


public class SunFlower extends AbstractPlant {

    public int stage = 0;
    public SunFlower(Level level, Builder builder) {
        super(PlantEntities.SUN_FLOWER.get(), level,builder);

    }

    @Override
    public void addSkills() {
        CircleSkill<AbstractPlant> idleSkill = new CircleSkill<>("idle",builder.attackInternalTick,0);
        CircleSkill<AbstractPlant> sunSkill = new CircleSkill<>("sun",builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(skills.canTrigger()){
                        produceSun(this, getSun(stage));
                    }
                });
        addSkill(idleSkill);
        addSkill(sunSkill);
    }

    public int getSun(int stage){
        return switch (stage){
            case 0 -> 15;
            case 1 -> 25;
            default -> 50;
        };
    }
}
