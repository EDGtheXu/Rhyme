package rhymestudio.rhyme.core.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import rhymestudio.rhyme.core.registry.items.*;

public class ModItems {
    public static void registerItems(IEventBus modEventBus) {
        PlantItems.PLANTS.register(modEventBus);
        MaterialItems.MATERIALS.register(modEventBus);
        IconItems.QUALITY_ITEMS.register(modEventBus);
        ArmorItems.ARMORS.register(modEventBus);
        ToolItems.TOOLS.register(modEventBus);
        SpawnEggItems.EGGS.register(modEventBus);
    }
}
