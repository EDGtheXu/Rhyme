package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.core.item.tool.EnergyBean;
import rhymestudio.rhyme.core.item.tool.PlantShovel;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class ToolItems {
    public static final DeferredRegister.Items TOOLS = DeferredRegister.createItems(Rhyme.MODID);

    public static final DeferredItem<Item> ENERGY_BEAN =register("energy_bean","能量豆", ()->new EnergyBean(new Item.Properties().stacksTo(16).component(ModDataComponentTypes.MOD_RARITY,ModRarity.GREEN)));


    //    public static final DeferredItem<Item> KILLER = register("debug_killer", "杀死周围生物", () -> new DebugRangeKiller(new Item.Properties().stacksTo(1).component(ModDataComponentTypes.MOD_RARITY,ModRarity.MASTER)));
    public static final DeferredItem<Item> PLANT_SHOVEL = register("plant_shovel", "植物铲子", () -> new PlantShovel(new Item.Properties().stacksTo(1).durability(50)
        .component(ModDataComponentTypes.MOD_RARITY,ModRarity.WHITE)
        .attributes(ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Rhyme.space("plant_shovel_attack_damage"), 4.0, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.HAND
                ).add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Rhyme.space("plant_shovel_attack_speed"), 1.2, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.HAND
                )
        .build())

));



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
