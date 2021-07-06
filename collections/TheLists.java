package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.StreamSupport;
import java.util.LinkedList;
import java.util.stream.Collectors;

/*
- List represents an 'ordered sequence' of values where some value 'may occur' more than one time. 
    It is an ordered collection (or sequence). Simply an array of sequence with varying length. 
    Internally it follows the insertion order (natural order which is the how the elements are inserted).

- It can contain duplicate elements. We can also insert NULL values.
- Each element in a list has an index and starts with 0. We can also insert an element at specific index. 
    If there are existing elements at that index, those elements will be pushed further down.
- Provides greater control over where each elements is inserted and retrieved by a position (index). 
    In detail, it provides positional access, search for specified element, iteration and range view etc.

Concrete Implementations:
-------------------------
- LinkedList:
- ArrayList: consumes less memory than LinkedList. It is faster than LinkedList. Infact, we should not use LinkedList.
- CopyOnWriteArrayList: it is thread safe and immutable is the best solution for lists.
- Vector: represents a growable array of objects.
- Stack (sub class of Vector): represents a 'Last-In-First-Out (LIFO) stack of objects'. It is a sub class of Vector.

*/
public class TheLists {
    public static void main(String[] args) {
        linkedLists();
        arraylists();
        copyOnWriteArrayLists();
        iteratorToList();
    }

    /**
     * - CopyOnWriteArrayList is introduced in Java 5.
     * - 'CopyOnWriteArrayList' useful in multithread programs when we want to iterate over a list in a 'thread-safe' 
     *       way without an explicit synchronization.
     * - 'thread-safe without synchronization' because when we use any modify methods (add or remove), whole content 
     *       of the list is copied into 'new internal copy'. Due to this fact, 'we can iterate the list safely even 
     *       when concurrent modification is happening'.
     * - It is best choice when we iterating over it more often than we are modifying it.
     * - If adding elements is common operation in a scenario, this is 'not a best choice'. Because the 'additional copies' 
     *       way will definitely lead to 'sub-par performance'.
     * - Removing an element while iterating is not allowed because of data (internal copy) copying mechanism, as it throws 
     *       UnsupportedOperationException.
    */    
    private static void copyOnWriteArrayLists() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 30, 2, 1, 3));
        ListIterator<Integer> literator = list.listIterator(); // it creates internal copy i.e. immutable data list
        list.add(38);
        literator.forEachRemaining(System.out::print); // 38 won't be there in the iterator
        System.out.println();
    }

    /**
     * - ArrayList is 'index' based data structure backed by an array. It implements *List* interface.
     * - Since it is index based, it provides 'random access' to its elements when getting an element, 
     *      hence the performance is equal to 'O(1)'. But insertion, addition and removal operations 
     *      are 'slower' than LinkedList because of the need that resizing the array or update the 
     *      index when an element is added.
     * - It consumes 'less memory' than LinkedList* because it holds only data and it's index.
     * - Adding an element takes ammortized constant time 'O(1)'.
     * - Inserting/deleting element takes 'O(n)'.
     * - Searching an element for unsorted array takes 'O(n)' and 'O(log n)' for sorted array.
     */
    private static void arraylists() {
        System.out.println("---------Array Lists--------------");
        List<Integer> list = List.<Integer>of(1, 2, 3, 4, 5, 6, 6, 4, 7, 8);
        ArrayList<Integer> numbers = new ArrayList<>(list);
        Collections.sort(numbers);
        int index = Collections.binarySearch(numbers, 6); // binary search is efficient when searching for an element
        System.out.println("6 found at : " + index);
        numbers.addAll(List.<Integer>of(9, 10, 11, 12));
        System.out.println();
        numbers.forEach(System.out::print);
        System.out.println();
    }

    /**
     * - LinkedList is a **doubly linked list** implementation of List and Deque interfaces.
     * - It is **ordered**, follows insertion order.
     * - It can have **duplicate** elements. (Because it is a *List* type).
     * - It is slower than *ArrayList* and consumes **more memory** than *ArrayList*. Infact, 
     *      LinkedList is not a right choice to use in present times.
     * - It is not **synchronized**. But we can retrieve a synchronized linked list using `Collections.synchronizedList(new LinkedList(..))`
     * - It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, 
     *      if list is modified then it throws *ConcurrentModificationException*).
     * - Every element is a node that keeps reference to next and previous nodes (It is NOT index based). 
     *      The search operation for an item has execution time equal to **O(n)**, hence it is slower than *ArrayList*. But insertion, addition and removal operations are **faster** because there is no need of resizing an array or update the index when an element is added (because only next, previous elements change when element is added).
     * - It consumes more memory because every node store two references (next and previous node references). 
     *      Whereas ArrayList holds only data and its index.
     * - It is a 'better fit' for constant insertion/deletion time (e.g., frequent insertions/deletions/updates), 
     *      over constant access time and effective memory usage.
     */
    private static void linkedLists() {
        System.out.println("---------Linked Lists--------------");
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "C");
        list.add(1, "B");
        list.add(2, "A");
        list.add("E");
        list.addFirst("D");
        list.addLast("F");
        list.add("D");
        list.add("F");
        list.add("D");
        list.add("F");
        list.add("D");
        list.add("F");
        list.add("D");
        list.add("F");
        list.forEach(System.out::print);
        list.removeFirst();
        list.removeLast();
        System.out.println();
        list.forEach(System.out::print);
        System.out.println();
        list.removeLastOccurrence("F");
        list.removeFirstOccurrence("D");
        list.poll(); // Deque(Queue) operation: retrieves and removes first(head) element
        list.pop(); // Deque(Queue) operation: removes and returns first(head) element
        list.forEach(System.out::print);
        System.out.println();
        list.push("D"); // Deque(Queue) operation: inserts element to head (first) of list
        list.push("F"); 
        list.forEach(System.out::print);
        System.out.println();
    }

    /**
     * Converting an iterator to a list using stream API. (Java 8 approach)
     */
    private static void iteratorToList() {
        Spliterator<Integer> splitr = Arrays.asList(1, 2, 3, 4, 5).spliterator();
        List<Integer> numbers = StreamSupport.stream(splitr, false).collect(Collectors.toList());
        numbers.forEach(System.out::print);
        System.out.println();
    }
}
