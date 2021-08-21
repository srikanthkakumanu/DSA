# **Reactive Streams**

<div style="text-align: justify;">

Java 9 reactive streams allows us to implement non-blocking asynchronous stream processing. Support for reactive-streams are introduced in Java 9 through `java.util.concurrent.Flow` API. Both synchronous and asynchronous programming models handle request and response bodies as reactive-streams. The Reactive Streams API offers interfaces that manage asynchronous streams of data, including the notion of **back pressure** in which data consumers can slow down producers to get an optimal flow of data.

Note: Java's new HTTP API (`HttpClient`) integrates with the **Reactive Streams APIs** that means that **we don’t need external libraries to do HTTP/2 calls or WebSocket communication in Java code**.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/reactive/reactive_stream_flow.png" alt="Reactive Stream Flow" width="500" height="300"></img> </br>

Reactive Streams is about asynchronous processing of stream. Hence there should be a **Publisher**, a **Subscriber** and a **Processor** (optional).

**Publisher**: The Publisher publishes the stream of data.
**Subscriber**: The Subscriber consumes the data.
**Processor**: is the optional entity sitting between the end publisher and subscriber when we want to transform the data received from publisher so that subscriber can understand it. We can have a chain of processors.

Important classes and interfaces of Flow API:

- `java.util.concurrent.Flow`: It is the `final` and main class of Flow API.
- `java.util.concurrent.Flow.Publisher`: A functional interface for Publisher.
- `java.util.concurrent.Flow.Subscriber`: A functional interface for Subscriber.
- `java.util.concurrent.Flow.Subscription`: It is used to create asynchronous non-blocking link between publisher and subscriber.
- `java.util.concurrent.Flow.Processor`: An interface which extends both Publisher and Subscriber.
- `java.util.concurrent.SubmissionPublisher`: A Publisher implementation that asynchronously issues submitted items to current subscribers until it is closed.

</div>