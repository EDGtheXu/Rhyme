package rhymestudio.rhyme.core.entity.zombies.prefab;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import rhymestudio.rhyme.core.entity.AbstractMonster;

public abstract class AbstractPrefab {
    /**
     * @param health 生命值
     * @param armor 防御值
     * @param attack 攻击力
     * @param followRange 跟随距离
     * @param knockBack 击退力
     * @param knockbackResistance 击退抗性
     */
    public AbstractPrefab(int health, int armor, int attack, int followRange, float knockBack, float knockbackResistance) {
        SIMPLE_FLY_DASH_MONSTER = new AbstractMonster.Builder()
                .setHealth(health)
                .setArmor(armor)
                .setAttackDamage(attack)
                .setFollowRange(followRange)
                .setKnockBack(knockBack)
                .setKnockbackResistance(knockbackResistance)
                .addTarget((t,e)->{
                    t.addGoal(1, new HurtByTargetGoal(e));
                    t.addGoal(2, new NearestAttackableTargetGoal<>(e, Player.class,false, LivingEntity::canBeSeenAsEnemy));
                    t.addGoal(3, new NearestAttackableTargetGoal<>(e, IronGolem.class,false, LivingEntity::canBeSeenAsEnemy));
                })
        ;
    }


    protected final AbstractMonster.Builder SIMPLE_FLY_DASH_MONSTER;

    public AbstractMonster.Builder getPrefab() {
        return SIMPLE_FLY_DASH_MONSTER;
    }
}
