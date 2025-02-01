package rhymestudio.rhyme.core.entity;

import net.minecraft.world.entity.Entity;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

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

//    default void addGeoAnim(CircleSkill skill){
//        getSelf().addSkill(skill);
//        getAnimationMap().put(skill.name, RawAnimation.begin().thenPlay(skill.name));
//    }


    default T getSelf(){
        return (T) this;
    }
    default void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        controllers.add(DefaultAnimations.genericIdleController(this));
        controllers.add(new AnimationController<>(this, "skills_controller",20, state -> {
            Entity entity = state.getData(DataTickets.ENTITY);
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
