package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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

CompletableFuture
-----------------
1. CompletableFuture class introduced in Java 8. It supports various Functional interfaces such as
    Runnable, Consumer, Supplier etc. Further Java 9 added additional functionality such as
    new Factory methods, support for delays and timeouts, improved support for subclassing. 

2. Biggest advantage of CompletableFuture is that we can combine CompletableFuture instances in a chain 
    of computation steps.

Parallel Streams:
-----------------
1. Java 8 introduced concept of Streams as an efficient way of carrying out bulk operations on data. 
    Parallel Streams can be obtained in environments that support concurrency. 

2. These streams can provide improved performance but at the cost of multi-threading overhead.    
*/

public class FuturesAndParallelStreams {
    
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //simulateFutures();
        //simulateParallelStreams();
        simulateCompletableFuture();
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
     * Simulates all possible combinations of CompletableFuture class
     */
    static void simulateCompletableFuture() throws InterruptedException, ExecutionException {
        Future<String> result = calculateAsync();
        System.out.println(result.get());

        // // if we already know the result of computation
        // Future<String> completableFuture = CompletableFuture.completedFuture("Hello");
        // System.out.println(completableFuture.get());

        // // Uses Supplier Interface get() method which returns the value
        // CompletableFuture<String> anotherCompletableFuture = CompletableFuture.supplyAsync( () -> "Hello" );
        // CompletableFuture<String> anotherFuture = anotherCompletableFuture.thenApply(value -> value + " World!");
        // System.out.println(anotherFuture.get());

        // CompletableFuture<Void> consumerFuture = anotherCompletableFuture.thenAccept(
        //                                             value -> System.out.println("Computation Returned: " + value));
        // System.out.println(consumerFuture.get());

        // CompletableFuture<Void> runnableFuture = anotherCompletableFuture.thenRun(
        //                                             () -> System.out.println("Computation Finished"));
        // System.out.println(runnableFuture.get());

        // CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
        //                                                             .thenCompose(value -> CompletableFuture.supplyAsync(() -> value + " World"));

        // CompletableFuture<String> completableFuture   = CompletableFuture.supplyAsync(() -> "Hello")
        //                                                                 .thenCombine(
        //                                                                     CompletableFuture.supplyAsync(
        //                                                                                 () -> " World"), (s1, s2) -> s1 + s2);

        // CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
        //                                             .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"),
        //                                                                                   (s1, s2) -> System.out.println(s1 + s2));                                                                                        

        // System.out.println(completableFuture.get());    

        // Running multiple Futures in parallel
        // CompletableFuture<String> future1  = CompletableFuture.supplyAsync(() -> "Hello");
        // CompletableFuture<String> future2  = CompletableFuture.supplyAsync(() -> "Beautiful");
        // CompletableFuture<String> future3  = CompletableFuture.supplyAsync(() -> "World");
        // CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
        // System.out.println(future1.isDone());
        // System.out.println(future2.isDone());
        // System.out.println(future3.isDone());        
        // System.out.println(combinedFuture.get());

        //exception handling
        // String name = null;
        // CompletableFuture<String> completableFuture  =  CompletableFuture.supplyAsync(() -> {
        //                                                         if (name == null) {
        //                                                             throw new RuntimeException("Computation error!");
        //                                                         }
        //                                                         return "Hello, " + name;
        //                                                 }).handle((s, t) -> s != null ? s : "Hello, Stranger!");
        // System.out.println(completableFuture.get());

        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));
        completableFuture.get(); // ExecutionException
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

    static Future<String> calculateAsync() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> { 
            TimeUnit.MILLISECONDS.sleep(300);
            future.complete("Hello");
            return null;
        });
        return future;
    }

    static Future<Integer> calculate(Integer value) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        
        return service.submit(() -> { 
            TimeUnit.MILLISECONDS.sleep(499);
            return value * value;
        });
    }
}