package rhymestudio.rhyme.core.entity.ai.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import rhymestudio.rhyme.core.entity.CrazyDave;

import java.util.EnumSet;

public class DaveTradeGoal extends Goal {
    private final CrazyDave mob;

    public DaveTradeGoal( CrazyDave mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.mob.isAlive()) {
            return false;
        } else if (this.mob.isInWater()) {
            return false;
        } else if (!this.mob.onGround()) {
            return false;
        } else if (this.mob.hurtMarked) {
            return false;
        } else {
            Player player = this.mob.tradingPlayer;
            if (player == null) {
                return false;
            } else {
                return !(this.mob.distanceToSqr(player) > 16.0);
            }
        }
    }
    public void tick() {
        this.mob.getNavigation().stop();
    }

    public void start() {
        this.mob.getNavigation().stop();
    }

    public void stop() {
        this.mob.tradingPlayer.closeContainer();
        this.mob.tradingPlayer = null;

    }
}
