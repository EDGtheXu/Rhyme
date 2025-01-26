package rhymestudio.rhyme.core.entity.plants.shroom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractGeoPlant;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.prefabs.GeneralCircleSkills;
import rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks;
import rhymestudio.rhyme.utils.Computer;

public class PuffShroom extends AbstractGeoPlant {

    private final PresetAttacks attackCallback;
    private LivingEntity target;

    public PuffShroom(EntityType<? extends AbstractPlant> type, Level level, PresetAttacks doAttack, Builder builder) {
        super(type, level,  builder);
        this.attackCallback = doAttack;
    }

    @Override
    public void addSkills() {
        CircleSkill<AbstractPlant> sleep = GeneralCircleSkills.SROOM_SLEEP_SKILLS.get();
        CircleSkill<AbstractPlant> idle = new CircleSkill<>( "idle",  999999999, builder.attackInternalTick)
                .onTick(a-> {
                    if(!level().isClientSide() && !level().isNight())
                        skills.forceStartIndex(0);
                    if(skills.canContinue() &&
                            getTarget() != null && getTarget().isAlive() &&
                            Computer.angle(this.calculateViewVector(this.getXRot(),this.yHeadRot), getTarget().getEyePosition().subtract(this.getEyePosition())) < Math.PI / 9){
                        target = getTarget();
                        skills.forceEnd();
                    }
                });
        CircleSkill<AbstractPlant> shoot = new CircleSkill<>( "shoot", builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(skills.canTrigger() && target!= null && target.isAlive()){
                        if(attackCallback!= null) attackCallback.getAttack(this).accept(this,target);
                    }})
                .onOver(a->{
                    skills.forceStartIndex(1);
                });
        this.addSkill(sleep);
        this.addSkill(idle);
        this.addSkill(shoot);
    }

    @Override
    public void aiStep() {
        if(!level().isClientSide && !level().isNight())
            return;
        super.aiStep();
    }
}
