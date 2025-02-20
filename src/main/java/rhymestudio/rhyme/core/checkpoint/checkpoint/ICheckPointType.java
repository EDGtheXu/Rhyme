package rhymestudio.rhyme.core.checkpoint.checkpoint;

/**
 * 关卡类型
 * @param <T>
 */
public interface ICheckPointType<T extends ICheckPoint<?>> {

    String name();

    record SimpleCheckPointType<T extends ICheckPoint<?>>(String name) implements ICheckPointType<T> {

    }

}
