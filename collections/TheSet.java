package collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*

1. A set is a collection. It models mathematical set abstraction.
2. It is NOT a ordered collection.
3. It cannot have duplicate elements.
4. A set can have at most one NULL element.

HashSet - stores elements in a Hashtable which is **best performing solution** but no ordering guaranteed. 
            It hashes the elements and distributes them into buckets by the hash value.

TreeSet - stores elements in a red-black tree and orders its elements based on their values. It is ordered 
            and navigable. It is slower or worse complexity than HashSet.

LinkedHashSet - Implemented as a Hashtable with linked list,  and orders elements by insertion-order

Symmetric Set Difference: The set of elements contained in either of two sets but not in BOTH.


*/
public class TheSet {
    public static void main(String[] args) {
        hashset();
    }

    private static void hashset() {
        // Java 8 approach
        Collection<String> set = Stream.of("A", "B", "C", "D")
                                        .collect(Collectors.toCollection(HashSet::new));
        
        Collection<String> dups = Stream.of("A", "B")
                                        .collect(Collectors.toCollection(HashSet::new));
        
        set.removeAll(dups);

        System.out.println("Unique alphabets: " + set);
        System.out.println("Duplicate alphabets: " + dups);

        symmetricSetDiff(set, dups);
    }

    private static void symmetricSetDiff(Collection<String> one, Collection<String> two) {
        Set<String> symmetricDiff = new HashSet<>(one);
        symmetricDiff.addAll(two);
        System.out.println("SymmetricDiff-------------");
        symmetricDiff.forEach(System.out::println);
        Set<String> another_set = new HashSet<>(one);
        another_set.retainAll(two);
        symmetricDiff.removeAll(another_set);
        System.out.println("Another Set-------------");
        symmetricDiff.forEach(System.out::println);
    }
}
