package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks;
import rhymestudio.rhyme.utils.Computer;

public class Pea extends AbstractPlant {

    public final PresetAttacks attackCallback;

    public Pea(EntityType<? extends AbstractPlant> type, Level level,  PresetAttacks doAttack, Builder builder) {
        super(type, level, builder);
        this.attackCallback = doAttack;

    }

    private LivingEntity target;

    @Override
    public void addSkills() {
        super.addSkills();
        //tip                                                  idle持续时间        触发攻击时间
        CircleSkill<AbstractPlant> idle = new CircleSkill<>( "idle",  999999999, builder.attackInternalTick).
                onTick(a-> {
                    if(skills.canContinue() &&
                            getTarget() != null && getTarget().isAlive() &&
                            Computer.angle(this.getForward(), getTarget().getEyePosition().subtract(this.getEyePosition())) < 20){
                        target = getTarget();
                        skills.forceEnd();
                    }
                    });
        // tip                                                攻击持续时间        射击触发时间
        CircleSkill<AbstractPlant>   shoot = new CircleSkill<>( "shoot", builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(target!= null && target.isAlive()){
                        if(skills.canTrigger() || (skills.canContinue() && ((skills.tick - builder.attackTriggerTick) % 5 == 0 &&  (skills.tick - builder.attackTriggerTick) / 5 < attackCallback.shootCount)))
                            if (attackCallback != null) attackCallback.attack.accept(this, target);
                    }
                });
        this.addSkill(idle);
        this.addSkill(shoot);
    }

}
