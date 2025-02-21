package rhymestudio.rhyme.core.checkpoint.checkpoint;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.ModRegistry;

/**
 * 关卡的实现类
 * @param <T>
 */
public interface ICheckPoint<T extends ICheckPoint<T>>{

    CheckPointProvider getCodec();

    ResourceLocation name();

    ICheckPointType<T> getType();

    default String getTranslatedName(){
        return name().toLanguageKey().replace("/", ".");
    }

    Codec<ICheckPoint<?>> TYPED_CODEC = ModRegistry.CHECK_POINT_PROVIDER_REGISTRY
            .byNameCodec()
            .dispatch(ICheckPoint::getCodec, CheckPointProvider::codec);

}
