package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
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
import rhymestudio.rhyme.core.registry.ModSounds;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PotatoMine extends AbstractPlant<PotatoMine> {

    private int readyTime;
    private final float explosionRadius;

    public Boolean triggerDeathSpeech;

    public PotatoMine(EntityType<? extends AbstractPlant> type, Level level,
                      int readyTick,
                      float explosionRadius,
                      Builder builder) {
        super(type, level,builder);
        this.readyTime = readyTick;
        this.explosionRadius = explosionRadius;
        this.triggerDeathSpeech = false;
    }

    public static final EntityDataAccessor<Float> DATA_SPEED = SynchedEntityData.defineId(PotatoMine.class, EntityDataSerializers.FLOAT);


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SPEED, 1F);

    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
        super.onSyncedDataUpdated(var1);
        if (var1 == DATA_SPEED && level().isClientSide) {
            this.animState.globalAnimSpeed = this.entityData.get(DATA_SPEED);

        }
    }

    @Override
    public void addSkills() {
        CircleMobSkill<PotatoMine> idle = new CircleMobSkill<>( "idle", readyTime, 0);
        CircleMobSkill<PotatoMine> up = new CircleMobSkill<>( "up",  29, 0);
        CircleMobSkill<PotatoMine> on = new CircleMobSkill<PotatoMine>( "idle_on",  999999999, 0)
                .onTick(a-> {

                        AtomicReference<Float> minDistance = new AtomicReference<>((float) 1000000000);
                        AtomicBoolean flag = new AtomicBoolean(false);
                        level().getEntities(this, this.getBoundingBox().inflate(5f)).forEach(e->{
                            float distance = distanceTo(e);
                            if(distance < 8){
                                if(e instanceof Mob mob && mob instanceof Enemy){
                                    if(distance < 1.5f){
                                        flag.set(true);
                                    }
                                    minDistance.set(Math.min(minDistance.get(), distance));

                                }
                            }

                        });
                        float distance = minDistance.get();
                        if(distance < 8f){
                            this.entityData.set(DATA_SPEED, 1/distance/distance*64);
                        }else this.entityData.set(DATA_SPEED, 1f);
                        if(flag.get()) {
                            this.entityData.set(DATA_SPEED, 1f);
                            skills.forceEnd();
                        }

                });
        CircleMobSkill<PotatoMine> boom = new CircleMobSkill<PotatoMine>( "bomb",  999999999, 20)
                .onTick(a-> {
                    if(skills.canTrigger()){
                        playSound(ModSounds.POTATO_MINE.get());
                        this.discard();
                        this.explode();
                        if(triggerDeathSpeech){
                            var bakedPotato = PlantEntities.BAKED_POTATO.get().create(this.level());
                            this.level().addFreshEntity(bakedPotato);
                            bakedPotato.setPos(this.getX(), this.getY(), this.getZ());
                            bakedPotato.setCardLevel(this.getCardLevel());
//                            bakedPotato.setHealth(bakedPotato.getMaxHealth());
                        }
                    }
                });
        this.addSkill(idle);
        this.addSkill(up);
        this.addSkill(on);
        this.addSkill(boom);
    }
    protected void explode() {
        this.level().explode(this, Explosion.getDefaultDamageSource(this.level(), this), USED_PORTAL_DAMAGE_CALCULATOR , this.getX(), this.getY(0.0625), this.getZ(), explosionRadius, false, Level.ExplosionInteraction.TRIGGER);
    }

    private  final ExplosionDamageCalculator USED_PORTAL_DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {
        @Override
        public boolean shouldBlockExplode(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, float power) {
            return false;
        }

        @Override
        public Optional<Float> getBlockExplosionResistance(
                Explosion explosion, BlockGetter blockGetter, BlockPos pos, BlockState state, FluidState fluidState
        ) {
            return state.is(Blocks.NETHER_PORTAL)
                    ? Optional.empty()
                    : super.getBlockExplosionResistance(explosion, blockGetter, pos, state, fluidState);
        }
        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
            return super.getEntityDamageAmount(explosion, entity) + builder.attackDamage;
        }

    };
    @Override
    public void tick() {
        super.tick();
    }
    public boolean canBeSeenAsEnemy(){
        return super.canBeSeenAsEnemy();
    }
    public boolean isInWall(){
        return false;
    }
}
