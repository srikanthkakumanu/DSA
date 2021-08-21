package reactive;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

public class ThePublisher {
    public static void main(String[] args) throws InterruptedException {
        publishWithOutProcessor();
        publishWithProcessor();
    }

    /**
     * Simple publisher without using processor
     * @throws InterruptedException
     */
    private static void publishWithOutProcessor() throws InterruptedException {
        // Create publisher
        SubmissionPublisher<Employee> pub = new SubmissionPublisher<>();
        // register subscriber
        TheSubscriber sub = new TheSubscriber();
        pub.subscribe(sub);

        List<Employee> emps = populateEmployees();

        // publish items
        System.out.println("Publishing items to subscriber");
        emps.stream().forEach(item -> pub.submit(item));

        // logic to wait till processing of all messages are over
		while (emps.size() != sub.getCounter()) 
			TimeUnit.MILLISECONDS.sleep(10);
		
        pub.close();
        System.out.println("Publisher completed");
    }

    /**
     * Publisher with Processor i.e. transformation in between
     * @throws InterruptedException
     */
    private static void publishWithProcessor() throws InterruptedException {
        // Create End Publisher
		SubmissionPublisher<Employee> pub = new SubmissionPublisher<>();

		// Create Processor
		TheProcessor transformProcessor = new TheProcessor(s -> {
			return new Consultant(s.getId(), s.getId() + 100, s.getName());
		});

		//Create End Subscriber
		ConsultantSubscriber subs = new ConsultantSubscriber();

		//Create chain of publisher, processor and subscriber
		pub.subscribe(transformProcessor); // publisher to processor
		transformProcessor.subscribe(subs); // processor to subscriber

		List<Employee> emps = populateEmployees();

		// Publish items
		System.out.println("Publishing Items to ConsultantSubscriber");
		emps.stream().forEach(i -> pub.submit(i));

		// Logic to wait for messages processing to finish
		while (emps.size() != subs.getCounter()) {
			TimeUnit.MILLISECONDS.sleep(10);
		}

		// Closing publishers
		pub.close();
		transformProcessor.close();

		System.out.println("Publisher completed");
    }

    /**
     * Simple utility method to populate list of employees
     * @return emps list of employees
     */
    private static List<Employee> populateEmployees() {
        Employee e1 = new Employee(1, "Dave");
		Employee e2 = new Employee(2, "Ross");
		Employee e3 = new Employee(3, "Eugene");
		Employee e4 = new Employee(4, "Michelle");
		Employee e5 = new Employee(5, "Katherine");
		
		List<Employee> emps = new ArrayList<>();
		emps.add(e1);
		emps.add(e2);
		emps.add(e3);
		emps.add(e4);
		emps.add(e5);
		
		return emps;
    }
}
