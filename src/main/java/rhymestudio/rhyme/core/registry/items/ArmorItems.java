package rhymestudio.rhyme.core.registry.items;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.armor.NormalArmorItem;
import rhymestudio.rhyme.core.registry.ModArmorMaterials;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import java.util.function.Function;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class ArmorItems {
    public static final DeferredRegister.Items ARMORS = DeferredRegister.createItems(Rhyme.MODID);

    public static final DeferredItem<Item> CONE_HELMET = register("cone_helmet","路障头盔", ModArmorMaterials.CONE_ARMOR_MATERIALS,ArmorItem.Type.HELMET,50,
            ModRarity.BLUE, p->p
                    .add(Attributes.MAX_HEALTH,new AttributeModifier(Rhyme.space("cone_helmet_max_health"),0.1f,AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.HEAD)
    );
    public static final DeferredItem<Item> IRON_BUCKET_HELMET = register("iron_bucket_helmet","废桶头盔", ModArmorMaterials.IRON_BUCKET_ARMOR_MATERIALS,ArmorItem.Type.HELMET,100,
            ModRarity.BLUE, p->p
                    .add(Attributes.MAX_HEALTH,new AttributeModifier(Rhyme.space("iron_bucket_helmet_max_health"),0.2f,AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.HEAD)
            );




    public static DeferredItem<Item> register(String en, String zh, Holder<ArmorMaterial> material, ArmorItem.Type type, int durability, ModRarity rarity, Function<ItemAttributeModifiers.Builder,ItemAttributeModifiers.Builder> modify) {
        DeferredItem<Item> item =  ARMORS.register("armor/"+en, () -> new NormalArmorItem(material,type,new Item.Properties().stacksTo(1).durability(durability).component(ModDataComponentTypes.MOD_RARITY,rarity).component(DataComponents.ATTRIBUTE_MODIFIERS,modify.apply(ItemAttributeModifiers.builder().add(Attributes.ARMOR,new AttributeModifier(Rhyme.space("tier_armor"),material.value().getDefense(type),AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.HEAD)).build())));
        add_zh_en(item, zh);
        return item;
    }
    public static DeferredItem<Item> register(String en, String zh, Holder<ArmorMaterial> material,ArmorItem.Type type, int durability, ModRarity rarity) {
        DeferredItem<Item> item =  ARMORS.register("armor/"+en, () -> new NormalArmorItem(material,type,new Item.Properties().stacksTo(1).durability(durability).component(ModDataComponentTypes.MOD_RARITY,rarity)
        ));
        add_zh_en(item, zh);
        return item;
    }
    public static DeferredItem<Item> register(String en, String zh, Holder<ArmorMaterial> material,ArmorItem.Type type, int durability) {
        return register(en, zh, material,type,durability,ModRarity.COMMON);
    }

}
