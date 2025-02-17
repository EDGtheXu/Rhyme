package rhymestudio.rhyme.core.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;
import rhymestudio.rhyme.core.registry.ModStructures;
import rhymestudio.rhyme.utils.RhymeUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public abstract class StaffStructure extends Structure {
    protected final List<String> generationPath;

    public static <T extends StaffStructure> MapCodec<T> getCodec(BiFunction<StructureSettings, List<String>, T> constructor){
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                settingsCodec(instance),
                Codec.STRING.listOf().fieldOf("generation").forGetter(str->str.generationPath)
        ).apply(instance, constructor));
    }

    /**
     * @param generationPath 随机json结构文件的路径
     */
    protected StaffStructure(StructureSettings settings, List<String> generationPath) {
        super(settings);
        this.generationPath = generationPath;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        int lowestY = getLowestY(context, 16, 16);
        if (lowestY < context.chunkGenerator().getSeaLevel() - 16) {
            return Optional.empty();
        }
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, builder -> {
            ChunkPos chunkPos = context.chunkPos();
            WorldgenRandom random = context.random();

            Piece piece = new Piece(chunkPos.getMinBlockX(), lowestY, chunkPos.getMinBlockZ(), Util.getRandom(RhymeUtils.HORIZONTAL, random), generationPath.get(random.nextInt(generationPath.size())));
            builder.addPiece(piece);
        });
    }



    public static class Piece extends ScatteredFeaturePiece {
        private boolean placed = false;
        String path;

        // [width, height, depth] -> [offsetX, offsetY, offsetZ]
        public Piece(int x, int y, int z, Direction orientation, String path) {
            super(ModStructures.DAVE_HOUSE_PIECE.get(), x, y, z, 24, 24, 24, orientation);
            this.path = path;
        }

        public Piece(CompoundTag tag) {
            super(ModStructures.DAVE_HOUSE_PIECE.get(), tag);
            this.placed = tag.getBoolean("Placed");
            this.path = tag.getString("Path");
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putBoolean("Placed", placed);
            tag.putString("Path", path);
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            if (placed) return;
            this.placed = true;

            try {
                StructureStaffComponent.generate(level, pos, Rhyme.space("worldgen/structure_staff/").withSuffix(path).withSuffix(".json"));

            } catch (IOException e) {
                throw new Error("Failed to load staff structure", e);
            }

        }
    }
}
