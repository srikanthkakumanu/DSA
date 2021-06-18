package concurrency;

/**
 * 1. join() method waits for a thread to die. 
 * 2. join() is another mechanism of inter-thread
 *      synchronization.
 * 3. join() dependant on OS for the timing.
 * Note: comment below join() sections to see the result difference
 */
public class Joins {
    public static void main(String[] args) {
        TheThread t1 = new TheThread("T1", 4);
        TheThread t2 = new TheThread("T2", 4);
        TheThread t3 = new TheThread("T3", 4);

        t1.start();
        System.out.println("Current Thread: " + Thread.currentThread().getName());

        try {
            System.out.println("T1 invoking join()");
            t1.join();
            System.out.println("T1 returned from join()");
        } catch (InterruptedException e) {
            System.out.println("error at t1.join(): " + e.getMessage());
        }

        t2.start();
        try {
            System.out.println("T2 invoking join()");
            t2.join();
            System.out.println("T2 returned from join()");
        } catch (InterruptedException e) {
            System.out.println("error at t2.join(): " + e.getMessage());
        }

        t3.start();
    }
}

