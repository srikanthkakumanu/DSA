# **Java Collections**

<div style="text-align: justify">

## **Table of Contents**

1. [Overview](#overview) </br> 
   1.1 [Benefits](#benefits) </br>
   1.2 [Interface Hierarchy](#interfaces) </br>

2. [Collection](#collection) </br>
   2.1 [List](#list) </br>
   2.2 [Set](#set) </br>
   2.3 [Sorted Set](#sortedset) </br>
   2.4 [Navigable Set](#navigableset) </br>
   2.4 [Queue](#queue) </br>
   2.5 [Deque](#deque) </br>

3. [Iterator + ListIterator + Spliterator + Iterable](#all-about-iterators) </br>
   3.1 [Performance](#performance-of-iterators) </br>

4. [Map](#map) </br>
   4.1. [Sorted Map](#sortedmap) </br>

5. [List Implementations](#list-implementations) </br>
   5.1 [LinkedList](#linkedlist) </br>
   5.2 [ArrayList](#arraylist) </br>
   5.3 [CopyOnWriteArrayList](#copyonwritearraylist) </br>

6. [Set Implementations](#set-implementations) </br>
   6.1 [HashSet](#hashset) </br>

7. [Hashing & hashCode()](#hashing) </br>

## **Overview**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

A collection is a contains that groups multiple elements into a single unit. Collections are used to store, retrieve, manipulate and communicate aggriate data.

A *collections framework* provides the following:

- Interfaces: Abstract data types to represent collections.

- Implementations: Reusable concrete types that implements interfaces.

- Algorithms: They are reusable functionality to perform useful computations such as searching and sorting and they are ***polymorphic** (the same method can be used on many different implementations of the appropriate collection interface)*.

**ORDERING:** ordered means, that you can access the elements in the order they occur in the list.

### **Benefits**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- Reduces programming effort
- Increases program speed and quality
- Allows interoperability among unrelated APIs
- Reduces effort learn and use new APIs
- Reduces effort to design new APIs
- Fosters software reuse

### **Interfaces:**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

These below given interfaces are abstract data types to represent collections. The distinct trees i.e. Collection and Map are top-level interfaces of the framework. Please note that **Map is not a true Collection**.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/collections/collections_hierarchy.png" alt="Java Collections Framework Hierarchy" width="500" height="300"></img> </br>

## **Collection**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

java.util.Collection<E> is a generic interface and is the root of Collection hierarchy. A collection represents group of objects known as its elements. Java platform doesn't provide direct impelementations of this interface but provide implementations to its sub interfaces.

**Aggregate-operations OR stream-operations:** Aggregate operations do process elements from a stream, not directly from collection. They support **behavior** as parameters i.e. we can specify *lambda expressions* as parameters for most aggregate operations. These operations introduced in Java 8. These operations are ***not mutative***, meaning they do not modify underlying collection.

Ex:

`
list.stream().filter(e -> e.getColor() == Color.RED).forEach(e -> System.out.println(e.getName()));
`

`
String joined = elements.stream().map(Object::toString).collect(Collectors.joining(", "));
`

**bulk-operations:** These operations operate on entire collections ex: `containsAll(), addAll(), removeAll(), retainAll(), clear()` etc. But key difference is that these old or legecy operations are all ***mutative*** meaning they all modify underlying collection.

Note: *Collections* class is utility class that provides static methods to operate on and return collections. *Properties* class (key/value is string/string) represents a persistant set of properties. The properties can be saved to stream or loaded from stream.

### **List**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is an ordered collection (or sequence). Simply an array of sequence with varying length. Internally it follows the insertion order (natural order which is the how the elements are inserted).
- It can contain duplicate elements. We can also insert NULL values.
- Each element in a list has an index and starts with 0. We can also insert an element at specific index. If there are existing elements at that index, those elements will be pushed further down.
- Provides greater control over where each elements is inserted and retrieved by a position (index). In detail, it provides positional access, search for specified element, iteration and range view etc.
- *ArrayList* consumes less memory.
- *ArrayList* is faster than LinkedList. In fact, you should not try to use a LinkedList.
- ***CopyOnWriteArrayList*** is thread-safe and immutable is the best solution for lists.
- **ArrayList, LinkedList, CopyOnWriteArrayList, Vector** are the concrete implementations of List interface.
- **Vector** implements a growable array of objects.
- **Stack** represents a **Last-In-First-Out (LIFO) stack of objects**. It is a sub class of Vector.

### **Set**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A set is a collection. It models mathematical set abstraction.
- It is NOT a ordered collection meaning it has no guaranteed internal order.
- It cannot have duplicate elements.
- A set can have at most one NULL element.
- ***HashSet*** - stores elements in an internal HashMap (a hash table (NOT Hashtable) data structure using HashMap) instance which is **best performing solution** but no ordering guaranteed. It hashes the elements and distributes them into buckets by the hash value.
- ***TreeSet*** - stores elements in a red-black tree (self-balancing binary search tree internally uses TreeMap) and orders (sorted) its elements based on their values. It is ordered and navigable. It is slower or worse complexity than HashSet.
- ***LinkedHashSet*** - Implemented as a HashMap (a hashtable (NOT Hashtable) data structure using HashMap) with linked list,  and guarantees element ordering by insertion-order

***Symmetric Set Difference:*** The set of elements contained in either of two sets but not in BOTH.

### **SortedSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A set that maintains elements in **sorted(ascending) order**. They are naturally ordered sets or we can sort according to *Comparator* provided at creation time.

### **NavigableSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is a sub interface of SortedSet.
- It is a set that is sorted(ascending) order. It is extended from SortedSet.
- It provides additional methods for easy navigation of the elements.
- *TreeSet (from Java 2), ConcurrentSkipListSet (from Java 6)* are concrete implementations of this NavigableSet interface.

### **Queue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A collection used to hold multiple elements prior to processing.
- It follows FIFO (First-In-FIrst-Out) to **order** elements (but not necessariliy). All elements are inserted at tail of the queue and they are removed at head of the queue.
- In addition to basic Collection operations, queues provide additional insertion, removal, and inspection operations such as `element(), offer(), peek(), poll(), remove()` etc.
- *LinkedList*, *PriorityQueue* are concrete implementations of Queue interface.
- But **priority queues (PriorityQueue)**, follows natural ordering (order elements according to supplied consumer or elements natural ordering).
- Some Queue implementations in *java.util.concurrent* are **bounded queues** (restrict the number of elements that it holds), but the implementations in *java.util* are not.
- Queue implementations do not allow insertion of NULL elements unless Queue implementation using LinkedList.

### **Deque**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is doubled ended ordered queue (just like queues).
- It can be used both as FIFO (First-In-First-Out) and LIFO (Last-In-First-Out) for ordering i.e. we can use it as both a *queue* and a *stack*.
- In deque, all new elements can be inserted, removed, retrieved from both ends.
- The Deque is a richer abstract data type (ADT) than both *Stack* and *Queue* because it implements both stacks and queues at the same time, that it can be used both as last-in-first-out (LIFO) stacks and first-in-first-out (FIFO) queues. It provides methods to support both.
- *LinkedList*, *ArrayDeque* are concrete implementations of this interface.

### **All about Iterators**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

**Iterable:** interface represents a collection of objects which is iterable. It provides forEach(), iterator(), spliterator() methods. It is the root interface for Collection interface.

**Iterator interface:**

- Iterator interface provides methods to iterate collection in forward direction.
- It supports removing element from collection.
- It improved method names compared to Enumeration (Enumeration predated to Iterator). Enumeration also can be converted to iterator using Enumeration.asIterator().
- It can traverse elements individually.

**ListIterator interface**

- ListIterator interface provides methods to iterate collection in bi-directional fashion but individual elements (no bulk operations as supported in Spliterator).
- ListIterator extends Iterator interface.

**Spliterator interface**

- Spliterator introduced in Java 8.
- It can traverse elements individually and support bulk operations.
- It can be obtained from source i.e. stream etc.
- It can be used to partition some elements and support parallel operations.

#### **Performance of Iterators**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

When iterating a collection **lots of times in a tight loop** (iterating a list **thousands of times per second**), Iterator's **forEach** loop is **slower than** standard for loop. Because each iteration will call the List iterator() method, which will create a new Iterator object. Creating a new object thousands perhaps even millions of times per second does have a small performance penalty compared to just iterating the List using a standard for-loop. For most standard business applications where collections are iterated occasionally, this performance difference is irrelevant. It only matters for very tight loops that are executed thousands of times per second.
### **Map**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- An object that maps keys to values. It is not a true collection. It models the mathematical function abstraction.
- A key at most can map to one value.
- It cannot contain duplicate keys.
- Map implementations internally uses Hashtable for **HashMap**, Balanced Tree for **TreeMap** and Linked List + Hashtable for **LinkedHashMap**.

### **SortedMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- An **ordered** map that maps the **keys** in **ascending** order(natural order) or according to *Comparator* provided at creation time.

### **List Implementations**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

List represents an **ordered sequence** of values where some value **may occur** more than one time. It is an ordered collection (or sequence). Simply an array of sequence with varying length. Internally it follows the insertion order (natural order which is the how the elements are inserted).

- It can contain duplicate elements. We can also insert NULL values.
- Each element in a list has an index and starts with 0. We can also insert an element at specific index. If there are existing elements at that index, those elements will be pushed further down.
- Provides greater control over where each elements is inserted and retrieved by a position (index). In detail, it provides positional access, search for specified element, iteration and range view etc.
- *ArrayList* consumes less memory.
- *ArrayList* is faster than LinkedList. In fact, you should not try to use a LinkedList.
- ***CopyOnWriteArrayList*** is thread-safe and immutable is the best solution for lists.
- **ArrayList, LinkedList, CopyOnWriteArrayList, Vector** are the concrete implementations of List interface.
- **Vector** implements a growable array of objects.
- **Stack** represents a **Last-In-First-Out (LIFO) stack of objects**. It is a sub class of Vector.

**Concrete Implementations of List interface:**

- LinkedList
- ArrayList
- CopyOnWriteArrayList
- Vector
- Stack (sub class of Vector)

Though **ArrayList** is faster than **LinkedList**, ***CopyOnWriteArrayList*** is thread-safe and immutable is the best solution for lists.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/collections/list_implementations.png" alt="Java List Implementations Hierarchy" width="500" height="300"></img> </br>

#### **LinkedList**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- LinkedList is a **doubly linked list** implementation of List and Deque interfaces.
- It is **ordered**, follows insertion order.
- It can have **duplicate** elements. (Because it is a *List* type).
- It is slower than *ArrayList* and consumes **more memory** than *ArrayList*. Infact, LinkedList is not a right choice to use in present times.
- It is not **synchronized**. But we can retrieve a synchronized linked list using `Collections.synchronizedList(new LinkedList(..))`
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if list is modified then it throws *ConcurrentModificationException*).
- Every element is a node that keeps reference to next and previous nodes (It is NOT index based). The search operation for an item has execution time equal to **O(n)**, hence it is slower than *ArrayList*.
- But insertion, addition and removal operations are **faster** because there is no need of resizing an array or update the index when an element is added (because only next, previous elements change when element is added). **It is better fit for constant insertion/deletion time (e.g., frequent insertions/deletions/updates), over constant access time and effective memory usage**.
- It consumes **more memory** because every node store two references (next and previous node references). Whereas ArrayList holds only data and its index.

#### **ArrayList**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- *ArrayList* is **index** based data structure backed by an array. It implements *List* interface.
- Since it is index based, it provides **random access** to its elements when getting an element, hence the performance is equal to **O(1)**. But insertion, addition and removal operations are **slower** than LinkedList because of the need that resizing the array or update the index when an element is added.
- It consumes **less memory** than *LinkedList* because it holds only data and it's index.
- Adding an element takes ammortized constant time **O(1)**.
- Inserting/deleting element takes **O(n)**.
- Searching an element for unsorted array takes **O(n)** and **O(log n)** for sorted array.

#### **CopyOnWriteArrayList**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- **CopyOnWriteArrayList** introduced in Java 5.
- **CopyOnWriteArrayList** useful in multithread programs when we want to iterate over a list in a **thread-safe** way without an explicit synchronization.
- **thread-safe without synchronization** because when we use any modify methods (add or remove), whole content of the list is copied into **new internal copy**. Due to this fact, *we can iterate the list safely even when concurrent modification is happening*.
- It is a **best choice** when we iterating over it more often than we are modifying it.
- If adding elements is common operation in a scenario, this is **not a best choice**. Because the **additional copies** will definitely lead to **sub-par performance**.
- Removing an element while iterating is not allowed because of data (internal copy) copying mechanism, as it throws UnsupportedOperationException.

#### **Set Implementations**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A set is a collection. It models mathematical set abstraction.
- It is NOT a ordered collection meaning it has no guaranteed internal order.
- It cannot have duplicate elements.
- A set can have at most one NULL element.
- ***HashSet*** - stores elements in a HashMap (a hashtable (NOT Hashtable) data structure using HashMap) which is **best performing solution** but no ordering guaranteed. It hashes the elements and distributes them into buckets by the hash value.
- ***TreeSet*** - stores elements in a red-black tree(self-balancing binary search tree) (internally uses TreeMap) and orders (sorted) its elements based on their values. It is ordered and navigable. It is slower or worse complexity than HashSet.
- ***LinkedHashSet*** - Implemented as a HashMap (a hashtable (NOT Hashtable) data structure using HashMap) with linked list,  and guarantees element ordering by insertion-order

***Symmetric Set Difference:*** The set of elements contained in either of two sets but not in BOTH.

**Concrete implementations of Set interface**

- HashSet
- LinkedHashSet (sub class of HashSet)

**Concrete implementations of SortedSet interface**

- TreeSet

**Concrete implementations of NavigableSet interface**

- TreeSet
- ConcurrentSkipListSet

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/collections/set_hierarchy.png" alt="Java Set Implementations Hierarchy" width="500" height="300"></img> </br>


#### **HashSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It stores **unique** elements in an **internal HashMap** (a hash table (NOT Hashtable) data structure using HashMap) instance.
- It hashes the elements and distributes them into buckets by the hash value.
- NO ordering guaranteed meaning it doesn't maintain insertion order.
- It permits atmost **one NULL element**.
- It is **not thread-safe i.e. NOT synchronized**. *If multiple threads access it concurrently, it must be synchronized externally.* We can synchronize it during creation time i.e. `Set s = Collections.synchronizedSet(new HashSet(...));`
- It's very important not to set the initial capacity too high (or the load factor too low) if iteration performance is important.
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).

#### **TreeSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It stores unique elements. TreeSet concrete implementation of NavigableSet (NavigableSet extends SortedSet).
- It stores elements in a **red-black tree** (self-balancing binary search tree internally uses **TreeMap**) and orders (sorted) its elements based on their values.
- It is ordered and navigable and it follows natural ordering in ascending order.
- Since Java 7, It does not support NULL elements.
- It is **not thread-safe i.e. NOT synchronized**. *If multiple threads access it concurrently, it must be synchronized externally.* We can synchronize it during creation time i.e. `Set s = Collections.synchronizedSortedSet(new TreeSet(...));`
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).
- It is slower or worse complexity than HashSet.
- add(), remove(), contains() guarantees O(log n) performance.
### **Hashing**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

Hashing is fundamental concept in computer science. Hashing algorithms are one-way functions used to verify integrity of data. Hashes are like a fingerprint for the original data. If the data changes, the fingerprint will no longer match. Common hashing algorithms are Common hashing algorithms are **MD5, SHA-1, SHA-256, SHA-384, SHA-512**.

A hash function takes input of any length and produces a fixed-length string (sometimes called **digest**). A hash function that transforms data in such a way that, given a hash result (digest), it is computatinally infeasible to produce original message.

In Java, efficient hashing algorithms used in HashMap and HashSet. The *hashCode()* returns an integer value (digest) generated by a hashing algorithm.Objects that are equal (their equals() implementation) must return the same hash code.

Various IDE platforms generate hashCode() automatically and all these implementations utilize **prime number 31** in some form for multiplication. This is because 31 has a nice property. Its multiplication can be replaced by a **bitwise shift**, which is faster than the **standard multiplication**.
Ex. `31 * i == (i << 5) - i`
IntelliJ, Eclipse also uses 31 in their hashcode generation code snippets.

java.util.Objects.hash() can also be used to generate hash code. Objects class introduced in Java 7. Lombok, Apache-commons HashcodeBuilder are also used to generate hashcode implementation. If we don't override hashCode(), HashMap and HashSet uses *system generated hash code*.

**Hash collision:** Hash collision happens when two or more objects might have the same hash code even if they're unequal. Hash collision strategies are separate chaining, open addressing (linear probing, quadratic probing), double hashing.

</div>