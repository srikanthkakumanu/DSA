# **Java Concurrency** (Multi-threading)

<div style="text-align: justify">

## **Table of Contents:**

1. [**Multi-Tasking**](#multi-tasking) </br>
    1.1 [Multi-Processing](#-multi-processing) </br>
    1.2 [Multi-Threading](#multi-threading)

2. [**Thread Scheduling**](#thread-scheduling) </br>
    2.1 [Pre-emptive Thread Scheduling](#-pre-emptive-scheduling) </br>
    2.2 [Time Slicing (Round-robin) Scheduling](#-time-slicing-scheduling) </br>
    2.3 [Scheduling Utlity: java.util.TimerTask](#-timertask) </br>

3. [**Thread Pooling**](#thread-pooling) </br>
    3.1 [Advantages](#-thread-pooling---advantages) </br>
    3.2 [Risks](#-thread-pooling---risks) </br>
    3.3 [Thread Pool Tuning](#-thread-pool-tuning) </br>
    3.4 [Executor Framework](#-thread-pooling---executor-framework) </br>
    3.5 [Thread Factory](#-thread-factory) </br>
    3.6 [Fork & Join Pool Framework](#-fork--join-pool-framework) </br>

4. [**Thread Grouping**](#thread-groups) </br>

5. [**Thread Safety**](#thread-safety)</br>
   5.1 [Thread Synchronization](#-thread-synchronization)</br>
   5.2 [ThreadLocal](#-thread-local) </br>
   5.3 [CountdownLatch](#-countdownlatch) </br>
   5.4 [CyclicBarrier](#-cyclicbarrier) </br>
   5.5 [Semaphore and Mutex](#-semaphore-and-mutex) </br>
   5.6 [Locks](#locks) </br>
   5.7 [Phaser](#phaser) </br>
   5.8 [volatile](#volatile) </br>

6. [**Dead Lock**](#dead-lock) </br>
   6.1 [Solutions](#-solutions)</br>

7. [**Fairness - Starvation - Deadlock**](#fairness---starvation---deadlock) </br>

8. [**wait() + notify() + notifyAll** (Inter-thread Communication)](#inter-thread-communication-waitnofifynotifyall)</br>

9. [Bounded-Buffer (producer-consumer) problem](#bounded-buffer-producer-consumer-problem) </br>
   9.1 [Blocking Queue (Bounded + Unbounded)](#-blockingqueue) </br>
   9.2 [Delay Queue](#-delayqueue) </br>

10. [Dining Philospher's Problem & Solution](#dining-philosophers-problem) </br>
    
11. [Thread Dump Analysis](#thread-dump-analysis) </br>

</br>
</br>

## **Multi tasking**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>
Multi tasking is a process of executing multiple tasks simultaneously. We use multi tasking to utilise the CPU.

Multi tasking can achieved in two ways:

* Multi-processing
* Multi-threading

### ||| **Multi processing**

</br> [Table Of Contents](#table-of-contents) </br>

A process is heavy weight and each process has an address in memory, each process allocates a separate memory area and cost of inter-process communication (IPC) is high. Switching (i.e. context-switching) from one process to another requires some time for saving and loading registers, memory maps, updating lists, etc.
</br>
</br>

## **Multi threading**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

A thread is light weight, threads share same memory space and IPC communication between threads is low. Note that at least one process is required for each thread. At a time, only one thread can run in a single process. Context switching between threads is done by OS as well in order to emulate parallelism. In Java, threads are mapped to system-level threads which are operating system's resources.

<U>Caution:</U>

* If threads are created un-controllably, we may run out of resources quickly.
* More threads we spawn, the less time each thread spends doing actual work.
</br>
</br>

### **Thread Scheduling**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

Thread scheduling is part of JVM and JVM uses native OS threads and thus relies on OS scheduler to perform thread scheduling mechanism. Thread scheduling algorithms are platform (OS) dependant and implemented using different algorithms on different OS. Java uses these algorithms of underlying Operating System.

However scheduling comes in two flavors:

* pre-emptive scheduling
* time slicing (round-robin)

#### ||| **pre-emptive scheduling**

</br> [Table Of Contents](#table-of-contents) </br>

Thread scheduler schedules the threads according to thread priority. But it is not guaranteed as it is dependant on JVM specification and OS scheduler that chooses thread scheduling type.
</br>
</br>

#### ||| **Time Slicing Scheduling**

</br> [Table Of Contents](#table-of-contents) </br>

Many systems implement time slicing of threads. In a time-slicing system, threads processing is chopped up so that each thread runs for a short period of time before the context is switched to next thread. Higher priority threads still pre-empt lower priority threads in this scheme. Time slicing mixes up the threads processing of same priority on multi-process machine and threads may even be run simultaneously. This can introduce a difference in behaviour for applications that don't use threads and synchronization properly.</br>

Since Java doesn't guarantee time slicing, we shouldn't write code that relies on this type of scheduling.</br>

#### ||| **TimerTask**

</br> [Table Of Contents](#table-of-contents) </br>


**java.util.Timer** is a utility class that can be used to schedule a thread to be executed at certain time in future. Timer class can be used to schedule a task to be run one-time or at regular intervals.

Timer class thread-safe and does not need explicit synchronization. Timer class is used for scheduling tasks i.e. internally it uses wait(), notify() to schedule the tasks.

java.util.TimerTask is an abstract class that implements Runnable interface. This is used for creating a custom task.

Timer uses java.util.TaskQueue to add tasks at given regular intervals any time and there can be only one thread running the TimerTask.

</br>

### **Thread Pooling**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

Thread pooling represents group of threads that are waiting for the job and re-use many times. Thread pooling can be achieved by Executor framework(built-in thread pool framework). It gives 'better performance' as it saves time because there is no need to create new thread. It solves the problem of thread cycle overhead and resource thrashing.

#### ||| **Thread Pooling - Advantages**

</br> [Table Of Contents](#table-of-contents) </br>

1. Thread Pooling reduces response time by avoiding thread creation i.e. thread cycle overhead during request or task processing.

2. Thread Pooling allows you to change your execution policy as you need. you can go from single thread to multiple thread by just replacing ExecutorService implementation.

3. Thread Pooling in Java application increases stability of system by creating a configured number of threads decided based on system load and available resource.

4. Thread Pool frees application developer from thread management stuff and allows to focus on business logic.

#### ||| **Thread Pooling - Risks**

</br> [Table Of Contents](#table-of-contents) </br>

1. Deadlock: thread pools introduce another case of deadlock, one in which all the executing threads are waiting for the results from the blocked threads waiting in the queue due to the unavailability of threads for execution.

2. Thread leakage: Thread Leakage occurs if a thread is removed from the pool to execute a task but not returned to it when the task completed. As an example, if the thread throws an exception and pool class does not catch this exception, then the thread will simply exit, reducing the size of the thread pool by one. If this repeats many times, then the pool would eventually become empty and no threads would be available to execute other requests.

3. Resource Thrashing: If the thread pool size is very large then time is wasted in context switching between threads. Having more threads than the optimal number may cause starvation problem leading to resource thrashing.

#### ||| **Thread pool tuning**

</br> [Table Of Contents](#table-of-contents) </br>

The optimum size of the thread pool depends on the number of processors available and the nature of the tasks. On a N processor system for a queue of only computation type processes, **N*(1+W/S)** for maximum efficiency.</br>
N or N+1: maximum thread pool size.</br>
W: ratio of waiting time.</br>
S: service time for a request.</br>
</br>

#### ||| **Thread Pooling - Executor Framework**

</br> [Table Of Contents](#table-of-contents) </br>

Executor framework gives the developer the ability to control the number of generated threads and the granularity of tasks that should be run by separate threads. The best use case for ExecutorService is the processing of independent tasks, such as transactions or requests according to the scheme ???one thread for one task.??? But fork/join framework, despite the simplicity and frequent performance gains associated with fork/join, it reduces developer control over concurrent execution.

The shutdown() method doesn't cause immediate destruction of the ExecutorService. It will make the ExecutorService stop accepting new tasks and shut down after all running threads finish their current work. But shutdownNow() tries to destroy the ExecutorService immediately, but it doesn't guarantee that all the running threads will be stopped at the same time.

**Executor:** interface for executing/launching new tasks(thread - which implements Runnable). Using Executor interface, we can decouple the task execution flow from the actual task execution mechanism. Executor does not 'strictly' require the task execution to be asynchronous.

**ExecutorService:** sub-interface for Executor, that simplifies running tasks in asynchronous mode. It is a complete solution for asynchronous processing. It manages an 'In-Memory' queue and schedules submitted tasks based on thread availability. It automatically provides a pool of threads and an API for assigning tasks to it. It contains features that help manage life cycle, both of the individual tasks and executor itself.

**ScheduledExecutorService:** sub-interface for ExecutorService, supports Future<T> and/or periodic execution of tasks.

**ThreadPoolExecutor & ScheduledThreadPoolExecutor:** ThreadPoolExecutor consist of Task Queue(BlockingQueue) and Thread Pool(Set of Worker) which have better performance and API to handle async tasks.

**Executors:** Executors is a helper class that contains several methods for the creation of pre-configured thread pool instances. It contains Factory and helper methods for Executor, ExecutorService, ScheduledExecutorService, ThreadFactory and Callable classes/interfaces.

**Future:** Future is used to represent the result of an asynchronous operation.

**FutureTask:** FutureTask is base concrete implementation of Future interface and provides asynchronous processing. It contains the methods to start and cancel a task and also methods that can return the state of the FutureTask as whether it???s completed or cancelled. We need a callable object to create a future task and then we can use Java Thread Pool Executor to process these asynchronously. i.e. FutureTask requires a Callable object. FutureTask comes handy when we want to override some of Future interface methods and don???t want to implement every method of Future interface.
</br>
</br>

#### ||| **Thread Factory**

</br> [Table Of Contents](#table-of-contents) </br>

ThreadFactory acts as a thread(non-existing) pool which creates a new thread on demand. It eliminates the need of lot of boilerplate code for implementing efficient thread creation mechanisms.
</br>

#### ||| **Fork & Join Pool Framework**

</br> [Table Of Contents](#table-of-contents) </br>

The fork/join framework was introduced in Java 7. It helps to speed up parallel processing by attempting to use all available processor cores and it uses **Divide and conqure approach** to accomplish this.

1. This framework **forks** recursively breaking the tasks into smaller independant tasks (until they are simple enough to be executed asynchronously).

2. After that, the **join** part begins by joining all results of sub tasks recursively into a single result.

To support parallel execution, it uses a thread pool called **ForkJoinPool** which manages worker threads (ForkJoinWorkerThread threads).

In Java 8, *ForkJoinPool.commonPool()* is used to create ForkJoinPool and it guarantees resource consumption since it discourages the creation of a separate thread pool per task.

In Java 7, *ForkJoinPool pool = new ForkJoinPool(2)* is used to create ForkJoinPool. 2 means the pool has parallelism level of 2, meaning that the pool will use 2 processor cores.

These worker threads can execute one task at a time. But ForkJoinPool does not create a separate thread for every single sub task. Instead each thread in the pool has it's own **double-ended queue** (which stores tasks) and balancing of thread's work loads happens with the help of *work stealing algorithm*.

**work stealing algorithm**

work stealing algorithm means free threads try to steal work from dequeues of busy threads. By default, a worker thread gets tasks from the head of it's own dequeue. When it is empty, that thread takes a task from a tail of another busy thread's dequeue or from global entry dequeue. It reduces possibility of threads complete for tasks. It also reduces number of times that thread goes and looking for work.

**ForkJoinTask** is a task that is executed inside ForkJoinPool.
</br>

### **Thread Groups**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

In Java, we can group multiple threads in a single object and we can suspend, resume or interrupt these grouped threads by a single call. A ThreadGroup represents set of threads and a ThreadGroup can also include another ThreadGroup. ThreadGroup creates a tree in which every thread group except the initial thread group has a parent.

Every Java thread is a member of a thread group. Thread groups provide a mechanism for collecting multiple threads into a single object and manipulating those threads all at once, rather than individually. Runtime system puts a thread into a thread group during thread construction. If no thread group is specified, it assigns the thread to **a default thread group(current thread group)**. Thus that thread becomes permanent member of that thread group, you cannot move a thread to a new group after that thread has been created.

**Note**: suspend(), resume(), stop() methods are deprecated.

A thread is allowed to access information about its own thread group, but it cannot access the information about its thread group's parent thread group or any other thread groups.

</br>
</br>

### **Thread Safety**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

In Java, multitple threads created from same object shares object variables and this can lead to **data inconsistency** when threads are used to read and update shared data. When different threads can access the same resources without exposing erroneous behaviour or producing unpredictable results, That programming methodology is known as **Thread Safety**.


There are different thread safety approaches are used to avoid the data inconsistency scenarios.

1. Stateless implementations </br>
2. Immutable Implementations </br>
3. Thread-Local fields </br>
4. Synchronized Collections </br>
5. Concurrent Collections </br>
6. Atomic Objects: Use of atomic classes from java.util.concurrent.atomic e.g: AtomicInteger </br>
7. Synchronized: class/method/block/static method Synchronization </br>
8. volatile fields: (make threads read data from main memory but not from thread cache)  </br>
9. ReentrantLock & ReentrantReadWriteLock: Use of locks from java.util.concurrent.locks </br>
10. Other: Avoid using Strings, any cachable or reusable objects as instrinsic locks for locking purposes. </br>

**stateless impelementations:**

</br>

Methods which are neither relies on external state or maintains state at all. Such methods are thread-safe and can be called by multiple threads at same time. Stateless implementations are the simplest way to achieve thread safety.

**Immutable implementations:**

</br>

If we need to share state between threads, we can create thread-safe classes by making them immutable. Immutability is a powerful, language agnostic concept and every easy to achieve in Java.

A class is immutable when its internal state can't be modified after it has been constructed. In Java, we can create an immutable class by declaring all the fields private and final and not providing setters.

**Thread-Local fields:**

</br>

We can crete thread-safe classes that don't share state between threads by making their fields thread-local. Two ways we can achieve this.

A. Create private, final fields inside Thread class implementation. With thread local fields, classes have their own state but that state is not shared with other threads. Thus the classes are thread-safe. Here, ThreadLocalFields class is thread-safe.

B. By using ThreadLocal fields inside class. Thread-local fields are pretty much like normal class fields, except that each thread that accesses them via a setter/getter gets an independently initialized copy of the field so that each thread has its own state.
</br>

**Synchronized collections**

</br>

We can easily create thread-safe collections by using the set of synchronized wrappers which are part of collections framework. But Synchronized collections use intrinsic locking in each method. That means that the methods can be accessed by only one thread at a time. while other threads will be blocked until the method is unlocked by first thread. Thus, synchronization has a penalty in performance due to underlying logic of synchronized access.
</br>

**Concurrent collections**

</br>

Alternative to synchronized collections, we can create concurrent collections to create thread-safe collections. java.util.concurrent package contains several concurrent collections. e.g. ConcurrentHashMap.

Concurrent collections achieve thread safety by dividing their data into segments while multiple threads can access the divided data segments simultaniously i.e. several threads can locks on different data segments. Concurrent collections offer more performance than synchronized collections due to inherent advantages of concurrent thread access.

*Note: synchronized and concurrent collections only make the collection itself thread-safe and not the contents.*
</br>

**Atomic objects**

</br>

It is possible to achieve thread-safety using the set of atomic classes such as AtomicInteger, AtomicLong, AtomicBoolean and AtomicReference. Atomic classes allow thread-safe atomic operations without using synchronization. An atomic operation is executed in one single machine level operation.

</br>

**Synchronized Methods  & Statements (blocks)**

</br>

Synchronized is expensive.

*Note: An intrinsic lock or monitor lock is an implicit entity associated with a particular class instance.*  

Synchronized methods: </br>
Simply, synchronized methods allows only one thread can access synchronized method at a time while blocking access to this method from other threads. Other threads remain blocked until first thread finishes or the method throws an exception. Synchronized methods rely on the use of 'intrinsic locks' or 'monitor locks'.
</br>

Synchronized blocks/statements: </br>
Unlike synchronized methods, synchronized statements/block must specify the object that provides the intrinsic lock, usually the this reference.
</br>

But we can slightly improve by using another object as monitor lock instead of 'this'. Not only this provides coordinated access to a shared resource in a multithreaded environment, but also it uses an external entity to enforce exclusive access to the resource. This is better because it promotes security at the lock level.
</br>

When using 'this' for intrinsic locking, an attacker could cause a deadlock by acquiring the intrinsic lock and triggering a denial of service (DoS) condition. But when using other objects, that private entity is not accessible from the outside. This makes it harder for an attacker to acquire the lock and cause a deadlock.

   e.g: </br>
   private final Object lock = new Object(); </br>
   synchronized(lock) { .. } </br>

**Avoid using Strings, any cachable or reusable objects as instrinsic locks for locking purposes.**

</br>

**Volatile fields**

</br>

Synchronized methods and blocks are handy for addressing variable visibility problems among threads. Even so, the values of regular class fields might be cached by the CPU. Hence, consequent updates to a particular field, even if they're synchronized, might not be visible to other threads. Hence, we use volatile class fields. volatile fields guarantees visibility of fields.

</br>

**Reentrant Locks & ReentrantReadWrite Locks**

</br>

Java provides an improved set of Lock implementations, whose behavior is slightly more sophisticated than the intrinsic locks. With intrinsic locks, the lock acquisition model is rigid. ReentrantLock instances allow preventing queued threads from suffering some types of 'resource starvation'.

</br>

#### ||| **Thread Synchronization**

</br> [Table Of Contents](#table-of-contents) </br>

Synchronized block/method/static method is the simplest way to create a **mutex** in Java.
Synchronization is a capability to control the access of multiple threads to any shared resource. We use synchronization to prevent thread interference and consistancy problem. Synchronization is built around an internal entity called **lock** or **monitor**. A thread that needs access to object's fields has to acquire object's lock before accessing them and then release the lock when it's done. Java 5 introduced **java.util.concurrent.locks** contains several lock implementations.

Thread synchronization in Java are two types i.e. **mutual-exclusive** and **inter-thread communication/co-operation**.

Mutual exclusive helps keep threads from interfering with one another while sharing data. Mutual Exclusive can be achieved by **synchronized method/block and static synchronization**.

**static synchronization**: If we make static method as synchronized, then lock will be on class but not on object.

**Scenario:**

 </br>

 Suppose there are two objects o1 and o2 of a class. </br>
 There are t1, t2 threads refers o1. t3, t4 threads refers o2.</br>
 In case of synchronized method and synchronized block, there cannot be any interference between (t1, t2) and (t3, t4).</br>
 Because (t1, t2) both refers to a common object o1 and (t1, t2) have a single lock.</br>
 But there can be interference between (t1, t3) or (t2, t4).</br>
 Because t1 acquires another lock and t3 acquires another lock.</br>
 When we want no interference between t1, t3 or t2, t4 then Static synchronization solves this problem.

Note: If a static block has a lock on .class, then it is equalant to static synchronization. e.g: synchronized(Table.class). It means only one object of that class can hold the lock.

Important: *CyclicBarrier, Phaser, CountDownLatch, Exchanger, Semaphore and SynchronousQueue offer out of the box functionality for common interaction patterns between threads.*
</br>
</br>

#### ||| **Thread Local**

</br> [Table Of Contents](#table-of-contents) </br>

1. ThreadLocal is used to create thread local variables.

2. We can use synchronization for thread safety but if we want to avoid synchronization, then we can use ThreadLocal variables.

3. Every thread has it's own ThreadLocal variable.

4. ThreadLocal instances are typically private static fields in classes that wish to associate state with a thread.
</br>
</br>

#### ||| **CountDownLatch**

</br> [Table Of Contents](#table-of-contents) </br>

CountDownLatch is introduced in Java 5. It is a utility class and a type of synchronizer which blocks set of threads until some operation completes. Essentially, by using a CountDownLatch we can cause a thread to block until other threads have completed a given task. A CountDownLatch has a **counter** field, which you can decrement as we require. We can then use it to block a calling thread until it's been counted down to zero.

Note: *Phaser is a more flexible solution than CyclicBarrier and CountDownLatch ??? used to act as a reusable barrier on which the dynamic number of threads need to wait before continuing execution. We can coordinate multiple phases of execution, reusing a Phaser instance for each program phase.*

</br>

#### ||| **CyclicBarrier**

</br> [Table Of Contents](#table-of-contents) </br>

A CyclicBarrier is a synchronizer or synchronization construct that allows a set of threads to wait for each other to reach a common execution point, also called a **barrier**. CyclicBarriers are used in programs in which we have a fixed number of threads that must wait for each other to reach a common point before continuing execution. CyclicBarrier is introduced in Java 5.

*The **barrier** is called cyclic because it can be re-used after the waiting threads are released.*

CyclicBarrier works almost same as CountDownLatch except that we can reuse it. Unlike CountDownLatch, it allows multiple threads to wait for each other using await() method (known as barrier condition) before invoking final task.

Note: *Phaser is a more flexible solution than CyclicBarrier and CountDownLatch ??? used to act as a reusable barrier on which the dynamic number of threads need to wait before continuing execution. We can coordinate multiple phases of execution, reusing a Phaser instance for each program phase.*

</br>

### ||| **Semaphore and Mutex**

</br> [Table Of Contents](#table-of-contents) </br>

**Semaphore:**

Semaphore introduced in Java 5. A semaphore maintains/contains set of permits. We can use semaphores to limit the number of concurrent threads accessing a specific resource. Semaphore is used for blocking thread level access to some part of physical or logical resource. Semaphore is **signaling mechanism**.

Whenever a thread tries to enter a critical section, it needs to check semaphore, if a permit is available or not. If permit is not available (via tryAcquire()), the thread is not allowed to jump into the critical section. However, if the permit is available the access is granted, and the permit counter decreases. Once the executing thread releases critical section, again the permit counter increases (done by release() method).

We can specify a timeout for acquiring access by using the *tryAcquire(long timeout, TimeUnit unit)* method. We can also check the number of available permits or the number of threads waiting to acquire the semaphore.

Apache commons *TimedSemaphore*: TimeSemaphore allows number of permits as a simple Semaphore but in a given period of time. After this period, the time reset and and all permits are released.

There are mainly two types of semaphores i.e.**counting semaphores** and **binary semaphores**.

Counting Semaphores are integer value semaphores and have an unrestricted value domain. These semaphores are used to coordinate the resource access, where the semaphore count is the number of available resources.

The binary semaphores are like counting semaphores but their value is restricted to 0 and 1. The wait operation only works when the semaphore is 1 and the signal operation succeeds when semaphore is 0.

**Mutex:**

A mutex (mutual exclusion) is the simplest type of synchronizer. It ensures that only one thread can execute the critical section of a computer program at a time. A mutex is a **locking mechanism** used to synchronize access to a resource.

It is used when: </br>

- Multiple threads accessing shared memory with write and read access.
- Multiple processes accessing a common resource (such as printer and camera etc.)

To access a critical section, a thread acquires the mutex, then accesses the critical section and finally releases the mutex. In the mean time, all other threads block till the mutex releases. As soon as a thread exits the critical section, another thread can enter the critical section.

There are various ways, we can implement a **mutex** in Java.

- **Using synchronized keyword**: </br>
  It is the simplest way to implement a mutex in Java. Every object in Java has an **intrinsic lock** associated with it. The synchronized method and the synchronized block use this intrinsic lock to restrict the access of the critical section to only one thread at a time. Therefore, when a thread invokes a synchronized method or enters a synchronized block, it automatically acquires the lock. The lock releases when the method or block completes or an exception is thrown from them.
- **Using ReentrantLock** </br>
  The ReentrantLock introduced in Java 5. It provides more flexibility and control than synchronized keyword approach to achieve *mutual exclusion*. </br>
  
  Note: *Alternatively, the Monitor class of Google's Guava library is a better alternative to the ReentrantLock class. As per its documentation, code using Monitor is more readable and less error-prone than the code using ReentrantLock.*

- **Using Semaphore** </br>
  While in case of a mutex only one thread can access a critical section, Semaphore allows a fixed number of threads to access a critical section. Therefore, we can also implement a mutex by *setting the number of allowed threads in a Semaphore to one*. Here mutex acts similarly to a binary semaphore.

**Mutex Vs. Semaphore** </br>

Mutex: </br>

1. It is a mutual exclusion object that synchronizes access to a resource.
2. The Mutex is a **locking mechanism** that makes sure only one thread can acquire the Mutex at a time and enter the critical section. This thread only releases the Mutex when it exits the critical section.
3. A Mutex is different than a semaphore as it is a locking mechanism while a semaphore is a signalling mechanism.
4. A binary semaphore can be used as a Mutex but a Mutex can never be used as a semaphore.

Semaphore: </br>

1. A semaphore is a **signalling mechanism** and a thread that is waiting on a semaphore can be signaled by another thread.
2. This is different than a mutex as the mutex can be signaled only by the thread that called the wait function.
3. A semaphore uses two atomic operations, wait and signal for process synchronization.

</br>

#### **Locks**

</br> [Table Of Contents](#table-of-contents) </br>

Locks are introduced in Java 5. Lock is a utility for blocking other threads from accessing a certain segment of code, apart from the thread that's executing it currently. A Lock is more flexible and sophisticated thread synchronization mechanism than the standard synchronized block.

**Lock Vs. Synchronized block:**

- A synchronized block is fully contained within a method ??? we can have Lock API's lock() and unlock() operation in separate methods.

- A synchronized block doesn't support the fairness. In Locks, any thread can acquire the lock once released, no preference can be specified. We can achieve fairness within the Lock APIs by specifying the fairness property. It makes sure that longest waiting thread is given access to the lock.

- A thread gets blocked if it can't get an access to the synchronized block. The Lock API provides tryLock() method. The thread acquires lock only if it's available and not held by any other thread. This reduces blocking time of thread waiting for the lock.

- A thread which is in ???waiting??? state to acquire the access to synchronized block, can't be interrupted. The Lock API provides a method *lockInterruptibly()* which can be used to interrupt the thread when it's waiting for the lock.

A locked instance should always be unlocked to avoid deadlock condition. A recommended approach to use the lock that it should contain a *try/catch* and *finally* block.

**Lock interface methods**: *lock(), lockInterruptibly(), tryLock(), tryLock(long timeout, TimeUnit timeUnit), unlock()*

**ReadWriteLock interface**: It maintains a pair of locks, one for read-only operations and one for the write operation.  The read lock may be simultaneously held by multiple threads as long as there is no write. It has the following methods i.e. *Lock readLock()*, *Lock writeLock()*

**Lock Implementations:**

- **ReentrantLock:** ReentrantLock offers the same concurrency and memory semantics, as the implicit monitor lock accessed using synchronized methods and statements, with extended capabilities.
  
- **ReentrantReadWriteLock:** ReentrantReadWriteLock implements ReadWriteLock interface and it can provides two different locks such as readLock() and writeLock(). If no thread acquired the write lock or requested for it then multiple threads can acquire the read lock. If no threads are reading or writing then only one thread can acquire the write lock.

- **StampedLock:** StampedLock is introduced in Java 8. It also supports both read and write locks. However, lock acquisition methods return a stamp that is used to release a lock or to check if the lock is still valid. Another feature provided by StampedLock is optimistic locking. Most of the time read operations don't need to wait for write operation completion and as a result of this, the full-fledged read lock isn't required. Instead, we can upgrade to read lock.

</br>

**Locks with Conditions:**

The Condition class provides the ability for a thread to wait for some condition to occur while executing the critical section. This can occur when a thread acquires the access to the critical section but doesn't have the necessary condition to perform its operation.

Traditionally Java provides wait(), notify() and notifyAll() methods for thread intercommunication. Conditions have similar mechanisms, but in addition, we can specify multiple conditions.

</br>

#### **Phaser**

</br> [Table Of Contents](#table-of-contents) </br>

Phaser is very similar to CountDownLatch that allows us to co-ordinate execution of threads. In comparison to CountDownLatch, Phaser has some additional functionality. The Phaser is a barrier on which the dynamic number of threads need to wait before continuing execution. In the CountDownLatch that number cannot be configured dynamically and needs to be supplied when we're creating the instance.

The Phaser allows us to build logic in which threads need to wait on the barrier before going to the next step of execution. We can coordinate multiple phases of execution, reusing a Phaser instance for each program phase. Each phase can have a different number of threads waiting for advancing to another phase.

To participate in the coordination, the thread needs to register() itself with the Phaser instance. The thread signals that it arrived at the barrier by calling the arriveAndAwaitAdvance(), which is a blocking method. When the number of arrived parties is equal to the number of registered parties, the execution of the program will continue, and the phase number will increase. When the thread finishes its job, we should call the arriveAndDeregister() method to signal that the current thread should no longer be accounted for in this particular phase.

Note: *Phaser is a more flexible solution than CyclicBarrier and CountDownLatch ??? used to act as a reusable barrier on which the dynamic number of threads need to wait before continuing execution. We can coordinate multiple phases of execution, reusing a Phaser instance for each program phase.*

</br>

#### **volatile**

</br> [Table Of Contents](#table-of-contents) </br>

The Java volatile keyword guarantees visibility of changes to variables across threads.

In absence of necessary synchronizations, the compiler, runtime or processors may apply several optimizations such as *Caching, re-ordering* in concurrent contexts. JVM provides many ways to provide **memory-order** and **volatile** keyword is one among them.

The Java volatile keyword is used to mark a Java variable as "*being stored in main memory*". More precisely that means, that every read of a volatile variable will be read from the computer's main memory (such as RAM and ROM), and not from the CPU cache (e.g: L1 and L2 Cache), and that every write to a volatile variable will be written to main memory, and not just to the CPU cache.

Declaring a *volatile* variable guarantees the visibility for other threads of writes to that variable. Visibility means that changes made by one thread to the shared data are visible to other threads to maintain **data consistency**. It follows **Happens-Before** ordering meaning, the values that were visible to A before writing the volatile variable will be visible to B after reading the volatile variable. *Any write to volatile field happens before every subsequent read of the same field. This is the volatile variable rule of the Java Memory Model.*

Multi threaded applications require *mutual exclusion, visibility* to ensure consistent behaviour. Synchronized methods and blocks provide both properties but at the cost of application performance. volatile provides visibility but not mutual exclusion. volatile is useful where multiple threads executing a block of code in parallel(no mutual exclusion) and we need to ensure visibility property.

</br>

### **Dead Lock**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

Deadlocks occur in a situation when a thread is waiting for an object lock that is acquired by another thread and second thread is waiting for an object lock that is acquired by first thread. Because both threads are waiting for each other to release the lock hence the scenario is called 'deadlock'.

#### ||| **Solutions:**

</br> [Table Of Contents](#table-of-contents) </br>

1. Avoid nested locks: Avoid locking another resource if you already hold one resource. It???s almost impossible to get deadlock situation if you are working with only one object lock.

2. Lock only what is Required: Lock only what is required: Acquire lock only on the resources that is required.

3. Avoid indefinite waits:  deadlock can occur when two threads are waiting for each other to finish indefinitely using thread join(). If a thread has to wait for another thread to finish, it???s always best to use join() with maximum time you want to wait for thread to finish.
</>
</br>

### **Fairness - Starvation - Deadlock**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

**Fairness -> Starvation -> Deadlock**

Fairness: A system is fair when each thread gets enough access to limited resource to make reasonable progress. A fair system prevents starvation and deadlock. If several concurrent threads are competing for resources, we must take precautions to ensure fairness.

Starvation: Starvation occurs when one or more threads in your program is blocked from gaining access to a resource and thus cannot make progress.

Deadlock: Deadlock is the ultimate form of starvation; it occurs when two or more threads are waiting on a condition that cannot be satisfied. Deadlock most often occurs when two (or more) threads are each waiting for the other(s) to do something.
</br>
</br>

### **Inter-Thread Communication (wait/nofify/notifyAll)**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

Inter thread communication/co-operation is all about synchronized threads to communicate with other. It is a mechanism in which a thread is paused running in its critical section and another thread allowed to enter (or lock) in the same critical section to be executed.

It can be achieved by Object's methods: wait(), notify(), notifyAll()

Note: Before Java 5, famous producer-consumer problem can be solved by using wait(), notify() methods but introduction of BlockingQueue has made it easy.

Wait(): Causes current thread to release lock and wait until another thread invokes notify()/notifyAll method or specified time elapses. The current thread must own this object's lock meaning it must be called from synchronized method/block otherwise it throws exception.

notify(): Wakes up the waiting thread on this object's lock. If any threads are waiting on this object, one of them is chosen to be awakened.

**Wait() vs. Sleep():** </br>
Wait() is a non-static method which **releases the lock** and is part of Object class. It should be notified by notify/notifyAll() methods.
Sleep() is a static method which **doesn't release lock** and is part of Thread class. Thread wakes up after sleep time elapsed.

If we have a set of threads that communicate with each other and resemble one of the common patterns, we can simply reuse the appropriate library classes (also called **Synchronizers**) instead of trying to come up with a custom scheme using a set of locks and condition objects and the synchronized keyword. *CyclicBarrier, Phaser, CountDownLatch, Exchanger, Semaphore and SynchronousQueue offer out of the box functionality for common interaction patterns between threads.*
</br>
</br>

### **Bounded-Buffer (producer-consumer) Problem**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

In computing, **bounded-buffer** or **producer-consumer** problem is a classic example of multi-process synchronization problem. The problem describes two processes, producer and consumer, which share a common, fixed-size buffer used as a queue.

- The producer's job is to generate data, put it into buffer and start again.
  
- At the same time, consumer is consuming the data (i.e. removing it from buffer), one piece at a time.

- Before Java 5, producer-consumer problem can be solved using wait() and notify() methods but introduction of BlockingQueue has made it very easy.

**BlockingQueue** can be used to provide solution for this *bounded-buffer* problem.

</br>

#### ||| **BlockingQueue**

</br> [Table Of Contents](#table-of-contents) </br>

**BlockingQueue** that supports operations that wait for the Queue to become non-empty when retrieving and removing an element and wait for space to become available in the Queue when adding an element.

BlockingQueue doesn't accept NULL values and throws NullPointerException if tried.

BlockingQueue is thread-safe and all queuing methods are atomic in nature, it uses internal locks or other forms of concurrency control.

We don???t need to worry about waiting for the space to be available for producer or object to be available for consumer in BlockingQueue because it???s handled by implementation classes of BlockingQueue. BlockingQueue implementations are *ArrayBlockingQueue,  LinkedBlockingQueue, PriorityBlockingQueue, DelayQueue, SynchronousQueue* etc.

Note: ***DelayQueue** is unbounded blocking queue of delayed elements.*

There are two types of **BlockingQueue**:

**unbounded Queue**: </br>
Unbounded blocking queues can grow almost indefinitely. The capacity of blocking queue will be set to MAX_VALUE and all operations that add an element to the unbounded queue will never block, thus it could grow to a very large size.

*The most important thing when designing a producer-consumer program using unbounded BlockingQueue is that consumers should be able to consume messages as quickly as producers are adding messages to the queue. Otherwise, the memory could fill up and we would get an OutOfMemory exception.*

**bounded Queue**: </br>

We can create such queues with maximum capacity defined. If we have a blockingQueue that has a capacity equal to 10, It means that when a producer tries to add an element to an already full queue, depending on a method that was used to add it (offer(), add() or put()), it will block until space for inserting object becomes available. Otherwise, the operations will fail.

*Using bounded queue is a good way to design concurrent programs because when we insert an element to an already full queue, that operations need to wait until consumers catch up and make some space available in the queue. It gives us throttling without any effort on our part.*

</br>

#### ||| **DelayQueue**

</br> [Table Of Contents](#table-of-contents) </br>

DelayQueue is infinite size (unbounded) blocking queue of delayed elements. With DelayQueue, an element can only be pulled if it's expiration time (known as user defined delay) is completed. Hence, the topmost element (head) will have most amount of delay and it will be pulled last.

When a consumer wants to take an element from the queue, it can take only when the delay for that particular element has expired.

it???s getDelay() method return a zero or negative value which indicate that the delay has already elapsed.

DelayQueue is a specialized *PriorityQueue* that orders elements based on their delay time and internally it uses ReentrantLock mechanism.

</br>

### **Thread dump analysis**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- **VisualVM Profiler:** If you are analyzing application for slowness, you must use a profiler. We can generate thread dump for any process using VisualVM profiler very easily.

- **jstack:** Java comes with jstack tool through which we can generate thread dump for a java process. e.g: jstack PID  (Use ps -eaf | grep java to get PID).

- **jcmd:** Java 8 has introduced jcmd utility. You should use this instead of jstack if we are on Java 8 or higher. Command to generate thread dump using jcmd is jcmd PID Thread.print.

</br>

### **Dining philosophers problem**

</br> [Table Of Contents](#table-of-contents) </br>

Standard Implementation: https://www.baeldung.com/java-dining-philoshophers </br>
Using Java 8: https://www.bruceeckel.com/2016/12/29/dining-philosophers-in-java-8/ (Using BlockingQueue, ScheduledExecutorService) </br>

</br>

</div>
