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
   6.2 [TreeSet](#treeset) </br>
   6.3 [LinkedHashSet](#linkedhashset) </br>
   6.4 [ConcurrentSkipListSet](#concurrentskiplistset) </br>

7. [Queue Implementations](#queue-implementations) </br>
   7.1 [PriorityQueue](#priorityqueue) </br>
   7.2 [ConcurrentLinkedQueue](#concurrentlinkedqueue) </br>
   7.3 [ArrayBlockingQueue](#arrayblockingqueue) </br>
   7.4 [LinkedBlockingQueue](#linkedblockingqueue) </br>
   7.5 [DelayQueue](#delayqueue) </br>
   7.6 [PriorityBlockingQueue](#priorityblockingqueue) </br>
   7.7 [LinkedTransferQueue](#linkedtransferqueue) </br>
   7.8 [ArrayDeque](#arraydeque) </br>

8. [Map Implementations](#map-implementations) </br>
   8.1 [HashMap](#hashmap) </br>
   8.2 [LinkedHashMap](#linkedhashmap) </br>
   8.3 [Hashtable](#hashtable) </br>
   8.4 [Properties](#properties) </br>
   8.5 [WeakHashMap](#weakhashmap) </br>
   8.6 [TreeMap](#treemap) </br>
   8.7 [ConcurrentHashMap](#concurrenthashmap) </br>
   8.8 [ConcurrentSkipListMap](#concurrentskiplistmap) </br>

9. [Strong vs. Soft vs. Weak References](#strong-vs-soft-vs-weak-references) </br>
10. [Unmodifiable vs. Immutable](#unmodifiable-vs-immutable) </br>
11. [Hashing & hashCode()](#hashing) </br>
12. [Unbounded and bounded queues](#unbounded-and-bounded-blocking-queues) </br>

## **Overview**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

A collection is a contains that groups multiple elements into a single unit. Collections are used to store, retrieve, manipulate and communicate aggriate data. The Java collections define two fundamental types of data structures. A **Collection** is a *grouping of objects* and a **Map** is *a set of mappings or associations between objects*.

Further to *Collection*, a *List* is a Collection that has ordered elements but may contain duplicates and a *Set* is a type of Collection with no duplicates.

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

java.util.Collection<E> is a generic interface and is the root of Collection hierarchy. A collection represents group of objects known as its elements. Java platform doesn't provide direct implementations of this interface but provide implementations to its sub interfaces.

To work properly with expectations of collections, we must take care when defining *hashCode()* and *equals()* methods.

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

- It is an ordered collection (or sequence) of objects. Simply an array of sequence with varying length. Internally it follows the insertion order (natural order which is the how the elements are inserted).
- It can contain **duplicate** elements. We can also insert NULL values.
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

A *Set* is a collection of objects that does not allow duplicates.

- A set is a collection. It models mathematical set abstraction.
- It is NOT a ordered collection meaning it has no guaranteed internal order.
- It cannot have duplicate elements i.e. It may not contain two references to same object or no references two objects *a* and *b* such that *a.equals(b)*.
- A set can have at most one NULL element i.e. no two references to *null*.
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

A *Queue* is an **ordered** collection of elements with methods for extracting elements **in order** from the *head* of the queue. Queue implementations are commonly based on *insertion-order* as in **First-In-First-Out (FIFO) queues** or **Last-In-First-Out (LIFO) queues**. **LIFO queues are stacks** and Java provides **Stack class but its use strongly discouraged instead use implementations of Deque interface**.

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

A *Map* is a set of *key* objects and a mapping from each member of that set to a *value* object (**association**).

- An object that maps keys to values. It is **not a true collection**. It models the mathematical function abstraction.
- A key **at most** can map to **one value**.
- It **cannot** contain **duplicate** keys.
- It is not advised/permissible for a map to contain itself as a key but can be a value with extreme caution (because equals(), hashCode() methods are not well defined on such map).
- Unmodifiable maps can be created using `Map.of(), Map.ofEntries(), Map.copyOf()` static factory methods. **Unmodifiable maps do not allow NULL keys and values**, if do, it throws NullPointerException.
- Map implementations internally uses Hash table data structure for **HashMap**, Balanced Tree for **TreeMap** and (Linked List + Hash table) for **LinkedHashMap**.

### **SortedMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- An **ordered** map that maps the **keys** in **ascending** order(natural order) or according to *Comparator* provided at creation time.

### **List Implementations**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

List represents an **ordered sequence** of values (objects) where some value **may occur** more than one time. It is an ordered collection (or sequence). Simply an array of sequence with varying length. Internally it follows the insertion order (natural order which is the how the elements are inserted).

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

### **Set Implementations**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

A *Set* is a collection of objects that does not allow duplicates.

- A set is a collection. It models mathematical set abstraction.
- It is NOT a ordered collection meaning it has no guaranteed internal order.
- It cannot have duplicate elements i.e. It may not contain two references to same object or no references two objects *a* and *b* such that *a.equals(b)*.
- A set can have at most one NULL element i.e. no two references to *null*.
- ***HashSet*** - stores elements in a HashMap (a hashtable (NOT Hashtable) data structure using HashMap) which is **best performing solution** but no ordering guaranteed. It hashes the elements and distributes them into buckets by the hash value.
- ***TreeSet*** - stores elements in a red-black tree(self-balancing binary search tree) (internally uses TreeMap) and orders (sorted) its elements based on their values. It is ordered and navigable. It is slower or worse complexity than HashSet.
- ***LinkedHashSet*** - Implemented as a HashMap (a hashtable (NOT Hashtable) data structure using HashMap) with doubly-linked list,  and guarantees element ordering by insertion-order

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

#### **LinkedHashSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is a **ordered** version of HashSet that maintains **doubly-linked list** across all elements.
- It hashes the elements and distributes them into buckets by the hash value.
- It stores unique values, duplicates are not allowed.
- It permits atmost **one NULL element**.
- It is **not thread-safe i.e. NOT synchronized**. *If multiple threads access it concurrently, it must be synchronized externally.* We can synchronize it during creation time i.e. `Set s = Collections.synchronizedSet(new LinkedHashSet(...));`
- It is slightly **slower than HashSet** as it spends additional CPU cycles and requires more memory.
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).

#### **ConcurrentSkipListSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is a scalable, concurrent, sorted, **natural/insertion ordered** version of NavigableSet.
- It stores unique values, duplicates are not allowed.
- It does NOT permit **NULL elements**.
- It is **thread-safe**.
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).

### **Queue Implementations**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

A *Queue* is an **ordered** collection of elements with methods for extracting elements **in order** from the *head* of the queue. Queue implementations are commonly based on *insertion-order* as in **First-In-First-Out (FIFO) queues** or **Last-In-First-Out (LIFO) queues**. **LIFO queues are stacks** and Java provides **Stack class but its use strongly discouraged instead use implementations of Deque interface**.

- Queue is introduced in Java 5 and it is a type of Collection. It is designed for holding elements prior to processing.
- Adding items to queues are particularly **useful in multi-threaded environments**. A Queue can be shared amongst threads and be used to block progress until space is available i.e. help in overcoming some common multi-threaded problems.
- Queues is **ordered**. typically (not necessarily) it order elements in **First-In-First-Out (FIFO)** manner. However **exceptions** are **priority queues** (*orders according to comparator or natural order*), Last-In-First-Out (**LIFO**) **queues** (or **stacks**).
- It does not allow **NULL** elements. But LinkedList allows though it is a duque.
- insert new element : `offer()`, remove an element : `poll()`, inspect the element at front of queue (without removing it) : `peek()`.
- Queue is implemented by four sub-interfaces *BlockingQueue, TransferQueue, Deque, BlockingDeque*.
- **thread-safe** queues are **ConcurrentLinkedQueue, ArrayBlockingQueue and ConcurrentLinkedDeque**.
- Some Queue implementations in java.util.concurrent are **bounded queues** (restrict the number of elements that it holds), but the implementations in java.util are not.
- **Blocking queues** support additional operations that force threads wait for the queue depending on current state. A thread may wait on the Queue to be non-empty when attempting a retrieval, or for it to become empty when adding a new element. It offers a simple thread-safe mechanism. In this queue, threads need to wait for the queue's availability. The producers will wait for available capacity before adding elements, while consumers will wait until the queue is empty. In those cases, the non-blocking queue will either throw an exception or return a special value, like null or false. To achieve this blocking mechanism, the BlockingQueue interface exposes two functions on top of the normal Queue functions: put and take. Those functions are the equivalent of add and remove in a standard Queue.
- **Transfer queues** designed toward producer-consumer pattern. It controls the flow of information from producer to consumer, creating **backpressure** in the system.
- **Deque** is **Double-Ended-Queue**, its elements may be taken from both the start and end of the queue. Deque provides methods (such as offer(), poll(), peek()) to operate at both **the top and bottom**.
- The **Deque** is a richer abstract data type (ADT) than both Stack and Queue because it implements both stacks and queues at the same time, that it can be used both as last-in-first-out (LIFO) stacks and first-in-first-out (FIFO) queues. It provides methods to support both.
- **Priority queues** are ordered based on their natural ordering, or by a defined Comparator provided when we construct the PriorityQueue.

BlockingQueue 

**Concrete implementations of Queue interface**

- ConcurrentLinkedQueue
- PriorityQueue

**Concrete implementations of BlockingQueue interface**

- ArrayBlockingQueue
- LinkedBlockingQueue
- DelayQueue
- PriorityBlockingQueue
- SynchronousQueue

**Concrete implementations of TransferQueue interface**

- LinkedTransferQueue

**Concrete implementations of Deque**

- ArrayDeque
- ConcurrentLinkedDeque
- LinkedList (also implements List)

**Concrete implementations of BlockingDeque**

- LinkedBlockingDeque

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/collections/queue_implementations.png" alt="Java Queue Implementations Hierarchy" width="500" height="300"></img> </br>

#### **PriorityQueue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is used when the objects are supposed to be processed based on priority unlike standard Queue which follows FIFO algorithm. It is based on the **priority heap**. The elements of the priority queue are ordered according to the **natural ordering**, or by a Comparator provided at queue construction time, depending on which constructor is used.
- Priority queues are **unbounded** queues.
- It stores unique values, duplicates are not allowed. We can’t create PriorityQueue of Objects that are *non-comparable*.
- It does not allow **NULL element**.
- It is **not thread-safe i.e. NOT synchronized**. Hence Java provides *PriorityBlockingQueue* to use in multi-threaded environment.

#### **ConcurrentLinkedQueue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It was added in Java 5 along with other concurrent utilities like CyclicBarrier, CountDownLatch, Semaphore, ConcurrentHashMap etc. It is an **unbounded, thread-safe and non-blocking queue**.
- It is a **lock-free** queue. It's locking based on **Michael & Scott algorithm for non-blocking, lock-free nodes**. It uses CAS (Compare-And-Swap) for its operations.
- It is an appropriate choice when many threads will share access to a common collection. Note that it **doesn't block operations** (*It does not block the accessing thread when the queue is empty and returns null*) as it is done in blocking queues (ArrayBlockingQueue, LinkedBlockingQueue etc). So there are no put(), take() methods which will wait if required.
- It stores elements as **linked nodes**. It **orders** elements in **FIFO** order.
- Concurrent linked queue is **unbounded**.
- It stores unique values, duplicates are not allowed. We can’t create PriorityQueue of Objects that are *non-comparable*.
- It does not allow **NULL element**.
- It is **thread-safe i.e. synchronized**.
- It's iterators (*Iterator* and *ListIterator*) are not *fail-fast*.

#### **ArrayBlockingQueue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is a **bounded blocking queue backed by an array**. Once created, the **capacity cannot be changed**. Attempts to put an element into a full queue will result in the operation blocking. Similarly attempts to take an element from an empty queue will also be blocked.
- It is generally used in a thread-safe environment where you want to block two or more operating on a single resource, allowing only one thread. Also, we can block a thread using the capacity bounding factor.
- It's ordering **not guaranteed**.
- It does not allow **NULL element**.
- It is **thread-safe i.e. synchronized**.
- It's iterators (*Iterator* and *ListIterator*) are not *fail-fast*.

#### **LinkedBlockingQueue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is **optionally-bounded blocking queue based on linked nodes**. Optionally-bounded meaning that the queue size can be specified if needed.
- Linked queues typically have **higher throughput** than array-based queues but **less predictable performance** in most concurrent applications.
- It is a **lock-based** queue. It's locking based on **two-lock queue algorithm**. It uses two different locks – the putLock and the takeLock. The put/take operations uses the first lock type, and the take/poll operations use the other lock type.
- It is **FIFO ordered**.
- It does not allow **NULL element**.
- It is **thread-safe i.e. synchronized**. Since it is blocking queue, it blocks the accessing threads when queue is empty.
- The blocking feature of the LinkedBlockingQueue is associated with some cost. This cost is because every put or the take operation is lock contended between the producer or the consumer threads. Therefore, in scenarios with many producers and consumers, put and take actions could be slower.
- **LinkedBlockingQueue needs to allocate and deallocate nodes every time an item is added or removed from the queue**. For this reason, **an ArrayBlockingQueue can be a better alternative if the queue grows fast and shrinks fast**.

#### **DelayQueue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is a **blocking queue** that could be used in **bounded-buffer/producer-consumer** programs. When the consumer wants to take an element from the queue, they can take it only when the delay for that particular element has expired. We use a DelayQueue when a **consumer can only take an expired item**.
- It is an **unbounded blocking queue** of *Delayed* elements.
- Each element we want to put in DelayQueue needs to implement the *Delayed* interface.
- It does not allow **NULL elements**.
- It is **thread-safe i.e. synchronized**.

#### **PriorityBlockingQueue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is an **unbounded blocking queue** that uses the same ordering rules as class PriorityQueue and supplies blocking retrieval operations. **Its elements are ordered by priority.**
- Elements that are needed to add must implement either *Comparable* or provide a *Comparator* instead.
- Higher priority element is always ordered first and when we remove an element from our queue, it will always be the one with the highest priority.
- It does not allow **NULL elements**.
- It is **thread-safe i.e. synchronized**.
  
#### **LinkedTransferQueue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It allows us to create programs according to the producer-consumer pattern, and coordinate messages passing from producers to consumers.
- It gives us the new ability to implement a form of backpressure. This means that, when the producer sends a message to the consumer using the transfer() method, the producer will stay blocked until the message is consumed.
- It can be very useful when we do not want an over-producing producer that will flood the queue with messages, resulting in the OutOfMemory errors. In such design, the consumer will be dictating the speed at which the producer will produce messages.
- It **allows a producer to wait for the consumption of an item**. By implementing a simple backpressure algorithm using this queue. Indeed, by blocking producers until consumption, **consumers can drive the flow of messages produced**.

#### **ArrayDeque**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is known as **Array double ended queue or Array Deck**. It is a special kind of a growable array that allows us to add or remove an element from both sides. It automatically doubles the size of an array when head and tail pointer meets each other while adding an element.
- It is backed by an array which doubles its size when it gets filled.
- It's implemented as a double-ended queue where it maintains two pointers namely a head and a tail.
- It does not allow **NULL elements**.
- It is **not thread-safe i.e. not synchronized**.
- It is significantly faster than the synchronized Stack. It is faster queue than LinkedList due to the better locality of reference. Most operations have amortized constant time complexity.
- It's iterators are *fail-fast*.

### **Map Implementations**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

A *Map* is a set of *key* objects and a mapping from each member of that set to a *value* object (**association**).

- Map is **not a true collection**. It is an object that maps keys to values. It models the mathematical function abstraction. It uses the internal concept of *associative arrays*.
- A key **at most** can map to **one value**.
- It **cannot** contain **duplicate** keys.
- It is not advised/permissible for a map to contain itself as a key but can be a value with extreme caution (because equals(), hashCode() methods are not well defined on such map).
- **Unmodifiable** maps can be created using `Map.of(), Map.ofEntries(), Map.copyOf()` static factory methods. Unmodifiable maps **do not allow NULL keys and values**, if do, it throws NullPointerException.
- Map implementations internally uses Hash table data structure for **HashMap**, Balanced Tree for **TreeMap** and (Linked List + Hash table) for **LinkedHashMap**.
- An **ordered** map that maps the **keys** in **ascending** order (natural order) or according to *Comparator* provided at creation time.

**Concrete implementations of Map interface**

- HashMap
- LinkedHashMap (sub class of HashMap)
- Hashtable
- Properties (sub class of Hashtable)
- WeakHashMap

**Concrete implementations of NavigableMap (extends SortedMap) interface**

- TreeMap

**Concrete implementations of ConcurrentMap interface**

- ConcurrentHashMap

**Concrete implementations of ConcurrentNavigableMap (extends NavigableMap + ConcurrentMap) interface**

- ConcurrentSkipListMap

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/collections/Map_Implementations.png" alt="Java Map Implementations Hierarchy" width="500" height="300"></img> </br>

#### **HashMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- HashMap uses an internal hash table (not Hashtable) data structure to store elements. It acts as a **binned (bucketed) hash table**. But when bin gets too large, they are transformed into bins of TreeNodes. These tree bins are ordered by **hash code**.
- The advantage of using HashMap is **run time performance**. HashMap stores elements in so-called **buckets** and the number of **buckets** is called **capacity**. As of Java 8, the data structure in which the values inside one bucket are stored is changed from a **list** to a **balanced tree** if a **bucket contains 8 or more values**, and **it's changed back to a list if, at some point, only 6 values are left in the bucket**. **This improves the performance to be O(log n)**.
- It allows **NULL** keys and values.
- It is not **ordered** i.e. does not maintain insertion order.
- It is not **thread-safe** i.e. not synchronized. *If multiple threads access it concurrently, it must be synchronized externally.* We can synchronize it during creation time i.e. `Map m = Collections.synchronizedMap(new HashMap(...));`
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).
- It's space complexity is O(n).
- `put(), get(), remove(), containsKey()` - O(1).
- search for a specific element - O(n). If it is sorted, O(log n).
- Iteration (next element) - O(h/n) i.e. h is capacity and n is size.

#### **LinkedHashMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- LinkedHashMap uses an internal (hash table + doubly linked list) data structure to store elements.
- It is **ordered** and maintains predictable iteration order (**insertion-order**). It maintains a doubly linked list running through all of its entries.
- It is **slower than HashMap** due to additional **maintainance of linked list**.
- This linked list defines iteration ordering.
- It allows **NULL** keys and values.
- It is not **thread-safe** i.e. not synchronized. *If multiple threads access it concurrently, it must be synchronized externally.* We can synchronize it during creation time i.e. `Map m = Collections.synchronizedMap(new LinkedHashMap(...));`.
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).
- `put(), get(), remove(), containsKey()` - O(1).
- search for specific element - O(n).
- Iteration (next element) - O(n). i.e. n is size

#### **Hashtable**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is the **oldest implementation of hash table data structure** in Java. An Hashtable key must not violate the *hashCode()* contract i.e. equal object must return same code.
- It uses the concept of hashing using buckets (bins) for storing data. Internally it uses an array. Each position in that array is a **bucket** which can be NULL or contain one or more key-value pairs. The index of each pair is calculated, because finding an element by index is much quicker than iterating through the elements with the comparison sequentially. Hence it maps keys to indexes.
- It is **deprecated** since **Java 8**. However, **ConcurrentHashMap is a great Hashtable replacement**. We should consider ConcurrentHashMap to use in applications with multiple threads.
- It is **thread-safe** i.e. synchronized.
- It is slower than HashMap and takes more memory because it is thread-safe.
- It is not **ordered** i.e. does not maintain insertion order.
- It **does not allow NULL** at all if tried, it throws *NullPointerException*.
- Hashtable uses **Enumerator** to iterate values. But we can create an iterator from **Enumeration**. If did, that iterator is **fail-fast**.
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*). But, the Enumerations returned by Hashtable's keys and elements methods are not fail-fast.
- If a **thread-safe** implementation is not needed, it is **recommended** to use **HashMap** in place of Hashtable. If a **thread-safe highly-concurrent implementation** is desired, then it is **recommended** to use **ConcurrentHashMap** in place of Hashtable.
- **The only difference between the HashTable and HashMap is that Hashtable is synchronized whereas HashMap is not**.

#### **Properties**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It represents persistant set of properties. It can be saved to a stream or loaded from a stream.
- It's **put*() methods usage is highly discouraged** as they allow the caller to insert entries whose keys or values are not Strings. The *setProperty()* method should be used instead.
- It's iterators (*Iterator* and *ListIterator*) are *not fail-fast*.
- It is **thread-safe**.

#### **WeakHashMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- WeakHashMap is a hashtable-based implementation of the Map interface with keys that are **weak reference** type i.e. java.lang.refs.WeakReference.
- An entry in a WeakHashMap will automatically be removed when its key is no longer in ordinary use, meaning that there is no single Reference that point to that key. When the garbage collection (GC) process discards a key, its entry is effectively removed from the map. So this class behaves somewhat differently from other Map implementations.
- It has an **efficient memory cache**.
- It allows **NULL** keys and values.
- It is not **thread-safe** i.e. not synchronized. *If multiple threads access it concurrently, it must be synchronized externally.* We can synchronize it during creation time i.e. `Map m = Collections.synchronizedMap(new WeakHashMap(...));`
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).
- It has performance characteristics similar to those of the HashMap class.
- It allows GC to automatically delete ununsed objects from memory.
- Ideally used when we want to build a cache of big images.

Refer [Strong vs. Soft vs. Weak References](#strong-vs-soft-vs-weak-references) section for details.

#### **TreeMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- TreeMap uses an internal self balanced binary search tree (red-black tree) to store elements.
- It sorts the keys in natural order, it is sorted in ascending order.
- It does not allow **NULL** keys but allows multiple NULL values.
- It is not **thread-safe** i.e. not synchronized. *If multiple threads access it concurrently, it must be synchronized externally.* We can synchronize it during creation time i.e. `Map m = Collections.synchronizedSortedMap(new TreeMap(...));`
- It's iterators (*Iterator* and *ListIterator*) are *fail-fast* (After iterator creation, if hash set is modified then it throws *ConcurrentModificationException*).
- It is **slower** than HashMap, LinkedHashMap. But TreeMap saves memory compared to HashMap.
- `get(), put(), remove(), containsKey()` - O(log n)

#### **ConcurrentHashMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- ConcurrentMap guarantees memory consistency on key/value operations in a multi-threading environment. It should yield much better performance in most concurrent cases for data retrieval and update.
- It is **not sorted**.
- It consists of an **array of nodes as table buckets** (used to **table segments prior to Java 8**) and uses **compare-and-swap** (CAS) operations during updates. It provides **better performance**. The table buckets are initialized lazily upon first insertion. Each bucket can be independently locked by locking the very first node in the bucket. Read operations do not block, and update contentions are minimized.
- The number of segments (table buckets) required is relative to the number of threads accessing the table so that the update in progress per segment would be no more than one most of time.
- It does not allow **NULL** keys but allows multiple NULL values.
- It's iterators (*Iterator* and *ListIterator*) are *not fail-fast*.
- It is **thread-safe**.

#### **ConcurrentSkipListMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is concurrent version of a TreeMap.
- It allows us to create **thread-safe logic** in a **lock-free way**. It's ideal for problems when we want to make an immutable snapshot of the data while other threads are still inserting data into the map.
- It is **ordered**, supports total ordering of its keys (in **ascending order by default**) and is concurrently navigable.
- There's no concurrent implementation of the red-black tree in Java. A **concurrent variant** of **SkipLists** is implemented in ConcurrentSkipListMap.
- It is **thread-safe**.
- `get(), put(), remove(), containsKey()` - O(log n)

### **Strong vs Soft vs Weak References**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

**Strong Reference** </br>

Ex. `Integer prime = 1`

The variable prime has a strong reference to an Integer object with value 1. Any object has a strong reference pointing to it is **not eligible for GC**.

**Soft Reference** </br>

Ex.

```java
Integer prime = 1;
SoftReference<Integer> soft = new SoftReference<>(prime);
prime = null;
```

An object that has a soft reference point to it won't be garbage collected until JVM absolutely needs memory. In the above example, variable prime has a strong reference to it (Integer object 1) and we are wrapping it into a soft reference. After making that strong reference null, a prime object is eligible for GC but will be collected only when JVM absolutely needs memory. Java 2 provides *java.lang.refs.SoftReference* class to support soft references.

**Weak Reference** </br>

Ex.

```java
Integer prime = 1;
WeakReference<Integer> weak = new WeakReference<>(prime);
prime = null;
```

The objects that are referenced only by weak references are garbage collected eagerly; the GC won't wait until it needs memory in that case. When we made a prime reference null, the prime object will be garbage collected in the next GC cycle, as there is no other strong reference pointing to it.

**References of a WeakReference type are used as keys in WeakHashMap.**

### **Unmodifiable vs Immutable**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

An Unmodifiable component (collection or map) is just a wrapper over a modifiable component (collection or map). It does not allow modifications to it directly. But underlying modifiable component (collection or map) can still be changed and the modifications are reflected in the unmodifiable component (collection or map) as well.

Ex.

```java
Map<String, String> mutable = new HashMap<>();
mutable.put("USA", "North America");

Map<String, String> unmodifiable = Collections.unmodifiableMap(mutable);
assertThrows(UnsupportedOperationException.class, 
() -> unmodifiable.put("Canada", "North America"));

mutable.remove("USA");
assertFalse(unmodifiable.containsKey("USA"));
		
mutable.put("Mexico", "North America");
assertTrue(unmodifiable.containsKey("Mexico"));
```

An Immutable component (collection or map) on the other hand contains its own private data and it does not allow modifications to it. Therefore, the data cannot change in any way (directly or indirectly) once an instance of immutable component (collection or map) is created.

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

### **Unbounded and bounded Blocking Queues**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

We can distinguish two types of BlockingQueue:

**unbounded queue**:

It can grow almost indefinitely. The Capacity of blockingQueue will be set to Integer.MAX_VALUE. All operations that add an element to the unbounded queue will never block, thus it could grow to a very large size. The most important thing when designing a producer-consumer program using unbounded BlockingQueue is that consumers should be able to consume messages as quickly as producers are adding messages to the queue. Otherwise, the memory could fill up and we would get an OutOfMemory exception.

**bounded queue**:

It grows with maximal capacity defined. We can create such queues by passing the capacity as an argument to a constructor. Using bounded queue is a good way to design concurrent programs because when we insert an element to an already full queue, that operations need to wait until consumers catch up and make some space available in the queue. It gives us throttling without any effort on our part.

</div>