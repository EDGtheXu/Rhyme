package rhymestudio.rhyme.core.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;

import java.util.function.Consumer;

public class CustomRarityItem extends Item implements IItemExtension{
    public ModRarity rarity;
    public Consumer<ItemStack> stackInitCallback;
    public CustomRarityItem(Properties properties, ModRarity rarity) {
        super(properties);
        this.rarity = rarity;
        this.stackInitCallback = null;
    }

    public CustomRarityItem(Properties properties, ModRarity rarity, Consumer<ItemStack> stackInitCallback) {
        super(properties);
        this.rarity = rarity;
        this.stackInitCallback = stackInitCallback;
    }



//    @Override
//    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        rarity.writeToNBT(stack.getOrCreateTag());
//        return super.initCapabilities(stack, nbt);
//    }

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
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        rarity.writeToNBT(stack.getOrCreateTag());
    }

    @Override
    public void onStackInit(ItemStack stack) {
        if(rarity != null)
            rarity.writeToNBT(stack.getOrCreateTag());
        if(stackInitCallback!= null)
            stackInitCallback.accept(stack);
    }
}
