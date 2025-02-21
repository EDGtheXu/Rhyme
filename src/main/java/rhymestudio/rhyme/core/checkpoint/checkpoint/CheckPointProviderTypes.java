package rhymestudio.rhyme.core.checkpoint.checkpoint;

import com.mojang.serialization.MapCodec;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.ModRegistry;

import java.util.function.Supplier;

/**
 * 注册关卡编解码器类型
 */
public class CheckPointProviderTypes {
    public static final ModRegistry.CheckPointProviders ENTITY_TYPE_GROUP_PROVIDERS = ModRegistry.CheckPointProviders.create(Rhyme.MODID);

    public static final Supplier<CheckPointProvider> SIMPLE_CHECKPOINT_PROVIDER = register("simple_checkpoint", CheckPoint.MAP_CODEC);


    public static Supplier<CheckPointProvider> register(String name, MapCodec<? extends ICheckPoint<?>> codec) {
        return ENTITY_TYPE_GROUP_PROVIDERS.register(name, ()->new CheckPointProvider(codec));
    }
}
