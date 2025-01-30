package rhymestudio.rhyme.core.entity.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HelmetEntity extends Entity {

    private static final int MAX_TICK_COUNT = 100;
    boolean isOnGround = false;
    public ItemStack helmetStack;
    public LivingEntity owner;
    public float cachedYaw;
    public float cachedPitch;
    public HelmetEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(false);
        float f = level.getRandom().nextFloat() * 6.28f;
        this.setDeltaMovement(Math.sin(f)*0.1f,0.2f,Math.cos(f)*0.1f);

    }

    protected double getDefaultGravity() {
        return 0.00;
    }

    public void setHelmetStack(ItemStack helmetStack) {
        this.helmetStack = helmetStack;
        this.getEntityData().set(DATA_ITEM_STACK, helmetStack);
    }
    public void setOwner(LivingEntity owner) {
        this.owner = owner;
        this.getEntityData().set(DATA_OWNER, owner.getId());
    }

    public static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(HelmetEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<Integer> DATA_OWNER = SynchedEntityData.defineId(HelmetEntity.class, EntityDataSerializers.INT);


    public void onAddedToWorld(){
        super.onAddedToWorld();

    }
    public void tick(){
        if(this.owner == null || this.helmetStack == null){discard();}
        if(this.tickCount > MAX_TICK_COUNT) discard();
        if(onGround()) isOnGround = true;
        if(isOnGround){
            float friction = 0.85f;
            this.setDeltaMovement(getDeltaMovement().multiply(friction, friction, friction));
            if(level().isClientSide()){

                this.setYRot(this.cachedYaw);
                this.setXRot(this.cachedPitch);
            }
        }else{
            if(level().isClientSide()){
                this.setXRot(tickCount*10);
                this.setYRot(tickCount*10);
                this.cachedYaw = this.getYRot();
                this.cachedPitch = this.getXRot();

            }
        }
        setDeltaMovement(getDeltaMovement().add(0.0, -0.08, 0.0));
        move(MoverType.SELF, getDeltaMovement());
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ITEM_STACK, ItemStack.EMPTY);
        this.entityData.define(DATA_OWNER, 0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {

        if (this.level().isClientSide() && DATA_ITEM_STACK.equals(key)) {
            this.helmetStack = this.getEntityData().get(DATA_ITEM_STACK);
        }else if(level().isClientSide() && DATA_OWNER.equals(key)){
            this.owner = (LivingEntity) level().getEntity(this.getEntityData().get(DATA_OWNER));
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
