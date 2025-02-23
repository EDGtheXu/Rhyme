package rhymestudio.rhyme.core.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import rhymestudio.rhyme.core.entity.AbstractMonster;
import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.List;

public class TauntAwareTargetGoal extends TargetGoal {
    private final AbstractMonster monster;
    private final int scanInterval;
    private int lastScanTick = -1000;
    private AbstractPlant cachedTarget;

    public TauntAwareTargetGoal(AbstractMonster monster) {
        super(monster, false);
        this.monster = monster;
        this.scanInterval = 1; // 每1tick扫描一次
    }

    @Override
    public boolean canUse() {
        if (monster.tickCount - lastScanTick >= scanInterval) {
            updateTarget();
            lastScanTick = monster.tickCount;
        }
        return cachedTarget != null && cachedTarget.isAlive();
    }

    private void updateTarget() {
        List<AbstractPlant> plants = monster.level()
            .getEntitiesOfClass(AbstractPlant.class,
                monster.getBoundingBox().inflate(32),
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
