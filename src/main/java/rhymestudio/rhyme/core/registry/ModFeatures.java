package rhymestudio.rhyme.core.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.worldgen.feature.SimpleBlockNBTFeature;

import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModFeatures {
    public static final Predicate<BlockState> IS_BASE_STONE = state -> state.is(BlockTags.BASE_STONE_OVERWORLD);
    public static final Predicate<BlockState> IS_REPLACEABLE = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Rhyme.MODID);
    public static final DeferredRegister<PlacementModifierType<?>> MODIFIER_TYPES = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, Rhyme.MODID);

    public static final Supplier<SimpleBlockNBTFeature> SIMPLE_BLOCK_NBT = FEATURES.register("simple_block_nbt", () -> new SimpleBlockNBTFeature(SimpleBlockNBTFeature.Config.CODEC));


    public static void createConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

    }

    public static void createPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> placedFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

    }


    public static @Nullable BlockEntity getBlockEntity(WorldGenLevel level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity == null) {
            Rhyme.LOGGER.error("Failed to fetch block entity at ({}, {}, {})", blockPos.getX(), blockPos.getY(), blockPos.getZ());
            return null;
        }
        return blockEntity;
    }

    public static boolean safeSetBlock(WorldGenLevel level, BlockPos pos, BlockState state, Predicate<BlockState> oldState) {
        if (oldState.test(level.getBlockState(pos))) {
            return level.setBlock(pos, state, 3);
        }
        return false;
    }

    public static boolean isPosAir(WorldGenLevel level, BlockPos blockPos) {
        return level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir);
    }

    public static boolean isPosSturdy(WorldGenLevel level, BlockPos blockPos, Direction face) {
        return level.isStateAtPosition(blockPos, blockState -> blockState.isFaceSturdy(level, blockPos, face));
    }


    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Rhyme.space(name));
    }

    private static ResourceKey<PlacedFeature> createPlacedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Rhyme.space(name));
    }

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
        MODIFIER_TYPES.register(eventBus);
    }


}
