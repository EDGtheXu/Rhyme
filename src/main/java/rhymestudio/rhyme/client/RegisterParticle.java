package rhymestudio.rhyme.client;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rhymestudio.rhyme.core.particle.BrokenProjParticle;
import rhymestudio.rhyme.core.registry.ModParticles;

import static rhymestudio.rhyme.Rhyme.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = MODID)

public class RegisterParticle {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BROKEN_PROJ_PARTICLE.get(), BrokenProjParticle.Provider::new);

    }
}
