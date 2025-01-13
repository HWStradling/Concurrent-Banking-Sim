package factories;

import accounts.BankAccount;
import accounts.PersonalAccount;
import com.github.javafaker.Faker;
import users.User;

public class AccountFactory {
    public void generateBankAccounts(int count) {
        Faker faker = new Faker();
        for (int i = 0; i < count; i++) {
             new PersonalAccount(new User(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.date().birthday().toString()));
        }
    }
}
