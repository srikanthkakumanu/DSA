package concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * 1. ThreadFactory introduced in Java 5. ThreadFactory acts as a thread(non-existing) pool 
 *      which creates a new thread on demand. 
 * 
 * 2. It eliminates the need of lot of boilerplate code for implementing efficient thread 
 *      creation mechanisms.
 */
public class TheThreadFactory {
    public static void main(String[] args) {
        CoffeeThreadFactory factory = new CoffeeThreadFactory("COFFEE");
        for(int i = 0; i < 4; i++) {
            factory.newThread(new Runner("COFFEE WORLD", 300L)).start();
        }
    }
}

class CoffeeThreadFactory implements ThreadFactory {
    private Integer id;
    private String name;

    public CoffeeThreadFactory(String name) {
        this.id = 1; this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread newThread = new Thread(r, name + "-Thread_" + id);
        id++;
        return newThread;
    }  
}