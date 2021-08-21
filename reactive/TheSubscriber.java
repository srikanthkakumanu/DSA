package reactive;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class TheSubscriber implements Subscriber<Employee> {

    private Subscription subscription;
    private int counter = 0;

    @Override
    public void onComplete() {
        System.out.println("All processing is done.");
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Exception caught at TheSubscriber: " + throwable.toString());
    }

    @Override
    public void onNext(Employee item) {
        System.out.println("Processing Employee " + item);
		counter++;
		// We can use Subscription cancel method to stop receiving message in subscriber. 
		// Note that if we cancel the subscription, then subscriber will not receive onComplete or onError signal.
		// if(counter == 3) {
		// 	this.subscription.cancel();
		// 	return;
		// }
		this.subscription.request(1);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        this.subscription = subscription;
        this.subscription.request(1); // requesting data from publisher
        System.out.println("onSubscribe(): requested 1 item");
    }

    public int getCounter() { return this.counter; }    
}
