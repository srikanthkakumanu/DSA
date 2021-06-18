package concurrency;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 1. ThreadLocal is used to create thread local variables. 
 * 
 * 2. We can use synchronization for thread safety but if we want to avoid
 *      synchronization, then we can use ThreadLocal variables.
 * 
 * 3. Every thread has it's own ThreadLocal variable.
 * 
 * 4. ThreadLocal instances are typically private static fields in classes
 *      that wish to associate state with a thread.
 */
public class ThreadLocals {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(new IamThread(), "T1");
        Thread t2 = new Thread(new IamThread(), "T2");
        Thread t3 = new Thread(new IamThread(), "T3");
        t1.start(); 
        Thread.sleep(new Random().nextInt(1000));
        t2.start(); 
        Thread.sleep(new Random().nextInt(1000));
        t3.start();
    }
    
}
class IamThread implements Runnable {

    private static final ThreadLocal<SimpleDateFormat> formatter = 
                            ThreadLocal.withInitial(() -> { return new SimpleDateFormat("yyyy-MM-dd");}); 

    @Override
    public void run() {
        System.out.println("Thread: " + Thread.currentThread().getName() + " Default Formatter : " + formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            System.out.println("Exception caught at IamThread.run(): " + e.getMessage());
        }
        formatter.set(new SimpleDateFormat());
        System.out.println("Thread: " + Thread.currentThread().getName() + " formatter: " + formatter.get().toPattern());
    }
}
