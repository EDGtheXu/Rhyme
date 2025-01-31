package rhymestudio.rhyme.datagen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;
import rhymestudio.rhyme.core.registry.entities.Zombies;

public class ModBiomeModifier {
    private static ResourceKey<BiomeModifier> createModifierKey(String name) {return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Rhyme.space(name));}

    public static final ResourceKey<BiomeModifier> NORMAL_ZOMBIE_SPAWN = createModifierKey("spawn/normal_zombie_spawns");
    public static final ResourceKey<BiomeModifier> CONE_ZOMBIE_SPAWN = createModifierKey("spawn/cone_zombie_spawns");
    public static final ResourceKey<BiomeModifier> IRON_BUCKET_ZOMBIE_SPAWN = createModifierKey("spawn/iron_bucket_zombie_spawns");
    public static final ResourceKey<BiomeModifier> CRAZY_DAVE_SPAWN = createModifierKey("spawn/crazy_dave_spawns");


    public static void createBiomeModifier(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);

        context.register(NORMAL_ZOMBIE_SPAWN, ExtendedAddSpawnsBiomeModifier.singleSpawn(
                biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD),
                biomeLookup.getOrThrow(BiomeTags.IS_END),
                new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(Zombies.NORMAL_ZOMBIE.get(),
                        80, 3, 5,
                        Zombies.NORMAL_ZOMBIE.get().getCategory())
                )
        );

        context.register(CONE_ZOMBIE_SPAWN, ExtendedAddSpawnsBiomeModifier.singleSpawn(
                        biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD),
                        biomeLookup.getOrThrow(BiomeTags.IS_END),
                        new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(Zombies.CONE_ZOMBIE.get(),
                                15, 1, 3,
                                Zombies.NORMAL_ZOMBIE.get().getCategory())
                )
        );


        context.register(IRON_BUCKET_ZOMBIE_SPAWN , ExtendedAddSpawnsBiomeModifier.singleSpawn(
                        biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD),
                        biomeLookup.getOrThrow(BiomeTags.IS_END),
                        new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(Zombies.IRON_BUCKET_ZOMBIE.get(),
                                5, 1, 2,
                                Zombies.NORMAL_ZOMBIE.get().getCategory())
                )
        );

        context.register(CRAZY_DAVE_SPAWN, ExtendedAddSpawnsBiomeModifier.singleSpawn(
                        biomeLookup.getOrThrow(BiomeTags.HAS_VILLAGE_PLAINS),
                        biomeLookup.getOrThrow(BiomeTags.IS_END),
                        new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(PlantEntities.CRAZY_DAVE.get(),
                                5, 1, 1,
                                PlantEntities.CRAZY_DAVE.get().getCategory())
                )
        );
    }
}
