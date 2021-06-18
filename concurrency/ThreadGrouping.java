package concurrency;

/**
 * 1. In Java, we can group multiple threads in a single object and we can suspend, resume or interrupt
 *      these grouped threads by a single call. A ThreadGroup represents set of threads and a ThreadGroup
 *      can also include another ThreadGroup. ThreadGroup creates a tree in which every thread group 
 *      except the initial thread group has a parent.
 * 
 * 2. Every Java thread is a member of a thread group. Thread groups provide a mechanism for collecting 
 *      multiple threads into a single object and manipulating those threads all at once, rather than
 *      individually. Runtime system puts a thread into a thread group during thread construction. If 
 *      no thread group is specified, it assigns the thread to a default thread group(current thread group).
 *      Thus that thread becomes permanent member of that thread group, you cannot move a thread to a 
 *      new group after that thread has been created. 
 * 
 * 2. Note that suspend(), resume(), stop() methods are deprecated.
 * 
 * 3. A thread is allowed to access information about its own thread group, but it cannot access the 
 *      information about its thread group's parent thread group or any other thread groups.
 * 
 */
public class ThreadGrouping {
    public static void main(String[] args) {

        ThreadGroup group = new ThreadGroup("TG");

        Thread t1 = new Thread(group, new Runner("T1"));
        Thread t2 = new Thread(group, new Runner("T2"));
        Thread t3 = new Thread(group, new Runner("T3"));
        
        t1.start(); t2.start(); t3.start();

        System.out.println("Group Name: " + group.getName());
        group.list();
    }
}
