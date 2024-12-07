package id_generator;

import records.RTransaction;
import transaction.Transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionIDGenerator extends IDGenerator { // done.
    static final Map<Long, Transaction> idMap = new ConcurrentHashMap<>();
    static final AtomicLong idGenerator = new AtomicLong();
    @Override
    public long generateID() {
        return idGenerator.incrementAndGet();
    }
    @Override
    public boolean putID(long key, Object transaction) {
        try {
            idMap.put(key,(Transaction) transaction);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public boolean hasID(long key) {
        return idMap.containsKey(key);
    }

    @Override
    protected Transaction getValueByID(long ID) {
        return idMap.get(ID);
    }
}

