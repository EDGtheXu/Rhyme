package rhymestudio.rhyme.core.entity.plants.prefabs;

import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.function.Function;
import java.util.function.Supplier;

public class PresetBuilders {


    public static final Supplier<AbstractPlant.Builder> NORMAL_PEA_PLANT = () -> new AbstractPlant.Builder()
            .setAnimSpeed(2)
            .setAttackDamage(3)//子弹伤害
            .setAttackInternalTick(20)//idle

            .setAttackTriggerTick(15)
            .setAttackAnimTick((int)(1.2083F * 20));//shoot

    public static final Supplier<AbstractPlant.Builder> NORMAL_SUNFLOWER_PLANT = () -> new AbstractPlant.Builder()
            .setAnimSpeed(5)//动画倍速
            .setAttackInternalTick(15 * 20)//产阳光间隔/tick

            .setAttackTriggerTick(10)//攻击动画触发时间
            .setAttackAnimTick(20);//攻击动画持续时间

    public static final Supplier<AbstractPlant.Builder> PUFF_SHROOM_PLANT = () -> new AbstractPlant.Builder()
            .setAttackDamage(3)//子弹伤害
            .setAttackInternalTick(5)

            .setAttackTriggerTick(35)
            .setAttackAnimTick(40)
            .setNoRotX();//shoot


    public static final Function<Integer,AbstractPlant.Builder> DEFENSE_PLANT = (hp) -> new AbstractPlant.Builder()
            .setHealth(hp);

    public static final Function<Integer,AbstractPlant.Builder> EXPLORE_PLANT = (attack) -> new AbstractPlant.Builder()
            .setHealth(50)
            .setAttackDamage(attack);


}
