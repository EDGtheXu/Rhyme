package rhymestudio.rhyme.core.entity;

import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.Chomper;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;

import java.util.Map;
import java.util.Objects;

import static rhymestudio.rhyme.core.entity.AbstractPlant.DATA_CAFE_POSE_NAME;

//@SuppressWarnings("all")

/**
 * 适配Geo的状态机接口
 * @param <T>
 */
public interface IFSMGeoMob<T extends AbstractPlant> extends GeoEntity {
    Map<String, RawAnimation> getAnimationMap();

    default void addGeoAnim(CircleSkill skill, boolean loop){
        getSelf().addSkill(skill);
        if(loop) {
            getAnimationMap().put(skill.skill, RawAnimation.begin().thenPlay(skill.skill));
        } else {
            getAnimationMap().put(skill.skill, RawAnimation.begin().thenLoop(skill.skill));
        }
    }


    default T getSelf(){
        return (T) this;
    }
    default void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
        controllers.add(new AnimationController<>(this, "skills_controller",20, state -> {
            Chomper entity =  (Chomper) state.getData(DataTickets.ENTITY);
            if (!entity.isAlive()) return PlayState.STOP;
            if (getSelf().skills.count() == 0) return PlayState.STOP;
            String name = getSelf().getEntityData().get(DATA_CAFE_POSE_NAME);
            RawAnimation skill = getAnimationMap().get(name);
            if(skill == null) return PlayState.STOP;

            state.setAnimation(skill);
            if (!Objects.equals(getSelf().lastAnimName, name)) {
                getSelf().lastAnimName = name;

                state.resetCurrentAnimation();
                return PlayState.STOP;
            }
            return PlayState.CONTINUE;
        }));
    }
}
