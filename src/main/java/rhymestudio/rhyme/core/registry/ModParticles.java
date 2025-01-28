package rhymestudio.rhyme.core.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.particle.options.BrokenProjOptions;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<BrokenProjOptions>> BROKEN_PROJ_PARTICLE = register("broken_proj_particle", false, BrokenProjOptions.CODEC, BrokenProjOptions.STREAM_CODEC);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PUFF_PROJ_PARTICLE = PARTICLES.register("puff_proj_particle", ()->new SimpleParticleType(false));


    private static <T extends ParticleOptions> DeferredHolder<ParticleType<?>, ParticleType<T>> register(String id,boolean overrideLimiter,MapCodec<T> mapCodec, StreamCodec<? super RegistryFriendlyByteBuf, T>streamCodec){
        return PARTICLES.register(id, () -> new ParticleType<>(overrideLimiter) {
            @Override
            @NotNull
            public MapCodec<T> codec(){
                return mapCodec;
            }

            @Override
            @NotNull
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec(){
                return streamCodec;
            }
        });
    }

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String id,boolean overrideLimiter){
        return PARTICLES.register(id, () -> new SimpleParticleType(overrideLimiter));
    }
}
