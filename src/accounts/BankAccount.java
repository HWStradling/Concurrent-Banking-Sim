package accounts;
import id_generator.AccountIDGenerator;
import users.User;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BankAccount { //done.
    private float balance;
    private long accountID;

    private User accountOwner ;

    // technically thread-safe as won't throw errors if modified while iterating through.
    private List<User> whiteList = new CopyOnWriteArrayList<User>(); // efficient for few writes frequent reads.

    public  BankAccount(User accountOwner){
        createAccount(accountOwner);
    }

    public boolean createAccount(User accountOwner){
        try {
            this.accountOwner = accountOwner;
            balance = 0;
            AccountIDGenerator idGen = new AccountIDGenerator();
            accountID = idGen.generateID();
            idGen.putID(accountID, this);
            whiteList.add(accountOwner);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean whiteListHasID(User user) {
        // can incorrectly return if whitelist changed after if statement. non issue.
        if (whiteList.contains(user)){
            return true;
        }
        return false;
    }

    public synchronized boolean addToWhitelist(User user) { //  synchronized to atomize compound actions on whitelist.
        if (!whiteList.contains(user)){
            whiteList.add(user);
            return true;
        }
        return true;
    }
}
