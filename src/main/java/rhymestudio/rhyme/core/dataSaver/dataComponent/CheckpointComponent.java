package rhymestudio.rhyme.core.dataSaver.dataComponent;

import com.mojang.serialization.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CheckpointComponent implements DataComponentType<CheckpointComponent> {

    private ResourceLocation location;

    public CheckpointComponent(ResourceLocation location) {
        this.location = location;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public static Codec<CheckpointComponent> CODEC =
            ResourceLocation.CODEC.xmap(CheckpointComponent::new, CheckpointComponent::getLocation);

    public static StreamCodec<RegistryFriendlyByteBuf,  CheckpointComponent> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, CheckpointComponent::getLocation,
            CheckpointComponent::new
            );

    @Override
    public @Nullable Codec<CheckpointComponent> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, CheckpointComponent> streamCodec() {
        return STREAM_CODEC;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CheckpointComponent component)) return false;
        return component.location.equals(location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }

}
