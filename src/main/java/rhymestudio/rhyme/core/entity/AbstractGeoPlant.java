package rhymestudio.rhyme.core.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.Chomper;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGeoPlant extends AbstractPlant implements GeoEntity, IFSMGeoMob<Chomper> {

    protected Map<String, RawAnimation> animationMap = new HashMap<>();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public <T extends AbstractPlant> AbstractGeoPlant(EntityType<T> entityType, Level level, Builder builder) {
        super(entityType, level, builder);
    }

    public void addSkill(CircleSkill skill) {
        super.addSkill(skill);
        animationMap.put(skill.name, RawAnimation.begin().thenPlay(skill.name));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    public Map<String, RawAnimation> getAnimationMap() {
        return animationMap;
    }

}
