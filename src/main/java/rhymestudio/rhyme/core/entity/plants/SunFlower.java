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

    }

    public void doSun(){
        SunItemEntity entity = new SunItemEntity(level(), position().add(0,0.5,0));
        entity.setDeltaMovement(random.nextFloat()*0.05f,0.05f,random.nextFloat()*0.05f);
        entity.setItem(new ItemStack(MaterialItems.SOLID_SUN.get()));
        level().addFreshEntity(entity);
    }

    @Override
    public void addSkills() {
        super.addSkills();
        CircleSkill<AbstractPlant> idleSkill = new CircleSkill<>("idle",builder.attackInternalTick,0);
        CircleSkill<AbstractPlant> sunSkill = new CircleSkill<>("sun",builder.attackAnimTick, builder.attackTriggerTick)
                .onTick(a->{
                    if(skills.canTrigger()){
                        doSun();
                    }
                });
        addSkill(idleSkill);
        addSkill(sunSkill);
    }
}
