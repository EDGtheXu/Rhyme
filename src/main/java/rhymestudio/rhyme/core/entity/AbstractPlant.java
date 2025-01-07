package rhymestudio.rhyme.core.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;
import rhymestudio.rhyme.core.entity.anim.CafeAnimationState;
import rhymestudio.rhyme.core.entity.ai.CircleSkills;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.zombies.NormalZombie;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModSounds;
import rhymestudio.rhyme.network.s2c.PlantRecorderPacket;

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

    protected void addSkills(){

    }

    public <T extends AbstractPlant> AbstractPlant(EntityType<T> tEntityType, Level level,Builder builder) {
        super(tEntityType, level);
        this.namePath = BuiltInRegistries.ENTITY_TYPE.getKey(this.getType()).getPath();
        this.builder = builder;
        if(level.isClientSide) builder.anim.accept(animState);
    }

    public boolean isPushable(){

        return !level().getEntities(this,this.getBoundingBox(),e->e instanceof AbstractPlant).isEmpty();
    }

    public void onAddedToLevel(){
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(builder.health);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(builder.attackDamage);
        addSkills();
        if(level().isClientSide)
            animState.playAnim(skills.getCurSkill(),tickCount);
        if(!level().isClientSide)this.skills.tick+= random.nextIntBetweenInclusive(0,50);
        super.onAddedToLevel();
    }

    public CafeAnimationState getCafeAnimState(){
        return animState;
    }

    @Override
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
    public void die(DamageSource damageSource) {
        playSound(ModSounds.GULP.get());
        super.die(damageSource);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        if(source.getEntity() instanceof NormalZombie ){
            return ModSounds.CHOMP.get();
        }
        return super.getHurtSound(source);
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

    @Override
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

    @Override
    public boolean ignoreExplosion(Explosion e){
//        return true;
        return e.getIndirectSourceEntity() instanceof AbstractPlant || super.ignoreExplosion(e);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (this.level().isClientSide() && DATA_CAFE_POSE_NAME.equals(key)) {
            String name = entityData.get(DATA_CAFE_POSE_NAME);
            this.animState.playAnim(name, this.tickCount);

        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public double getEyeY(){
        return this.getY() + 0.5;
    }

    @Override
    public void onRemovedFromLevel() {
        super.onRemovedFromLevel();
        if(owner instanceof ServerPlayer serverPlayer){ // 只在服务端才有owner
            var list = serverPlayer.getData(ModAttachments.PLANT_RECORDER_STORAGE).ids;

            list.removeIf(id->id==this.getId() || level().getEntity(id)==null || level().getEntity(id).isRemoved());
            PacketDistributor.sendToPlayer(serverPlayer, new PlantRecorderPacket(list));
        }
    }


    public static class Builder{
        //默认参数（豌豆）
        public  int health = 20;
        public  float animSpeed = 1;
        public float projSpeed = 1;
        private boolean shouldRotHead = true;

        public  int attackTriggerTick = 20;
        public  int attackAnimTick = 30;

        public int attackInternalTick = 60;
        public  int attackDamage = 1;

        public Consumer<CafeAnimationState> anim = (state)->{};

        public Builder setProjSpeed(float projSpeed) {
            this.projSpeed = projSpeed;
            return this;
        }
        public Builder setAnimSpeed(int speed) {
            this.animSpeed = speed;
            return this;
        }

        public Builder setHealth(int health) {
            this.health = health;
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

        public Builder setAnim(Consumer<CafeAnimationState> anim) {
            this.anim = anim;
            return this;
        }

        public Builder setNoRotHead() {
            this.shouldRotHead = false;
            return this;
        }


    }


}
