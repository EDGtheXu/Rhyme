package rhymestudio.rhyme.core.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomRarityItem extends Item {
    public CustomRarityItem(Properties properties) {
        super(properties);
    }
    

//    @Override
//    public @NotNull Component getName(@NotNull ItemStack pStack) {
//        var data = pStack.get(ModDataComponentTypes.MOD_RARITY);
//        if (data == null) {
//            return super.getName(pStack);
//        }
//        var style1 = data.getStyle();
//        if (style1 == null) {
//            return super.getName(pStack);
//        }
//        return Component.translatable(getDescriptionId()).withStyle(style -> style.withColor(data.getColor()));
//    }
}
