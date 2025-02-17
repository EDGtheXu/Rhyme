package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlantRecorderAttachment implements INBTSerializable<CompoundTag> {
//    public List<Integer> ids = new LinkedList<>();
    public List<UUID> uuids = new CopyOnWriteArrayList<>();


    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
//        tag.putIntArray("ids", ids);
        int i = uuids.size();
        tag.putInt("size", i);
        for (int j = 0; j < i; j++) {
            tag.putUUID(String.valueOf(j), uuids.get(j));
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
//        ids = Arrays.stream(compoundTag.getIntArray("ids")).boxed().collect(Collectors.toList());
        int size = compoundTag.getInt("size");
        for (int i = 0; i < size; i++) {
            uuids.add(compoundTag.getUUID(String.valueOf(i)));
        }
    }
}