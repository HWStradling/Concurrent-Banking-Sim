package static_collections;

import transaction.InternalTransaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionCollection {
    private static Map<Long, InternalTransaction> idMap = new ConcurrentHashMap<>();

    /**
     *
     * @param key
     * @param transaction
     * @return
     */
    public static boolean put(long key, InternalTransaction transaction) {
        try {
            idMap.put(key, transaction);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean hasID(long key) {
        return idMap.containsKey(key);
    }
    public static InternalTransaction getTransaction(long ID) {
        return idMap.get(ID);

    }
}
