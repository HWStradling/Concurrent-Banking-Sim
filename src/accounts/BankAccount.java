package accounts;
import id_generator.AccountIDGenerator;
import users.User;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BankAccount {
    private long balance;
    private long accountID;
    private User accountOwner ;
    private final ReentrantLock transactionalLock = new ReentrantLock();

    // technically thread-safe as won't throw errors if modified while iterating through.
    private List<User> whiteList = new CopyOnWriteArrayList<>(); // efficient for few writes frequent reads.

    /**
     * Superclass constructor to be used by subclasses.
     * @param accountOwner the owner of the account.
     */
    public  BankAccount(User accountOwner){
        this.accountOwner = accountOwner;
        balance = 0;
        AccountIDGenerator idGen = new AccountIDGenerator();
        accountID = idGen.generateAndStoreID(this);
        whiteList.add(accountOwner);
    }

    /**
     * Thread-safe method to withdraw from balance.
     * uses a reentrant lock.
     * @param amount to be withdrawn.
     * @return true or false success boolean.
     */
    public boolean withdraw(long amount) {
        transactionalLock.lock();
        try {
            if (balance - amount >= 0){
                balance+= -amount;
                return true;
            }
            return false;
        } catch (Exception e){
            handleTransactionExceptions(e);
            return false;
        } finally {
            transactionalLock.unlock();
        }
    }

    /**
     * Thread-safe method to deposit/add to balance.
     * uses a reentrant lock.
     * @param amount to deposit.
     * @return true or false success boolean.
     */
    public boolean deposit(long amount){
        transactionalLock.lock();
        try {
            balance+= amount;
            return true;
        } catch (Exception e){
            handleTransactionExceptions(e);
            return false;
        } finally {
            transactionalLock.unlock();
        }
    }
    /**
     * Thread safe method to check balance.
     * Should be called in a try-finally block to ensure ReentrantLock unlocking.
     * @return account balance or 0 if exception occurs.
     */
    public long checkBalance() {
        transactionalLock.lock();
        try {
            return balance;
        } catch (Exception e){
            handleTransactionExceptions(e);
            return 0;
        } finally {
            transactionalLock.unlock();
        }
    }
    /**
     * Handles exceptions thrown/generated during transactions.
     * Todo implement transaction rollback functionality
     * @param e exception from a transaction.
     */
    private void handleTransactionExceptions(Exception e) {

        switch (e ){ // propagates certain exceptions caused by incorrect code logic. // todo change this
            case InvalidParameterException ipe -> throw ipe;
            case IllegalStateException ese -> throw ese;
            default -> System.out.println("Caught unusual exception: "+ e.getCause() + e.getMessage());
        }
        // todo rollback functionality
    }
    public boolean whiteListHasID(User user) {
        // can incorrectly return if whitelist changed after if statement. non issue.
        return whiteList.contains(user);
    }
    public synchronized boolean addToWhitelist(User user) { //  synchronized to atomize compound actions on whitelist.
        if (!whiteList.contains(user)){
            whiteList.add(user);
            return true;
        } else {
            return false;
        }
    }
    public void acquireTransactionalLock() {
        transactionalLock.lock();
    }
    public void releaseCompoundOperationLock() {
        transactionalLock.unlock();
    }
    public long getAccountID() {
        return accountID;
    }
}
