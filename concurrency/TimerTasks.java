package concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 1. java.util.Timer is a utility class that can be used to schedule a thread to be executed
 *      at certain time in future. Timer class can be used to schedule a task to be run one-time
 *      or at regular intervals.
 * 
 * 2. Timer class thread-safe and does not need explicit synchronization. Timer class is used for
 *      scheduling tasks i.e. internally it uses wait(), notify() to schedule the tasks.
 * 
 * 3. java.util.TimerTask is an abstract class that implements Runnable interface. This is used
 *      for creating a custom task. 
 * 
 * 4. Timer uses java.util.TaskQueue to add tasks at given regular intervals any time and there
 *      can be only one thread running the TimerTask.
 */

 public class TimerTasks {
     public static void main(String[] args) {
        TimerTask task = new TheTask();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0, 10 * 1000);
        System.out.println("TimerTask started");
        try {
            TimeUnit.MILLISECONDS.sleep(120000);
        } catch(InterruptedException e) {
            System.out.println("Exception caught at scheduleAtFixedRate: " + e.getMessage());
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
        try {
            TimeUnit.MILLISECONDS.sleep(30000);
        } catch(InterruptedException e) {
            System.out.println("Exception caught at scheduleAtFixedRate: " + e.getMessage());
        }
     }
 }

class TheTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("Timer task started at: " + System.currentTimeMillis());
        complete();
        System.out.println("Timer task finished at: " + System.currentTimeMillis());
    } 

    private void complete() {
        try {
            TimeUnit.MILLISECONDS.sleep(20000);
        } catch(InterruptedException e) { 
            System.out.println("Exception caught at TheTimerTask.complete(): " + e.getMessage());
        }
    }
}
