package rhymestudio.rhyme.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import rhymestudio.rhyme.core.registry.ModBlocks;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootProvider extends BlockLootSubProvider {

    public static final Set<Block> BLOCK = Set.of(
//            ModBlock.YU_YAN_ORE.get(),
//            ModBlock.BA_JIN_ORE.get()
    );

    public ModBlockLootProvider(HolderLookup.Provider registries) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(),registries);
    }

    @Override
    protected void generate() {
        getKnownBlocks().forEach(this::dropSelf);

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(entry -> entry.get()).collect(Collectors.toList());
    }
}