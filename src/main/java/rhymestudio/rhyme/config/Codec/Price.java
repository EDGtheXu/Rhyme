package rhymestudio.rhyme.config.Codec;

import net.minecraft.world.item.ItemStack;

public class Price {
    public int price;
    public ItemStack itemStack;
    public Price(int price, ItemStack itemStack) {
        this.price = price;
        this.itemStack = itemStack;
    }
}
