package collections;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.NavigableSet;

/*
1. ConcurrentSkipListSet introduced in Java 6.
2. ConcurrentSkipListSet is concrete implementation of NavigableSet.
3. 
*/
public class TheNavigableSet {
    public static void main(String[] args) {
        NavigableSet<String> navigable = new ConcurrentSkipListSet<>(Set.<String>of("A", "D", "B", "C"));
        System.out.println(navigable.descendingSet());
        System.out.println(navigable.headSet("B", true));
    }
}
