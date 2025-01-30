//package rhymestudio.rhyme.datagen.biome;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.MapCodec;
//
//import net.minecraft.core.HolderGetter;
//import net.minecraft.core.registries.Registries;
//
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
//import net.minecraft.world.level.levelgen.placement.PlacedFeature;
//
//import net.minecraftforge.common.world.BiomeModifier;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//import rhymestudio.rhyme.Rhyme;
//
//import static rhymestudio.rhyme.Rhyme.MODID;
//
//
//public class ModBiomes {
//
//    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
//
//    public static final RegistryObject<Codec<ExtendedAddSpawnsBiomeModifier>> ADD_SPAWNS_BIOME_MODIFIER_TYPE = BIOME_MODIFIER_SERIALIZERS.register("extended_add_spawns", () -> ExtendedAddSpawnsBiomeModifier.CODEC);
//
//    public static final ResourceKey<Biome> VAMPIRE_FOREST = ResourceKey.create(Registries.BIOME, Rhyme.space("vampire_forest"));
//
//
//    public static void register(IEventBus bus) {
//        BIOME_MODIFIER_SERIALIZERS.register(bus);
//    }
//
//    static void createBiomes(BootstrapContext<Biome> context) {
//        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
//        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
//
////        context.register(ModBiomes.VAMPIRE_FOREST, VampirismBiomes.createVampireForest(placedFeatures, configuredCarvers));
//    }
//}
