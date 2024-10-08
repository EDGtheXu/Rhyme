package rhymestudio.rhyme.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.registry.ModEntities;

public abstract class AbstractPlant extends Mob {
    /*
    public AbstractPlant(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }*/

    public Player owner;
    public void setOwner(Player player) {
        this.owner = player;
    }

    public abstract ResourceLocation getTexture();

    public <T extends AbstractPlant> AbstractPlant(EntityType<T> tEntityType, Level level) {
        super(tEntityType, level);
    }


    @Override
    public boolean hurt(DamageSource source, float damage) {
        return !(source.getEntity() instanceof Player) && !(source.getEntity() instanceof AbstractPlant);
    }
    @Override
    public boolean canAttack(LivingEntity target) {
        if(target instanceof Monster
                || target instanceof Slime
                || (target instanceof NeutralMob  && !(target instanceof IronGolem))
        )return true;

        if(target instanceof AbstractPlant || target instanceof Player)return false;


        return false;
    }
}
