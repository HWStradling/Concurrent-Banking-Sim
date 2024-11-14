package accounts;

import users.User;

import java.util.ArrayList;

public abstract class BankAccount {
    float balance;
    int accountNumber;

    User accountHolder;

    ArrayList<User> userWhitelist;



}
