package collections;

import java.util.Collections;

import java.util.List;
import java.util.LinkedList;

public class TheLists {
    public static void main(String[] args) {
        linkedLists();
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
}
