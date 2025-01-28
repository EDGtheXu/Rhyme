package rhymestudio.rhyme.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import rhymestudio.rhyme.core.particle.BrokenProjParticle;
import rhymestudio.rhyme.core.particle.PuffProjParticle;
import rhymestudio.rhyme.core.registry.ModParticles;

import static rhymestudio.rhyme.Rhyme.MODID;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = MODID)

public class RegisterParticle {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BROKEN_PROJ_PARTICLE.get(), BrokenProjParticle.Provider::new);
        event.registerSpriteSet(ModParticles.PUFF_PROJ_PARTICLE.get(), PuffProjParticle.Provider::new);
    }
}
