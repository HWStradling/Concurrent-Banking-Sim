package accounts;

import users.User;

public class SavingsAccount extends BankAccount {
    public SavingsAccount(User accountOwner) {
        super(accountOwner);
    }
    @Override
    public synchronized boolean addToWhitelist(User user){
        return false;
        //not allowed to add extra users for personal account
    }

    //todo interest implementation (time based)
}
