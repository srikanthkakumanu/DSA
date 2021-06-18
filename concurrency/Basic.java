package concurrency;

/**
 * 1. Threads can have different priorities (MIN_PRIORITY, NORMAL_PRIORITY, MAX_PRIORITY).
 *      But it does not guarantee that high priority thread will execute first than lower
 *      priority thread. Thread scheduler is part of OS implementation and when a thread is
 *      is started, it's execution is controlled by Thread scheduler and JVM doesn't have
 *      any control on it's execution. 
 */
public class Basic {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("TG");

        Thread even = new TheThread(group, "EVEN", 3);
        even.setPriority(Thread.MIN_PRIORITY);
        

        Thread odd = new TheThread(group, "ODD", 3);
        odd.setPriority(Thread.MAX_PRIORITY);

        Thread prime = new TheThread(group, "PRIME", 3);
        prime.setPriority(Thread.NORM_PRIORITY);

        even.start();
        odd.start();
        prime.start();

    }
}




