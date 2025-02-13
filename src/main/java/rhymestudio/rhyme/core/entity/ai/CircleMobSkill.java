package rhymestudio.rhyme.core.entity.ai;

import net.minecraft.world.entity.Mob;
import rhymestudio.rhyme.core.entity.AbstractPlant;


import java.util.function.Consumer;

public class CircleMobSkill<T extends Mob> {

    public String name;
    public int timeContinue;
    public int timeTrigger;

    public Consumer<T> stateInit;
    public Consumer<T> stateTick;
    public Consumer<T> stateOver;

    /**
     * @param name 动画名称
     * @param timeContinue 状态持续时间
     * @param timeTrigger 逻辑触发时间
     */
    public CircleMobSkill(String name, int timeContinue, int timeTrigger){
        this.name = name;
        this.timeContinue = timeContinue;
        this.timeTrigger = timeTrigger;
    }

    public CircleMobSkill(String animName, int timeContinue, int timeTrigger,
                          Consumer<T> stateInit,
                          Consumer<T> stateTick,
                          Consumer<T> stateOver
    ){
        this.name = animName;
        this.timeContinue = timeContinue;
        this.timeTrigger = timeTrigger;

        this.stateInit = stateInit;
        this.stateTick = stateTick;
        this.stateOver = stateOver;
    }

    public CircleMobSkill<T> onTick (Consumer<T> stateTick){
        this.stateTick = stateTick;
        return this;
    };
    public CircleMobSkill<T> onInit (Consumer<T> stateInit){
        this.stateInit = stateInit;
        return this;
    };
    public CircleMobSkill<T> onOver (Consumer<T> stateOver){
        this.stateOver = stateOver;
        return this;
    };
}
