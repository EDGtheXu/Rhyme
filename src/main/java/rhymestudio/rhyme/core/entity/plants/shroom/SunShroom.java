package rhymestudio.rhyme.core.entity.plants.shroom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractGeoPlant;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.prefabs.GeneralCircleSkills;

import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks.produceSun;

public class SunShroom extends AbstractGeoPlant {

    public int stage = 0;
    public int growth = 0;
    public static final EntityDataAccessor<Integer> DATA_GROWTH_STAGE = SynchedEntityData.defineId(SunShroom.class, EntityDataSerializers.INT);


    public <T extends AbstractPlant> SunShroom(EntityType<T> entityType, Level level, Builder builder) {
        super(entityType, level, builder);
    }

    @Override
    protected void addSkills() {
        CircleSkill<AbstractPlant> sleep = GeneralCircleSkills.SROOM_SLEEP_SKILLS.get();
        CircleSkill<AbstractPlant> idleSkill = new CircleSkill<>("idle",builder.attackInternalTick,0);
        CircleSkill<AbstractPlant> sunSkill = new CircleSkill<>("glow",builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(skills.canTrigger()){
                        produceSun(this, getSun(stage));
                    }
                });
        addSkill(sleep);
        addSkill(idleSkill);
        addSkill(sunSkill);
    }
    @Override
    public void aiStep() {
        if(!level().isClientSide && !level().isNight()) {
            skills.forceStartIndex(0);
            return;
        }
        if(!level().isClientSide) {
            growth++;
            if(growth > 60 && stage == 0){
                stage = 1;
                this.entityData.set(DATA_GROWTH_STAGE, stage);
            }else if(growth > 120 && stage == 1){
                stage = 2;
                this.entityData.set(DATA_GROWTH_STAGE, stage);
            }
        }
        super.aiStep();
    }

    public int getSun(int stage){
        return switch (stage){
            case 0 -> 15;
            case 1 -> 25;
            default -> 50;
        };
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_GROWTH_STAGE, 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (level().isClientSide && key.equals(DATA_GROWTH_STAGE)) {
            this.growth = this.entityData.get(DATA_GROWTH_STAGE);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.stage = compound.getInt("growth_stage");
        this.entityData.set(DATA_GROWTH_STAGE, stage);
        this.growth = compound.getInt("growth");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("growth_stage", stage);
        compound.putInt("growth", growth);
    }
}
