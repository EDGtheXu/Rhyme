package rhymestudio.rhyme.core.item.armor;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public class NormalArmorItem extends ArmorItem implements IModelArmor{
    Multimap<Attribute, AttributeModifier> attributeModifiers;

    public NormalArmorItem(ArmorMaterial material, Type type,Multimap<Attribute, AttributeModifier> modify, Properties properties) {
        super(material, type, properties);
        this.attributeModifiers = modify;

    }
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return attributeModifiers;
    }
    // todo

//    @Override
//    public @NotNull MutableComponent getName(@NotNull ItemStack pStack) {
//        return Component.translatable(getDescriptionId()).withStyle(style -> style.withColor(pStack.get(ModDataComponentTypes.MOD_RARITY).getColor()));
//    }
}
