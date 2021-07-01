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

3. [Map](#map) </br>
   3.1. [Sorted Map](#sortedmap) </br>

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

### **List**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is an ordered collection (or sequence). Simply an array of sequence with varying length.
- It can contain duplicate elements.
- Provides greater control over where each elements is inserted and retrieved by a position (index). In detail, it provides positional access, search for specified element, iteration and range view etc.
- It consumes less memory.
- It is faster than LinkedList. In fact, you should try not to use a LinkedList.
- ***CopyOnWriteArrayList*** is thread-safe and immutable is the best solution for lists.

### **Set**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A set is a collection. It models mathematical set abstraction.
- It is NOT a ordered collection.
- It cannot have duplicate elements.
- A set can have at most one NULL element.
- ***HashSet*** - stores elements in a Hashtable which is **best performing solution** but no ordering guaranteed. It hashes the elements and distributes them into buckets by the hash value.
- ***TreeSet*** - stores elements in a red-black tree and orders its elements based on their values. It is ordered and navigable. It is slower or worse complexity than HashSet.
- ***LinkedHashSet*** - Implemented as a Hashtable with linked list,  and orders elements by insertion-order

***Symmetric Set Difference:*** The set of elements contained in either of two sets but not in BOTH.

### **SortedSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A set that maintains elements in **sorted(ascending) order**. They are naturally ordered sets or we can sort according to *Comparator* provided at creation time.

### **NavigableSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is a set that is sorted(ascending) order. It is extended from SortedSet.
- It provides methods for easy navigation of the elements.


### **Queue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A collection used to hold multiple elements prior to processing.
- It follows FIFO (First-In-FIrst-Out) to **order** elements (but not necessariliy). All elements are inserted at tail of the queue and they are removed at head of the queue.
- In addition to basic Collection operations, queues provide additional insertion, removal, and inspection operations such as `element(), offer(), peek(), poll(), remove()` etc.
- But **priority queues (PriorityQueue)**, follows natural ordering (order elements according to supplied consumer or elements natural ordering).
- Some Queue implementations in *java.util.concurrent* are **bounded queues** (restrict the number of elements that it holds), but the implementations in *java.util* are not.
- Queue implementations do not allow insertion of NULL elements unless Queue implementation using LinkedList.

### **Deque**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is doubled ended ordered queue (just like queues).
- It can be used both as FIFO (First-In-First-Out) and LIFO (Last-In-First-Out) for ordering.
- In deque, all new elements can be inserted, removed, retrieved from both ends.
- The Deque is a richer abstract data type (ADT) than both *Stack* and *Queue* because it implements both stacks and queues at the same time, that it can be used both as last-in-first-out (LIFO) stacks and first-in-first-out (FIFO) queues. It provides methods to support both.


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

</div>