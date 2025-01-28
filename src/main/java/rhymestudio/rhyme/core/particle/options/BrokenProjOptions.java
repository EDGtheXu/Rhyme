package rhymestudio.rhyme.core.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.registry.ModParticles;

public record BrokenProjOptions(String texture) implements ParticleOptions {
    @Override
    @NotNull
    public ParticleType<?> getType(){
        return ModParticles.BROKEN_PROJ_PARTICLE.get();
    }

    public static final MapCodec<BrokenProjOptions> CODEC = RecordCodecBuilder.mapCodec(
        (thisOptionsInstance) -> thisOptionsInstance.group(
            Codec.STRING.fieldOf("texture").forGetter(options -> options.texture)
        ).apply(thisOptionsInstance, BrokenProjOptions::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BrokenProjOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, BrokenProjOptions::texture,
            BrokenProjOptions::new
    );

//    public static final StreamCodec<RegistryFriendlyByteBuf, BrokenProjOptions> STREAM_CODEC = StreamCodec.of(
//        (buf, options) -> {
//            buf.writeUtf(options.texture);
//        },
//        buf -> {
//            String texture = buf.readUtf();
//            return new BrokenProjOptions(texture);
//        }
//    );


}
