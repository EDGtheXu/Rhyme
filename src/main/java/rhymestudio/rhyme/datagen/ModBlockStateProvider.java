package rhymestudio.rhyme.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.ModBlocks;


import java.util.List;

import static rhymestudio.rhyme.Rhyme.MODID;


public class ModBlockStateProvider extends BlockStateProvider {
//    private static final String[] WOODS = Arrays.stream(LogBlocks.WoodSetType.values()).map(woodSetType -> woodSetType.name().toLowerCase()).toArray(String[]::new);

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // 一般方块
        List<DeferredRegister<Block>> normalBlocks = List.of(
                ModBlocks.BLOCKS
        );
        normalBlocks.forEach(blocks -> blocks.getEntries().forEach(block -> {
            Block value = block.get();
            try {
                simpleBlock(value);
            } catch (Exception e) {
                Rhyme.LOGGER.error(e.getMessage());
            }
        }));

    }

}
