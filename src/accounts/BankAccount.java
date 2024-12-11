package accounts;
import id_generator.AccountIDGenerator;
import users.User;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BankAccount {
    private long balance; // todo make a custom class with throws to trigger rollbacks in withdraw maybe
    private long accountID;
    private User accountOwner ;
    private ReentrantLock transactionalLock;

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
        accountID = idGen.generateID();
        idGen.putID(accountID, this);
        whiteList.add(accountOwner);
    }

    /**
     * Thread-safe method to withdraw from balance.
     * Should be called in a try-finally block to ensure ReentrantLock unlocking.
     * @param amount to be withdrawn.
     * @param transactionalLock Reentrant lock that belongs to the account/instance
     *                          the method is being called from, get using acquireTransactionalLock().
     * @return success boolean.
     */
    public boolean withdraw(long amount, ReentrantLock transactionalLock) {
        try {
            validateLock(transactionalLock);
            if (balance - amount > 0){
                balance+= -amount;
                return true;
            }
            return false;
        } catch (Exception e){
            handleTransactionExceptions(e);
            return false;
        }
    }

    /**
     * Thread-safe method to deposit/add to balance.
     * Should be called in a try-finally block to ensure ReentrantLock unlocking.
     * @param amount to deposit.
     * @param transactionalLock Reentrant lock that belongs to the account/instance
     *                          the method is being called from, get using acquireTransactionalLock().
     * @return success boolean.
     */
    public boolean deposit(long amount, ReentrantLock transactionalLock){
        try {
            validateLock(transactionalLock);
            balance+= amount;
            return true;
        } catch (Exception e){
            handleTransactionExceptions(e);
            return false;
        }
    }

    /**
     * Thread safe method to check balance.
     * Should be called in a try-finally block to ensure ReentrantLock unlocking.
     * @param transactionalLock Reentrant lock that belongs to the account/instance
     *                          the method is being called from, get using acquireTransactionalLock().
     * @return account balance or 0 if exception occurs.
     */
    public long checkBalance( ReentrantLock transactionalLock) {
        try {
            validateLock(transactionalLock);
            return balance;
        } catch (Exception e){
            handleTransactionExceptions(e);
            return 0;
        }
    }

    /**
     * Ensures the lock object passed to it is held by the thread and belongs to its instance.
     * @param transactionalLock to validate.
     * @throws IllegalStateException if transactionLock is not held by current thread.
     * @throws InvalidParameterException transactionLock was not acquired from this instance.
     */
    private void validateLock(ReentrantLock transactionalLock) throws IllegalStateException, InvalidParameterException {
        if (transactionalLock != this.transactionalLock  ){
           throw new InvalidParameterException(" this instances transactionLock was not acquired from this instance!!!");
        } else if(!transactionalLock.isHeldByCurrentThread()){
            throw new IllegalStateException(" this instances transactionLock is not held by current thread !!!");
        }
    }

    /**
     * Handles exceptions thrown/generated during transactions.
     * Todo implement transaction rollback functionality
     * @param e exception from a transaction.
     */
    private void handleTransactionExceptions(Exception e) {

        switch (e ){ // propagates certain exceptions caused by incorrect code logic.
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
        }
        return true;
    }
    public ReentrantLock acquireTransactionalLock() {
        transactionalLock.lock();
        return transactionalLock;
    }
    public void releaseCompoundOperationLock(ReentrantLock transactionalLock) {
        transactionalLock.unlock();
    }
}
