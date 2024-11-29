package rhymestudio.rhyme.registry.Entities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.entity.AbstractMonster;
import rhymestudio.rhyme.entity.zombies.prefab.LandMonsterPrefab;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class Zombies {
    public static final DeferredRegister<EntityType<?>> ZOMBIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<AbstractMonster>> NORMAL_ZOMBIE = registerSimpleMonster("normal_zombie", LandMonsterPrefab.NORMAL_ZOMBIE_PREFAB,0.6F,1.95F);




    // 用于调整包围盒
    public static DeferredHolder<EntityType<?>, EntityType<AbstractMonster>> registerSimpleMonster(String name, Supplier<AbstractMonster.Builder> builder, float width, float height) {
        return ZOMBIES.register(name, () -> EntityType.Builder.<AbstractMonster>of((type,level)->new AbstractMonster(type,level,builder.get()), MobCategory.MONSTER).clientTrackingRange(10).setTrackingRange(50).setShouldReceiveVelocityUpdates(true).sized(width,height).build(Key(name)));
    }
    public static DeferredHolder<EntityType<?>, EntityType<AbstractMonster>> registerSimpleMonster(String name, Supplier<AbstractMonster.Builder> builder) {
        return registerSimpleMonster(name, builder, 1, 1);
    }
    public static String Key(String key){
        return MODID + ":" + key;
    }
}
