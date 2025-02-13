package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;

import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks.produceSun;


public class SunFlower extends AbstractPlant<SunFlower> {

    public int stage = 0;
    public int singleSun = 25;
    public float cdReduction = 1f;
    public SunFlower(Level level, Builder builder) {
        super(PlantEntities.SUN_FLOWER.get(), level,builder);

    }

    @Override
    public void addSkills() {
        builder.attackInternalTick *= cdReduction;
        CircleMobSkill<SunFlower> idleSkill = new CircleMobSkill<>("idle", builder.attackInternalTick, 0);
        CircleMobSkill<SunFlower> sunSkill = new CircleMobSkill<SunFlower>("sun", builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(skills.canTrigger()){
                        produce();
                    }
                });
        addSkill(idleSkill);
        addSkill(sunSkill);
    }

    public void produce(){
        produceSun(this, singleSun);
    }
}
