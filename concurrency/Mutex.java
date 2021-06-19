package concurrency;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. A mutex (mutual exclusion) is the simplest type of synchronizer. It ensures that only one thread can execute the critical
 *      section of a computer program at a time. A mutex is a locking mechanism used to synchronize access to a resource.
 * 
 * 2. To access a critical section, a thread acquires the mutex, then accesses the critical section and finally releases the mutex.
 *      In the mean time, all other threads block till the mutex releases. As soon as a thread exits the critical section, another
 *      thread can enter the critical section.
 * 
 * 3. There are various ways, we can implement a **mutex** in Java.
 * - Using synchronized keyword: 
 *      It is the simplest way to implement a mutex in Java. Every object in Java has an **intrinsic lock** associated with it. 
 *      The synchronized method and the synchronized block use this intrinsic lock to restrict the access of the critical section
 *      to only one thread at a time. Therefore, when a thread invokes a synchronized method or enters a synchronized block, it 
 *      automatically acquires the lock. The lock releases when the method or block completes or an exception is thrown from them.
 * - Using ReentrantLock:
 *      The ReentrantLock introduced in Java 5. It provides more flexibility and control than synchronized keyword approach to 
 *      achieve mutual exclusion. 
 *      
 *      Note: Alternatively, the Monitor class of Google's Guava library is a better alternative to the ReentrantLock class. As per
 *      its documentation, code using Monitor is more readable and less error-prone than the code using ReentrantLock.
 * - Using Semaphore:
 *      While in case of a mutex only one thread can access a critical section, Semaphore allows a fixed number of threads to access
 *      a critical section. Therefore, we can also implement a mutex by setting the number of allowed threads in a Semaphore to one.
 *      Here mutex acts similarly to a binary semaphore.
 * 
 * Mutex Vs. Semaphore
 * -------------------
 * Mutex:
 * 1. It is a mutual exclusion object that synchronizes access to a resource.
 * 2. The Mutex is a locking mechanism that makes sure only one thread can acquire the Mutex at
 *      a time and enter the critical section. This thread only releases the Mutex when it exits the critical section.
 * 3. A Mutex is different than a semaphore as it is a locking mechanism while a semaphore is a signalling mechanism.
 * 4. A binary semaphore can be used as a Mutex but a Mutex can never be used as a semaphore. 
 * 
 * Semaphore:
 * 1. A semaphore is a signalling mechanism and a thread that is waiting on a semaphore can be signaled by another thread.
 * 2. This is different than a mutex as the mutex can be signaled only by the thread that called the wait function.
 * 3. A semaphore uses two atomic operations, wait and signal for process synchronization.       
  */
public class Mutex {
    public static void main(String[] args) throws Exception {
        simulate();
    }

    static void simulate() throws Exception {
        // without mutex
        withOutMutex();
        // mutex with synchronized keyword approach
        syncrhonizedSequenceGenerator();
        // mutex with ReentrantLock approach
        sequenceGeneratorUsingReentrantLock();
        // mutex with semaphore approach
        sequenceGeneratorUsingSemaphore();
    }

    /**
     * Without mutex and a critical section accessed by multiple threads
     * @throws Exception
     */
    private static void withOutMutex() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGenerator(), count);
        System.out.println("Approach: Without Mutex, Count: " + count + ", UniqueSequences size: " + uniqueSequences.size());
    }

    /**
     * Mutex implementation with synchronized keyword approach
     * @throws Exception
     */
    private static void syncrhonizedSequenceGenerator() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SynchronizedSequenceGenerator(), count);
        System.out.println("Approach: Synchronized, Count: " + count + ", UniqueSequences size: " + uniqueSequences.size());
    }

    /**
     * Mutex implementation using ReentrantLock
     * @throws Exception
     */
    private static void sequenceGeneratorUsingReentrantLock() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingReentrantLock(), count);
        System.out.println("Approach: ReentrantLock, Count: " + count + ", UniqueSequences size: " + uniqueSequences.size());
    }

    /**
     * Mutex implementation using Semaphore
     * @throws Exception
     */
    private static void sequenceGeneratorUsingSemaphore() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSemaphore(), count);
        System.out.println("Approach: Semaphore, Count: " + count + ", UniqueSequences size: " + uniqueSequences.size());
    }

    private static Set<Integer> getUniqueSequences(SequenceGenerator generator, int count) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();
    
        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(generator::getNextSequence));
        }
    
        for (Future<Integer> future : futures) {
            uniqueSequences.add(future.get());
        }
    
        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();
    
        return uniqueSequences;
    }
}

class SequenceGenerator {
    private Integer currentValue = 0;

    public Integer getNextSequence() { 
        currentValue = currentValue + 1;
        return currentValue;
    }
}

class SynchronizedSequenceGenerator extends SequenceGenerator {
    private Object mutex = new Object();

    @Override
    public Integer getNextSequence() {
        synchronized (mutex) {
            return super.getNextSequence();
        }
    }
}

class SequenceGeneratorUsingReentrantLock extends SequenceGenerator {
    private ReentrantLock mutex = new ReentrantLock();

    @Override
    public Integer getNextSequence() {
        try {
            mutex.lock();
            return super.getNextSequence();
        } finally {
            mutex.unlock();
        }
    }
}

class SequenceGeneratorUsingSemaphore extends SequenceGenerator {
    private Semaphore mutex = new Semaphore(1);

    @Override
    public Integer getNextSequence() {
        Integer value = 0;
        try {
            mutex.acquire();
            value = super.getNextSequence();
            return value;
        } catch (InterruptedException e) {
            System.out.println("Exception caught at SequenceGeneratorUsingSemaphore.getNextSequence: " + e.getMessage());
        } finally {
            mutex.release();
        }
        return value;
    }
}