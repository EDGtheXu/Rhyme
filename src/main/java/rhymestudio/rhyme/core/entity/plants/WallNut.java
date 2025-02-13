package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

public class WallNut<T extends WallNut<T>> extends AbstractPlant<T> {
    public WallNut(EntityType<? extends AbstractPlant> type, Level level,
                   Builder builder) {
        super(type, level,builder);
    }

    @Override
    public void addSkills() {
        CircleMobSkill<T> idle1 = new CircleMobSkill<T>( "idle1",  999999999, 0)
                .onTick(a-> {
                    doSmth();
                    if(this.getHealth() / this.getMaxHealth() < 0.666){
                        skills.forceEnd();
                    }
                });
        CircleMobSkill<T> idle2 = new CircleMobSkill<T>( "idle2",  999999999, 0)
                .onTick(a-> {
                    doSmth();
                    if(this.getHealth() / this.getMaxHealth() < 0.333){
                        skills.forceEnd();
                    }
                });
        CircleMobSkill<T> idle3 = new CircleMobSkill<T>( "idle3",  999999999, 0)
                .onTick(a-> doSmth());
        this.addSkill(idle1);
        this.addSkill(idle2);
        this.addSkill(idle3);
    }

    private void doSmth(){
        level().getEntities(this, this.getBoundingBox().inflate(5f)).forEach(e -> {
            if(e instanceof Mob mob) {
                if(mob instanceof Enemy && mob.isAggressive())
                    mob.setTarget(this);
            }
        });
    }

}
