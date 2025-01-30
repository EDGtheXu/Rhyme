package rhymestudio.rhyme.core.dataSaver.dataComponent;

public abstract class AbstractDataComponent<T> implements IDataComponentType<T> {
    protected boolean isValid = true;
    public boolean isValid() {
        return isValid;
    }
    public void setInvalid() {
        isValid = false;
    }
}
