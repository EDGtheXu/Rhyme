package rhymestudio.rhyme.core.item.tool;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;

import java.util.List;

public class Pole extends CustomRarityItem {
    Multimap<Attribute, AttributeModifier> attributeModifiers;

    public Pole(Properties properties, Multimap<Attribute, AttributeModifier> attributeModifiers) {
        super(properties, ModRarity.BLUE);
        this.attributeModifiers = attributeModifiers;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(player.onGround()) {
            player.setDeltaMovement(player.getDeltaMovement().add(new Vec3(0, 0.3, 0)).scale(7));
            ItemStack itemstack = player.getItemInHand(usedHand);
            if (level instanceof ServerLevel sl)
                itemstack.hurtAndBreak(1, player, c -> {
                });
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }

    public void appendHoverText(ItemStack stack, Level context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {


    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return attributeModifiers;
        }
        return super.getAttributeModifiers(slot, stack);
    }

}
