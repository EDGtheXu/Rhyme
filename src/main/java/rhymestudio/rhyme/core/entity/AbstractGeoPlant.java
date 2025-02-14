package rhymestudio.rhyme.core.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkills;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AbstractGeoPlant<T extends AbstractPlant<T>> extends AbstractPlant<T> implements GeoEntity, IFSMGeoMob<T> {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected final ClientBoundAnimationMessage clientBoundAnimationMessage = new ClientBoundAnimationMessage();

    public AbstractGeoPlant(EntityType<T> entityType, Level level, Builder builder) {
        super(entityType, level, builder);
        skills = new CircleMobSkills(this, DATA_CAFE_POSE_NAME);
    }

    public void addSkill(CircleMobSkill skill) {
        IFSMGeoMob.super.addSkill(skill);
    }

    public void changeSkill(CircleMobSkill skill) {
        IFSMGeoMob.super.changeSkill(skill);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (this.level().isClientSide() && DATA_CAFE_POSE_NAME.equals(key)) {
            String name = entityData.get(DATA_CAFE_POSE_NAME);
            this.skills.playSkill(name);
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public CircleMobSkills<T> getSkills(){
        return skills;
    }

    @Override
    public ClientBoundAnimationMessage getAnimationMessage() {
        return clientBoundAnimationMessage;
    }
}
