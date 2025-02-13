package rhymestudio.rhyme.core.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkills;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AbstractGeoMonster<T extends AbstractGeoMonster<T>> extends AbstractMonster implements IFSMGeoMob<T>{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected final ClientBoundAnimationMessage skillMessage = new ClientBoundAnimationMessage();
    public static final EntityDataAccessor<String> DATA_GEO_SKILL_NAME = SynchedEntityData.defineId(AbstractGeoMonster.class, EntityDataSerializers.STRING);

    public CircleMobSkills<T> skills;

    public AbstractGeoMonster(EntityType<T> type, Level level, Builder builder) {
        super(type, level, builder);
        skills = new CircleMobSkills(this, DATA_GEO_SKILL_NAME);
        addSkills();
    }


    @Override
    public void tick(){
        super.tick();
        skills.tick();

    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_GEO_SKILL_NAME, "idle");
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (this.level().isClientSide() && DATA_GEO_SKILL_NAME.equals(key)) {
            skills.index = skills.str2intMap.get(entityData.get(DATA_GEO_SKILL_NAME));
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("skill_index", skills.index);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        skills.index = compound.getInt("skill_index");
        entityData.set(DATA_GEO_SKILL_NAME, skills.getCurSkillName());
    }

    @Override
    public CircleMobSkills<T> getSkills() {
        return skills;
    }

    @Override
    public ClientBoundAnimationMessage getAnimationMessage() {
        return skillMessage;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
