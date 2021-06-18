package concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

/**
 * 
 */
public class CountDownLatcher {

    public static void main(String[] args) throws InterruptedException {
        whenParallelProcessing_thenMainThreadWillBlockUntilCompletion();
        whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime();
    }

    static void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {
        // When parallel processing then main thread
        // will block until completion.
        List<String> outputText = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(5);

        List<Thread> workers = Stream.generate(
                                () -> new Thread(new Worker(outputText, latch)))
                                            .limit(5)
                                            .collect(toList());
        workers.forEach(Thread::start);
        latch.await();
        outputText.add("Latch released");
        outputText.stream().filter(value -> {
            if(value.contentEquals("Counted down") || value.contentEquals("Latch released"))
                return true;
            return false;
        }).forEach(value -> {
            System.out.println(value);
        });
    }

    static void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime() throws InterruptedException {
        // when doing lots of threads in parallel then start them at same time
        List<String> outputText = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);

        List<Thread> workers = Stream.generate(() -> new Thread(new WaitingWorker(
                                            outputText, readThreadCounter, 
                                            callingThreadBlocker, completedThreadCounter
                                ))).limit(5).collect(toList());
        
        workers.forEach(Thread::start);
        readThreadCounter.await();
        outputText.add("Workers ready");
        callingThreadBlocker.countDown();
        completedThreadCounter.await();
        outputText.add("Workers complete");
        
        outputText.stream().filter(value -> {
            if(value.contentEquals("Workers ready") || value.contentEquals("Counted down") || value.contentEquals("Workers complete"))
                return true;
            return false;
        }).forEach(value -> {
            System.out.println(value);
        });
        
    }
}

class WaitingWorker implements Runnable {
    private List<String> outputText;
    private CountDownLatch readThreadCounter;
    private CountDownLatch callingThreadBlocker;
    private CountDownLatch completedThreadCounter;

    public WaitingWorker(List<String> outputText, CountDownLatch readThreadCounter, CountDownLatch callingThreadBlocker,
            CountDownLatch completedThreadCounter) {
        this.outputText = outputText;
        this.readThreadCounter = readThreadCounter;
        this.callingThreadBlocker = callingThreadBlocker;
        this.completedThreadCounter = completedThreadCounter;
    }

    @Override
    public void run() {
        readThreadCounter.countDown();
        try {
            callingThreadBlocker.await();
            work();
            outputText.add("Counted down");
        } catch (InterruptedException e) {
            System.out.println("Exception caught at WaitingWorker.run(): " + e.getMessage());
        } finally {
            completedThreadCounter.countDown();
        }
    }

    public void work() {
        System.out.println("work");
    }
    
}

class Worker implements Runnable {
    private List<String> outputText;
    private CountDownLatch latch;

    public Worker(List<String> outputText, CountDownLatch latch) {
        this.outputText = outputText;
        this.latch = latch;
    }

    @Override
    public void run() {
        work();
        outputText.add("Counted down");
        latch.countDown();
    }

    public void work() {
        System.out.println("work");
    }
}
