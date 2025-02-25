package rhymestudio.rhyme.core.checkpoint.checkpoint;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.core.registry.ModRegistry;

/**
 * 关卡的实现类
 * @param <T>
 */
public interface ICheckPoint<T extends ICheckPoint<T>>{

    /**
     * 编解码器
     */
    CheckPointProvider getCodec();

    /**
     * 关卡名称，资源位置
     */
    ResourceLocation name();

    /**
     * 关卡的索引
     */
    int index();

    ICheckPointType<T> getType();

    /**
     * 翻译键
     */
    default String getTranslatedName(){
        return "checkpoint."+name().toLanguageKey().replace("/", ".");
    }

    /**
     * 编解码器dispatcher
     */
    Codec<ICheckPoint<?>> TYPED_CODEC = ModRegistry.CHECKPOINT_PROVIDER_REGISTRY
            .byNameCodec()
            .dispatch(ICheckPoint::getCodec, CheckPointProvider::codec);

}
