package accounts;

import users.User;
public class PersonalAccount extends BankAccount {
    public PersonalAccount(User accountOwner) {
        super(accountOwner);
    }
    @Override
    public synchronized boolean addToWhitelist(User user){
        return false;
        //not allowed to add extra users for personal account
    }
}
