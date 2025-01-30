package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantRecorderAttachment implements INBTSerializable<CompoundTag> {
    public List<Integer> ids = new LinkedList<>();

    @Override
    public @UnknownNullability CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("ids", ids);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        ids = Arrays.stream(compoundTag.getIntArray("ids")).boxed().collect(Collectors.toList());
    }
}