package id_generator;

public abstract class IDGenerator { // done.
    protected abstract long generateID();
    protected abstract boolean putID(long key, Object obj); // todo find better way than object
    protected abstract boolean hasID(long key);
    protected abstract Object getValueByID(long ID);

    public  boolean updateValue(long key, Object newValue){
        if (hasID(key)) {
            putID(key, newValue);
            return true;
        }
        return false;
    };



}
