package rhymestudio.rhyme.core.dataSaver.dataComponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;


public record EntitySaverComponent(CompoundTag tag, ResourceLocation type) implements DataComponentType<EntitySaverComponent> {

    public static final Codec<EntitySaverComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CompoundTag.CODEC.fieldOf("tag").forGetter(EntitySaverComponent::tag),
            ResourceLocation.CODEC.fieldOf("type").forGetter(EntitySaverComponent::type)
    ).apply(instance, EntitySaverComponent::new));

    public static final StreamCodec<ByteBuf, EntitySaverComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);


    @Override
    public @Nullable Codec<EntitySaverComponent> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, EntitySaverComponent> streamCodec() {
        return STREAM_CODEC;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof EntitySaverComponent component)) return false;
        return component.tag.equals(tag);
    }

}
