package rhymestudio.rhyme.core.entity.plants.shroom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.AbstractGeoPlant;
import rhymestudio.rhyme.core.entity.AbstractPlant;

public abstract class AbstractShroom extends AbstractGeoPlant {


    public <T extends AbstractPlant> AbstractShroom(EntityType<T> entityType, Level level, Builder builder) {
        super(entityType, level, builder);
    }

    @Override
    protected void addSkills() {
//        CircleSkill<AbstractPlant> sleep = GeneralCircleSkills.SROOM_SLEEP_SKILLS.get();
//        addSkill(sleep);
    }

    @Override
    public void aiStep() {
        /*
        if(!level().isClientSide && !level().isNight() && !this.isUltimating) {
            if(this.skills.index != 0)
                skills.forceStartIndex(0);
            Vec3 vec31 = new Vec3(this.xxa, this.yya, this.zza);
            if (this.hasEffect(MobEffects.SLOW_FALLING) || this.hasEffect(MobEffects.LEVITATION)) {
                this.resetFallDistance();
            }

            label111: {
                LivingEntity var17 = this.getControllingPassenger();
                if (var17 instanceof Player player) {
                    if (this.isAlive()) {
                        Vec3 vec3 = this.getRiddenInput(player, vec31);
                        this.tickRidden(player, vec3);
                        if (this.isControlledByLocalInstance()) {
                            this.setSpeed(this.getRiddenSpeed(player));
                            this.travel(vec3);
                        } else {
                            this.calculateEntityAnimation(false);
                            this.setDeltaMovement(Vec3.ZERO);
                            this.tryCheckInsideBlocks();
                        }
                        break label111;
                    }
                }

                this.travel(vec31);
            }
            return;
        }*/

        actualAiStep();
        super.aiStep();
    }
    protected abstract void actualAiStep();
}
