package rhymestudio.rhyme.core.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkills;
import rhymestudio.rhyme.mixinauxiliary.SelfGetter;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static rhymestudio.rhyme.core.entity.AbstractPlant.DATA_CAFE_POSE_NAME;

//@SuppressWarnings("all")

/**
 * 适配Geo的状态机接口
 * @param <T>
 */
public interface IFSMGeoMob<T extends Mob> extends GeoEntity , SelfGetter<T> {

    CircleMobSkills<T> getSkills();

    ClientBoundAnimationMessage getAnimationMessage();

    void addSkills();

    default void addSkill(CircleMobSkill<T> skill) {
        getSkills().pushSkill(skill);
        getAnimationMessage().animationMap.put(skill.name, RawAnimation.begin().thenPlay(skill.name));
    }

    default void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "skills_controller",5, state -> {
            Entity entity = state.getData(DataTickets.ENTITY);
            if (!entity.isAlive()) return PlayState.STOP;
            if (getSkills().count() == 0) return PlayState.STOP;
            String name = getSkills().getCurSkillName();
            RawAnimation skill = getAnimationMessage().animationMap.get(name);
            if(skill == null) return PlayState.STOP;

            state.setAnimation(skill);
            if (!Objects.equals(getAnimationMessage().lastAnimName, name)) {
                getAnimationMessage().lastAnimName = name;

                state.resetCurrentAnimation();
                return PlayState.STOP;
            }
            return PlayState.CONTINUE;
        }));
    }

    class ClientBoundAnimationMessage {
        public String lastAnimName = "idle";
        public Map<String, RawAnimation> animationMap = new HashMap<>();;
    }
}
