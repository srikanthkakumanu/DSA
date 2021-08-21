# **Reactive Streams**




## **Overview**

---

Java 9 reactive streams allows us to implement non-blocking asynchronous stream processing. Support for reactive-streams are introduced in Java 9 through `java.util.concurrent.Flow` API. Both synchronous and asynchronous programming models handle request and response bodies as reactive-streams. The Reactive Streams API offers interfaces that manage asynchronous streams of data, including the notion of **back pressure** in which data consumers can slow down producers to get an optimal flow of data.

Note: Java's new HTTP API (`HttpClient`) integrates with the **Reactive Streams APIs** that means that **we don’t need external libraries to do HTTP/2 calls or WebSocket communication in Java code**.

