package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * 1. Semaphore introduced in Java 5. A semaphore maintains/contains set of permits.
 *      We can use semaphores to limit the number of concurrent threads accessing 
 *      a specific resource. Semaphore is signaling mechanism.
 * 
 * 2. Semaphore is used for blocking thread level access to some part of physical or 
 *      logical resource.
 * 
 * 3. Whenever a thread tries to enter a critical section, it needs to check semaphore, if
 *      a permit is available or not. If permit is not available (via tryAcquire()), the 
 *      thread is not allowed to jump into the critical section. However, if the permit is
 *      available the access is granted, and the permit counter decreases.
 * 
 * 4. Once the executing thread releases critical section, again the permit counter increases
 *      (done by release() method).
 * 
 * 5. We can specify a timeout for acquiring access by using the tryAcquire(long timeout, TimeUnit unit)
 *      method. 
 * 
 * 6. We can also check the number of available permits or the number of threads waiting to
 *      acquire the semaphore. 
 * 
 * 7. Apache commons TimedSemaphore: TimeSemaphore allows number of permits as a simple Semaphore
 *      but in a given period of time. After this period, the time reset and and all permits
 *      are released. 
 *  
 * 8. We can implement a 'Mutex' like data structure using Semaphore. Mutex acts similarly
 *      to a binary semaphore. We can use it to implement mutual exclusion.      
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
public class Semaphores {
    public static void main(String[] args) {
        givenLoginQueue_whenReachLimit_thenBlocked();
        givenLoginQueue_whenLogout_thenSlotsAvailable();
    }
    
    static void givenLoginQueue_whenReachLimit_thenBlocked() {
        Integer slots = 10;
        ExecutorService service = Executors.newScheduledThreadPool(slots);
        LoginQueueSemaphore semaphore = new LoginQueueSemaphore(slots);
        IntStream.range(0, slots)
                .forEach(user -> service.execute(semaphore::login));
        service.shutdown();
        System.out.println("Available Slots: " + semaphore.availableSlots());
        semaphore.login();
    }

    static void givenLoginQueue_whenLogout_thenSlotsAvailable() {
        Integer slots = 10;
        ExecutorService service = Executors.newScheduledThreadPool(slots);
        LoginQueueSemaphore semaphore = new LoginQueueSemaphore(slots);
        IntStream.range(0, slots)
                .forEach(user -> service.execute(semaphore::login));
        service.shutdown();
        System.out.println("Available Slots: " + semaphore.availableSlots());
        semaphore.logout();
        System.out.println("Available Slots > 0: " + (semaphore.availableSlots() > 0));
        System.out.println("Login: " + semaphore.login());
    }
}

class LoginQueueSemaphore {

    private Semaphore semaphore;
    
    public LoginQueueSemaphore(Integer slotLimit) {
        semaphore = new Semaphore(slotLimit);
    }

    /**
     * tryAcquire(): returns true if a permit is available immediately and acquire it 
     * otherwise return false, but acquire() acquires a permit and blocking until one
     * is available.
     */
    boolean login() {
        return semaphore.tryAcquire();
    }

    /**
     * relese(): release a permit.
     */
    void logout() {
        semaphore.release();
    }

    /**
     * availablePermits(): Returns number of permits available.
     */
    Integer availableSlots() {
        return semaphore.availablePermits();
    }
}

