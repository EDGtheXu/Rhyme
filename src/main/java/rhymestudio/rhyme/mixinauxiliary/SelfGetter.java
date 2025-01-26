package rhymestudio.rhyme.mixinauxiliary;

@SuppressWarnings("unchecked")
public interface SelfGetter<T> {
    default T rhyme$self(){
        return (T) this;
    }
}
