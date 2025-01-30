package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.core.registry.ModAttachments;

public class PlantRecordProvider implements ICapabilitySerializable<CompoundTag> {


    private PlantRecorderAttachment playerAbility;
    private final LazyOptional<PlantRecorderAttachment> abilityLazyOptional = LazyOptional.of(this::getOrCreateStorage);

    private PlantRecorderAttachment getOrCreateStorage() {
        if (playerAbility == null) {
            this.playerAbility = new PlantRecorderAttachment();
        }
        return playerAbility;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return ModAttachments.PLANT_RECORDER_STORAGE.orEmpty(capability, abilityLazyOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateStorage().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreateStorage().deserializeNBT(nbt);
    }
}
