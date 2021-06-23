package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/*
Future:
-------
1. Future is an interface and introduced in Java 5. It is useful when working with asynchronous calls
    and concurrent processing. 

2. Future represents a future result of an asynchronous computation. A result that will eventually 
    appear in the Future after the processing is complete. Long running methods are good candidates
    for asynchronous processing and the Future interface.   
    
3. Future.isDone(): tells us if the executor has finished processing the task. If the task is 
    completed, it will return true otherwise, it returns false.    

4. Future.get(): It returns actual result. But this this method blocks the execution until the
    task is complete.

5. Future.cancel(true): to tell the executor to stop the operation and interrupt its underlying thread.  

Parallel Streams:
-----------------
1. Java 8 introduced concept of Streams as an efficient way of carrying out bulk operations on data. 
    Parallel Streams can be obtained in environments that support concurrency. 

2. These streams can provide improved performance but at the cost of multi-threading overhead.    
*/

public class FuturesAndParallelStreams {
    
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        simulateFutures();
        simulateParallelStreams();
    }

    /**
     * Simulates Futures
     */
    static void simulateFutures() throws InterruptedException, ExecutionException {
        Future<Integer> future = calculate(43);
        while(!future.isDone()) {
            System.out.println("Calculating...");
            TimeUnit.MILLISECONDS.sleep(300);
        }
        Integer result = future.get();
        System.out.println("Result: " + result);
    }

    /**
     * Simulates parallel streams
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void simulateParallelStreams() throws InterruptedException, ExecutionException {
        long first = 1, last = 1_000;

        List<Long> aList = LongStream.rangeClosed(first, last).boxed().collect(Collectors.toList());
        ForkJoinPool pool = new ForkJoinPool(4);
        try {
            long total = pool.submit(() -> aList.parallelStream().reduce(0L, Long::sum)).get();
            System.out.println("Total: " + total);
        }
        finally {
            pool.shutdown();
        }
    }

    static Future<Integer> calculate(Integer value) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        
        return service.submit(() -> { 
            TimeUnit.MILLISECONDS.sleep(499);
            return value * value;
        });
    }
}