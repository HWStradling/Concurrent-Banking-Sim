package id_generator;

public abstract class IDGenerator {
    public long getID(String value){
        long key = generateID();
        putID(key, value);
        return key;
    }

    protected abstract long generateID();
    protected abstract boolean putID(long key, Object value); // todo find better way than object
    protected abstract boolean hasID(long key);

    public  boolean updateValue(long key, Object newValue){
        if (hasID(key)) {
            putID(key, newValue);
            return true;
        }
        return false;
    };



}
