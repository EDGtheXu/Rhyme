package rhymestudio.rhyme.core.checkpoint.entitygroup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

/**
 * <h1>僵尸组</h1>
 * 记录单个僵尸组的配置
 *
 * @param type  僵尸类型
 * @param count 僵尸数量
 */
public record SingleZombie(EntityType<?> type, int count) implements IEntityTypeGroup {

    public static MapCodec<SingleZombie> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    ResourceLocation.CODEC.fieldOf("entityType").forGetter((singleZombie) -> BuiltInRegistries.ENTITY_TYPE.getKey(singleZombie.type())),
                    Codec.INT.fieldOf("count").forGetter(SingleZombie::count))
            .apply(instance, (type, count) -> new SingleZombie(BuiltInRegistries.ENTITY_TYPE.get(type), count)));


    public EntityType<?> getType() {
        return type;
    }

    @Override
    public EntityTypeGroupProvider getCodec() {
        return EntityTypeGroupProviderTypes.SINGLE_ENTITY_TYPE.get();
    }

    @Override
    public int getCount() {
        return count;
    }
}
