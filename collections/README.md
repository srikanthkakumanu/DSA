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

### **List**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is an ordered collection (or sequence).
- It can contain duplicate elements.
- Provides greater control over where each elements is inserted and retrieved by a position (index).

### **Set**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A set is a collection and cannot contain duplicate elements.

### **SortedSet**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A set that maintains elements in ascending order. They are naturally ordered sets.

### **Queue**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- A collection used to hold multiple elements prior to processing.
- It follows FIFO (First-In-FIrst-Out) to order elements (but not necessariliy). All elements are inserted at tail of the queue and they are removed at head of the queue. 
- But priority queues, follows natural ordering (order elements according to supplied consumer or elements natural ordering).

### **Deque**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- It is doubled ended queue (just like queues).
- It can be used both as FIFO (First-In-First-Out) and LIFO (Last-In-First-Out).
- In deque, all new elements can be inserted, removed, retrieved from both ends.

### **Map**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- An object that maps keys to values. It is not a true collection.
- A key at most maps to one value.

### **SortedMap**

<HR>

</br> [Table Of Contents](#table-of-contents) </br>

- An ordered map that maps the keys in ascending order(natural order).

</div>