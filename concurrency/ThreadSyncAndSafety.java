package concurrency;

import java.util.concurrent.TimeUnit;

/**
 * In Java, multitple threads created from same object shares object variables and
 * this can lead to 'data inconsistency' when threads are used to read and update
 * shared data. 
 * 
 * Thread Safety
 * -------------
 * In Java thread safety can be achieved by the following approaches.
 * 1. class/method/block/static method Synchronization
 * 2. Use of atomic classes from java.util.concurrent.atomic e.g: AtomicInteger
 * 3. Use of locks from java.util.concurrent.locks
 * 4. Using thread safe collection classes e.g: ConcurrentHashMap
 * 5. Using volatile keyword (make threads read data from main memory but not from thread cache).
 * 
 * Synchronization
 * ---------------
 * Synchronized block/method are the simplest way to create a 'mutex' in Java. 
 * 1. Synchronization is a capability to control the access of multiple threads to any shared resource. 
 *      We use synchronization to prevent thread interference and consistancy problem. 
 * 
 * 2. Synchronization is built around an internal entity called 'lock' or 'monitor'. A thread that needs
 *      access to object's fields has to acquire object's lock before accessing them and then release the 
 *      lock when it's done.
 * 
 * 3. Java 5 introduced 'java.util.concurrent.locks' contains several lock implementations. 
 * 
 * 4. Thread synchronization in Java are two types i.e. 'mutual-exclusive' and 'inter-thread communication/co-operation'.
 * 
 * 5. Mutual exclusive helps keep threads from interfering with one another while sharing data. Mutual Exclusive
 *      can be achieved by 'synchronized method/block and static synchronization'.
 * 
 * 6. static synchronization: If we make static method as synchronized, then lock will be on class but not on object.
 *      Scenario:
 *      Suppose there are two objects o1 and o2 of SyncTable. 
 *      there are t1, t2 refers o1. t3, t4 refers o2.
 *      In case of synchronized method and synchronized block, there cannot be any interference between t1, t2
 *      and t3, t4. 
 *      Because t1, t2 both refers to a common object o1 and t1, t2 have a single lock.
 *      But there can be interference between t1 and t3 or t2 and t4. 
 *      Because t1 acquires another lock and t3 acquires another lock.
 *      When we want no interference between t1, t3 or t2, t4, Static synchronization solves this problem.
 *    Note: If a static block has a lock on .class, then it is equalant to static synchronization. e.g: synchronized(Table.class)
 * 
 * 
 * 
 */
public class ThreadSyncAndSafety {
    public static void main(String[] args) {
        SyncTable table = new SyncTable();
        SyncThread t1 = new SyncThread(table);
        SyncThread t2 = new SyncThread(table);
        SyncThread t3 = new SyncThread();
        SyncThread t4 = new SyncThread();

        t1.setName("MY"); t1.setN(1);
        t2.setName("YOUR"); t2.setN(10);
        t3.setName("HE"); t3.setN(100);
        t4.setName("OTHER"); t4.setN(1000);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class SyncThread extends Thread {
    SyncTable table;
    private Integer N;
    public SyncThread() {}
    public SyncThread(SyncTable table) { this.table = table; }
    public void run() { 
        //table.syncMethod(this.getName(), N); 
        //table.syncBlock(this.getName(), N);
        SyncTable.staticSyncMethod(this.getName(), this.N);
    }
    public void setN(Integer N) { 
        this.N = N;
    }
}

class SyncTable {
    
    /** 
     * Synchronization method
    */
    synchronized void syncMethod(String name, int n) {
        System.out.println("Thread Name: [ " + name + " ] = Synchronized method");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Thread Name: [ " + name + " ] = " + n * i);
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch(InterruptedException e) {
                System.out.println("Exception caught at syncMethod.sleep(): " + e.getMessage());
            }
        }
    }

    /**
     * Synchronization block
     * @param name
     * @param n
     */
    void syncBlock(String name, int n) {
        System.out.println("Thread Name: [ " + name + " ] = Synchronized block");
        synchronized(this) {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread Name: [ " + name + " ] = " + n * i);
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch(InterruptedException e) {
                    System.out.println("Exception caught at syncBlock.sleep(): " + e.getMessage());
                }
            }
        }
    }

    /**
     * Static Synchronization method
     * @param name
     * @param n
     */
    synchronized static void staticSyncMethod(String name, int n) {
        System.out.println("Thread Name: [ " + name + " ] = Static Synchronized method");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Thread Name: [ " + name + " ] = " + n * i);
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch(InterruptedException e) {
                System.out.println("Exception caught at staticSyncMethod.sleep(): " + e.getMessage());
            }
        }
    }
}


