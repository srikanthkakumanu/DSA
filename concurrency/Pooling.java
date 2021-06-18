package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 1. Thread pooling represents group of threads that are waiting for
 *      the job and re-use many times. Thread pooling can be achieved by
 *      Executor framework(built-in thread pool framework)
 * 
 * 2. It gives 'better performance' as it saves time because there is
 *      no need to create new thread.
 * 3. It solves the problem of thread cycle overhead and resource thrashing.
 * 
 * 4. Executor: interface for executing/launching new tasks(thread - which 
 *      implements Runnable). Using Executor interface, we can decouple the
 *      task execution flow from the actual task execution mechanism.
 *      Executor does not 'strictly' require the task execution to be asynchronous.
 * 
 * 5. ExecutorService: sub-interface for Executor, that simplifies running 
 *      tasks in asynchronous mode. It is a complete solution for asynchronous
 *      processing. It manages an 'In-Memory' queue and schedules submitted 
 *      tasks based on thread availability. It automatically provides a pool of threads
 *      and an API for assigning tasks to it. It contains features that help
 *      manage life cycle, both of the individual tasks and executor itself.
 * 
 * 6. ScheduledExecutorService: sub-interface for ExecutorService, supports Future<T>
 *      and/or periodic execution of tasks.
 * 
 * 7. ThreadPoolExecutor & ScheduledThreadPoolExecutor: ThreadPoolExecutor consist of 
 *      Task Queue(BlockingQueue) and Thread Pool(Set of Worker) which have better 
 *      performance and API to handle async tasks.
 * 
 * 8. Executors: Executors is a helper class that contains several methods for the 
 *      creation of pre-configured thread pool instances. It contains Factory and 
 *      helper methods for Executor, ExecutorService, ScheduledExecutorService, 
 *      ThreadFactory and Callable classes/interfaces.
 * 
 * 9. Future: Future is used to represent the result of an asynchronous operation.

 * 10. FutureTask: FutureTask is base concrete implementation of Future interface and
 *      provides asynchronous processing. It contains the methods to start and cancel
 *      a task and also methods that can return the state of the FutureTask as whether
 *      it’s completed or cancelled. We need a callable object to create a future task
 *      and then we can use Java Thread Pool Executor to process these asynchronously.
 *      i.e. FutureTask requires a Callable object. FutureTask comes handy when we want
 *      to override some of Future interface methods and don’t want to implement every 
 *      method of Future interface.
 * 
 * 11. Thread pool tuning: The optimum size of the thread pool depends on the number 
 *      of processors available and the nature of the tasks. On a N processor system 
 *      for a queue of only computation type processes, 
 *      N*(1+W/S) for maximum efficiency.
 *      N or N+1: maximum thread pool size.
 *      W: ratio of waiting time.
 *      S: service time for a request.
 * 
 * 12. The shutdown() method doesn't cause immediate destruction of the ExecutorService. 
 *      It will make the ExecutorService stop accepting new tasks and shut down after all 
 *      running threads finish their current work. But shutdownNow() tries to destroy 
 *      the ExecutorService immediately, but it doesn't guarantee that all the running 
 *      threads will be stopped at the same time.
 * 
 * 13. Executor framework gives the developer the ability to control the number of generated
 *      threads and the granularity of tasks that should be run by separate threads. The best
 *      use case for ExecutorService is the processing of independent tasks, such as transactions
 *      or requests according to the scheme “one thread for one task.” But fork/join framework, 
 *      Despite the simplicity and frequent performance gains associated with fork/join, it 
 *      reduces developer control over concurrent execution.
 * 
 * Benefits:
 * ---------
 * 1. Thread Pooling reduces response time by avoiding thread creation i.e. 
 *      thread cycle overhead during request or task processing.
 * 
 * 2. Thread Pooling allows you to change your execution policy as you need.
 *      You can go from single thread to multiple thread by just replacing 
 *      ExecutorService implementation. 
 * 
 * 3. Thread Pooling in Java application increases stability of system by 
 *      creating a configured number of threads decided based on system load 
 *      and available resource.
 * 
 * 4. Thread Pooling frees application developer from thread management stuff
 *      and allows to focus on business logic. 
 * 
 * Risks: 
 * ------
 * 1. Deadlock: thread pools introduce another case of deadlock, one in which 
 *      all the executing threads are waiting for the results from the blocked
 *      threads waiting in the queue due to the unavailability of threads for 
 *      execution.
 * 
 * 2. Thread leakage: Thread Leakage occurs if a thread is removed from the pool
 *      to execute a task but not returned to it when the task completed. As an example,
 *      if the thread throws an exception and pool class does not catch this exception,
 *      then the thread will simply exit, reducing the size of the thread pool by one.
 *      If this repeats many times, then the pool would eventually become empty and no
 *      threads would be available to execute other requests. 
 * 
 * 3. Resource Thrashing: If the thread pool size is very large then time is wasted
 *      in context switching between threads. Having more threads than the optimal 
 *      number may cause starvation problem leading to resource thrashing.
 * 
 */
public class Pooling {
    public static void main(String[] args) {
        executorServiceRunnable();
        executorServiceCallable();
        executorServiceCallableWithFutureTask();
    }

    static void executorServiceRunnable() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        Runnable runnableTask = () -> {
            System.out.println("from RunnableTask");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Exception caught at RunnableTask: " + e.getMessage());
            }
        };
        executor.execute(runnableTask);
        // for(int counter = 1; counter <= 5; counter++) {
        //     Runnable runner = new Runner("Srikanth", 2000L);
        //     executor.execute(runner);
        // }
        executor.shutdown();
        // while(!executor.isTerminated()) {}
    }

    static void executorServiceCallable() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        Callable<String> callableTask = () -> {
            System.out.println("from CallableTask");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Exception caught at CallableTask: " + e.getMessage());
            }
            return "from CallableTask";
        };

        executor.submit(callableTask);

        // for(int counter = 1; counter <= 5; counter++) {
        //     Future<String> future = executor.submit(() -> {
        //         // lambda implementing call() method of Callable interface
        //         String message = "Srikanth Callable!";
        //         Long sleep = 599L;
        //         System.out.println(Thread.currentThread().getName() + " (start) Messgae: " + message);
        //         try {
        //             Thread.sleep(sleep);
        //         } catch (InterruptedException e) {
        //             System.out.println("Exception caught at executorServiceCallable(): " + e.getMessage());
        //         }
        //         System.out.println(Thread.currentThread().getName() + " (end)");
        //         return "Done!";
        //     }
        //     );
        //     try {
        //         System.out.println("Result of Future get(): " + future.get());
        //     } catch (InterruptedException | ExecutionException | CancellationException e) {
        //       System.out.println("Exception caught at future.get(): " + e.getMessage());  
        //     } 
        // }
        executor.shutdown();
        // while(!executor.isTerminated()) {}
    }

    /** 
     * Executor Framework with FutureTask
     */
    static void executorServiceCallableWithFutureTask() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        Callable<String> callableTask = () -> {
            System.out.println("from CallableTask");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Exception caught at CallableTask: " + e.getMessage());
            }
            return "from CallableTask";
        };

        FutureTask<String> futureTask = new FutureTask<>(callableTask);
        executor.execute(futureTask);
        executor.shutdown();
    }
}
