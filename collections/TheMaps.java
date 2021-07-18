import java.util.List;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedHashMap;

/**

* 1. Map is 'not a true collection'. It is an object that maps keys to values. It models the mathematical function abstraction.
* 2. A key 'at most' can map to 'one value'.
* 3. It 'cannot' contain 'duplicate keys'.
* 4. It is not advised/permissible for a map to contain itself as a key but can be a value with extreme caution
*       (because equals(),hashCode()methods are not well defined on such map).
* 5. `Unmodifiable maps' can be created using`Map.of(),Map.ofEntries(),Map.copyOf()`static factory methods. 'Unmodifiable maps are
*       do not allow NULL keys and values'. If do,it throws NullPointerException.
* 6. Map implementations internally uses Hash table data structure for `HashMap', Balanced Tree for `TreeMap' and 
*       (Linked List + Hash table) for `LinkedHashMap`.
* 7. An `ordered map` that maps the `keys` in `ascending` order (natural order) or according to 'Comparator' provided 
*       at creation time.

Map interface Implementations
-----------------------------

- HashMap
- LinkedHashMap(sub class of HashMap)
- Hashtable
- Properties (sub class of Hashtable)
- WeakHashMap

NavigableMap (extends SortedMap) interface Implementations
----------------------------------------------------------

- TreeMap

ConcurrentMap interface Implementations
---------------------------------------

- ConcurrentHashMap

ConcurrentNavigableMap (extends NavigableMap + ConcurrentMap) interface implementations
---------------------------------------------------------------------------------------

- ConcurrentSkipListMap

 */
public class TheMaps {
    public static void main(String[] args) throws Exception {
        // hashmaps();
        // linkedHashMaps();
        concurrentHashMaps();
    }

    private static void concurrentHashMaps() throws InterruptedException {
        // calculate parallel sums of 100
        Map<String, Integer> chmap = new ConcurrentHashMap<>();
        int execution_times = 1000;

        List<Integer> sumList = new ArrayList<>(1000);

        for(int outer = 0; outer < execution_times; outer++) {
            chmap.put("test", 0);
            ExecutorService service = Executors.newFixedThreadPool(4);

            for(int inner = 0; inner < 10; inner++) {
                service.execute(() -> { 
                    for(int linner = 0; linner < 10; linner++) {
                        chmap.computeIfPresent("test", (key, value) -> value + 1);
                    }
                });
            }
            service.shutdown();
            service.awaitTermination(5, TimeUnit.SECONDS);
            sumList.add(chmap.get("test"));
        }
        System.out.print(sumList.stream().distinct().count());
        System.out.print(sumList.stream().filter(num -> num != 100).count());
        System.out.println();
    }

    private static void linkedHashMaps() {
        // initial capacity: 16, load factor: .75f, access factor: true
        // initial capacity is initial (default) bucket size i.e. 16
        // initial load factor is default load factor 0.75f
        // access factor is ordering mode/mechanism/strategy. If true, it follows 'access order' otherwise default is 'insertion order'.
        // 16 * 0.75 = 12. It represents that 12th key-value pair of hashmap will keep its size to 16. As soon as 13th element 
        // (key-value pair) will come into the Hashmap, it will increase its size from default 24 = 16 buckets to 25 = 32 buckets.
        
        // Map<String, String> lhmap = new LinkedHashMap<>(16, .75f, true);
        
        // Java 9 alternative approach
        LinkedHashMap<String, String> lhmap = new LinkedHashMap<>(
                                        Map.ofEntries(
                                            new AbstractMap.SimpleEntry<>("08644", "TENALI"),
                                            new AbstractMap.SimpleEntry<>("0866", "VIJAYAWADA"),
                                            new AbstractMap.SimpleEntry<>("0863", "GUNTUR"),
                                            new AbstractMap.SimpleEntry<>("353", "IRELAND"),
                                            new AbstractMap.SimpleEntry<>("040", "HYDERABAD")
                                        )
                                    );
        
        lhmap.put("044", "CHENNAI");
        lhmap.put("080", "BENGALURU");


    }

