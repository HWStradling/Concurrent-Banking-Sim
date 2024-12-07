package id_generator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AccountIDGenerator extends IDGenerator {
    static final Map<Long, String> idMap = new ConcurrentHashMap<>();
    static final AtomicLong idGenerator = new AtomicLong();
    @Override
    protected long generateID() {
        return idGenerator.incrementAndGet();
    }
    @Override
    protected boolean putID(long key, Object value) {
        try {
            idMap.put(key,(String) value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    protected boolean hasID(long key) {
        return idMap.containsKey(key);
    }
}
