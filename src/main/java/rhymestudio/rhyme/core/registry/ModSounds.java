package rhymestudio.rhyme.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;

import static rhymestudio.rhyme.Rhyme.MODID;


public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MODID);

    public static final DeferredHolder<SoundEvent,SoundEvent> BUZZER = register("buzzer");
    public static final DeferredHolder<SoundEvent,SoundEvent> CHOMP = register("chomp");
    public static final DeferredHolder<SoundEvent,SoundEvent> DIRT_RISE = register("dirt_rise");
    public static final DeferredHolder<SoundEvent,SoundEvent> BGM = register("grass_walk");
    public static final DeferredHolder<SoundEvent,SoundEvent> GROAN = register("groan");
    public static final DeferredHolder<SoundEvent,SoundEvent> GULP = register("gulp");
    public static final DeferredHolder<SoundEvent,SoundEvent> PLANT = register("plant");
    public static final DeferredHolder<SoundEvent,SoundEvent> PLASTIC_HIT = register("plastic_hit");
    public static final DeferredHolder<SoundEvent,SoundEvent> POINTS = register("points");
    public static final DeferredHolder<SoundEvent,SoundEvent> POTATO_MINE = register("potato_mine");
    public static final DeferredHolder<SoundEvent,SoundEvent> PUFF = register("puff");
    public static final DeferredHolder<SoundEvent,SoundEvent> SHIELD_HIT = register("shield_hit");
    public static final DeferredHolder<SoundEvent,SoundEvent> SHOVEL = register("shovel");
    public static final DeferredHolder<SoundEvent,SoundEvent> SNOW_PROJ_HIT = register("snow_proj_hit");
    public static final DeferredHolder<SoundEvent,SoundEvent> SPLAT = register("splat");


    private static DeferredHolder<SoundEvent,SoundEvent> register(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(Rhyme.space(id)));
    }

}
