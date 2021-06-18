package concurrency;

/**
 * 1. Inter thread communication/co-operation is all about synchronized threads 
 * to communicate with other. It is a mechanism in which a thread is paused running
 * in its critical section and another thread allowed to enter (or lock) in the same
 * critical section to be executed. 
 * 
 * 2. It can be achieved by Object's methods: wait(), notify(), notifyAll()
 * 
 * 3. Wait(): Causes current thread to release lock and wait until another thread 
 *      invokes notify()/notifyAll method or specified time elapses. The current
 *      thread must own this object's lock meaning it must be called from synchronized
 *      method/block otherwise it throws exception.
 * 
 * 4. notify(): Wakes up the waiting thread on this object's lock. If any threads are 
 *      waiting on this object, one of them is chosen to be awakened.
 * 
 * 5. Wait() vs. Sleep(): Wait() releases the lock, is part of Object class, is a non-
 *      static method and should be notified by notify/notifyAll() methods. Sleep() method
 *      doesn't release lock, is part of Thread class, is a static method and wakes up
 *      after sleep time elapsed.
 * 
 * 6. Before Java 5, famous 'bounded-buffer' or 'producer-consumer' problem can be solved by using wait(),
 *      notify() methods but introduction of BlockingQueue has made it easy.  
 */
public class InterThreadWaitNotify {

    public static void main(String[] args) {
        Customer customer = new Customer();
        Runnable withdrawTask = () -> { customer.withdraw(12999); };
        Runnable depositTask = () -> { customer.deposit(5000); };
        Thread withdraw = new Thread(withdrawTask);
        Thread deposit = new Thread(depositTask);
        withdraw.start(); deposit.start();
    }
}
class Customer {
    private Integer balance = 10000;

    synchronized void withdraw(Integer amount) {
        System.out.println("Going to withdraw: " + amount + " and Current Balance: " + balance);
        if(balance < amount) {
            System.err.println("Balance is Low: Waiting for Deposit..");
            try {
                wait();
            } catch (InterruptedException e) { System.err.println("Exception caught at withdraw: " + e.getMessage()); }
        }
        balance -= amount;
        System.out.println("Withdraw completed and current balance: " + balance);
    }

    synchronized void deposit(Integer amount) {
        System.out.println("Going to deposit: " + amount);
        balance += amount;
        System.out.println("Deposit completed and current balance: " + balance);
        notify();
    }
}