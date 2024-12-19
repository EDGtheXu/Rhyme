package rhymestudio.rhyme.core.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import rhymestudio.rhyme.core.entity.anim.CafeAnimationState;
import rhymestudio.rhyme.core.entity.ai.CircleSkills;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;

import java.util.function.Consumer;

public abstract class AbstractPlant extends Mob implements ICafeMob{

    public String namePath;
    public Player owner;
    public Builder builder;
    public String lastAnimName = "idle";
    public CafeAnimationState animState = new CafeAnimationState(this);
    public CircleSkills<AbstractPlant> skills = new CircleSkills<>(this);

    public void setOwner(Player player) {
        this.owner = player;
    }

    protected void cafeDefineAnimations(){
        if(builder.defineAnim!=null) builder.defineAnim.accept(this);
    }

    protected void addSkills(){
        if(builder.defineSkill!=null) builder.defineSkill.accept(this);
    }

    public <T extends AbstractPlant> AbstractPlant(EntityType<T> tEntityType, Level level,Builder builder) {
        super(tEntityType, level);
        this.namePath = getName().getString().split("\\.")[2];
        this.builder = builder;
    }

    public boolean isPushable(){

        return !level().getEntities(this,this.getBoundingBox(),e->e instanceof AbstractPlant).isEmpty();
    }

    public void onAddedToLevel(){
        this.cafeDefineAnimations();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(builder.health);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(builder.attackDamage);

        addSkills();
        animState.playAnim(skills.getCurSkill(),tickCount);
        if(!level().isClientSide)this.skills.tick+= random.nextIntBetweenInclusive(0,50);
        super.onAddedToLevel();
    }

    public CafeAnimationState getCafeAnimState(){
        return animState;
    }

    public void registerGoals(){
        //this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, LivingEntity.class, 20.0F));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10,true,true, this::canAttack));
        this.goalSelector.addGoal(5,new LookAtPlayerGoal(this, Player.class,3,0.1f){
            @Override
            public boolean canUse() {
                return (getTarget()==null || !getTarget().isAlive())&& super.canUse();
            }});
        this.goalSelector.addGoal(6,new RandomLookAroundGoal(this){
            @Override
            public boolean canUse() {
                return (getTarget()==null || !getTarget().isAlive())&& super.canUse();
            }
        });


        ;
        //this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Slime.class, true));
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if(source.getEntity() instanceof Player || source.getEntity() instanceof AbstractPlant){
            return false;
        }
        return super.hurt(source, damage);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if(target.isAlive() && (
                target instanceof Monster
                        || target instanceof Slime
                        || (target instanceof NeutralMob  && !(target instanceof IronGolem))
                )
        ) return true;
//        if(!target.isAlive()) {setTarget(null);return false;}
        if(target instanceof AbstractPlant || target instanceof Player)return false;
        return false;
    }

    public void tick(){

        if (!level().isClientSide) skills.tick();

        super.tick();
        if(this.getTarget()!=null && getTarget().isAlive()){
            if(builder.shouldRotHead){
                this.lookControl.setLookAt(getTarget());
                this.lookAt(getTarget(),360,85);
            }
            this.setYBodyRot(this.yHeadRot);
        }
    }

    public void addSkill(CircleSkill bossSkill) {skills.pushSkill(bossSkill);}

    public void addSkillNoAnim(CircleSkill bossSkill) {skills.pushSkill(bossSkill);}


    public static final EntityDataAccessor<String> DATA_CAFE_POSE_NAME = SynchedEntityData.defineId(AbstractPlant.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> DATA_SKILL_INDEX = SynchedEntityData.defineId(AbstractPlant.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_SKILL_TICK = SynchedEntityData.defineId(AbstractPlant.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Vector3f> DATA_ROTATE = SynchedEntityData.defineId(AbstractPlant.class, EntityDataSerializers.VECTOR3);
    // 动画数据同步
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SKILL_INDEX, 0);
        builder.define(DATA_SKILL_TICK, 0);
        builder.define(DATA_ROTATE, new Vector3f());
        builder.define(DATA_CAFE_POSE_NAME, "idle");
    }

    public boolean ignoreExplosion(Explosion e){
        return !(e.getIndirectSourceEntity() instanceof AbstractPlant);
    }


    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (this.level().isClientSide() && DATA_CAFE_POSE_NAME.equals(key)) {
            String name = entityData.get(DATA_CAFE_POSE_NAME);
            this.animState.playAnim(name, this.tickCount);

        }
        super.onSyncedDataUpdated(key);
    }

    public double getEyeY(){
        return this.getY() + 0.5;
    }


    public static class Builder{
        //默认参数（豌豆）
        public  int health = 20;
        public  float animSpeed = 1;
        private boolean shouldRotHead = true;

        public  int attackTriggerTick = 20;
        public  int attackAnimTick = 30;

        public int attackInternalTick = 60;
        public  int attackDamage = 1;
        public  float projSpeed = 0.5f;

        public Consumer<AbstractPlant> defineAnim;
        public Consumer<AbstractPlant> defineSkill;


        public Builder setAnimSpeed(int speed) {
            this.animSpeed = speed;
            return this;
        }

        public Builder setHealth(int health) {
            this.health = health;
            return this;
        }
        public Builder setProjSpeed(int speed) {
            this.projSpeed = speed;
            return this;
        }

        public Builder setAttackDamage(int damage) {
            this.attackDamage = damage;
            return this;
        }

        public Builder setAttackInternalTick(int attackInternalTick) {
            this.attackInternalTick = attackInternalTick;
            return this;
        }

        public Builder setAttackTriggerTick(int attackTriggerTick) {
            this.attackTriggerTick = attackTriggerTick;
            return this;
        }

        public Builder setAttackAnimTick(int attackAnimTick) {
            this.attackAnimTick = attackAnimTick;
            return this;
        }
        public Builder setDefineAnim(Consumer<AbstractPlant> defineAnim) {
            this.defineAnim = defineAnim;
            return this;
        }

        public Builder setDefineSkill(Consumer<AbstractPlant> defineSkill) {
            this.defineSkill = defineSkill;
            return this;
        }
        public Builder setNoRotHead() {
            this.shouldRotHead = false;
            return this;
        }


    }


}
