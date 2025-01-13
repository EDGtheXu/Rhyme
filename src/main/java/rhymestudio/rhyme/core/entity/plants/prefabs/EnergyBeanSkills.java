package rhymestudio.rhyme.core.entity.plants.prefabs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.Chomper;
import rhymestudio.rhyme.core.entity.plants.PotatoMine;
import rhymestudio.rhyme.utils.Computer;

import java.util.ArrayList;
import java.util.List;

import static rhymestudio.rhyme.core.registry.entities.PlantEntities.POTATO_MINE;

public class EnergyBeanSkills {


    public static CircleSkill<PotatoMine> PotatoEnergy = new CircleSkill<PotatoMine>("ultimate",30, 5)
            .onInit(e->e.canBePush = false)
            .onTick(e-> {
                if (e.skills.canTrigger()){
                    for(int i=0;i<3;i++){
                        PotatoMine en = POTATO_MINE.get().create(e.level());
                        Vec3 center = e.position();
                        double r = e.getRandom().nextFloat()*2*Math.PI;
                        Vec3 pos = center.add(Math.sin(r),0.5f,Math.cos(r));
                        en.setPos(pos);
                        en.setDeltaMovement(pos.subtract(e.position()).normalize().scale(0.5f).add(0,0.4f,0));
                        e.level().addFreshEntity(en);
                        en.skills.forceStartIndex(2);
                    }
                }
            })
            .onOver(e->{
                e.skills.forceStartIndex(2);e.canBePush = true;
            })
            ;

    public static CircleSkill<Chomper> ChomperSkill = new CircleSkill<Chomper>("ultimate",50, 0)
            .onTick(e->{
                if(e.skills.tick % 3 == 0 && e.skills.tick < 12){
                    List<LivingEntity> targets = new ArrayList<>();
                    e.level().getEntities(e,e.getBoundingBox().inflate(20),target->target instanceof Enemy).forEach(tar->{
                        if(tar instanceof  LivingEntity liv && Computer.angle(e.calculateViewVector(e.getXRot(),e.yHeadRot),tar.position().subtract(e.position()).normalize()) < 20){
                            targets.add(liv);
                        }
                    });
                    LivingEntity nearest = null;
                    double distance = Double.MAX_VALUE ;

                    for(var liv : targets){
                        double dis = e.distanceToSqr(liv);
                        if(nearest != liv && dis < distance && !(e.ultimateTargets.contains(liv))){
                            nearest = liv;
                            distance = dis;
                        }
                    }
                    if(!e.ultimateTargets.contains(nearest))
                        e.ultimateTargets.add(nearest);
                }

                e.ultimateTargets.forEach(liv->{
                    if(liv!=null && liv.isAlive()){
                        liv.setDeltaMovement(liv.position().subtract(e.position()).normalize().scale(-0.5f));
                        if(liv.distanceToSqr(e) < 5){
                            e.doAttack(liv);
                        }
                    }

                });
            })
            .onOver(e->e.ultimateTargets.clear())
            ;

}
