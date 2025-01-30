package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.Rhyme;

public class ToolItems {
    public static final DeferredRegister<Item> TOOLS = DeferredRegister.create(ForgeRegistries.ITEMS, Rhyme.MODID);

//    public static final RegistryObject<Item> ENERGY_BEAN =register("energy_bean","能量豆", ()->new EnergyBean(new Item.Properties().stacksTo(16).component(ModDataComponentTypes.MOD_RARITY,ModRarity.GREEN)));
//
//
//    //    public static final RegistryObject<Item> KILLER = register("debug_killer", "杀死周围生物", () -> new DebugRangeKiller(new Item.Properties().stacksTo(1).component(ModDataComponentTypes.MOD_RARITY,ModRarity.MASTER)));
//    public static final RegistryObject<Item> PLANT_SHOVEL = register("plant_shovel", "植物铲子", () -> new PlantShovel(new Item.Properties().stacksTo(1).durability(50)
//        .component(ModDataComponentTypes.MOD_RARITY,ModRarity.WHITE)
//        .attributes(ItemAttributeModifiers.builder()
//                .add(
//                        Attributes.ATTACK_DAMAGE,
//                        new AttributeModifier(Rhyme.space("plant_shovel_attack_damage"), 4.0, AttributeModifier.Operation.ADD_VALUE),
//                        EquipmentSlotGroup.HAND
//                ).add(
//                        Attributes.ATTACK_SPEED,
//                        new AttributeModifier(Rhyme.space("plant_shovel_attack_speed"), 1.2, AttributeModifier.Operation.ADD_VALUE),
//                        EquipmentSlotGroup.HAND
//                )
//        .build())
//
//));
//    public static final RegistryObject<Item> PLANT_PUTTER = register("plant_putter", "手推车", () -> new  PlantPutter(new Item.Properties().stacksTo(1)));
//
//
//
//
//
//
//    public static RegistryObject<Item> register(String en, String zh, Supplier<? extends Item> supplier) {
//        RegistryObject<Item> item =  TOOLS .register("tool/"+en, supplier);
//        add_zh_en(item, zh);
//        return item;
//    }
//    public static RegistryObject<Item> register(String en, String zh, ModRarity rarity) {
//        return register("tool/"+en, zh,() -> new CustomRarityItem(new Item.Properties().component(ModDataComponentTypes.MOD_RARITY,rarity)));
//
//    }
//    public static RegistryObject<Item> register(String en, String zh) {
//        return register(en, zh, ModRarity.COMMON);
//    }

}
