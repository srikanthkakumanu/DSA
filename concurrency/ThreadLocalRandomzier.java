package concurrency;

import java.util.concurrent.ThreadLocalRandom;

/*
1. ThreadLocalRandom introduced in Java 7 and it is for generating random numbers in multi-threaded
    environment.

2. Random class is not thread-safe meaning doesn't perform well in multi-threaded environment. It is
    due to contention meaning given that multiple threads share same Random instance. Because Random
    provides numbers globally. Random allows setting seed explicitly.

3. ThreadLocalRandom is a combination of ThreadLocal and Random classes and isolated to the current
    thread. Thus it achieves better performance in a multi-threaded environment by simply avoiding
    any concurrent access to instances of Random. ThreadLocalRandom does not allow setting seed 
    explicitly. ThreadLocalRandom is more efficient in a highly concurrent environment.
*/
public class ThreadLocalRandomzier {
    public static void main(String[] args) {
        Integer unboundedRandomValue = ThreadLocalRandom.current().nextInt();
        // 0 is inclusive lower limit and 100 is exclusive upper limit
        Integer boundedRandomValue = ThreadLocalRandom.current().nextInt(0, 100);
        System.out.println("Intger: unbounded: " + unboundedRandomValue + ", bounded: " + boundedRandomValue);
        // Java 8 introduced nextGaussian()
        // nextGaussian(): generates next normally distributed value with 0.0 (mean) and 1.0 (standard deviation)
        Double distributedValue = ThreadLocalRandom.current().nextGaussian();
        System.out.println("Distributed value: " + distributedValue);
    }
}
