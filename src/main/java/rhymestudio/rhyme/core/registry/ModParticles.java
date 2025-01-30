package rhymestudio.rhyme.core.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;


import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.particle.options.BrokenProjOptions;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<ParticleType<BrokenProjOptions>> BROKEN_PROJ_PARTICLE = register("broken_proj_particle", false, BrokenProjOptions.DESERIALIZER, BrokenProjOptions.CODEC);


    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String pKey, boolean pOverrideLimiter, ParticleOptions.Deserializer<T> pDeserializer, final  Codec<T> pCodecFactory) {
        return PARTICLES.register(pKey, () -> new ParticleType<>(pOverrideLimiter, pDeserializer) {
            public @NotNull Codec<T> codec() {
                return pCodecFactory;
            }
        });
    }
}
