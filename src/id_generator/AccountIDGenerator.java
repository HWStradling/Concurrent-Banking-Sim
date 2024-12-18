package id_generator;

import accounts.BankAccount;
import static_collections.AccountCollection;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread-safe account id generator using an atomic long, stored in a concurrent hashmap.
 */
public class AccountIDGenerator extends IDGenerator { //done.
    static final AtomicLong idGenerator = new AtomicLong();

    /**
     * Method to generate a unique account ID using atomic long then store it in the static account collection (thread-safe).
     * @param bankAccount an object that extends BankAccount.
     * @return the generated id.
     */
    @Override
    public long generateAndStoreID(Object bankAccount) {
        long newKey = idGenerator.incrementAndGet();
        AccountCollection.put(newKey, (BankAccount) bankAccount);
        return newKey;
    }
}
