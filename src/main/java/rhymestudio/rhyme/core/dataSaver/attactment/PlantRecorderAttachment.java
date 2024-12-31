package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantRecorderAttachment implements INBTSerializable<CompoundTag> {
    public List<Integer> ids = new LinkedList<>();

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("ids", ids);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        ids = Arrays.stream(compoundTag.getIntArray("ids")).boxed().collect(Collectors.toList());
    }
}