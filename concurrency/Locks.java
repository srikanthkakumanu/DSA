package concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/*
1. Locks are introduced in Java 5. Lock is a utility for blocking other threads from accessing a certain segment of code, 
apart from the thread that's executing it currently. A Lock is more flexible and sophisticated thread synchronization 
mechanism than the standard synchronized block.

2. Lock Vs. Synchronized block:
----------------------------
- A synchronized block is fully contained within a method – we can have Lock API's lock() and unlock() operation in 
    separate methods.

- A synchronized block doesn't support the fairness. In Locks, any thread can acquire the lock once released, no 
    preference can be specified. We can achieve fairness within the Lock APIs by specifying the fairness property. 
    It makes sure that longest waiting thread is given access to the lock.

- A thread gets blocked if it can't get an access to the synchronized block. The Lock API provides tryLock() method. 
    The thread acquires lock only if it's available and not held by any other thread. This reduces blocking time of
    thread waiting for the lock.

- A thread which is in “waiting” state to acquire the access to synchronized block, can't be interrupted. The Lock 
    API provides a method lockInterruptibly() which can be used to interrupt the thread when it's waiting for the lock.

3. A locked instance should always be unlocked to avoid deadlock condition. A recommended approach to use the lock 
    that it should contain a try/catch and finally block.

4. Lock interface methods: lock(), lockInterruptibly(), tryLock(), tryLock(long timeout, TimeUnit timeUnit), unlock()

5. ReadWriteLock interface: It maintains a pair of locks, one for read-only operations and one for the write operation.  
    The read lock may be simultaneously held by multiple threads as long as there is no write. It has the following 
    methods i.e. Lock readLock(), Lock writeLock()

6. Lock Implementations:
------------------------
- ReentrantLock: ReentrantLock offers the same concurrency and memory semantics, as the implicit monitor lock accessed 
    using synchronized methods and statements, with extended capabilities.
  
- ReentrantReadWriteLock: ReentrantReadWriteLock implements ReadWriteLock interface and it can provides two different 
    locks such as readLock() and writeLock(). If no thread acquired the write lock or requested for it then multiple 
    threads can acquire the read lock. If no threads are reading or writing then only one thread can acquire the 
    write lock.

- StampedLock: StampedLock is introduced in Java 8. It also supports both read and write locks. However, lock 
    acquisition methods return a stamp that is used to release a lock or to check if the lock is still valid. 
    Another feature provided by StampedLock is optimistic locking. Most of the time read operations don't need
    to wait for write operation completion and as a result of this, the full-fledged read lock isn't required. 
    Instead, we can upgrade to read lock.

7. Locks with Conditions:
-------------------------
7.1 The Condition class provides the ability for a thread to wait for some condition to occur while executing the 
    critical section. This can occur when a thread acquires the access to the critical section but doesn't have 
    the necessary condition to perform its operation. 

7.2 Traditionally Java provides wait(), notify() and notifyAll() methods for thread intercommunication. 
    Conditions have similar mechanisms, but in addition, we can specify multiple conditions.
*/
public class Locks {
    public static void main(String[] args) throws Exception {
        //reentrantLock();
        reentrantReadWriteLock();
    }

    static void reentrantLock() {
        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final ReentrantLocker object = new ReentrantLocker();
        service.execute(object::performLock);
        service.execute(object::performTryLock);
        service.shutdown();
    }

    static void reentrantReadWriteLock() {
        final int threadCount = 3;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        ReentrantReadWriteLocker object = new ReentrantReadWriteLocker();

        service.execute(new Thread(new Writer(object), "Writer"));
        service.execute(new Thread(new Reader(object), "Reader1"));
        service.execute(new Thread(new Reader(object), "Reader2"));

        service.shutdown();
    }
}

class ReentrantReadWriteLocker {
    private static Map<String, String> syncHashMap = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock(); // read lock can be acquired by many threads
    private final Lock writeLock = lock.writeLock(); // write lock can be acquired by only one thread

    public void put(String key, String value) throws InterruptedException {
        try {
            writeLock.lock();
            System.out.println("Thread - " + Thread.currentThread().getName() + " writing");
            syncHashMap.put(key, value);
            Thread.sleep(1000);
        }
        finally {
            writeLock.unlock();
        }
    }

    public String get(String key) {
        try {
            readLock.lock();
            System.out.println("Thread - " + Thread.currentThread().getName() + " reading");
            return syncHashMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public String remove(String key) {
        try {
            writeLock.lock();
            return syncHashMap.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean containsKey(String key) {
        try {
            readLock.lock();
            return syncHashMap.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    boolean isReadLockAvailable() {
        return readLock.tryLock();
    }


}

class ReentrantLocker {
    ReentrantLock lock = new ReentrantLock(true); // true - specifies fairness policy
    Integer counter = 0;

    /**
     * Simple synchronized block alternative approach and avoids deadlock because of try + finally
     */
    public void performLock() {
        lock.lock();
        String name = Thread.currentThread().getName();
        System.out.println("Thread - " + name + " acquired the lock");
        try {
            System.out.println("Thread - " + name + " incrementing counter");
            counter++;
        } 
        finally {
            lock.unlock();
            System.out.println("Thread - " + name + " released the lock");
        }
    }

    /**
     * tryLock: will wait for 1 second and will give up waiting if the lock isn't available.
     */
    public void performTryLock() {
        
        System.out.println("Thread - " + Thread.currentThread().getName() + " attempting to acquire the lock");
        try {
            boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
            if (isLockAcquired) {
                try {
                    System.out.println("Thread - " + Thread.currentThread().getName() + " acquired the lock");

                    System.out.println("Thread - " + Thread.currentThread().getName() + " incrementing counter");
                    Thread.sleep(1000);
                } 
                finally {
                    lock.unlock();
                    System.out.println("Thread - " + Thread.currentThread().getName() + " released the lock");
                }
            }
        } 
        catch (InterruptedException exception) {
            System.err.println(" Interrupted Exception: " + exception.getMessage());
        }
        System.out.println("Thread - " + Thread.currentThread().getName() + " could not acquire the lock");    
    }

    public ReentrantLock getLock() {
        return lock;
    }

    boolean isLocked() {
        return lock.isLocked();
    }

    boolean hasQueuedThreads() {
        return lock.hasQueuedThreads();
    }

    int getCounter() {
        return counter;
    }
}

class Reader implements Runnable {

    ReentrantReadWriteLocker object;

    Reader(ReentrantReadWriteLocker object) {
        this.object = object;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            object.get("key" + i);
        }
    }
}

class Writer implements Runnable {

    ReentrantReadWriteLocker object;

    public Writer(ReentrantReadWriteLocker object) {
        this.object = object;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                object.put("key" + i, "value" + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

