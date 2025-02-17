package rhymestudio.rhyme.core.dataSaver.dataComponent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 构造法杖
 * @param blocks
 */
public record StructureStaffComponent(List<Tuple> blocks, int maxx, int maxy, int maxz) implements DataComponentType<StructureStaffComponent> {

    public List<StructureStaffComponent> split(int maxBlocksPerPacket) {
        List<StructureStaffComponent> result = new ArrayList<>();
        int currentSize = 0;
        List<Tuple> currentList = new ArrayList<>();
        for (Tuple tuple : blocks) {
            if (currentSize + 1 > maxBlocksPerPacket) {
                result.add(new StructureStaffComponent(currentList, maxx, maxy, maxz));
                currentSize = 0;
                currentList = new ArrayList<>();
            }
            currentList.add(tuple);
            currentSize++;
        }
        if (!currentList.isEmpty()) {
            result.add(new StructureStaffComponent(currentList, maxx, maxy, maxz));
        }
        return result;
    }


    /**
     * 从json中生成结构
     * @param level
     * @param startPos
     * @param json
     */
    public static void generate(LevelAccessor level, BlockPos startPos, ResourceLocation json) throws IOException {
        CODEC.decode(JsonOps.INSTANCE, GsonHelper.parseArray(level.getServer().getResourceManager().openAsReader(json))).result().ifPresent(pair -> {
            pair.getFirst().generate(level, startPos);
        });
    }
    /**
     * 从json中生成结构
     * @param level
     * @param startPos
     * @param json
     */
    public static void generate(LevelAccessor level, BlockPos startPos, JsonElement json){
        CODEC.decode(JsonOps.INSTANCE, json).result().ifPresent(pair -> {
            pair.getFirst().generate(level, startPos);
        });
    }

    /**
     * 生成结构
     * @param level
     * @param startPos
     */
    public void generate(LevelAccessor level, BlockPos startPos) {
        blocks.forEach(tuple -> {
            BlockPos pos = tuple.position().offset(startPos);
            level.setBlock(pos, tuple.block(), 2);
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity!=null && tuple.nbt().isPresent()){
                entity.loadWithComponents(tuple.nbt().get(), level.registryAccess());
            }
        });
    }

    /**
     * 保存结构
     * @return
     */
    public JsonObject save(){
        return CODEC.encodeStart(JsonOps.INSTANCE, this).result().get().getAsJsonObject();
    }

    /**
     * 从方块坐标保存结构
     * @param level
     * @param p1
     * @param p2
     * @return
     */
    public static StructureStaffComponent loadFromVertex(Level level, BlockPos p1, BlockPos p2){
        int minx = Math.min(p1.getX(), p2.getX());
        int maxx = Math.max(p1.getX(), p2.getX());
        int miny = Math.min(p1.getY(), p2.getY()) + 1;
        int maxy = Math.max(p1.getY(), p2.getY()) - 1;
        int minz = Math.min(p1.getZ(), p2.getZ());
        int maxz = Math.max(p1.getZ(), p2.getZ());

        List<StructureStaffComponent.Tuple> tuples = new ArrayList<>();
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                for (int z = minz; z <= maxz; z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(blockPos);
                    BlockEntity entity = level.getBlockEntity(blockPos);

                    if(!state.isAir())
                        tuples.add(new StructureStaffComponent.Tuple(state, new BlockPos(x - minx, y - miny, z - minz), entity==null? Optional.empty(): Optional.of(entity.saveCustomAndMetadata(level.registryAccess()))));
                }
            }
        }
        return new StructureStaffComponent(tuples, maxx - minx + 1, maxy - miny + 1, maxz - minz + 1);
    }


    public record Tuple(BlockState block, BlockPos position, Optional<CompoundTag> nbt){
        public static final Codec<Tuple> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockState.CODEC.fieldOf("block").forGetter(Tuple::block),
                BlockPos.CODEC.fieldOf("position").forGetter(Tuple::position),
                CompoundTag.CODEC.optionalFieldOf("nbt").forGetter(Tuple::nbt)
        ).apply(instance, Tuple::new));
    }

    public static final Codec<StructureStaffComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Tuple.CODEC.listOf().fieldOf("blocks").forGetter(StructureStaffComponent::blocks),
                    Codec.INT.fieldOf("maxx").forGetter(StructureStaffComponent::maxx),
                    Codec.INT.fieldOf("maxy").forGetter(StructureStaffComponent::maxy),
                    Codec.INT.fieldOf("maxz").forGetter(StructureStaffComponent::maxz)
                    ).apply(instance, StructureStaffComponent::new)
            );


    public static final StreamCodec<? super RegistryFriendlyByteBuf, StructureStaffComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    @Override
    public @NotNull Codec<StructureStaffComponent> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, StructureStaffComponent> streamCodec() {
        return STREAM_CODEC;
    }
}
