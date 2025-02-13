package rhymestudio.rhyme.core.entity.plants.shroom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks.produceSun;

public class SunShroom extends AbstractShroom<SunShroom>  {

    public int stage = 0;
    public int growth = 0;
    public int lastGrowthTick = 0;
    public int growthTick = 60 * 20;
    public int sunCountStage_0 = 25;
    public int sunCountStage_1 = 50;
    public int sunCountStage_2 = 75;
    public float cdReduction = 1f;
    public static final EntityDataAccessor<Integer> DATA_GROWTH_STAGE = SynchedEntityData.defineId(SunShroom.class, EntityDataSerializers.INT);


    public  SunShroom(EntityType<SunShroom> entityType, Level level, Builder builder) {
        super(entityType, level, builder);
    }

    @Override
    public void addSkills() {
        super.addSkills();
        builder.attackInternalTick *= cdReduction;
        CircleMobSkill<SunShroom> idleSkill = new CircleMobSkill<>("idle", builder.attackInternalTick, 0);
        CircleMobSkill<SunShroom> sunSkill = new CircleMobSkill<SunShroom>("glow",builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(skills.canTrigger()){
                        produceSun(this, getSun(stage));
                    }
                });
        addSkill(idleSkill);
        addSkill(sunSkill);
    }

    @Override
    protected void actualAiStep() {
        if(!level().isClientSide) {
            growth++;
            if(growth > growthTick && stage == 0){
                stage = 1;
                growth = 0;
                this.entityData.set(DATA_GROWTH_STAGE, stage);
            }else if(growth > growthTick && stage == 1){
                stage = 2;
                growth = 0;
                this.entityData.set(DATA_GROWTH_STAGE, stage);
            }
        }
    }

    public int getSun(int stage){
        return switch (stage){
            case 0 -> sunCountStage_0;
            case 1 -> sunCountStage_1;
            default -> sunCountStage_2;
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
            this.stage = this.entityData.get(DATA_GROWTH_STAGE);
            this.growth = 0;
            this.lastGrowthTick = tickCount;
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
