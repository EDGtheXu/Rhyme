package rhymestudio.rhyme.core.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.core.effect.FrozenEffect;
import rhymestudio.rhyme.core.effect.SlowDownEffect;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModEffects {
    public static  DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);

    public static final DeferredHolder<MobEffect,MobEffect> SLOWDOWN_EFFECT =registerDeferredHolder("slowdown",()->new SlowDownEffect(MobEffectCategory.HARMFUL,0x80FFFF));

    public static final DeferredHolder<MobEffect,MobEffect> FROZEN_EFFECT =registerDeferredHolder("frozen",()->new FrozenEffect(MobEffectCategory.HARMFUL,0x80FFFF));

    public static DeferredHolder<MobEffect,MobEffect> registerDeferredHolder(String name, Supplier<MobEffect> supplier){
        return EFFECTS.register(name,supplier);
    }

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
