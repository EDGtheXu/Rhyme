package rhymestudio.rhyme.core.checkpoint.entitygroup;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EntityType;
import rhymestudio.rhyme.core.registry.ModRegistry;

/**
 * <h1>僵尸组选择方式</h1>
 */
public interface IEntityTypeGroup {

    /**
     * 获取僵尸的类型
     * @return 僵尸的类型
     */
    EntityType<?> getType();

    /**
     * 获取编解码器
     * @return 编解码器
     */
    EntityTypeGroupProvider getCodec();

    /**
     * 抽中僵尸次数
     * @return 抽中僵尸次数
     */
    int getCount();

    Codec<IEntityTypeGroup> TYPED_CODEC = ModRegistry.ENTITY_TYPE_GROUP_PROVIDER_REGISTRY
            .byNameCodec()
            .dispatch(IEntityTypeGroup::getCodec, EntityTypeGroupProvider::codec);

    Codec<IEntityTypeGroup> CODEC = Codec.lazyInitialized(
            () -> {
                Codec<IEntityTypeGroup> codec = Codec.withAlternative(TYPED_CODEC, SingleZombie.CODEC.codec());
                return codec;
//                return Codec.either(WeightSelectedZombie.MAP_CODEC.codec(), codec)
//                        .xmap(Either::unwrap, group -> {
//                            if(group instanceof SingleZombie constantvalue){
//                                return Either.right(constantvalue);
//                            }
//                            else if(group instanceof WeightSelectedZombie weightselectedzombie){
//                                return  Either.left(weightselectedzombie);
//                            }
//                            return null;
//                        });
            }
    );
}
