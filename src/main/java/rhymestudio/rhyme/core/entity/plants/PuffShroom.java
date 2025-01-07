package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks;
import rhymestudio.rhyme.utils.Computer;

public class PuffShroom extends AbstractPlant {

    private final PresetAttacks attackCallback;

    public PuffShroom(EntityType<? extends AbstractPlant> type, Level level, PresetAttacks doAttack, Builder builder) {
        super(type, level,  builder);
        this.attackCallback = doAttack;

    }

    public void registerGoals(){
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10,true,false, entity->entity.canBeSeenAsEnemy() && this.skills.index!=0));
    }

    private LivingEntity target;
    @Override
    public void addSkills() {
        super.addSkills();
//        this.animState.playAnim("sleep",tickCount);
        CircleSkill sleep = new CircleSkill( "sleep",  999999999, 0)
                .onTick(a->{
                    if(!level().isClientSide() && level().isNight())
                        skills.forceEnd();
                });
        CircleSkill idle = new CircleSkill( "idle",  999999999, builder.attackInternalTick)
                .onTick(a-> {
                    if(!level().isClientSide() && !level().isNight())
                        skills.forceStartIndex(0);
                    if(skills.canContinue() &&
                            getTarget() != null && getTarget().isAlive() &&
                            Computer.angle(this.getForward(), getTarget().getEyePosition().subtract(this.getEyePosition())) < 20){
                        target = getTarget();
                        skills.forceEnd();
                    }
                });
        CircleSkill  shoot = new CircleSkill( "shoot", builder.attackAnimTick, builder.attackTriggerTick,
                a->{},
                a->{
                    if(skills.canTrigger() && target!= null && target.isAlive()){
                        if(attackCallback!= null) attackCallback.attack.accept(this,target);
                    }
                },
                a->{skills.forceStartIndex(1);}
        );
        this.addSkill(sleep);
        this.addSkill(idle);
        this.addSkill(shoot);
    }

}
