package rhymestudio.rhyme.core.entity.zombies.prefab;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;

import net.minecraft.world.entity.player.Player;
import rhymestudio.rhyme.client.animation.zombieAnimations.NormalZombieAnimation;
import rhymestudio.rhyme.core.entity.AbstractMonster;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.ai.goals.JumpOverBlockGoal;
import rhymestudio.rhyme.core.registry.ModSounds;

import java.util.function.Supplier;

import static rhymestudio.rhyme.core.entity.AbstractMonster.copyFrom;

public class LandMonsterPrefab extends AbstractPrefab {

    public static Supplier<AbstractMonster.Builder> NORMAL_ZOMBIE_PREFAB =
            ()->new LandMonsterPrefab(35,2,4,30,0.5f,0.1f).getPrefab()
                    .setStepHeight(2.4f)
                    .setJumpStrength(0.5f)
                    .setAmbientSound(ModSounds.GROAN)
                    .addTarget((t,e)->{
                        t.addGoal(1,new HurtByTargetGoal(e));
                        t.addGoal(2, new NearestAttackableTargetGoal<>(e, AbstractPlant.class,false, LivingEntity::canBeSeenAsEnemy));
                        t.addGoal(3, new NearestAttackableTargetGoal<>(e, Player.class,false, LivingEntity::canBeSeenAsEnemy));

                    })
                    .addGoal((g,e)-> {
//                        g.addGoal(1, new JumpAttack(e, 3, 8));
                        g.addGoal(2, new JumpOverBlockGoal(e));
                        g.addGoal(3, new MeleeAttackGoal(e,  1f, true));

                        g.addGoal(7, new WaterAvoidingRandomStrollGoal(e, 1.0));

                    })
                    .addAnimation(state->{
                        state.addAnimation("walk", NormalZombieAnimation.walk);
                        state.addAnimation("idle", NormalZombieAnimation.idle);
                        state.addAnimation("attack", NormalZombieAnimation.attack);
                        state.addAnimation("hurt", NormalZombieAnimation.hurt);
                        state.addAnimation("run", NormalZombieAnimation.run);
                    })
            ;

    public static Supplier<AbstractMonster.Builder> CONE_ZOMBIE_PREFAB =()->
            copyFrom(NORMAL_ZOMBIE_PREFAB).setHealth(70);

    public static Supplier<AbstractMonster.Builder> IRON_BUCKET_ZOMBIE_PREFAB =()->
            copyFrom(NORMAL_ZOMBIE_PREFAB).setHealth(105);





    public LandMonsterPrefab(int health,int armor,int attack,int followRange,float knockBack,float knockbackResistance) {
        this(health,armor,attack,0.2f,followRange,knockBack,knockbackResistance);
    }

    public LandMonsterPrefab(int health,int armor,int attack,float moveSpeed,int followRange,float knockBack,float knockbackResistance) {
        super(health,armor,attack,followRange,knockBack,knockbackResistance);
        SIMPLE_FLY_DASH_MONSTER
                .setNavigation((e)->new GroundPathNavigation(e,e.level()))
                .setSafeFall(8)

                .setMovementSpeed(moveSpeed)

        ;
    }


    public AbstractMonster.Builder getPrefab() {
        return SIMPLE_FLY_DASH_MONSTER;
    }

}
