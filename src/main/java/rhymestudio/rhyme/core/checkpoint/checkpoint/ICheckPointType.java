package rhymestudio.rhyme.core.checkpoint.checkpoint;

import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.Rhyme;

/**
 * 关卡类型
 * @param <T>
 */
public interface ICheckPointType<T extends ICheckPoint<?>> {

    ResourceLocation resource();

    default String getTranslationKey(){
        return "checkpoint.type." + Rhyme.toLang(resource());
    }

    record SimpleCheckPointType<T extends ICheckPoint<?>>(ResourceLocation resource) implements ICheckPointType<T> {

    }

}
