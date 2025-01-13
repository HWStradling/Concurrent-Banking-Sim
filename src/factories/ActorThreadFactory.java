package factories;

public class ActorThreadFactory {

    public Thread[] generateBankAccounts(int count) {

        Thread[] threadArray = new Thread[count];
        for (Thread thread:threadArray){
            thread = new Thread(  () -> {
                Actor threadActor = new Actor();
            });
        }
        return threadArray;
    }
}
