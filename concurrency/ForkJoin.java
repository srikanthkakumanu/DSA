package concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

/*
The fork/join framework was introduced in Java 7. It helps to speed up parallel processing by attempting to use all 
available processor cores and it uses **Divide and conqure approach** to accomplish this.

1. This framework 'forks' recursively breaking the tasks into smaller independant tasks (until they are simple enough
    to be executed asynchronously).
2. After that, the 'join' part begins by joining all results of sub tasks recursively into a single result.

To support parallel execution, it uses a thread pool called **ForkJoinPool** which manages worker threads 
    (ForkJoinWorkerThread threads).

In Java 8, 'ForkJoinPool.commonPool()' is used to create ForkJoinPool and it guarantees resource consumption since it 
    discourages the creation of a separate thread pool per task.

In Java 7, 'ForkJoinPool pool = new ForkJoinPool(2)' is used to create ForkJoinPool. 2 means the pool has parallelism 
    level of 2, meaning that the pool will use 2 processor cores.

These worker threads can execute one task at a time. But ForkJoinPool does not create a separate thread for every 
    single sub task. Instead each thread in the pool has it's own 'double-ended queue' (which stores tasks) and balancing
    of thread's work loads happens with the help of *work stealing algorithm*.

work stealing algorithm
-----------------------
work stealing algorithm means free threads try to steal work from dequeues of busy threads. By default, a worker thread 
    gets tasks from the head of it's own dequeue. When it is empty, that thread takes a task from a tail of another 
    busy thread's dequeue or from global entry dequeue. It reduces possibility of threads complete for tasks. It also 
    reduces number of times that thread goes and looking for work.

ForkJoinTask: is a task that is executed inside ForkJoinPool.

*/
public class ForkJoin {
    static int[] workload;
    public static void main(String[] args) {
        init();
        simulateTask();
        simulateAction();
    }

    // simulates RecursviveAction
    private static void simulateAction() {
        ForkJoinRecursiveAction action = new ForkJoinRecursiveAction("sdlfkjalkfalksdfhadslkhfas");
        ForkJoinPool.commonPool().invoke(action);

        System.out.println("isDone: " + action.isDone());
    }

    // simulates RecursiveTask<V>
    private static void simulateTask() {
        int[] workload = new int[10];
        ForkJoinRecursiveTask task_one = new ForkJoinRecursiveTask(workload);
        ForkJoinRecursiveTask task_two = new ForkJoinRecursiveTask(workload);
        ForkJoinRecursiveTask task_last = new ForkJoinRecursiveTask(workload);

        // alternative way of submitting task and joining them
        // task_one.fork();
        // task_two.fork();
        // task_last.fork();

        // int result = 0;
        
        // result += task_last.join();
        // result += task_two.join();
        // result += task_one.join();

        Pool.pool.execute(task_one);
        Pool.pool.execute(task_two);
        Pool.pool.execute(task_last);

        task_one.join();
        task_two.join();
        task_last.join();

        System.out.println("isDone: " + task_one.isDone() + ":" + task_two.isDone() + ":" + task_last.isDone());
    }

    private static void init() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        workload = new int[50];
        for (int i = 0; i < workload.length; i++) {
            workload[i] = random.nextInt(35);
        }
    }
}

class Pool {
    public static ForkJoinPool pool = new ForkJoinPool(2);
}

class ForkJoinRecursiveAction extends RecursiveAction {
    private String workload = "";
    private static final Integer THRESHOLD = 4;

    public ForkJoinRecursiveAction(String workload) { this.workload = workload; }

    @Override
    protected void compute() {
        if(workload.length() > THRESHOLD)
            ForkJoinTask.invokeAll(subTasks());
        else
            process(workload);
    }

    private Collection<ForkJoinRecursiveAction> subTasks() {
        List<ForkJoinRecursiveAction> subtasks = new ArrayList<>();
        String one = workload.substring(0, workload.length()/2); // first half of string
        String two = workload.substring(workload.length()/2, workload.length()); // second half of string
        subtasks.add(new ForkJoinRecursiveAction(one));
        subtasks.add(new ForkJoinRecursiveAction(two));
        return subtasks;
    }

    private void process(String work) {
        String result = work.toUpperCase();
        String name = Thread.currentThread().getName();
        System.out.println("This result - (" + result + ") - was processed by " + name);
    }
}

class ForkJoinRecursiveTask extends RecursiveTask<Integer> {
    private static final Integer THRESHOLD = 20;
    private int workload[];

    public ForkJoinRecursiveTask(int[] workload) { this.workload = workload; }

    @Override
    protected Integer compute() { 
        if(workload.length > THRESHOLD)
            return ForkJoinTask.invokeAll(subtasks()).stream().mapToInt(ForkJoinTask::join).sum();
        else
            return process(workload);
    }

    private Collection<ForkJoinRecursiveTask> subtasks() {
        List<ForkJoinRecursiveTask> subtasks = new ArrayList<>();
        subtasks.add(new ForkJoinRecursiveTask(Arrays.copyOfRange(workload, 0, workload.length/2))); // first half of array
        subtasks.add(new ForkJoinRecursiveTask(Arrays.copyOfRange(workload, workload.length/2, workload.length))); // second half of array
        return subtasks;
    }

    private Integer process(int[] workload) {
        return Arrays.stream(workload).filter(value -> value > 10 && value < 27).map(value -> value * 10).sum();
    }
}