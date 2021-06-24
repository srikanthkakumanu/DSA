package concurrency;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
1. When different threads can access the same resources without exposing erroneous behaviour or producing
    unpredictable results, That programming methodology is known as "Thread Safety".

2. Thread safety can be achieved by many ways:
    - stateless implementations
    - Immutable implementations
    - Thread-Local fields
    - Synchronized collections
    - Concurrent Collections
    - Atomic objects
    - Synchronized methods & statements (blocks)
    - volatile fields
    - ReentrantLock and ReentrantReadWriteLock
    
3. stateless impelementations: 
   --------------------------- 
    Methods which are neither relies on external state or maintains state
    at all. Such methods are thread-safe and can be called by multiple threads at same time. Stateless
    implementations are the simplest way to achieve thread safety.
    
4. Immutable implementations: 
   --------------------------
   If we need to share state between threads, we can create thread-safe classes by making them immutable.
   Immutability is a powerful, language agnostic concept and every easy to achieve in Java. 
   
   A class is immutable when its internal state can't be modified after it has been constructed. In Java,
   we can create an immutable class by declaring all the fields private and final and not providing setters.

5. Thread-Local fields:
   --------------------
   We can crete thread-safe classes that don't share state between threads by making their fields thread-local.
    Two ways we can achieve this. 
   5.1 Create private, final fields inside Thread class implementation. With thread local fields, classes have
        their own state but that state is not shared with other threads. Thus the classes are thread-safe. 
        Here, ThreadLocalFields class is thread-safe.
    
    5.2 By using ThreadLocal fields inside class. Thread-local fields are pretty much like normal class fields, 
        except that each thread that accesses them via a setter/getter gets an independently initialized copy of 
        the field so that each thread has its own state.

6. Synchronized collections
   ------------------------
   We can easily create thread-safe collections by using the set of synchronized wrappers which are part of
   collections framework. But Synchronized collections use intrinsic locking in each method. That means that
   the methods can be accessed by only one thread at a time. while other threads will be blocked until the
   method is unlocked by first thread.
   Thus, synchronization has a penalty in performance due to underlying logic of synchronized access. 

7. Concurrent collections
   ----------------------
   Alternative to synchronized collections, we can create concurrent collections to create thread-safe 
   collections. java.util.concurrent package contains several concurrent collections. e.g. ConcurrentHashMap

   Concurrent collections achieve thread safety by dividing their data into segments while multiple threads 
   can access the divided data segments simultaniously i.e. several threads can locks on different data segments. 
   Concurrent collections offer more performance than synchronized collections due to inherent advantages of
   concurrent thread access.
   
   Note: synchronized and concurrent collections only make the collection itself thread-safe and not the contents.

8. Atomic objects
   --------------
   It is possible to achieve thread-safety using the set of atomic classes such as AtomicInteger, AtomicLong, 
   AtomicBoolean and AtomicReference. Atomic classes allow thread-safe atomic operations without using
   synchronization. An atomic operation is executed in one single machine level operation. 

9. Synchronized Methods  & Statements (blocks)
   -------------------------------------------
   Synchronized is expensive. 

   Note: An intrinsic lock or monitor lock is an implicit entity associated with a particular class instance.  

   Synchronized methods:
   Simply, synchronized methods allows only one thread can access synchronized method at a time while blocking
   access to this method from other threads. Other threads remain blocked until first thread finishes or
   the method throws an exception. Synchronized methods rely on the use of 'intrinsic locks' or 'monitor locks'. 

   Synchronized blocks/statements:
   Unlike synchronized methods, synchronized statements/block must specify the object that provides the intrinsic
   lock, usually the this reference. 
   
   But we can slightly improve by using another object as monitor lock instead of 'this'. Not only this provides 
   coordinated access to a shared resource in a multithreaded environment, but also it uses an external entity to 
   enforce exclusive access to the resource. This is better because it promotes security at the lock level. 
   
   When using 'this' for intrinsic locking, an attacker could cause a deadlock by acquiring the intrinsic lock 
   and triggering a denial of service (DoS) condition. But when using other objects, that private entity is not 
   accessible from the outside. This makes it harder for an attacker to acquire the lock and cause a deadlock.
   
   e.g:
   private final Object lock = new Object();
   synchronized(lock) { .. }

