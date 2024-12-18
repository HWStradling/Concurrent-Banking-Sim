package id_generator;

import static_collections.TransactionCollection;
import transaction.InternalTransaction;

import java.util.concurrent.atomic.AtomicLong;

public class TransactionIDGenerator extends IDGenerator { // done.

    static final AtomicLong idGenerator = new AtomicLong();
    @Override
    public long generateAndStoreID(Object transaction) {
        long newKey = idGenerator.incrementAndGet();
        TransactionCollection.put(newKey,(InternalTransaction) transaction);
        return newKey;
    }
}

