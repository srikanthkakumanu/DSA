package concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * 1. CyclicBarrier is introduced in Java 5. Cyclic Barriers are synchronization constructs.
 * 
 * 2. CyclicBarrier works almost same as CountDownLatch except that we can reuse it. Unlike
 *      CountDownLatch, it allows multiple threads to wait for each other using await() 
 *      method (known as barrier condition) before invoking final task.
 */
public class TheCyclicBarrier {
    public static void main(String[] args) {
        simple();
    }

    static void simple() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("All previous tasks are completed");
        });
        Thread t1 = new Thread(new CyclicBarrierTask(barrier), "T1");
        Thread t2 = new Thread(new CyclicBarrierTask(barrier), "T2");
        Thread t3 = new Thread(new CyclicBarrierTask(barrier), "T3");
        
        if(!barrier.isBroken()) {
            t1.start(); t2.start(); t3.start();
        }
    }
}

class CyclicBarrierTask implements Runnable {
    private CyclicBarrier barrier;
    
    public CyclicBarrierTask(CyclicBarrier barrier) { this.barrier = barrier; }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting.");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " is released");
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("Exception caught at CyclicBarrierTask.run(): " + e.getMessage());
        }
    }
}

