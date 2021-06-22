package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/*
1. Phaser is very similar to CountDownLatch that allows us to co-ordinate execution of threads. 
    In comparison to CountDownLatch, Phaser has some additional functionality. 

2. The Phaser is a barrier on which the dynamic number of threads need to wait before continuing
    execution. In the CountDownLatch that number cannot be configured dynamically and needs to be 
    supplied when we're creating the instance.

3. The Phaser allows us to build logic in which threads need to wait on the barrier before going 
    to the next step of execution. We can coordinate multiple phases of execution, reusing a Phaser 
    instance for each program phase. Each phase can have a different number of threads waiting for 
    advancing to another phase.

4. To participate in the coordination, the thread needs to register() itself with the Phaser instance. 
    The thread signals that it arrived at the barrier by calling the arriveAndAwaitAdvance(), which is 
    a blocking method. When the number of arrived parties is equal to the number of registered parties, 
    the execution of the program will continue, and the phase number will increase. When the thread 
    finishes its job, we should call the arriveAndDeregister() method to signal that the current thread 
    should no longer be accounted for in this particular phase.

Note: Phaser is a more flexible solution than CyclicBarrier and CountDownLatch – used to act as a reusable 
        barrier on which the dynamic number of threads need to wait before continuing execution. We can 
        coordinate multiple phases of execution, reusing a Phaser instance for each program phase.

 */
public class Phasers {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Phaser phaser = new Phaser(1);
        System.out.println("Phaser Number: " + phaser.getPhase());

        // submitting 3 threads to a phaser
        service.submit(new LongRunningAction("A", phaser));
        service.submit(new LongRunningAction("B", phaser));
        service.submit(new LongRunningAction("C", phaser));

        phaser.arriveAndAwaitAdvance();
        System.out.println("Phaser Number: " + phaser.getPhase());

        // submitting 2 threads to phaser
        service.submit(new LongRunningAction("D", phaser));
        service.submit(new LongRunningAction("E", phaser));

        phaser.arriveAndAwaitAdvance();
        System.out.println("Phaser Number: " + phaser.getPhase());

        phaser.arriveAndDeregister();
    }
}

class LongRunningAction implements Runnable {

    private String name;
    private Phaser phaser;

    public LongRunningAction(String name, Phaser phaser) {
        this.name = name;
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance();
        try {
            System.out.println("Thread: " + name + " of Phaser: " + phaser.getPhase() + " running");
            TimeUnit.MILLISECONDS.sleep(20);
        } catch(InterruptedException e) {
            System.out.println("Exception caught LongRunningAction.run(): " + e.getMessage());
        }
        phaser.arriveAndDeregister();
    }
}
