package rhymestudio.rhyme.core.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.function.Consumer;

public class ShootGoal extends Goal {

    private final Mob mob;
    private final Level level;

    private final Consumer<Mob> recall;

    public ShootGoal(Mob mob,
                     Consumer<Mob> recall
    ) {
        this.mob = mob;
        this.level = mob.level();
        this.recall = recall;
    }

    public boolean canUse() {
        LivingEntity livingentity = mob.getTarget();

        if (livingentity != null) {
            return mob.canAttack(livingentity);
        }
        return false;
    }

    public void start() {

    }

    public void tick() {
        if(mob instanceof AbstractPlant plant)
            if(plant.builder.shouldRotHead){
                plant.getLookControl().setLookAt(plant.getTarget());
                plant.lookAt(plant.getTarget(),360,85);
        }
    }
}