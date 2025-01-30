package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.entity.AbstractGeoPlant;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.IFSMGeoMob;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chomper extends AbstractGeoPlant {
    int eatTime;
    int killBlood;
    public List<LivingEntity> ultimateTargets = new ArrayList<>();

    /**
     * @param eatTime 咀嚼时间
     * @param killBlood 秒杀血量
     */
    public <T extends AbstractPlant> Chomper(EntityType<T> tEntityType, Level level, int eatTime,int killBlood,Builder builder) {
        super(tEntityType, level,builder);
        this.eatTime = eatTime;
        this.killBlood = killBlood;
    }
    LivingEntity target;
    @Override
    public void addSkills() {
        this.entityData.set(DATA_CAFE_POSE_NAME, "misc.idle");
        CircleSkill<AbstractPlant> idle = new CircleSkill<>( "misc.idle",  999999999, 0)
                .onTick(a-> {
                    if(skills.canContinue() &&
                            getTarget() != null && getTarget().isAlive() &&
                            getTarget().distanceToSqr(this) < 16){
                        target = getTarget();
                        skills.forceEnd();
                    }
                });
        CircleSkill<AbstractPlant>  attack = new CircleSkill<>( "attack.strike", 30, 25)
                .onInit(a-> this.attackAnim = builder.attackAnimTick)
                .onTick(a->{
                    if(skills.canTrigger() ){
                        if(target!= null && target.isAlive()){
                            doAttack(target);
                        }else{
                            skills.forceStartIndex(0);
                        }
                    }
                });
        CircleSkill<AbstractPlant> eating = new CircleSkill<>( "eating", eatTime, 0);
        CircleSkill<AbstractPlant> eatingFinish = new CircleSkill<>( "eating_finish", 60, 0);

        addSkill(idle);
        addSkill(attack);
        addSkill(eating);
        addSkill(eatingFinish);
    }
    @Override
    public boolean hurt(DamageSource damageSource, float damage){
        Entity sourceEntity = damageSource.getEntity();
        if(!(sourceEntity instanceof LivingEntity))return true;
        if(entityData.get(DATA_CAFE_POSE_NAME).equals("misc.idle") && sourceEntity.distanceToSqr(this) < 10)
            return false;
        return super.hurt(damageSource, damage);
    }

    public void doAttack(@NotNull LivingEntity tar){
        float hp = tar.getHealth() - killBlood;
        tar.hurt(this.damageSources().mobAttack(this),killBlood);
        if(hp <= 0) {
            tar.discard();
        }
    }

}
