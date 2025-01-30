package rhymestudio.rhyme.core.dataSaver.dataComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IDataComponentType<T> {

    String name();

    void readFromNBT(CompoundTag tag);
    void writeToNBT(CompoundTag tag);
    boolean isValid();

    default CompoundTag getNBT(ItemStack itemStack){
        return itemStack.getOrCreateTag().getCompound(name());
    }

    default void setNBT(ItemStack itemStack, CompoundTag tag){
        itemStack.getOrCreateTag().put(name(), tag);
    }


}
