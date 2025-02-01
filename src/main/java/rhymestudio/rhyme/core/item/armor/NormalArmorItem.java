package rhymestudio.rhyme.core.item.armor;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.IItemExtension;

public class NormalArmorItem extends ArmorItem implements IModelArmor, IItemExtension {
    Multimap<Attribute, AttributeModifier> attributeModifiers;
    Type type;
    ModRarity rarity;

    public NormalArmorItem(ArmorMaterial material, Type type, Multimap<Attribute, AttributeModifier> modify,ModRarity rarity, Properties properties) {
        super(material, type, properties);
        this.attributeModifiers = modify;
        this.type = type;
        this.rarity = rarity;
    }
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if(type.getSlot() == slot)
            return attributeModifiers;
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        var data = ModRarity.getRarity(pStack);
        if (data == null) {
            return super.getName(pStack);
        }
        var style1 = data.getStyle();
        if (style1 == null) {
            return super.getName(pStack);
        }
        return Component.translatable(getDescriptionId()).withStyle(style -> style.withColor(data.getColor()));
    }

    @Override
    public void onStackInit(ItemStack stack) {
        if(rarity != null)
            rarity.writeToNBT(stack.getOrCreateTag());
    }
}
