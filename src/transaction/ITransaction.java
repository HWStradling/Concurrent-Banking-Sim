package transaction;

import accounts.BankAccount;

public interface ITransaction {
    public boolean transaction(long originAccountID, long destinationAccountID, long transactionAmount);
}
