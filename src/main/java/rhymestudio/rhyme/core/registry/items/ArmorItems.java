package rhymestudio.rhyme.core.registry.items;

import com.google.common.collect.*;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.armor.NormalArmorItem;

import java.util.UUID;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class ArmorItems {
    public static final DeferredRegister<Item> ARMORS = DeferredRegister.create(ForgeRegistries.ITEMS,Rhyme.MODID);


    static PropertyDispatch.TriFunction<Integer,Integer,Item,ArmorMaterial> supplier = (dura, defense, item) -> new ArmorMaterial() {
        @Override
        public int getDurabilityForType(@NotNull ArmorItem.Type armorType) {
            return dura;
        }

        @Override
        public int getDefenseForType(@NotNull ArmorItem.Type armorType) {
            return defense;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(item);
        }

        @Override
        public @NotNull String getName() {
            return item.getDescriptionId();
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    };

    public static final RegistryObject<Item> CONE_HELMET = register("cone_helmet","路障头盔", supplier.apply(50,2,Items.CLAY), ArmorItem.Type.HELMET,
            ModRarity.BLUE, ImmutableMultimap.of(Attributes.MAX_HEALTH,new AttributeModifier("bf9582d8-e9a6-43c2-9dca-d8e1030ca74a",0.1f,AttributeModifier.Operation.MULTIPLY_BASE)));

    public static final RegistryObject<Item> IRON_BUCKET_HELMET = register("iron_bucket_helmet","废桶头盔",  supplier.apply(100,3,Items.IRON_INGOT),ArmorItem.Type.HELMET,
            ModRarity.BLUE, ImmutableMultimap.of(Attributes.MAX_HEALTH,new AttributeModifier("fa7e05b7-1290-41fb-85df-a9537c2035ee",0.2f,AttributeModifier.Operation.MULTIPLY_BASE)));




    public static RegistryObject<Item> register(String en, String zh, ArmorMaterial material, ArmorItem.Type type, ModRarity rarity, Multimap<Attribute, AttributeModifier> modify) {
        RegistryObject<Item> item =  ARMORS.register("armor/"+en, () -> new NormalArmorItem(material,type, modify, rarity, new Item.Properties().stacksTo(1)
//                .component(DataComponents.ATTRIBUTE_MODIFIERS, modify.apply(ItemAttributeModifiers.builder().add(Attributes.ARMOR,new AttributeModifier(Rhyme.space("tier_armor"),material.getDefenseForType(type),AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.HEAD)).build()))
                ));
        add_zh_en(item, zh);
        return item;
    }
    public static RegistryObject<Item> register(String en, String zh, ArmorMaterial material,ArmorItem.Type type, ModRarity rarity) {
        return register(en, zh, material,type,rarity, ImmutableMultimap.of());
    }
    public static RegistryObject<Item> register(String en, String zh, ArmorMaterial material,ArmorItem.Type type) {
        return register(en, zh, material,type,ModRarity.COMMON);
    }

}
