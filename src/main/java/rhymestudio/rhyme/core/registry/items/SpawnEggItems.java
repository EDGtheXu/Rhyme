package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;
import rhymestudio.rhyme.core.registry.entities.Zombies;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class SpawnEggItems{

    public static final DeferredRegister<Item> EGGS = DeferredRegister.create(ForgeRegistries.ITEMS,Rhyme.MODID);

    public static final RegistryObject<Item> NORMAL_ZOMBIE_EGG = register("normal_zombie_egg", "普通僵尸刷怪蛋", Zombies.NORMAL_ZOMBIE,0x216853,0x2e5c43);
    public static final RegistryObject<Item> CONE_ZOMBIE_EGG = register("cone_zombie_egg", "路障僵尸刷怪蛋", Zombies.CONE_ZOMBIE, 0x216853,0xc37612);
    public static final RegistryObject<Item> IRON_BUCKET_ZOMBIE_EGG = register("pyramid_zombie_egg", "铁桶僵尸刷怪蛋", Zombies.IRON_BUCKET_ZOMBIE,0x216853,0xffffff);
    public static final RegistryObject<Item> CRAZY_DAVE_EGG = register("crazy_dave_egg", "疯狂戴夫刷怪蛋", PlantEntities.CRAZY_DAVE,0xFFFFFF,0xe5af2c);




    public static <T extends Mob>RegistryObject<Item> register(String en, String zh, Supplier<EntityType<T>> entityType, int color1, int color2 , ModRarity rarity) {
        RegistryObject<Item> item =  EGGS .register("egg/"+en, () -> new ForgeSpawnEggItem(entityType,color1,color2,new Item.Properties()/*.component(ModDataComponentTypes.MOD_RARITY,rarity)*/));
        add_zh_en(item, zh);
        return item;
    }
    public static <T extends Mob>RegistryObject<Item> register(String en, String zh,Supplier<EntityType<T>> entityType,int color1,int color2) {
        return register(en, zh,entityType, color1, color2, ModRarity.COMMON);
    }
    public static <T extends Mob>RegistryObject<Item> register(String en, String zh, Supplier<EntityType<T>> entityType) {
        return register(en, zh, entityType, 0xFFFFFF, 0x000000);
    }
}
