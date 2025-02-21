package rhymestudio.rhyme.core.checkpoint.checkpoint;

import net.minecraft.resources.ResourceLocation;

/**
 * 关卡类型
 * @param <T>
 */
public interface ICheckPointType<T extends ICheckPoint<?>> {


    ResourceLocation resource();

    record SimpleCheckPointType<T extends ICheckPoint<?>>(ResourceLocation resource) implements ICheckPointType<T> {

    }

}
