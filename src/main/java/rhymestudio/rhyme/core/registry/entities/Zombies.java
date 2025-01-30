package rhymestudio.rhyme.core.registry.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.core.entity.AbstractMonster;
import rhymestudio.rhyme.core.entity.zombies.NormalZombie;
import rhymestudio.rhyme.core.entity.zombies.prefab.LandMonsterPrefab;

import static rhymestudio.rhyme.Rhyme.MODID;
import static rhymestudio.rhyme.Rhyme.add_zh_en;
import static rhymestudio.rhyme.core.registry.ModEntities.Key;

public class Zombies {
    public static final DeferredRegister<EntityType<?>> ZOMBIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<NormalZombie>> NORMAL_ZOMBIE = registerMonster("normal_zombie", "普通僵尸",(e, l)->new NormalZombie(e,l,LandMonsterPrefab.NORMAL_ZOMBIE_PREFAB.get()) ,0.6F,1.95F);
    public static final RegistryObject<EntityType<NormalZombie>> CONE_ZOMBIE = registerMonster("cone_zombie","路障僵尸", (e, l)->new NormalZombie(e,l,LandMonsterPrefab.CONE_ZOMBIE_PREFAB.get()),0.6F,1.95F);
    public static final RegistryObject<EntityType<NormalZombie>> IRON_BUCKET_ZOMBIE = registerMonster("iron_bucket_zombie", "铁桶僵尸", (e,l)->new NormalZombie(e,l,LandMonsterPrefab.IRON_BUCKET_ZOMBIE_PREFAB.get()),0.6F,1.95F);



    public static <T extends AbstractMonster> RegistryObject<EntityType<T>> registerMonster(String name,String zh, EntityType.EntityFactory<T> entityFactory, float width, float height) {
        var entity = ZOMBIES.register(name, () -> EntityType.Builder.of(entityFactory, MobCategory.MONSTER).clientTrackingRange(10).setTrackingRange(50).setShouldReceiveVelocityUpdates(true).sized(width,height).build(Key(name)));
        add_zh_en(entity, zh);
        return entity;
    }


}
