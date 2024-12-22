package rhymestudio.rhyme.core.entity.zombies;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.entity.AbstractMonster;
import rhymestudio.rhyme.core.entity.misc.HelmetEntity;
import rhymestudio.rhyme.core.entity.misc.ModelPartEntity;
import rhymestudio.rhyme.core.registry.ModSounds;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;
import rhymestudio.rhyme.core.registry.entities.Zombies;
import rhymestudio.rhyme.core.registry.items.ArmorItems;

public class NormalZombie extends AbstractMonster {
    public static int healthToDropArm = 20;
    public static int healthToDropHead = 10;
    public boolean isDropArm = false;
    public boolean isDropHead = false;
    public NormalZombie(EntityType<? extends Monster> type, Level level, Builder builder) {
        super(type, level, builder);
        if(this.getType() == Zombies.CONE_ZOMBIE.get()){
            this.setItemSlot(EquipmentSlot.HEAD, ArmorItems.CONE_HELMET.toStack());
        }else if(this.getType() == Zombies.IRON_BUCKET_ZOMBIE.get()){
            this.setItemSlot(EquipmentSlot.HEAD, ArmorItems.IRON_BUCKET_HELMET.toStack());
        }
    }
    public static final EntityDataAccessor<Boolean> DATA_DROP_ARM = SynchedEntityData.defineId(NormalZombie.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> DATA_DROP_HEAD = SynchedEntityData.defineId(NormalZombie.class, EntityDataSerializers.BOOLEAN);

    // 动画数据同步
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DROP_ARM, false);
        builder.define(DATA_DROP_HEAD, false);
    }


    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
         super.onSyncedDataUpdated(key);
        if (this.level().isClientSide() && DATA_DROP_ARM.equals(key)) {
            this.isDropArm = this.getEntityData().get(DATA_DROP_ARM);
        }else if (this.level().isClientSide() && DATA_DROP_HEAD.equals(key)) {
            this.isDropHead = this.getEntityData().get(DATA_DROP_HEAD);
        }
    }

    public String getNamePath() {
        return "normal_zombie";
    }

    public boolean hurt(DamageSource source, float amount){
        ItemStack stack = this.getItemBySlot(EquipmentSlot.HEAD);
        if(!level().isClientSide ){
            if (this.getHealth() - amount < 35 && !stack.isEmpty()) {
                HelmetEntity entity = MiscEntities.HELMET_ENTITY.get().create(level());
                entity.setPos(this.getEyePosition());
                entity.setOwner(this);
                entity.setHelmetStack(stack);
                level().addFreshEntity(entity);
                this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);

            }
            if(this.getHealth() - amount < healthToDropArm && !isDropArm){
                isDropArm = true;
                this.entityData.set(DATA_DROP_ARM, true);
                ModelPartEntity modelPartEntity = MiscEntities.MODEL_PART_ENTITY.get().create(level());
                modelPartEntity.setPos(this.getX(), this.getY()+1.5f, this.getZ());

                modelPartEntity.setOwner(this,"left_arm");
                level().addFreshEntity(modelPartEntity);
            }
            if(this.getHealth() - amount < healthToDropHead && !isDropHead){
                isDropHead = true;
                this.entityData.set(DATA_DROP_HEAD, true);
                ModelPartEntity modelPartEntity = MiscEntities.MODEL_PART_ENTITY.get().create(level());
                modelPartEntity.setPos(this.getX(), this.getY()+1.5f, this.getZ());

                modelPartEntity.setOwner(this,"head");
                level().addFreshEntity(modelPartEntity);
            }

        }

        return super.hurt(source, amount);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        if(this.getItemBySlot(EquipmentSlot.HEAD).is(ArmorItems.CONE_HELMET)){
            return ModSounds.PLASTIC_HIT.get();
        }else if(this.getItemBySlot(EquipmentSlot.HEAD).is(ArmorItems.IRON_BUCKET_HELMET)){
            return ModSounds.SHIELD_HIT.get();
        }
        return super.getHurtSound(damageSourceIn);
    }

    public void tick(){
        super.tick();
        if(this.getHealth() < healthToDropHead && tickCount % 40 == 0){
            LivingEntity mob = this.getLastHurtByMob();
            if(mob != null){
                this.hurt(mob.damageSources().generic(), 1);
            }else{
                this.hurt(damageSources().generic(), 1);
            }
        }
    }

}
