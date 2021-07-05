package collections;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.stream.Stream;

/*
Iterable<E>: 
- interface represents a collection of objects which is iterable. 
- It provides forEach(), iterator(), spliterator() methods. 
- It is the root interface for Collection interface.

Iterator<E>:
- Iterator interface provides methods to iterate collection in forward direction.
- It supports removing element from collection.
- It improved method names compared to Enumeration (Enumeration predated to Iterator). 
- Enumeration also can be converted to iterator using Enumeration.asIterator().
- It can traverse elements individually.

ListIterator<E>:
- ListIterator interface provides methods to iterate collection in bi-directional 
   fashion but individual elements only (no bulk operations as supported in Spliterator).
- ListIterator extends Iterator interface.

Spliterator<T>:
- Spliterator introduced in Java 8.
- It can traverse elements individually and support bulk operations.
- It can be obtained from source i.e. stream etc.
- It can be used to partition some elements and support parallel operations.

Performance of Iterators
------------------------

When iterating a collection 'lots of times in a tight loop' (iterating a list 
    'thousands of times per second'), Iterator's forEach() loop is 'slower than' 
    standard for loop. 

Because each iteration will call the List iterator() method, which will create a new Iterator 
    object. Creating a new object thousands perhaps even millions of times per second does have 
    a small performance penalty compared to just iterating the List using a standard for-loop. 
    For most standard business applications where collections are iterated occasionally, this 
    performance difference is irrelevant. It only matters for very tight loops that are executed 
    thousands of times per second.
*/
public class AllIterators {
    public static void main(String[] args) {
        List<String> alphabets = new ArrayList<>();
        alphabets.add("A"); alphabets.add("C"); alphabets.add("B");

        // Iterator
        Iterator<String> iterator = alphabets.iterator();
        iterator.forEachRemaining(value -> System.out.println("value=" + value));

        // ListIterator
        ListIterator<String> literator = alphabets.listIterator();
        while(literator.hasNext()) 
            System.out.println("Forward: value = " + literator.next());
        
        while(literator.hasPrevious())
            System.out.println("Backward: value = " + literator.previous());

        // Spliterator
        List<Integer> numbers = new ArrayList<>();
        numbers.add(101); numbers.add(201);
        numbers.add(301); numbers.add(401);
        numbers.add(501); numbers.add(601);

        Stream<Integer> stream = numbers.stream();
        Spliterator<Integer> splitr = stream.spliterator();
        System.out.println(splitr.estimateSize());
        System.out.println(splitr.getExactSizeIfKnown());
        System.out.println(splitr.characteristics());
        System.out.println(splitr.hasCharacteristics(splitr.characteristics()));

        Spliterator<Integer> another_splitr = splitr.trySplit();
        
        if(another_splitr != null) {
            System.out.println("Output from another_splitr:");
            another_splitr.forEachRemaining(System.out::println);
        }
        System.out.println("Output from splitr: ");
        splitr.forEachRemaining(System.out::println);
    }
}