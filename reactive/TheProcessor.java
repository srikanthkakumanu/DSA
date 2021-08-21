package reactive;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class TheProcessor extends SubmissionPublisher<Consultant> implements Processor<Employee, Consultant> {

	private Subscription subscription;
	private Function<Employee, Consultant> function;
	
	public TheProcessor(Function<Employee, Consultant> function) {  
	    super();  
	    this.function = function;  
	  }  
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Employee emp) {
		submit((Consultant) function.apply(emp));  
	    subscription.request(1);  
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("Done");
	}

}