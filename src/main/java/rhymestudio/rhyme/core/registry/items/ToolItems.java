package rhymestudio.rhyme.core.registry.items;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.core.item.tool.DebugRangeKiller;
import rhymestudio.rhyme.core.item.tool.EnergyBean;
import rhymestudio.rhyme.core.item.tool.PlantPutter;
import rhymestudio.rhyme.core.item.tool.PlantShovel;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class ToolItems {
    public static final DeferredRegister<Item> TOOLS = DeferredRegister.create(ForgeRegistries.ITEMS, Rhyme.MODID);

    public static final RegistryObject<Item> ENERGY_BEAN =register("energy_bean","能量豆", ()->new EnergyBean(new Item.Properties().stacksTo(16), ModRarity.GREEN));

        public static final RegistryObject<Item> KILLER = register("debug_killer", "杀死周围生物", () -> new DebugRangeKiller(new Item.Properties().stacksTo(1) ,ModRarity.MASTER));
    public static final RegistryObject<Item> PLANT_SHOVEL = register("plant_shovel", "植物铲子", () -> new PlantShovel(new Item.Properties().stacksTo(1).durability(50),
            ImmutableMultimap.of(
                    Attributes.ATTACK_DAMAGE, new AttributeModifier("15169af8-39c4-489d-ba0d-f28e66bc765d", 4.0, AttributeModifier.Operation.ADDITION),
                            Attributes.ATTACK_SPEED, new AttributeModifier("af613f76-f71a-46d8-8563-f23dc01cc23b", 1.2, AttributeModifier.Operation.ADDITION)
                )
            , ModRarity.CYAN
));
    public static final RegistryObject<Item> PLANT_PUTTER = register("plant_putter", "手推车", () -> new PlantPutter(new Item.Properties().stacksTo(1), ModRarity.PURPLE));




    public static RegistryObject<Item> register(String en, String zh, Supplier<? extends Item> supplier) {
        RegistryObject<Item> item =  TOOLS .register("tool/"+en, supplier);
        add_zh_en(item, zh);
        return item;
    }
    public static RegistryObject<Item> register(String en, String zh, ModRarity rarity) {
        return register("tool/"+en, zh,() -> new CustomRarityItem(new Item.Properties(),rarity));

    }
    public static RegistryObject<Item> register(String en, String zh) {
        return register(en, zh, ModRarity.COMMON);
    }

}
