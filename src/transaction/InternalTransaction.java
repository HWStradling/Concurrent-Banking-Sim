package transaction;

import accounts.BankAccount;
import id_generator.TransactionIDGenerator;
import static_collections.AccountCollection;

public class InternalTransaction implements ITransaction {
    BankAccount originAccount;
    BankAccount destinationAccount;
    long transactionAmount;
    long transactionID;

    // todo lookup strategy pattern for abstract classes to use interface

    public boolean transaction(long originAccountID, long destinationAccountID, long transactionAmount){
        TransactionIDGenerator IDGen = new TransactionIDGenerator();
        transactionID = IDGen.generateAndStoreID(this);
        this.originAccount = AccountCollection.getAccount(originAccountID);
        this.destinationAccount = AccountCollection.getAccount(destinationAccountID);
        this.transactionAmount = transactionAmount;
        // takes both accounts locks to ensure atomicity for compound operations on their balances.
        originAccount.acquireTransactionalLock();
        destinationAccount.acquireTransactionalLock();
        try{
            if(originAccount.checkBalance() - transactionAmount >= 0) {
                boolean t1 = originAccount.withdraw(transactionAmount);
                boolean t2 = destinationAccount.deposit(transactionAmount);
                return t1 && t2;
            }
            return false;
        } catch (Exception e){
        return false;
        } finally {
            originAccount.releaseCompoundOperationLock();
            destinationAccount.releaseCompoundOperationLock();
        }

    }
}
