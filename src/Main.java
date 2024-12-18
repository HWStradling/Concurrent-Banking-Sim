import accounts.BankAccount;
import accounts.BusinessAccount;
import accounts.PersonalAccount;
import static_collections.AccountCollection;
import transaction.InternalTransaction;
import users.User;

public class Main {
    public static void main(String[] args) {

        User u1 = new User("harry", "stradling", "08/03/2000");
        BankAccount b1 = new PersonalAccount(u1);
        b1.deposit(500);

        User u2 = new User("tom", "maeriland", "08/03/1901");
        BankAccount b2 = new BusinessAccount(u2);
        b2.deposit(2000);
        

        Thread t3 = new Thread(() -> {
            System.out.println(new InternalTransaction().transaction(1,2,500));
        });
        Thread t4 = new Thread(() -> {
            System.out.println(new InternalTransaction().transaction(1,2,500));
        });

        Thread t5 = new Thread(() -> {
            System.out.println(AccountCollection.getAccount(1));
            System.out.println(AccountCollection.getAccount(2));

        });


        t3.start();
        t4.start();
        t5.start();
    }
    }
