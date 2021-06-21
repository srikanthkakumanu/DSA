# **Java Concurrency** (Multi-threading)

<div style="text-align: justify">

Table of Contents:

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
    3.5 [Fork & Join Pool Framework](#-fork--join-pool-framework) TODO: </br>

4. [**Thread Grouping**](#thread-groups) </br>

5. [**Thread Safety**](#thread-safety)</br>
   5.1 [Thread Synchronization](#thread-synchronization)</br>
   5.2 [ThreadLocal](#thread-local) </br>
   5.3 [CountdownLatch](#countdownlatch) </br>
   5.4 [CyclicBarrier](#cyclicbarrier) </br>
   5.5 [Semaphore and Mutex](#semaphore-and-mutex) </br>

6. [**Dead Lock**](#dead-lock) </br>
   6.1 [Solutions](#solutions)</br>

7. [**Fairness - Starvation - Deadlock**](#fairness---starvation---deadlock) </br>

8. [**wait() + notify() + notifyAll** (Inter-thread Communication)](#inter-thread-communication-waitnofifynotifyall)</br>

9. [Bounded-Buffer (producer-consumer) problem solution using BlockingQueue](#bounded-buffer-producer-consumer-problem) <br>

10. [Semaphore]() TODO </br> 
    10.1 [mutex vs. semaphore]() TODO </br>
11. [Thread Dump Analysis](#thread-dump-analysis) </br>

</br>
</br>

## **Multi tasking**

<HR>

Multi tasking is a process of executing multiple tasks simultaneously. We use multi tasking to utilise the CPU.

Multi tasking can achieved in two ways:

* Multi-processing
* Multi-threading

### ||| **Multi processing**

A process is heavy weight and each process has an address in memory, each process allocates a separate memory area and cost of inter-process communication (IPC) is high. Switching (i.e. context-switching) from one process to another requires some time for saving and loading registers, memory maps, updating lists, etc.
</br>
</br>

## **Multi threading**

<HR>

A thread is light weight, threads share same memory space and IPC communication between threads is low. Note that at least one process is required for each thread. At a time, only one thread can run in a single process. Context switching between threads is done by OS as well in order to emulate parallelism. In Java, threads are mapped to system-level threads which are operating system's resources.

<U>Caution:</U>

* If threads are created un-controllably, we may run out of resources quickly.
* More threads we spawn, the less time each thread spends doing actual work.
</br>
</br>

### **Thread Scheduling**

<HR>

Thread scheduling is part of JVM and JVM uses native OS threads and thus relies on OS scheduler to perform thread scheduling mechanism. Thread scheduling algorithms are platform (OS) dependant and implemented using different algorithms on different OS. Java uses these algorithms of underlying Operating System.

However scheduling comes in two flavors:

* pre-emptive scheduling
* time slicing (round-robin)

#### ||| **pre-emptive scheduling**

Thread scheduler schedules the threads according to thread priority. But it is not guaranteed as it is dependant on JVM specification and OS scheduler that chooses thread scheduling type.
</br>
</br>

#### ||| **Time Slicing Scheduling**

Many systems implement time slicing of threads. In a time-slicing system, threads processing is chopped up so that each thread runs for a short period of time before the context is switched to next thread. Higher priority threads still pre-empt lower priority threads in this scheme. Time slicing mixes up the threads processing of same priority on multi-process machine and threads may even be run simultaneously. This can introduce a difference in behaviour for applications that don't use threads and synchronization properly.</br>

Since Java doesn't guarantee time slicing, we shouldn't write code that relies on this type of scheduling.</br>

#### ||| **TimerTask**

</br>

**java.util.Timer** is a utility class that can be used to schedule a thread to be executed at certain time in future. Timer class can be used to schedule a task to be run one-time or at regular intervals.

Timer class thread-safe and does not need explicit synchronization. Timer class is used for scheduling tasks i.e. internally it uses wait(), notify() to schedule the tasks.

java.util.TimerTask is an abstract class that implements Runnable interface. This is used for creating a custom task.

Timer uses java.util.TaskQueue to add tasks at given regular intervals any time and there can be only one thread running the TimerTask.

</br>

### **Thread Pooling**

<HR>

Thread pooling represents group of threads that are waiting for the job and re-use many times. Thread pooling can be achieved by Executor framework(built-in thread pool framework). It gives 'better performance' as it saves time because there is no need to create new thread. It solves the problem of thread cycle overhead and resource thrashing.

#### ||| **Thread Pooling - Advantages**

1. Thread Pooling reduces response time by avoiding thread creation i.e. thread cycle overhead during request or task processing.

2. Thread Pooling allows you to change your execution policy as you need. you can go from single thread to multiple thread by just replacing ExecutorService implementation.

3. Thread Pooling in Java application increases stability of system by creating a configured number of threads decided based on system load and available resource.

4. Thread Pool frees application developer from thread management stuff and allows to focus on business logic.

#### ||| **Thread Pooling - Risks**

1. Deadlock: thread pools introduce another case of deadlock, one in which all the executing threads are waiting for the results from the blocked threads waiting in the queue due to the unavailability of threads for execution.

2. Thread leakage: Thread Leakage occurs if a thread is removed from the pool to execute a task but not returned to it when the task completed. As an example, if the thread throws an exception and pool class does not catch this exception, then the thread will simply exit, reducing the size of the thread pool by one. If this repeats many times, then the pool would eventually become empty and no threads would be available to execute other requests.

3. Resource Thrashing: If the thread pool size is very large then time is wasted in context switching between threads. Having more threads than the optimal number may cause starvation problem leading to resource thrashing.

#### ||| **Thread pool tuning**

</br>

The optimum size of the thread pool depends on the number of processors available and the nature of the tasks. On a N processor system for a queue of only computation type processes, **N*(1+W/S)** for maximum efficiency.</br>
N or N+1: maximum thread pool size.</br>
W: ratio of waiting time.</br>
S: service time for a request.</br>
</br>

#### ||| **Thread Pooling - Executor Framework**

</br>

Executor framework gives the developer the ability to control the number of generated threads and the granularity of tasks that should be run by separate threads. The best use case for ExecutorService is the processing of independent tasks, such as transactions or requests according to the scheme “one thread for one task.” But fork/join framework, despite the simplicity and frequent performance gains associated with fork/join, it reduces developer control over concurrent execution.

The shutdown() method doesn't cause immediate destruction of the ExecutorService. It will make the ExecutorService stop accepting new tasks and shut down after all running threads finish their current work. But shutdownNow() tries to destroy the ExecutorService immediately, but it doesn't guarantee that all the running threads will be stopped at the same time.

**Executor:** interface for executing/launching new tasks(thread - which implements Runnable). Using Executor interface, we can decouple the task execution flow from the actual task execution mechanism. Executor does not 'strictly' require the task execution to be asynchronous.

**ExecutorService:** sub-interface for Executor, that simplifies running tasks in asynchronous mode. It is a complete solution for asynchronous processing. It manages an 'In-Memory' queue and schedules submitted tasks based on thread availability. It automatically provides a pool of threads and an API for assigning tasks to it. It contains features that help manage life cycle, both of the individual tasks and executor itself.

**ScheduledExecutorService:** sub-interface for ExecutorService, supports Future<T> and/or periodic execution of tasks.

**ThreadPoolExecutor & ScheduledThreadPoolExecutor:** ThreadPoolExecutor consist of Task Queue(BlockingQueue) and Thread Pool(Set of Worker) which have better performance and API to handle async tasks.

**Executors:** Executors is a helper class that contains several methods for the creation of pre-configured thread pool instances. It contains Factory and helper methods for Executor, ExecutorService, ScheduledExecutorService, ThreadFactory and Callable classes/interfaces.

**Future:** Future is used to represent the result of an asynchronous operation.

**FutureTask:** FutureTask is base concrete implementation of Future interface and provides asynchronous processing. It contains the methods to start and cancel a task and also methods that can return the state of the FutureTask as whether it’s completed or cancelled. We need a callable object to create a future task and then we can use Java Thread Pool Executor to process these asynchronously. i.e. FutureTask requires a Callable object. FutureTask comes handy when we want to override some of Future interface methods and don’t want to implement every method of Future interface.
</br>
</br>

#### ||| **Fork & Join Pool Framework**

</br>
</br>

### **Thread Groups**

<HR>

In Java, we can group multiple threads in a single object and we can suspend, resume or interrupt these grouped threads by a single call. A ThreadGroup represents set of threads and a ThreadGroup can also include another ThreadGroup. ThreadGroup creates a tree in which every thread group except the initial thread group has a parent.

Every Java thread is a member of a thread group. Thread groups provide a mechanism for collecting multiple threads into a single object and manipulating those threads all at once, rather than individually. Runtime system puts a thread into a thread group during thread construction. If no thread group is specified, it assigns the thread to **a default thread group(current thread group)**. Thus that thread becomes permanent member of that thread group, you cannot move a thread to a new group after that thread has been created.

**Note**: suspend(), resume(), stop() methods are deprecated.

A thread is allowed to access information about its own thread group, but it cannot access the information about its thread group's parent thread group or any other thread groups.

</br>
</br>

### **Thread Safety**

<HR>

In Java, multitple threads created from same object shares object variables and this can lead to **data inconsistency** when threads are used to read and update shared data.

There are different thread safety approaches are used to avoid the data inconsistency scenarios.

1. Synchronization: class/method/block/static method Synchronization </br>
2. Use of atomic classes from java.util.concurrent.atomic e.g: AtomicInteger </br>
3. Use of locks from java.util.concurrent.locks </br>
4. Using thread safe collection classes e.g: ConcurrentHashMap </br>
5. Using volatile keyword (make threads read data from main memory but not from thread cache). </br>

#### ||| **Thread Synchronization**

</br>

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

</br>

1. ThreadLocal is used to create thread local variables.

2. We can use synchronization for thread safety but if we want to avoid synchronization, then we can use ThreadLocal variables.

3. Every thread has it's own ThreadLocal variable.

4. ThreadLocal instances are typically private static fields in classes that wish to associate state with a thread.
</br>
</br>

#### ||| **CountDownLatch**

</br>

CountDownLatch is introduced in Java 5. It is a utility class and a type of synchronizer which blocks set of threads until some operation completes. Essentially, by using a CountDownLatch we can cause a thread to block until other threads have completed a given task. A CountDownLatch has a **counter** field, which you can decrement as we require. We can then use it to block a calling thread until it's been counted down to zero.

</br>

#### ||| **CyclicBarrier**

</br>

A CyclicBarrier is a synchronizer or synchronization construct that allows a set of threads to wait for each other to reach a common execution point, also called a **barrier**. CyclicBarriers are used in programs in which we have a fixed number of threads that must wait for each other to reach a common point before continuing execution. CyclicBarrier is introduced in Java 5.

*The **barrier** is called cyclic because it can be re-used after the waiting threads are released.*

CyclicBarrier works almost same as CountDownLatch except that we can reuse it. Unlike CountDownLatch, it allows multiple threads to wait for each other using await() method (known as barrier condition) before invoking final task.

</br>

### ||| **Semaphore and Mutex**

</br>

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

### **Dead Lock**

<HR>

Deadlocks occur in a situation when a thread is waiting for an object lock that is acquired by another thread and second thread is waiting for an object lock that is acquired by first thread. Because both threads are waiting for each other to release the lock hence the scenario is called 'deadlock'.

#### ||| **Solutions:**

1. Avoid nested locks: Avoid locking another resource if you already hold one resource. It’s almost impossible to get deadlock situation if you are working with only one object lock.

2. Lock only what is Required: Lock only what is required: Acquire lock only on the resources that is required.

3. Avoid indefinite waits:  deadlock can occur when two threads are waiting for each other to finish indefinitely using thread join(). If a thread has to wait for another thread to finish, it’s always best to use join() with maximum time you want to wait for thread to finish.
</>
</br>

### **Fairness - Starvation - Deadlock**

<HR>

**Fairness -> Starvation -> Deadlock**

Fairness: A system is fair when each thread gets enough access to limited resource to make reasonable progress. A fair system prevents starvation and deadlock. If several concurrent threads are competing for resources, we must take precautions to ensure fairness.

Starvation: Starvation occurs when one or more threads in your program is blocked from gaining access to a resource and thus cannot make progress.

Deadlock: Deadlock is the ultimate form of starvation; it occurs when two or more threads are waiting on a condition that cannot be satisfied. Deadlock most often occurs when two (or more) threads are each waiting for the other(s) to do something.
</br>
</br>

### **Inter-Thread Communication (wait/nofify/notifyAll)**

<HR>

</br>

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

</br>

In computing, **bounded-buffer** or **producer-consumer** problem is a classic example of multi-process synchronization problem. The problem describes two processes, producer and consumer, which share a common, fixed-size buffer used as a queue.

- The producer's job is to generate data, put it into buffer and start again.
  
- At the same time, consumer is consuming the data (i.e. removing it from buffer), one piece at a time.

- Before Java 5, producer-consumer problem can be solved using wait() and notify() methods but introduction of BlockingQueue has made it very easy.

BlockingQueue that supports operations that wait for the Queue to become non-empty when retrieving and removing an element and wait for space to become available in the Queue when adding an element.

BlockingQueue doesn't accept NULL values and throws NullPointerException if tried.

BlockingQueue is thread-safe and all queuing methods are atomic in nature, it uses internal locks or other forms of concurrency control.

We don’t need to worry about waiting for the space to be available for producer or object to be available for consumer in BlockingQueue because it’s handled by implementation classes of BlockingQueue. BlockingQueue implementations are *ArrayBlockingQueue,  LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue* etc.

### **Semaphores**

<HR>

#### **mutex vs. semaphores**

</br>

### **Thread dump analysis**

<HR>

</br>

- **VisualVM Profiler:** If you are analyzing application for slowness, you must use a profiler. We can generate thread dump for any process using VisualVM profiler very easily.

- **jstack:** Java comes with jstack tool through which we can generate thread dump for a java process. e.g: jstack PID  (Use ps -eaf | grep java to get PID).

- **jcmd:** Java 8 has introduced jcmd utility. You should use this instead of jstack if we are on Java 8 or higher. Command to generate thread dump using jcmd is jcmd PID Thread.print.


</div>
