package concurrency;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Delayed;

/**
 * 1. DelayQueue is infinite size (unbounded) blocking queue of delayed elements. 
 * 
 * 2. With DelayQueue, an element can only be pulled if it's expiration time (known as user defined delay) 
 *      is completed. Hence, the topmost element (head) will have most amount of delay and it will be 
 *      pulled last. 
 * 
 * 3. When a consumer wants to take an element from the queue, it can take only when the delay for that
 *      particular element has expired.
 * 
 * 4. It’s getDelay() method return a zero or negative value which indicate that the delay has already elapsed.
 * 
 * 5. DelayQueue is a specialized PriorityQueue that orders elements based on their delay time and internally
 *      it uses RentrantLock mechanism.
 */
public class ProducerConsumerDelayQueue {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        DelayQueue<DelayedEvent> queue = new DelayQueue<>();
        AtomicInteger counter = new AtomicInteger();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
        ses.scheduleAtFixedRate(new DelayedEventProducer(queue, counter), 1, 2, TimeUnit.SECONDS);
        ses.scheduleAtFixedRate(new DelayedEventConsumer(queue), 1, 10, TimeUnit.SECONDS);
    }
}


class DelayedEventConsumer implements Runnable 
{
    private final DelayQueue<DelayedEvent> queue;
 
    public DelayedEventConsumer(DelayQueue<DelayedEvent> queue) {
        this.queue = queue;
    }
 
    @Override
    public void run() 
    {
        List<DelayedEvent> events = new ArrayList<DelayedEvent>();
        queue.drainTo(events);
        System.out.println("\n Consumer::Event processing start **********\n");
        events.stream().forEach(System.out::println);
        System.out.println("\nConsumer::Event processing end **********\n");
    }
}

class DelayedEventProducer implements Runnable {

    private final DelayQueue<DelayedEvent> queue;
    private AtomicInteger counter;
    public DelayedEventProducer(DelayQueue<DelayedEvent> queue, AtomicInteger counter) {
        this.queue = queue;
        this.counter = counter;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        int id = counter.incrementAndGet();
        DelayedEvent event = new DelayedEvent(id, "Task-" + id, now);
        System.out.println("Producer:: Added to queue :: " + event);
        queue.add(event);
    }
}

class DelayedEvent implements Delayed {
    
    private Integer id;
    private String name;
    private LocalDateTime activationTimestamp;

    public DelayedEvent(Integer id, String name, LocalDateTime activationTimestamp) {
        super();
        this.id = id;
        this.name = name;
        this.activationTimestamp = activationTimestamp;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getActivationTimestamp() {
        return activationTimestamp;
    }
    public void setActivationTimestamp(LocalDateTime activationTimestamp) {
        this.activationTimestamp = activationTimestamp;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        LocalDateTime now = LocalDateTime.now();
        long diff = now.until(activationTimestamp, ChronoUnit.MILLIS);
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    public int compareTo(Delayed that) {
        long result = this.getDelay(TimeUnit.NANOSECONDS) - that.getDelay(TimeUnit.NANOSECONDS);
        
        if(result < 0)
            return -1;
        else if(result > 0)
            return 1;

        return 0;
    }
    
    @Override
    public String toString() {
        return "DelayedEvent [id=" + id + ", name=" + name + ", activationDateTime=" + activationTimestamp + "]";
    }
}
