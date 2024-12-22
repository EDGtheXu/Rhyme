package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;
import rhymestudio.rhyme.utils.Computer;

import java.util.function.BiConsumer;

public class Pea extends AbstractPlant {

    private final PresetAttacks attackCallback;

    public Pea(EntityType<? extends AbstractPlant> type, Level level, AnimationDefinition idle, AnimationDefinition shoot, PresetAttacks doAttack, Builder builder) {
        super(type, level, builder);
        this.attackCallback = doAttack;
        this.animState.addAnimation("idle_on", idle,1);
        this.animState.addAnimation("shoot", shoot,1);
    }

    private LivingEntity target;
    @Override
    public void addSkills() {
        super.addSkills();
        //tip                                                  idle持续时间        触发攻击时间
        CircleSkill  idle = new CircleSkill( "idle_on",  999999999, builder.attackInternalTick).
                onTick(a-> {
                    if(skills.canContinue() &&
                            getTarget() != null && getTarget().isAlive() &&
                            Computer.angle(this.getForward(), getTarget().getEyePosition().subtract(this.getEyePosition())) < 20){
                        target = getTarget();
                        skills.forceEnd();
                    }
                    });
        // tip                                                攻击持续时间        射击触发时间
        CircleSkill  shoot = new CircleSkill( "shoot", builder.attackAnimTick, builder.attackTriggerTick)
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
