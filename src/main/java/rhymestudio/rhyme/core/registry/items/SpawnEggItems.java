package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.entities.Zombies;

public class SpawnEggItems{

    public static final DeferredRegister.Items EGGS = DeferredRegister.createItems(Rhyme.MODID);

    public static final DeferredItem<Item> NORMAL_ZOMBIE_EGG = register("normal_zombie_egg", "普通僵尸刷怪蛋", Zombies.NORMAL_ZOMBIE);
    public static final DeferredItem<Item> CONE_ZOMBIE_EGG = register("cone_zombie_egg", "路障僵尸刷怪蛋", Zombies.CONE_ZOMBIE);
    public static final DeferredItem<Item> IRON_BUCKET_ZOMBIE_EGG = register("pyramid_zombie_egg", "铁桶僵尸刷怪蛋", Zombies.IRON_BUCKET_ZOMBIE);




    public static <T extends Mob>DeferredItem<Item> register(String en, String zh, DeferredHolder<EntityType<?>,EntityType<T>> entityType, int color1, int color2 , ModRarity rarity) {
        DeferredItem<Item> item =  EGGS .register("egg/"+en, () -> new DeferredSpawnEggItem(entityType,color1,color2,new Item.Properties().component(ModDataComponentTypes.MOD_RARITY,rarity)));
        Rhyme.chineseProviders.add((c)->c.add(item.get(),zh));
        return item;
    }
    public static <T extends Mob>DeferredItem<Item> register(String en, String zh,DeferredHolder<EntityType<?>,EntityType<T>> entityType,int color1,int color2) {
        return register(en, zh,entityType, color1, color2, ModRarity.COMMON);
    }
    public static <T extends Mob>DeferredItem<Item> register(String en, String zh, DeferredHolder<EntityType<?>,EntityType<T>> entityType) {
        return register(en, zh, entityType, 0xFFFFFF, 0x000000);
    }
}
