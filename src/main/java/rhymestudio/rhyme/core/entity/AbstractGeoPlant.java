package rhymestudio.rhyme.core.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkills;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.Map;

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
