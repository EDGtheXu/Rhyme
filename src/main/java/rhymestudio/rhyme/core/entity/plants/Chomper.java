package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.entity.AbstractGeoPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

import java.util.ArrayList;
import java.util.List;

public class Chomper<T extends Chomper<T>> extends AbstractGeoPlant<T> {
    public int eatTime;
    public int killBlood;
    public int cdReduction = 0;
    public float recoverHealth = 0f;
    private double attackRangePower = 4.0 * 4.0;
    public List<LivingEntity> ultimateTargets = new ArrayList<>();

    /**
     * @param eatTime 咀嚼时间
     * @param killBlood 秒杀血量
     */
    public Chomper(EntityType<T> tEntityType, Level level, int eatTime,int killBlood,Builder builder) {
        super(tEntityType, level,builder);
        this.eatTime = eatTime;
        this.killBlood = killBlood;
    }
    LivingEntity target;
    @Override
    public void addSkills() {
        this.entityData.set(DATA_CAFE_POSE_NAME, "misc.idle");
        CircleMobSkill<Chomper> idle = new CircleMobSkill<Chomper>( "misc.idle",  999999999, 0)
                .onTick(a-> {
                    if(skills.canContinue() &&
                            getTarget() != null && getTarget().isAlive() &&
                            getTarget().distanceToSqr(this) < attackRangePower){
                        target = getTarget();
                        skills.forceEnd();
                    }
                });
        CircleMobSkill<Chomper> attack = new CircleMobSkill<Chomper>( "attack.strike", 30, 25)
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
        CircleMobSkill<Chomper> eating = new CircleMobSkill<>( "eating", eatTime, 0);
        CircleMobSkill<Chomper> eatingFinish = new CircleMobSkill<Chomper>( "eating_finish", 60, 0)
                .onOver(a -> {
                    setHealth(this.getHealth() + recoverHealth);
                    if (eatTime > 0 && cdReduction > 0) {
                        setEatTime(cdReduction < eatTime ? eatTime - cdReduction : 0);
                    }
                });

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

    public void setAttackRange(double distance) {
        this.attackRangePower = distance * distance;
    }

    public void setEatTime(int eatTime) {
        this.eatTime = eatTime;
        CircleMobSkill<Chomper> eating = new CircleMobSkill<>( "eating", this.eatTime, 0);
        changeSkill(eating);
    }
}
