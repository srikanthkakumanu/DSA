package concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
 *      ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue etc.
 * 
 */
public class ProducerConsumerBlockingQueue {
    public static void main(String[] args) {
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
