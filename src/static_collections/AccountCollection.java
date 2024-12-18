package static_collections;

import accounts.BankAccount;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountCollection { //
    private static final Map<Long, BankAccount> idMap = new ConcurrentHashMap<>();

    /**
     *
     * @param key
     * @param bankAccount
     * @return
     */
    public static boolean put(long key, BankAccount bankAccount) {
        try {
            idMap.put(key, bankAccount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean hasID(long key) {
        return idMap.containsKey(key);
    }
    public static BankAccount getAccount(long ID) {
        return idMap.get(ID);
    }

    public  boolean updateAccount(long key, BankAccount newValue){
        if (hasID(key)) {
            put(key, newValue);
            return true;
        }
        return false;
    }
}

