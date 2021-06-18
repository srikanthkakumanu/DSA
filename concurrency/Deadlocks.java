package concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 1. Deadlocks occur in a situation when a thread is waiting for an object lock that
 *      is acquired by another thread and second thread is waiting for an object lock
 *      that is acquired by first thread. Because both threads are waiting for each
 *      other to release the lock hence the scenario is called 'deadlock'.
 * 
 * Solutions: 
 * 1. Avoid nested locks: Avoid locking another resource if you already hold one resource. 
 *      It’s almost impossible to get deadlock situation if you are working with only one
 *      object lock.
 * 
 * 2. Lock only what is Required: Lock only what is required: Acquire lock only on the 
 *      resources that is required.
 * 
 * 3. Avoid indefinite waits:  deadlock can occur when two threads are waiting for each 
 *      other to finish indefinitely using thread join(). If a thread has to wait for 
 *      another thread to finish, it’s always best to use join() with maximum time you 
 *      want to wait for thread to finish.
 * 
 * 
 */
public class Deadlocks {
    public static void main(String[] args) throws Exception {
        String one = new String("ONE");
        String two = new String("TWO");
        String three = new String("THREE");
        
        // deadlock scenario
        // Thread t1 = new Thread(new WithDeadLock(one, two), "T1");
        // Thread t2 = new Thread(new WithDeadLock(two, three), "T2");
        // Thread t3 = new Thread(new WithDeadLock(three, one), "T3");

        // without deadlock scenario
        Thread t1 = new Thread(new NoDeadLock(one, two), "T1");
        Thread t2 = new Thread(new NoDeadLock(two, three), "T2");
        Thread t3 = new Thread(new NoDeadLock(three, one), "T3");

        t1.start();
        Thread.sleep(5000);
        t2.start();
        Thread.sleep(5000);
        t3.start();
    }
}

/**
 * Solution: Avoid nested locks
 */
class NoDeadLock implements Runnable {

    private String one;
    private String two;

    public NoDeadLock(String one, String two) {
         this.one = one; this.two = two;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();

        System.out.println("Thread:" + name + " acquiring lock on " + one);
        synchronized(one) {
            System.out.println("Thread: " + name + " acquired lock on " + one);
            something();
        }
        System.out.println("Thread:" + name + " released lock on " + one);
        
        System.out.println("Thread:" + name + " acquiring lock on " + two);
        synchronized(two) {
                System.out.println("Thread: " + name + " acquired lock on " + two);
                something();
        }
        System.out.println("Thread:" + name + " released lock on " + two);
        
        System.out.println("Thread:" + name + " finished execution.");
    }

    private void something() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch(InterruptedException e) {
            System.out.println("Exception caught at DoThread.something(): " + e.getMessage());
        }
    }
}

/**
 * Deadlock scenario: nested locks
 */
class WithDeadLock implements Runnable {

    private String one;
    private String two;

    public WithDeadLock(String one, String two) {
         this.one = one; this.two = two;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();

        System.out.println("Thread:" + name + " acquiring lock on " + one);
        synchronized(one) {
            System.out.println("Thread: " + name + " acquired lock on " + one);
            something();
            System.out.println("Thread:" + name + " acquiring lock on " + two);
            synchronized(two) {
                System.out.println("Thread: " + name + " acquired lock on " + two);
                something();
            }
            System.out.println("Thread:" + name + " released lock on " + two);
        }
        System.out.println("Thread:" + name + " released lock on " + one);
        System.out.println("Thread:" + name + " finished execution.");
    }

    private void something() {
        try {
            TimeUnit.MILLISECONDS.sleep(30000);
        } catch(InterruptedException e) {
            System.out.println("Exception caught at DoThread.something(): " + e.getMessage());
        }
    }
}