package rhymestudio.rhyme.core.item;

import net.minecraft.world.item.ItemStack;

public interface IItemExtension {
    void onStackInit(ItemStack stack);
    
}
