package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static rhymestudio.rhyme.core.registry.ModAttachments.PLAYER_STORAGE;

public class SunCountProvider implements ICapabilitySerializable<CompoundTag> {

    private SunCountAttachment playerAbility;
    private final LazyOptional<SunCountAttachment> abilityLazyOptional = LazyOptional.of(this::getOrCreateStorage);

    private SunCountAttachment getOrCreateStorage() {
        if (playerAbility == null) {
            this.playerAbility = new SunCountAttachment();
        }
        return playerAbility;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return PLAYER_STORAGE.orEmpty(capability, abilityLazyOptional);
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
