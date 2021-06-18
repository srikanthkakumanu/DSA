package concurrency;

public class Runner implements Runnable {

    private String message = "Hello World!";
    private Long sleep = 100L;

    public Runner(String message) {
        this.message = message;
    }

    public Runner(String message, Long sleep) {
        this.message = message;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " (start) Message: " + message);
        process();
        System.out.println(Thread.currentThread().getName() + " (end)");
    }

    private void process() {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("Exception caught at process(): " + e.getMessage());
        }
    }
}