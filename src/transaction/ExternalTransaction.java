package transaction;

import accounts.BankAccount;

public class ExternalTransaction implements ITransaction {

    @Override
    public boolean transaction(long originAccountID, long destinationAccountID, long transactionAmount) {
        return false;
    }
}
