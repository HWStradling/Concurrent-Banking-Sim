package transaction;

public class CompositeTransactionKey<AccountID> {
    private AccountID destination;
    private AccountID origin;

    public CompositeTransactionKey(AccountID origin, AccountID destination){
        this.origin = origin;
        this.destination = destination;
    }

    public AccountID getDestination() {
        return destination;
    }

    public void setDestination(AccountID destination) {
        this.destination = destination;
    }

    public AccountID getOrigin() {
        return origin;
    }

    public void setOrigin(AccountID origin) {
        this.origin = origin;
    }
}
