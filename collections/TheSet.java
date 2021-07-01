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
        Collection<String> one = Stream.of("A", "B", "C", "D")
                                        .collect(Collectors.toCollection(HashSet::new));
        
        Collection<String> two = Stream.of("A", "B", "E", "F")
                                        .collect(Collectors.toCollection(HashSet::new));

        Set<String> union = new HashSet<>(one);
        // addAll: union set has elements that are present in either sets i.e. one OR two
        union.addAll(two); 
        System.out.println("ONE: " + one + " TWO: " + two + " = addAll(UNION): " + union);  

        Set<String> intersection = new HashSet<>(one);
        // retainAll: intersection set has elements commonly present in both sets i.e. one AND two
        intersection.retainAll(two); 
        System.out.println("ONE: " + one + " TWO: " + two + " = retainAll(INTERSECTION): " + intersection);  

        Set<String> asymmetricDiff = new HashSet<>(one);
        // removeAll: asymmetricDiff set has difference elements that are present in one but not in two i.e. one - two
        asymmetricDiff.removeAll(two); 
        System.out.println("ONE: " + one + " TWO: " + two + " = removeAll(ASYMMETRIC-DIFF): " + asymmetricDiff);  

        symmetricDiff(one, two);

    }

    /**
     * the symmetric difference of two sets, also known as the disjunctive union, is the set of elements which are 
     * in either of the sets, but not in their intersection.
     * Ex. A{1,2,3} and B{3,4} is SYMMETRIC_DIFFERENCE{1,2,4}.
     * @param one set
     * @param two set
     */
    private static void symmetricDiff(Collection<String> one, Collection<String> two) {
        Set<String> symmetricDiff = new HashSet<>(one);
        symmetricDiff.addAll(two); // union: symmetricDiff(one) OR two
        Set<String> three = new HashSet<>(one);
        three.retainAll(two); // intersection: three(one) AND two
        symmetricDiff.removeAll(three); // symmetricDiff - three
        System.out.println("ONE: " + one + " TWO: " + two + " THREE: " + three + " = SYMMETRIC-DIFF: " + symmetricDiff);  
    }
}
