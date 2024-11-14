package transaction;

import id_generator.TransactionIDGenerator;

public abstract class Transaction {
    CompositeTransactionKey transactionKey;
    long transactionID;
}
