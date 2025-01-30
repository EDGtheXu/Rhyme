package rhymestudio.rhyme.core.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.registry.ModParticles;

public record BrokenProjOptions(String texture) implements ParticleOptions {
    @Override
    @NotNull
    public ParticleType<?> getType(){
        return ModParticles.BROKEN_PROJ_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUtf(texture);
    }

    @Override
    public String writeToString() {
        return texture;
    }

    public static final ParticleOptions.Deserializer<BrokenProjOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        @NotNull
        public BrokenProjOptions fromCommand(@NotNull ParticleType<BrokenProjOptions> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            String texture = reader.readUnquotedString();
            return new BrokenProjOptions(texture);
        }

        @Override
        @NotNull
        public BrokenProjOptions fromNetwork(@NotNull ParticleType<BrokenProjOptions> particleType, FriendlyByteBuf buffer) {
            String texture = buffer.readUtf();
            return new BrokenProjOptions(texture);
        }
    };

    public static final Codec<BrokenProjOptions> CODEC = RecordCodecBuilder.create(
            (thisOptionsInstance) -> thisOptionsInstance.group(
                    Codec.STRING.fieldOf("texture").forGetter(BrokenProjOptions::texture)
            ).apply(thisOptionsInstance, BrokenProjOptions::new)
    );
}
