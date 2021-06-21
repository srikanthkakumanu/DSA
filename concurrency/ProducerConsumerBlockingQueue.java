package concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 1. In computing, 'bounded-buffer' or 'producer-consumer' problem is a classic example of 
 *      multi-process synchronization problem. The problem describes two processes, producer
 *      and consumer, which share a common, fixed-size buffer used as a queue. 
 * 
 * 2. The producer's job is to generate data, put it into buffer and start again.
 * 
 * 3. At the same time, consumer is consuming the data (i.e. removing it from buffer), one 
 *      piece at a time.
 * 
 * 4. Before Java 5, producer-consumer problem can be solved using wait() and notify() 
 *      methods but introduction of BlockingQueue has made it very easy.
 * 
 * 5. BlockingQueue that supports operations that wait for the Queue to become non-empty
 *      when retrieving and removing an element and wait for space to become available in
 *      the Queue when adding an element.
 * 
 * 6. BlockingQueue doesn't accept NULL values and throws NullPointerException if tried.
 * 
 * 7. BlockingQueue is thread-safe and all queuing methods are atomic in nature, it uses
 *      internal locks or other forms of concurrency control.
 * 
 * 8. We don’t need to worry about waiting for the space to be available for producer or
 *      object to be available for consumer in BlockingQueue because it’s handled by 
 *      implementation classes of BlockingQueue. BlockingQueue implementations are
 *      ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue, DelayQueue, 
 *      SynchronousQueue etc.
 * 
 * 9. There are two types of BlockingQueue: unbounded and bounded.
 *      unbounded: can grow almost indefinitely
 *      bounded: with maximum capacity defined
 * 
 * unbounded blocking queue:
 * ------------------------- 
 * Unbounded blocking queues can grow almost indefinitely. The capacity of blocking queue 
 *  will be set to MAX_VALUE and all operations that add an element to the unbounded queue
 *  will never block, thus it could grow to a very large size. The most important thing when
 *  designing a producer-consumer program using unbounded BlockingQueue is that consumers 
 *  should be able to consume messages as quickly as producers are adding messages to the queue.
 *  Otherwise, the memory could fill up and we would get an OutOfMemory exception.
 * 
 * bounded blocking queue:
 * -----------------------
 * We can create such queues with maximum capacity defined. If we have a blockingQueue that has 
 *  a capacity equal to 10, It means that when a producer tries to add an element to an already
 *  full queue, depending on a method that was used to add it (offer(), add() or put()), it will
 *  block until space for inserting object becomes available. Otherwise, the operations will fail.
 *  Using bounded queue is a good way to design concurrent programs because when we insert an 
 *  element to an already full queue, that operations need to wait until consumers catch up and 
 *  make some space available in the queue. It gives us throttling without any effort on our part.
 * 
 */
public class ProducerConsumerBlockingQueue { 
    public static void main(String[] args) {
        //unbounded(); // Using unbounded BlockingQueue approach.
        bounded(); // Using bounded BlockingQueue approach.
    }

    static void unbounded() {
        // unbounded queue i.e. can grow almost indefinitely
        BlockingQueue<TheMessage> queue = new LinkedBlockingQueue<>(); 
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
        System.out.println("Producer and Consumer has been started");

    }

    static void bounded() {
        // bounded queue i.e. with maximum capacity defined
        BlockingQueue<TheMessage> queue = new ArrayBlockingQueue<>(10); 
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
        System.out.println("Producer and Consumer has been started");
    }
}

/**
 * Consumer: consuming messages from BlockingQueue
 */
class Consumer implements Runnable {
    private BlockingQueue<TheMessage> queue;
    
    public Consumer(BlockingQueue<TheMessage> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // consume the messages
        TheMessage message;

        try {
            while((message = queue.take()).getMessage() != "Exit") {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("Consumed " + message.getMessage());
            }
        } catch (InterruptedException e) {
            System.out.println("Exception caught at Consumer.run(): " + e.getMessage());
        }
    }
}

/**
 * Producer: producing messages into BlockingQueue
 */
class Producer implements Runnable {
    private BlockingQueue<TheMessage> queue;
    
    public Producer(BlockingQueue<TheMessage> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // produce the messages
        for(int i = 0; i <= 10; i++) {
            TheMessage message = new TheMessage("Message: " + i);
            try {
                TimeUnit.MILLISECONDS.sleep(i);
                queue.put(message);
                System.out.println("Produced " + message.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Exception caught at Producer.run(): " + e.getMessage());
            }
        }
        TheMessage message = new TheMessage("Exit");
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            System.out.println("Exception caught at Producer.queue.put(): " + e.getMessage());
        }
    }
}

class TheMessage {
    private String message;
    public TheMessage(String message) { this.message = message; }
    public String getMessage() { return message; }
}
