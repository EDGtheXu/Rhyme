package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.core.item.tool.PlantShovel;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class ToolItems {
    public static final DeferredRegister.Items TOOLS = DeferredRegister.createItems(Rhyme.MODID);

//    public static final DeferredItem<Item> KILLER = register("debug_killer", "杀死周围生物", () -> new DebugRangeKiller(new Item.Properties().stacksTo(1).component(ModDataComponentTypes.MOD_RARITY,ModRarity.MASTER)));
    public static final DeferredItem<Item> PLANT_SHOVEL = register("plant_shovel", "植物铲子", () -> new PlantShovel(new Item.Properties().stacksTo(1).component(ModDataComponentTypes.MOD_RARITY,ModRarity.WHITE)));




    public static DeferredItem<Item> register(String en, String zh, Supplier<? extends Item> supplier) {
        DeferredItem<Item> item =  TOOLS .register("tool/"+en, supplier);
        add_zh_en(item, zh);
        return item;
    }
    public static DeferredItem<Item> register(String en, String zh, ModRarity rarity) {
        return register("tool/"+en, zh,() -> new CustomRarityItem(new Item.Properties().component(ModDataComponentTypes.MOD_RARITY,rarity)));

    }
    public static DeferredItem<Item> register(String en, String zh) {
        return register(en, zh, ModRarity.COMMON);
    }

}
