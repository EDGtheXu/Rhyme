package rhymestudio.rhyme.core.entity.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import rhymestudio.rhyme.core.entity.AbstractMonster;
import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.List;

public class TauntAwareTargetGoal extends TargetGoal {
    private final AbstractMonster monster;
    private AbstractPlant cachedTarget;
    private int range;

    public TauntAwareTargetGoal(AbstractMonster monster, int range) {
        super(monster, false);
        this.monster = monster;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        updateTarget();
        return cachedTarget != null && cachedTarget.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        monster.setTarget(cachedTarget);
        return cachedTarget != null && cachedTarget.isAlive();
    }

    private void updateTarget() {
        List<AbstractPlant> plants = monster.level()
            .getEntitiesOfClass(AbstractPlant.class,
                monster.getBoundingBox().inflate(this.range),
                plant -> plant != null &&
                        plant.isAlive()
            );

        plants.sort((a, b) ->
            Integer.compare(b.getTauntLevel(), a.getTauntLevel()) // 降序排序
        );

        cachedTarget = plants.isEmpty() ? null : plants.get(0);
    }

    @Override
    public void start() {
        super.start();
        LivingEntity currentTarget = monster.getTarget();

        boolean shouldReplace = currentTarget == null
                || !(currentTarget instanceof AbstractPlant currentPlant)
                || currentPlant.getTauntLevel() < cachedTarget.getTauntLevel();

        if (cachedTarget != null && shouldReplace) {
            monster.setTarget(cachedTarget);
        }
    }
}