10. Avoid using Strings, any cachable or reusable objects as instrinsic locks for locking purposes.

11. Volatile fields
    ---------------
    Synchronized methods and blocks are handy for addressing variable visibility problems among threads. Even so, 
    the values of regular class fields might be cached by the CPU. Hence, consequent updates to a particular 
    field, even if they're synchronized, might not be visible to other threads. Hence, we use volatile class fields.
    volatile fields guarantees visibility of fields.

12. Reentrant Locks & ReentrantReadWrite Locks
    ------------------------------------------
    Java provides an improved set of Lock implementations, whose behavior is slightly more sophisticated than 
    the intrinsic locks. With intrinsic locks, the lock acquisition model is rigid. ReentrantLock instances
    allow preventing queued threads from suffering some types of 'resource starvation'.
*/
public class ThreadSafety {
    public static void main(String[] args) {
        // Refer 3. stateless Implementations
        System.out.println("Factorial: " + factorial(10));
        // Refer 4. thread-safe immutable class
        Immutable immutable = new Immutable("FIRST", "First Description");
        System.out.println("Immutable name=" + immutable.getName() + ", description=" + immutable.getDescription());

        // Refer 5.1 & 5.2. thread-safe thread local fields inside Thread or ThreadLocal instance in a class. 
        ThreadLocalFields thread = new ThreadLocalFields(); thread.start(); // 5.1
        System.out.println("State : " + ThreadState.getState().getState()); // 5.2
        synchronizedCollections(); // Refer 6.
        concurrentCollections(); // Refer 7.
        atomic(); // Refer 8.
        
    }

    /**
     * Stateless implementations: stateless method. Refer 3.
     * This method neither relies on external state nor maintains state at all. Hence it is thread safe.
     */
    private static BigInteger factorial(int number) {
        BigInteger f = new BigInteger("1");
        for(int i = 2; i <= number; i++) 
            f = f.multiply(BigInteger.valueOf(i));
        return f;
    }
    /*
    Refer 6. Synchronized collections
    */
    private static void synchronizedCollections() {
        Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
        Thread thread1 = new Thread(() -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
        Thread thread2 = new Thread(() -> syncCollection.addAll(Arrays.asList(7, 8, 9, 10, 11, 12)));
        thread1.start();
        thread2.start();
    }

    /*
    Refer 7. Concurrent collections
    */
    private static void concurrentCollections() {
        Map<String, String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("1", "ONE");
        concurrentMap.put("2", "TWO");
        concurrentMap.put("3", "THREE");
    }

    /*
    Refer 8. Atomic objects/classes
    */
    private static void atomic() { 
        AtomicCounter counter = new AtomicCounter();
        counter.incrementCounter();
        System.out.println(counter.getCounter());
    }
}

// Immutable implementation. Refer 4.
class Immutable {
    // all fields are private and final
    private final String name;
    private final String description;

    public Immutable(String name, String description) {
        this.name = name; this.description = description;
    }
    // only getter methods are provided
    public String getName() { return name; }
    public String getDescription() { return description; }
}

/* 
Refer 5.1.
Thread Local fields inside Thread implementations which does not share state with other threads.
*/ 
class ThreadLocalFields extends Thread {
    private final List<Integer> numbers = Arrays.asList(1,2,3,4,5,6); // thread local field

    @Override
    public void run() { numbers.forEach(System.out::println); }
}

/*
Refer 5.2
private static final ThreadLocal fields inside a class. 
*/
class ThreadState {
    public static final ThreadLocal<StateHolder> statePerThread = new ThreadLocal<>() {
        @Override
        protected StateHolder initialValue() { return new StateHolder("active"); }
    };
    public static StateHolder getState() { return statePerThread.get(); }
}

class StateHolder {
    private final String state;
    public StateHolder(String state) { this.state = state; }
    public String getState() { return state; }
}

// Refer 8. Atomic classes/objects
class AtomicCounter {
    private final AtomicInteger counter = new AtomicInteger();
    public void incrementCounter() { 
        counter.incrementAndGet(); // incrementAndGet() is atomic. 
    }
    public int getCounter() { return counter.get(); }
}