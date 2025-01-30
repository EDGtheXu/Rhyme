package rhymestudio.rhyme.core.registry;

import net.minecraft.sounds.SoundEvent;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.Rhyme;

import static rhymestudio.rhyme.Rhyme.MODID;


public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> BUZZER = register("buzzer");
    public static final RegistryObject<SoundEvent> CHOMP = register("chomp");
    public static final RegistryObject<SoundEvent> DIRT_RISE = register("dirt_rise");
    public static final RegistryObject<SoundEvent> BGM = register("grass_walk");
    public static final RegistryObject<SoundEvent> GROAN = register("groan");
    public static final RegistryObject<SoundEvent> GULP = register("gulp");
    public static final RegistryObject<SoundEvent> PLANT = register("plant");
    public static final RegistryObject<SoundEvent> PLASTIC_HIT = register("plastic_hit");
    public static final RegistryObject<SoundEvent> POINTS = register("points");
    public static final RegistryObject<SoundEvent> POTATO_MINE = register("potato_mine");
    public static final RegistryObject<SoundEvent> PUFF = register("puff");
    public static final RegistryObject<SoundEvent> SHIELD_HIT = register("shield_hit");
    public static final RegistryObject<SoundEvent> SHOVEL = register("shovel");
    public static final RegistryObject<SoundEvent> SNOW_PROJ_HIT = register("snow_proj_hit");
    public static final RegistryObject<SoundEvent> SPLAT = register("splat");


    private static RegistryObject<SoundEvent> register(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(Rhyme.space(id)));
    }

}
