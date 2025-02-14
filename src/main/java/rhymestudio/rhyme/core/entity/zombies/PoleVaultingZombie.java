package rhymestudio.rhyme.core.entity.zombies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import rhymestudio.rhyme.core.entity.AbstractGeoMonster;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;
import rhymestudio.rhyme.core.entity.misc.HelmetEntity;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;
import rhymestudio.rhyme.core.registry.items.ToolItems;

import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class PoleVaultingZombie extends AbstractGeoMonster<PoleVaultingZombie> {

    public static final EntityDataAccessor<Boolean> DATA_HAVE_POLE = SynchedEntityData.defineId(PoleVaultingZombie.class, EntityDataSerializers.BOOLEAN);

    public PoleVaultingZombie(EntityType<PoleVaultingZombie> type, Level level, Builder builder) {
        super(type, level, builder);

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HAVE_POLE, true);

    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("have_pole", entityData.get(DATA_HAVE_POLE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(DATA_HAVE_POLE, compound.getBoolean("have_pole"));
    }

    public boolean havePole() {
        return entityData.get(DATA_HAVE_POLE);
    }

    void setHavePole(boolean havePole) {
        entityData.set(DATA_HAVE_POLE, havePole);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if(!level().isClientSide)
            setHavePole(true);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void addSkills() {

        CircleMobSkill<PoleVaultingZombie> before = new CircleMobSkill<PoleVaultingZombie>("before", 99999, 5)
                .onTick(e->{
                    if(!e.havePole() && tickCount > 20){
                        skills.forceStartIndex(2);
                    }
                    e.setSprinting(getTarget() != null);
                    if(getTarget() != null && getTarget().distanceToSqr(e) < 5*5){
                        skills.forceStartIndex(1);
                    }
                });

        CircleMobSkill<PoleVaultingZombie> high_jump = new CircleMobSkill<PoleVaultingZombie>("high_jump", 30, 20)
                .onTick(e->{
                    if(skills.canTrigger()){
                        double f = Math.min(e.getDeltaMovement().length() * 20, 2);
                        Vec3 v = e.getDeltaMovement().normalize().scale(f).add(0, 2.5, 0);
                        Vec3 v2 = new Vec3(v.x, Math.min(v.y, 1.5), v.z);
                        System.out.println(v);
                        e.setDeltaMovement(v2);
                    }
                    if(e.onGround() && skills.canContinue())
                        skills.forceEnd();
                })
                .onOver(e->{
                    HelmetEntity entity = MiscEntities.HELMET_ENTITY.get().create(level());
                    entity.setPos(this.position().subtract(e.getDeltaMovement().scale(5)));
                    entity.setDeltaMovement(getDeltaMovement());
                    entity.setOwner(this);
                    entity.setHelmetStack(ToolItems.POLE.get().getDefaultInstance());

                    level().addFreshEntity(entity);
                })
                ;

        CircleMobSkill<PoleVaultingZombie> after = new CircleMobSkill<PoleVaultingZombie>("after", 99999, 5)
                .onTick(e->{
                    e.setHavePole(false);
                    e.setSprinting(false);
                    skills.tick = 0;
                })
                ;


        this.addSkill(before);
        this.addSkill(high_jump);
        this.addSkill(after);

    }

    RawAnimation high_jump = RawAnimation.begin().thenPlay("high_jump");
    RawAnimation hurt = RawAnimation.begin().thenPlay("hurt");
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkRunIdleController(this),
                new AnimationController<GeoAnimatable>(this, "hurt",5,state->{
                    if(this.hurtTime > 0 ) {
                        state.setAnimation(hurt);
                        return PlayState.CONTINUE;
                    }
                    state.resetCurrentAnimation();
                    return PlayState.CONTINUE;
                })
                );
        controllers.add(DefaultAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_STRIKE));
        controllers.add(new AnimationController<GeoAnimatable>(this, "jump",10,state->{
                    if(this.skills.index == 1) {
                        state.setAnimation(high_jump);
                        return PlayState.CONTINUE;
                    }
                    return PlayState.CONTINUE;
                })
        );
    }
}
