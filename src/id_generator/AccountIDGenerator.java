package id_generator;

import accounts.BankAccount;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread-safe account id generator using an atomic long, stored in a concurrent hashmap.
 */
public class AccountIDGenerator extends IDGenerator { //done.
    static final Map<Long, BankAccount> idMap = new ConcurrentHashMap<>(); // stores all created bank accounts with ids.
    static final AtomicLong idGenerator = new AtomicLong();

    /**
     * Method to generate and return a new AccountID using atomic long (thread-safe).
     * @return the generated id.
     */
    @Override
    public long generateID() {
        return idGenerator.incrementAndGet();
    }

    @Override
    public boolean putID(long key, Object bankAccount) {
        try {
            idMap.put(key, (BankAccount) bankAccount);
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
    protected BankAccount getValueByID(long ID) {
        return idMap.get(ID);
    }
}
