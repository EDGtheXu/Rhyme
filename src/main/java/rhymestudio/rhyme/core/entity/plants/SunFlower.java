package rhymestudio.rhyme.core.entity.plants;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.client.animation.plantAnimations.SunflowerAnimation;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.misc.SunItemEntity;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;
import rhymestudio.rhyme.core.registry.items.MaterialItems;


public class SunFlower extends AbstractPlant {

    public SunFlower(Level level, Builder builder) {
        super(PlantEntities.SUN_FLOWER.get(), level,builder);
        this.animState.addAnimation("idle_on", SunflowerAnimation.idle,1);
        this.animState.addAnimation("sun", SunflowerAnimation.sun,1);
    }

    @Override
    public void addSkills() {
        super.addSkills();
//        CircleSkill idleSkill = new CircleSkill("idle_on",40,0);
        CircleSkill idleSkill = new CircleSkill("idle_on",builder.attackInternalTick,0);
        CircleSkill sunSkill = new CircleSkill("sun",builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(skills.canTrigger()){
                        SunItemEntity entity = new SunItemEntity(level(), position().add(0,0.5,0));
                        entity.setItem(new ItemStack(MaterialItems.SOLID_SUN.get()));
                        level().addFreshEntity(entity);
                    }
                });
        addSkill(idleSkill);
        addSkill(sunSkill);
    }
}
