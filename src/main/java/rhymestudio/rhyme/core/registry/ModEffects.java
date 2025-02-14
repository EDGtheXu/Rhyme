package rhymestudio.rhyme.core.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.core.effect.BleedEffect;
import rhymestudio.rhyme.core.effect.FrozenEffect;
import rhymestudio.rhyme.core.effect.SlowDownEffect;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModEffects {
    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);

    public static final RegistryObject<MobEffect> SLOWDOWN_EFFECT =registerDeferredHolder("slowdown",()->new SlowDownEffect(MobEffectCategory.HARMFUL,0x80FFFF));

    public static final RegistryObject<MobEffect> FROZEN_EFFECT =registerDeferredHolder("frozen",()->new FrozenEffect(MobEffectCategory.HARMFUL,0x80FFFF));

    public static final RegistryObject<MobEffect> BLEED_EFFECT =registerDeferredHolder("bleed",()->new BleedEffect(MobEffectCategory.HARMFUL,0x80FF80));

    public static RegistryObject<MobEffect> registerDeferredHolder(String name, Supplier<MobEffect> supplier){
        return EFFECTS.register(name,supplier);
    }

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
