package factories;

import accounts.BankAccount;

public class AccountFactory {
    public BankAccount[] getBankAccounts(int length){
        BankAccount[] accountsArray = new BankAccount[length];

        return accountsArray;
    }
}
