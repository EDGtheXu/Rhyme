package rhymestudio.rhyme.core.checkpoint.entitygroup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import rhymestudio.rhyme.utils.Computer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权重选择僵尸组
 * @param typeList 抽卡池
 * @param count 抽卡数量
 */
public record WeightSelectedZombie(List<tuple> typeList, int count) implements IEntityTypeGroup {

    public record tuple(EntityType<?> type, float probability){
        static final MapCodec<tuple> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    ResourceLocation.CODEC.fieldOf("entityType").forGetter((singleZombie) -> BuiltInRegistries.ENTITY_TYPE.getKey(singleZombie.type())),
                    Codec.FLOAT.fieldOf("probability").forGetter(tuple::probability))
            .apply(instance, (type, probability) -> new tuple(BuiltInRegistries.ENTITY_TYPE.get(type), probability)));
    }

    public static MapCodec<WeightSelectedZombie> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    tuple.CODEC.codec().listOf().fieldOf("typeList").forGetter(WeightSelectedZombie::typeList),
                    Codec.INT.fieldOf("count").forGetter(WeightSelectedZombie::count))
            .apply(instance, WeightSelectedZombie::new));


    public EntityType<?> getType() {
        return Computer.getRandomByWeight(typeList.stream()
                .collect(Collectors.toMap(
                        tuple::type,
                        tuple::probability
                )));
    }

    @Override
    public EntityTypeGroupProvider getCodec() {
        return EntityTypeGroupProviderTypes.WEIGHTED_ENTITY_TYPE.get();
    }

    @Override
    public int getCount() {
        return count;
    }
}
