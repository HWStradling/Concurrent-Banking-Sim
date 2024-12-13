package transaction;

import accounts.BankAccount;
import records.RTransaction;

import java.util.Optional;

public abstract class Transaction {
    RTransaction transactionRecord;

    // todo transaction methods. lookup strategy pattern for abstract classes to use interface

    public boolean transaction(BankAccount originAccount, BankAccount destinationAccount, long transactionAmount){
        originAccount.acquireTransactionalLock();
        destinationAccount.acquireTransactionalLock();
        try{
            if(originAccount.checkBalance() - transactionAmount > 0){
                Optional.of(originAccount.withdraw(transactionAmount))
                        .filter(Boolean::booleanValue)
                        .ifPresentOrElse(success -> logTransaction(),
                                this::handleFailure);

                Optional.of(destinationAccount.deposit(transactionAmount))
                        .filter(Boolean::booleanValue)
                        .ifPresentOrElse(success -> logTransaction(),
                                this::handleFailure);
            }
        } catch (Exception e){
        //todo finish
        } finally {
            originAccount.releaseCompoundOperationLock();
            destinationAccount.releaseCompoundOperationLock();
        }

    return true;
    }

    private void handleFailure() {
    }

    private void logTransaction() {

    }
}