    /**
     * 1. HashMap uses an internal hash table (not Hashtable) data structure to store elements. 
     *      It acts as a 'binned (bucketed) hash table'. But when bin gets too large, they are 
     *      transformed into bins of TreeNodes. These tree bins are ordered by 'hash code'.
     * 2. The advantage of using HashMap is 'run time performance'. HashMap stores elements in 
     *      so-called 'buckets' and the number of 'buckets' is called 'capacity'. As of Java 8, 
     *      the data structure in which the values inside one bucket are stored is changed from 
     *      a 'list' to a 'balanced tree' if a 'bucket contains 8 or more values', and 'it's changed 
     *      back to a list if, at some point, only 6 values are left in the bucket'. 'This improves 
     *      the performance to be O(log n)'.
     * 3. It allows NULL keys and values.
     * 4. It is not 'ordered' i.e. does not maintain insertion order.
     * 5. It is not 'thread-safe' i.e. not synchronized. 'If multiple threads access it concurrently, 
     *      it must be synchronized externally.'' We can synchronize it during creation time i.e. 
     *      `Map m = Collections.synchronizedMap(new HashMap(...));`
     * 6. It's iterators (Iterator and ListIterator) are 'fail-fast' (After iterator creation, if hash 
     *      set is modified then it throws ConcurrentModificationException).
     * 7. It's space complexity is O(n).
     * 8. put(), get(), remove(), containsKey() - O(1)
     * 9. search for a specific element - O(n). If it is sorted, O(log n)
     * 10. next element - O(h/n) i.e. h is capacity
     */
    private static void hashmaps() {
        // Java 8 approach but performance overhead is the issue
        Map<Integer, String> hashMap = Stream.of(new AbstractMap.SimpleEntry<>(1, "A"), 
                                                new AbstractMap.SimpleEntry<>(2, "B"),
                                                new AbstractMap.SimpleEntry<>(3, "C")
                                                ).collect(
                                                Collectors.toMap(Map.Entry::getKey, 
                                                                Map.Entry::getValue)
                                                );
        hashMap.put(4, "D");
        hashMap.put(5, "FIVE from hashMap");
        hashMap.entrySet().forEach(System.out::println);

        // Java 9 approach   
        Map<Integer, String> hmap = new HashMap<>(Map.ofEntries(
                                        new AbstractMap.SimpleEntry<Integer, String>(1, "ONE"),    
                                        new AbstractMap.SimpleEntry<Integer, String>(2, "TWO")));

        hmap.put(3, "THREE");
        hmap.put(4, "FOUR");
        hmap.put(null, "NULL");
        hmap.entrySet().forEach(System.out::println);                                
        System.out.println(hmap.containsKey(null));
        System.out.println(hmap.containsValue("NULL"));                                               
        System.out.println(hmap.get(null));
        hmap.put(null, "ANOTHER NULL");
        System.out.println(hmap.get(null));
        System.out.println(hmap.getOrDefault(7, "DEFAULT"));
        hmap.putIfAbsent(5, "FIVE");
        System.out.println(hmap.get(5));
        // note: merge and compute both are similar but with different parameter usage
        hmap.forEach((key, value) -> hashMap.merge(key, value, (v1, v2) -> v1.equalsIgnoreCase(v2) ? v1 : v2));
        System.out.println();
        hashMap.entrySet().forEach(System.out::println);                                
        System.out.println();
        hmap.entrySet().forEach(System.out::println);      
        
        // sorting hash map by key
        hmap.entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, String>comparingByKey())
            .forEach(System.out::println);

        hmap.entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, String>comparingByValue())
            .forEach(System.out::println);
        
        // We used LinkedHashMap because it ensures iteration order but HashMap is not.
        Map<Integer, String> result = hmap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
              Map.Entry::getKey, 
              Map.Entry::getValue, 
              (oldValue, newValue) -> oldValue, LinkedHashMap::new));            

    }
}