package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

public class Betelnut<T extends Betelnut<T>> extends AbstractPlant<T> {

    @FunctionalInterface
    interface MobAction {
        void execute();
    }

    public MobAction doSmth = this::doSmth3;

    public Betelnut(EntityType<? extends AbstractPlant> type, Level level,
                   Builder builder) {
        super(type, level,builder);
        setTauntLevel(2);
    }

    @Override
    public void addSkills() {
        CircleMobSkill<T> idle = new CircleMobSkill<T>( "idle_test_betelnut",  999999999, 0)
                .onTick(a-> {
                    doSmth.execute();
                });
        this.addSkill(idle);
    }

    private void doSmth1(){
        level().getEntities(this, this.getBoundingBox().inflate(5f)).forEach(e -> {
            if(e instanceof Mob mob) {
                if(mob instanceof Enemy && mob.isAggressive())
                    mob.setTarget(this);
            }
        });
    }

    private void doSmth2() {
        level().getEntities(this, this.getBoundingBox().inflate(5f)).forEach(e -> {
            if(e instanceof Mob mob) {
                if(mob instanceof Enemy && mob.isAggressive() && mob.getTarget() != null && !(mob.getTarget() instanceof Betelnut))
                    mob.setTarget(this);
            }
        });
    }

    private void doSmth3() {
    }
}
