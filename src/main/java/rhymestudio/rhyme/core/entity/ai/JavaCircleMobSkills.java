package rhymestudio.rhyme.core.entity.ai;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Mob;
import rhymestudio.rhyme.core.entity.ICafeMob;

public class JavaCircleMobSkills<T extends Mob & ICafeMob> extends CircleMobSkills<T> {

    public JavaCircleMobSkills(T owner, EntityDataAccessor<String> DATA_CAFE_POSE_NAME) {
        super(owner, DATA_CAFE_POSE_NAME);

    }

    public void forceStartIndex(int index) {
        super.forceStartIndex(index);
        owner.getCafeAnimState().playAnim(bossSkills.get(index).name,owner.tickCount);
    }

}
