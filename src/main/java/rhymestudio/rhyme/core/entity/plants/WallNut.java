package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.CircleMobSkill;

import java.util.Optional;

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
    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if(this.cardLevel >= 1){
            explode();
        }
    }

    protected void explode() {
        this.level().explode(this, damageSources().explosion(this,this), USED_PORTAL_DAMAGE_CALCULATOR , this.getX(), this.getY(0.0625), this.getZ(), 1, false, Level.ExplosionInteraction.MOB);
    }

    private  final ExplosionDamageCalculator USED_PORTAL_DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {
        @Override
        public boolean shouldBlockExplode(Explosion p_353087_, BlockGetter p_353096_, BlockPos p_353092_, BlockState p_353086_, float p_353094_) {
            return false;
        }

        @Override
        public Optional<Float> getBlockExplosionResistance(
                Explosion p_353090_, BlockGetter p_353088_, BlockPos p_353091_, BlockState p_353093_, FluidState p_353095_
        ) {
            return p_353093_.is(Blocks.NETHER_PORTAL)
                    ? Optional.empty()
                    : super.getBlockExplosionResistance(p_353090_, p_353088_, p_353091_, p_353093_, p_353095_);
        }
//        @Override
//        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
//            return super.getEntityDamageAmount(explosion, entity) + builder.attackDamage;
//        }

    };

}
