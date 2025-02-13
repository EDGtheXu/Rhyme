package rhymestudio.rhyme.core.entity.plants.derivate;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

public class BakedPotato<T extends BakedPotato<T>>  extends AbstractPlant<T> {
    public BakedPotato(EntityType<? extends AbstractPlant> type, Level level,
                   Builder builder) {
        super(type, level,builder);
    }

    @Override
    protected void addSkills() {
        CircleMobSkill<T> idle = new CircleMobSkill<T>("idle", 999999999, 0)
                .onTick(a-> {
                    doSmth();
                });
        this.addSkill(idle);
    }

    private void doSmth(){
        level().getEntities(this, this.getBoundingBox().inflate(7f)).forEach(e -> {
            if(e instanceof Mob mob) {
                if(mob instanceof Enemy && mob.isAggressive())
                    mob.setTarget(this);
            }
        });
    }
}
