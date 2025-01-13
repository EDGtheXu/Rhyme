package rhymestudio.rhyme.core.registry.entities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.animation.plantAnimations.*;
import rhymestudio.rhyme.core.entity.CrazyDave;
import rhymestudio.rhyme.core.entity.ai.CircleSkill;
import rhymestudio.rhyme.core.entity.plants.*;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.plants.prefabs.CardLevelModifier;
import rhymestudio.rhyme.core.registry.ModSounds;

import static rhymestudio.rhyme.Rhyme.add_zh_en;
import static rhymestudio.rhyme.core.entity.plants.prefabs.EnergyBeanSkills.ChomperSkill;
import static rhymestudio.rhyme.core.entity.plants.prefabs.EnergyBeanSkills.PotatoEnergy;
import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks.*;
import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetBuilders.*;
import static rhymestudio.rhyme.core.registry.ModEntities.Key;


public class PlantEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Rhyme.MODID);

    // tip 植物
    //      tip 生产类
    public static final DeferredHolder<EntityType<?>, EntityType<SunFlower>> SUN_FLOWER = registerCreature("sunflower","向日葵" ,(type, level)->
            new SunFlower(level,NORMAL_SUNFLOWER_PLANT.get().setAnim(s->{
                s.addAnimation("idle", SunflowerAnimation.idle,1);
                s.addAnimation("sun", SunflowerAnimation.sun,1);
            }).setUltimate(new CircleSkill<>("ultimate",50, 0)
                            .onTick(e->{ if(e.tickCount % 5 == 0)
                                ((SunFlower)e).doSun();
                            }))

            ));

    //      tip 豌豆类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> PEA = registerCreature("pea_shooter","豌豆射手",(type, level)->
            new Pea(type,level, builder().setAttack(PEA_SHOOT).build(), NORMAL_PEA_PLANT.get()
                    //动画
            .setAnim(s->{
                s.addAnimation("idle", PeaAnimation.idle,1);
                s.addAnimation("shoot", PeaAnimation.shoot,1);
            })
                    // 大招
            .setUltimate(new CircleSkill<>("ultimate",50, 0)
                    .onTick(e->{ if(e.tickCount % 3 == 0)
                        PEA_SHOOT_ATTACK_BASE.accept(e, null, MiscEntities.PEA_PROJ, e.getRandom().nextFloat()*0.5f - 0.25F);
                    })
            )
                    //升级
            .setCardLevelModifier(CardLevelModifier.<Pea>builder()
                    .addModifier(1,pea->pea.attackCallback = builder()
                            .setAttack(p->p.getRandom().nextFloat() < 0.2f? SNOW_PEA_SHOOT: PEA_SHOOT)
                            .build())
                    .addModifier(2,pea->pea.attackCallback = builder()
                            .setAttack(p->p.getRandom().nextFloat() < 0.3f? SNOW_PEA_SHOOT: PEA_SHOOT)
                            .setShootCount(p->p.getRandom().nextFloat() < 0.2? 2 : 1)
                            .build())
                    .addModifier(3,pea->pea.attackCallback = builder()
                            .setAttack(p->p.getRandom().nextFloat() < 0.4f? SNOW_PEA_SHOOT: PEA_SHOOT)
                            .setShootCount(p->p.getRandom().nextFloat() < 0.3f? 2 : 1)
                            .build())
                    .addModifier(4,pea->pea.attackCallback = builder()
                            .setAttack(p->p.getRandom().nextFloat() < 0.5f? SNOW_PEA_SHOOT: PEA_SHOOT)
                            .setShootCount(p->p.getRandom().nextIntBetweenInclusive(1,3))
                            .build())
                    .buildLevelModifier()
            )
            ));

    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> SNOW_PEA = registerCreature("snow_pea_shooter","寒冰射手",(type, level)->
            new Pea(type,level, builder().setAttack(SNOW_PEA_SHOOT).build(), NORMAL_PEA_PLANT.get().setAnim(s->{
                s.addAnimation("idle", IcePeaAnimation.idle);
                s.addAnimation("shoot", IcePeaAnimation.shoot);
            }).setUltimate(new CircleSkill<>("ultimate",50, 0)
                    .onTick(e->{ if(e.tickCount % 3 == 0)
                        PEA_SHOOT_ATTACK_BASE.accept(e, null, MiscEntities.ICE_PEA_PROJ, e.getRandom().nextFloat()*0.5f - 0.25F);
                    })
            )
            ));

    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> REPEATER = registerCreature("repeater","双发射手",(type, level)->
            new Pea(type,level, builder().setAttack(PEA_SHOOT).setShootCount(2).build(), NORMAL_PEA_PLANT.get().setAnim(s->{
                s.addAnimation("idle", RepeaterAnimation.idle);
                s.addAnimation("shoot", RepeaterAnimation.shoot);
            }).setUltimate(new CircleSkill<>("ultimate",50, 0)
                    .onTick(e->{ if(e.tickCount % 2 == 0)
                        PEA_SHOOT_ATTACK_BASE.accept(e, null, MiscEntities.PEA_PROJ, e.getRandom().nextFloat()*0.5f - 0.25F);
                    })
            )
            ));

    //      tip 投手类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> CABBAGE_PULT = registerCreature("cabbage_pult","卷心菜投手",(type, level)->
            new Pea(type,level, builder().setAttack(THROWN_PEA_SHOOT).build(), NORMAL_PEA_PLANT.get().setAttackDamage(10).setAnim(s->{
                s.addAnimation("idle", CabbageAnimation.idle);
                s.addAnimation("shoot", CabbageAnimation.shoot);
            }).setUltimate(new CircleSkill<>("ultimate",80, 10)
                    .onInit(e->e.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(Rhyme.space("energy"),100, AttributeModifier.Operation.ADD_VALUE)))
                    .onTick(e->{ if(e.skills.canTrigger()){
                        level.getEntities(e,e.getBoundingBox().inflate(20),target->target instanceof Monster).forEach(tar->{
                            if(tar instanceof Monster monster)
                                THROWN_PEA_SHOOT.accept(e,monster);
                        });
                    }})
                    .onOver(e-> (e.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(Rhyme.space("energy")))
            )
            ));


    //      tip 坚果类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> WALL_NUT = registerCreature("wall_nut","坚果墙",(type, level)->
            new WallNut(type,level, DEFENSE_PLANT.apply(150).setAnim(s->{
                s.addAnimation("idle1", WallNutAnimation.idle1,1);
                s.addAnimation("idle2", WallNutAnimation.idle2,1);
                s.addAnimation("idle3", WallNutAnimation.idle3,1);
            }).setUltimate(new CircleSkill<>("ultimate",30, 5)
                    .onInit(e->e.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,500,20)))
            )
            ));


    //      tip 土豆雷类
    public static final DeferredHolder<EntityType<?>, EntityType<PotatoMine>> POTATO_MINE = registerCreature("potato_mine","土豆雷",(type, level)->
            new PotatoMine(type,level, 15 * 20,1,EXPLORE_PLANT.apply(1800).setAnim(s->{
                s.addAnimation("idle", PotatoMineAnimation.idle);
                s.addAnimation("up", PotatoMineAnimation.up);
                s.addAnimation("idle_on", PotatoMineAnimation.idle_on);
                s.addAnimation("bomb", PotatoMineAnimation.bomb);
            }).setUltimate(PotatoEnergy)
            ),1f,0.5f);

    //      tip 蘑菇类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> PUFF_SHROOM = registerCreature("puff_shroom","小喷菇",(type, level)->
            new PuffShroom(type,level, builder().setAttack(SPORE_SHOOT).setSound(ModSounds.PUFF).build(), NORMAL_PEA_PLANT.get().setAnim(s->{
                s.addAnimation("sleep", PuffShroomAnimation.sleeping,1);
                s.addAnimation("idle", PuffShroomAnimation.idle,1);
                s.addAnimation("shoot", PuffShroomAnimation.attack,1);
            }).setUltimate(new CircleSkill<>("ultimate",50, 0)
                    .onTick(e->{ if(e.tickCount % 3 == 0)
                        PEA_SHOOT_ATTACK_BASE.accept(e, null, MiscEntities.PUFF_SHROOM_PROJ, e.getRandom().nextFloat()*0.5f - 0.4F);
                    })
            )),0.5f,0.5f);


    //      tip 大嘴花类
    public static final DeferredHolder<EntityType<?>, EntityType<Chomper>> CHOMPER = registerCreature("chomper","大嘴花",(type, level)->
            new Chomper(type,level, 20 * 15,200,NORMAL_PEA_PLANT.get()
                    .setUltimate(ChomperSkill)
            ),1.1F,2.1F);



    // tip 疯狂戴夫
    public static final DeferredHolder<EntityType<?>, EntityType<CrazyDave>> CRAZY_DAVE = registerCreature("crazy_dave","疯狂戴夫",(type, level)->
            new CrazyDave(type,level),0.95f,2);


    public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerCreature(String name, String zh, EntityType.EntityFactory<T> entityFactory) {
        return registerCreature(name,zh,entityFactory,0.9F,1);
    }
    public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerCreature(String name, String zh, EntityType.EntityFactory<T> entityFactory, float w, float h) {
        var entity =ENTITIES.register(name, ()->EntityType.Builder.of(entityFactory , MobCategory.CREATURE).clientTrackingRange(10).sized(w,h).build(Key(name)));
        add_zh_en(entity, zh);
        return entity;
    }


}
