package rhymestudio.rhyme.core.checkpoint.checkpoint;

import com.mojang.serialization.Codec;
import rhymestudio.rhyme.core.registry.ModRegistry;

/**
 * 关卡的实现类
 * @param <T>
 */
public interface ICheckPoint<T> {

    CheckPointProvider getCodec();

    Codec<ICheckPoint<?>> TYPED_CODEC = ModRegistry.CHECK_POINT_PROVIDER_REGISTRY
            .byNameCodec()
            .dispatch(ICheckPoint::getCodec, CheckPointProvider::codec);

}
