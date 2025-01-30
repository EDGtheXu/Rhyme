package rhymestudio.rhyme.core.entity.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;

public class ModelPartEntity extends Entity {

    public ModelPartEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(false);
        float f =level.getRandom().nextFloat() * 6.28f;
        this.setDeltaMovement(Math.sin(f)*0.1f,0.2f,Math.cos(f)*0.1f);

    }


    private static final int MAX_TICK_COUNT = 100;
    boolean isOnGround = false;
    public String name;
    public LivingEntity owner;
    public float cachedYaw;
    public float cachedPitch;

    protected double getDefaultGravity() {
        return 0.00;
    }


    public void setOwner(LivingEntity owner, String name) {
        this.owner = owner;
        this.name = name;
        this.getEntityData().set(DATA_OWNER, owner.getId());
        this.getEntityData().set(DATA_PART, name);
    }

    public static final EntityDataAccessor<String> DATA_PART = SynchedEntityData.defineId(ModelPartEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> DATA_OWNER = SynchedEntityData.defineId(ModelPartEntity.class, EntityDataSerializers.INT);


    public void onAddedToWorld(){
        super.onAddedToWorld();

    }
    public void tick(){
        if(this.owner == null || this.name==null){discard();}
        if(this.tickCount > MAX_TICK_COUNT) discard();
        if(onGround()) isOnGround = true;
        if(isOnGround){
            float friction = 0.85f;
            this.setDeltaMovement(getDeltaMovement().multiply(friction, friction, friction));
        }
        setDeltaMovement(getDeltaMovement().add(0.0, -0.08, 0.0));
        move(MoverType.SELF, getDeltaMovement());
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_PART, "");
        this.entityData.define(DATA_OWNER, 0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {

        if (this.level().isClientSide() && DATA_PART.equals(key)) {
            this.name = this.getEntityData().get(DATA_PART);
        }else if(level().isClientSide() && DATA_OWNER.equals(key)){
            this.owner = (LivingEntity) level().getEntity(this.getEntityData().get(DATA_OWNER));
            if(this.owner!=null)
                this.cachedYaw = this.owner.yBodyRot;
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
