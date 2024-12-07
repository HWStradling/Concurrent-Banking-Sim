package records;

import accounts.BankAccount;

import java.time.Instant;

public record RTransaction(
        long transactionID,
        BankAccount origin,
        BankAccount destination,
        long amount,
        Instant timestamp,
        boolean success
        ) {
}
