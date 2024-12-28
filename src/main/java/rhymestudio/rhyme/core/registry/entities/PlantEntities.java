package rhymestudio.rhyme.core.registry.entities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.animation.plantAnimations.*;
import rhymestudio.rhyme.core.entity.CrazyDave;
import rhymestudio.rhyme.core.entity.plants.*;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.registry.ModSounds;

import static rhymestudio.rhyme.Rhyme.add_zh_en;
import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetAttacks.*;
import static rhymestudio.rhyme.core.entity.plants.prefabs.PresetBuilders.*;


public class PlantEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Rhyme.MODID);

    // tip 植物
    //      tip 生产类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> SUN_FLOWER = registerPlants("sunflower","向日葵" ,(type, level)->
            new SunFlower(level,NORMAL_SUNFLOWER_PLANT.get()));

    //      tip 豌豆类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> PEA = registerPlants("pea_shooter","豌豆射手",(type, level)->
            new Pea(type,level, PeaAnimation.idle,PeaAnimation.shoot,
                    builder().setAttack(PEA_SHOOT).build(), NORMAL_PEA_PLANT.get()));

    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> SNOW_PEA = registerPlants("snow_pea_shooter","寒冰射手",(type, level)->
            new Pea(type,level, IcePeaAnimation.idle_normal, IcePeaAnimation.shoot,
                    builder().setAttack(SNOW_PEA_SHOOT).build(), NORMAL_PEA_PLANT.get()));

    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> DOUBLE_PEA = registerPlants("double_pea_shooter","双发射手",(type,level)->
            new Pea(type,level, DoublePeaAnimation.idle_normal,DoublePeaAnimation.shoot,
                    builder().setAttack(PEA_SHOOT).setShootCount(2).build(), NORMAL_PEA_PLANT.get()));

    //      tip 坚果类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> WALL_NUT = registerPlants("wall_nut","坚果墙",(type, level)->
            new WallNut(type,level, WallNutAnimation.idle1,WallNutAnimation.idle2,WallNutAnimation.idle3,
                    DEFENSE_PLANT.apply(150)));

    //      tip 土豆雷类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> POTATO_MINE = registerPlants("potato_mine","土豆雷",(type,level)->
            new PotatoMine(type,level, PotatoMineAnimation.idle, PotatoMineAnimation.up, PotatoMineAnimation.idle_on, PotatoMineAnimation.bomb,
                    15 * 20,1,EXPLORE_PLANT.apply(1800)),1f,0.5f);

    //      tip 蘑菇类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> PUFF_SHROOM = registerPlants("puff_shroom","小喷菇",(type, level)->
            new PuffShroom(type,level, PuffShroomAnimation.sleeping, PuffShroomAnimation.idle, PuffShroomAnimation.attack ,
                    builder().setAttack(SPORE_SHOOT).setSound(ModSounds.PUFF).build(), NORMAL_PEA_PLANT.get()),0.5f,0.5f);

    //      tip 投手类
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractPlant>> CABBAGE_PULT = registerPlants("cabbage_pult","卷心菜投手",(type,level)->
            new Pea(type,level, CabbageAnimation.idle, CabbageAnimation.attack,
                    builder().setAttack(THROWN_PEA_SHOOT).build(), NORMAL_PEA_PLANT.get().setAttackDamage(10)));

    //      tip 大嘴花类
    public static final DeferredHolder<EntityType<?>, EntityType<Chomper>> CHOMPER = registerPlants("chomper","大嘴花",(type,level)->
            new Chomper(type,level, 20 * 15,200,NORMAL_PEA_PLANT.get()),1.1F,2.1F);


    //      tip 疯狂戴夫
    public static final DeferredHolder<EntityType<?>, EntityType<CrazyDave>> CRAZY_DAVE = registerPlants("crazy_dave","疯狂戴夫",(type, level)->
            new CrazyDave(type,level),0.95f,2);


    public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerPlants(String name, String zh, EntityType.EntityFactory<T> entityFactory) {
        return registerPlants(name,zh,entityFactory,0.9F,1);
    }
    public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerPlants(String name, String zh,EntityType.EntityFactory<T> entityFactory, float w,float h) {
        var entity =ENTITIES.register(name, ()->EntityType.Builder.of(entityFactory , MobCategory.MISC).clientTrackingRange(10).sized(w,h).build("rhyme:entity."+name));
        add_zh_en(entity, zh);
        return entity;
    }









}
