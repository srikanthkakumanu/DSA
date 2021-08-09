# **Basic I/O and New I/O**

<div style="text-align: justify;">

## **Table Of Contents**

1. [Basic I/O: Overview](#overview) </br>
2. [Basic I/O: Streams](#streams) </br>
3. [Basic I/O: Byte Streams](#byte-streams) </br>
4. [Basic I/O: Character Streams](#character-streams) </br>
5. [Basic I/O: Un-buffered Streams](#unbuffered-streams) </br>
6. [Basic I/O: Buffered Streams](#buffered-streams) </br>
7. [Basic I/O: Data Streams](#data-streams) </br>
8. [Basic I/O: Object Streams](#object-streams) </br>
9. [Basic I/O: Piped Streams](#piped-streams) </br>
10. [Basic I/O: Sequence Streams](#sequence-streams) </br>
11. [Basic I/O: Scanner + Format + Console](#scanner-and-console) </br>
12. [NIO: Overview](#new-io-nio-overview) </br>
13. [NIO: Buffers](#buffers) </br>
14. [NIO: Direct vs. Indirect Buffers](#direct-and-non-direct-buffers) </br>
15. [NIO: View Buffers](#view-buffers) </br>
16. [NIO: Channels](#channels) </br>
17. [Random Access Files](#random-access-files) </br>

## **Overview**

---

Java's I/O API categorized into two named Basic I/O and File I/O(NIO).

- **Basic I/O**: It focuses on I/O streams, serialization which simplifies I/O operations. Most of the classes of this section are in java.io package.
- **File I/O (NIO)**: It focuses on file I/O, file system operations including random access files. Most of the classes of this section are in java.nio.file package.

## **Streams**

---

A stream is a sequence of data and they are **uni-directional** (one-way) in nature. An **I/O stream** represents an **input source** or **output destination**. A program uses an **input stream** to read data from a source (one item at a time), Another program uses an **output stream** to write data to a destination (one item at a time). Uni-direction means it can transfer bytes in one direction only either reading data from the source into a program or writing data from a program to the destination. Therefore Java classifies streams into two types named input streams and output streams.

- A stream can represent many kinds of sources and destinations such as **disk files, devices, other programs and memory arrays**.
- A stream support many types of data such as **simple bytes, primitive data types, localized characters and objects**.
- Some streams simply pass on data and others may manipulate then transform the data in meaningful way.
- Irrespective of how they work internally, all streams present same simple model to programs that use them.

There are some **specialized** stream classes to handle different types of input/output sources. They are listed below.

- **Byte Streams**: handle I/O of raw binary data.
- **Character Streams**: handle I/O of character data, automatically handling translation to and from the local character set.
- **Buffered Streams**: optimize input and output by reducing the number of calls to the native API.
- **Scanning and Formatting**: allows a program to read and write formatted text.
- **I/O from the Command Line**: describes the Standard Streams and the Console object.
- **Data Streams**: handle binary I/O of primitive data type and String values.
- **Object Streams**: handle binary I/O of objects.
- **Piped Streams**: Pipes are used to channel the output from one thread into the input of another thread.

**Note**:

- Both byte and characters streams are all **sequential access streams**. In contrast, RandomAccessFile lets you randomly access the contents of a file.
- Both byte and character streams further categorized into **Buffered** and **Unbuffered** streams.
- Byte streams read/write **8-bit bytes** and are limited to ISO-Latin-1 8-bit bytes.
- Character streams read/write **16-bit characters** (character in Unicode character set).

## **Byte Streams**

---

Programs use **byte streams** to perform I/O (from input source or to output source) of **8-bit bytes**. All byte stream classes are descended from *InputStream* and *OutputStream* (both are abstract classes). There are many kinds of byte stream classes to operate on different types of data such as file, buffer, byte array, object, primitive data types, pipes, combined streams (sequence streams i.e. combine multiple streams into one and then read from that comibined stream).

- **FileInputStream** and **FileOutputStream**: Contains methods to handle (read/write) stream of bytes of file in a file system.
- **BufferedInputStream** and **BufferedOutputStream**: Contain methods to handle (read/write) from **buffer** (**memory area**).
- **ByteArrayInputStream** and **ByteArrayOutputStream**: Contain methods to handle (read/write) stream of bytes from/to byte array. It contains an internal buffer that contains bytes that may be read from the stream.
- **DataInputStream** and **DataOutputStream**: Contain methods to handle (read/write) primitive Java data types from/to a stream.
- **FilterInputStream** and **FilterOutputStream**: Filter streams contain **some other stream** (uses it as **basic source/sink of data**) and transform the data along the way or provide additional functionality.
- **ObjectInputStream** and **ObjectOutputStream**: Contain methods to handle (read/write) objects as data. They **serialize/de-serialize** primitive data and objects.
- **PipedInputStream** and **PipedOutputStream**: Pipes are used to channel the output from one thread into the input of another. **Pipes** in IO provides **a link between two threads running in JVM at the same time**. So, Pipes are used both as source or destination. A pipe is said to be **broken** if a thread that was providing data bytes to the connected piped output stream is no longer alive.
- **SequenceInputStream**: Contains methods to combine multiple streams (the logical concatenation of other input streams) and then read from that combined stream.
- **PushbackInputStream**: It adds funtionality to another input stream. It does the **push-back** or **un-read** bytes by **storing pushed-back bytes in an internal buffer**.

**Note**: Byte streams should only be used for the most primitive I/O (byte streams use low level I/O). Therefore we should avoid them when we are dealing with character data (**Character streams** are best approach to use).

## **Character Streams**

---

Characters in Java internally uses Unicode character set. Character stream I/O automatically translates Unicode to and from the local character set. In Western locales, the local character set is usually an 8-bit superset of ASCII. A program that uses character streams in place of byte streams automatically adopts to the local character set and is ready for internationalization — all without extra effort by the programmer. All character stream classes are descended from Reader and Writer (both are abstract classes).

**Note**: Character streams are often "**wrappers**" for byte streams. The character stream uses the byte stream to perform the physical I/O, while the character stream handles translation between characters and bytes.

- **FileReader** and **FileWriter**: Contains methods to handle (read/write) stream of characters of file in a file system.
- **BufferedReader** and **BufferedWriter**: Contain methods to handle (read/write) from **buffer** (**memory area**).
- **LineNumberReader**: A buffered character-input stream that keeps track of line numbers.
- **CharArrayReader** and **CharArrayWriter**: Contain methods to handle (read/write) stream of characters from/to character array.
- **PushbackReader**: It is a type of filter stream that contain **some other stream** (uses it as **basic source of data**) and transform the data along the way or provide additional functionality.
- **InputStreamReader** and **OutputStreamWriter**: They are a **bridge** from byte streams to character streams. They read/write bytes and decodes them into characters using a specified character set.
- **PipedWriter** and **PipedWriter**: Pipes are used to channel the output from one thread into the input of another. **Pipes** in IO provides **a link between two threads running in JVM at the same time**. So, Pipes are used both as source or destination. A pipe is said to be **broken** if a thread that was providing data to the connected piped output character stream is no longer alive.
- **PrintWriter**: Prints formatted representations of objects to a text-output stream.
- **StringReader** and **StringWriter**: A character stream where input source is string and output in a string buffer (which then can be used to construct a string).

## **Unbuffered streams**

---

**Un-buffered** stream means that each read and write request is handled directly by the **underlying OS**. This can make a program much **less efficient**, since **each such request often triggers disk access, network activity, or some other operation that is relatively expensive**. Below are the un-buffered stream classes.

**Byte Stream**: FileInputStream, FileOutputStream </br>
**Character Stream**: FileReader, FileWriter </br>

## **Buffered streams**

---

**Un-buffered** stream means that each read and write request is handled directly by the **underlying OS**. This can make a program much **less efficient**, since **each such request often triggers disk access, network activity, or some other operation that is relatively expensive**.

Therefore, to reduce this **overhead**, Java platform implements **buffered I/O streams**. Buffered input streams read data from a memory area known as a **buffer**. The native input API is called only when the buffer is empty. Similarly, buffered output streams write data to a buffer, and the native output API is called only when the buffer is full.

We can **convert an unbuffered stream into a buffered stream** by passing the unbuffered stream object to a buffered stream class constructor.

Buffered streams adds functionality to existing streams i.e. buffering by creating/adding an internal buffer array. They can read large chunks of data and buffering them can speed up IO quite a bit. Rather than reading one byte at a time from the network or disk, they can read a larger block at a time into an internal buffer.

**Byte Stream**: BufferedInputStream, BufferedOutputStream, ByteArrayInputStream, ByteArrayOutputStream </br>
**Character Stream**: BufferedReader, BufferedWriter </br>

## **Scanner and Console**

---

The **Scanner** API breaks input into individual tokens associated with bits of data. By default, *Scanner* uses a **whitespace** to separate tokens. To use different separator, invoke `useDelimiter()` and specify the regular expression as argument.

The **Formatting API** assembles data into nicely formatted, human-readable form: System.out.format() method to be used for formatting.

Java supports the user to interact with CLI environment in **two ways**: **Standard streams and Console**. **Console** is more advanced than standard streams and it has most of the features provided standard streams.

Standard streams example: `InputStreamReader isr = new InputStreamReader(System.in);` </br>
Console example: `Console c = System.console(); String login = c.readLine("Enter Your Login: "); c.readPassword("Enter Your Old Password: ");`

## **Data Streams**

---

**Data streams** support **binary I/O of primitive data types and string values**. Supported primitive data types are *boolean, char, byte, short, int, long, float and double*. 

DataStreams uses one very bad programming technique: it uses floating point numbers to represent monetary values. In general, floating point is bad for precise values. It's particularly bad for decimal fractions, because common values (such as 0.1) do not have a binary representation.

**Note**: Data streams can only be created as a **wrapper** for an **existing byte stream objects**.
e.g. `dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));`

**Byte Stream**: DataInputStream, DataOutputStream </br>

## **Object Streams**

---

**Object streams** support I/O of objects (Serialization and De-serialization) just like data streams support I/O of primitive data types. All the primitive data I/O methods covered in data streams are also implemented in object streams. So an object stream **can contain a mixture of primitive and object values**. The **biggest advantage** of object streams is that **while they are writing/reading an object, all corresponding references(inherent references or other objects that are stored within a object) of that object are also preserved**.

e.g. *If a -> (b, c) and b -> (d, e) then object stream preserves whole a, b, c, d, e while reading/writing object a.*

Basically, the ObjectOutputStream converts Java objects into corresponding streams. This is known as **Serialization**. Those converted streams can be stored in files or transferred through networks. Now, if we need to read those objects, we will use the ObjectInputStream that will convert the streams back to corresponding objects. This is known as **De-serialization**.

If two objects on the same stream both contain references to a single object, and they both refer to a single object when they're read back as well. Because, A stream can only contain one copy of an object, though it can contain any number of references to it. Thus if you explicitly write an object to a stream twice, you're really writing only the reference twice. However, if a single object is written to **two different streams**, it is effectively duplicated — a single program reading both streams back will see two distinct objects.

**Note**: De-serialization of untrusted data is inherently dangerous and should be avoided. Untrusted data should be carefully validated meaning that it is advisable to follow Serialization and De-serialization guidelines such as **do not serialize sensitive data** etc.

**Byte Stream**: ObjectInputStream, ObjectOutputStream </br>

## **Piped Streams**

---

Pipes are used to channel the output from one thread into the input of another thread. **Pipes** in IO provides **a link between two threads running in JVM at the same time**. So, Pipes are used both as **source** or **destination**. A pipe is said to be **broken** if a thread that was providing data bytes to the connected piped output stream is no longer alive.

The **advantage with pipe streams** is that **the output from one method could be piped into the next** otherwise (without pipe streams) the program would have to store the results somewhere (such as in a file or in memory) between each step. In essence, a method's output could be used as the input for another so that **you could string a series of method calls together to perform a higher-order function**.

**Byte Stream**: PipedInputStream, PipedOutputStream </br>
**Character Stream**: PipedReader, PipedWriter </br>

## **Sequence Streams**

---

Sequence input stream combine one or more input streams into one input stream i.e. combine a single input stream from multiple input sources.

**Byte Stream**: SequenceInputStream </br>

## **New IO (NIO) Overview**

---

I/O is classified as **block-oriented or stream-oriented**. Reading from or writing to a file is an example of block-oriented I/O. In constast, reading from keyboard or writing to a network connection is an example of stream-oriented I/O. **Stream I/O is often slower than block I/O**.

The Java NIO (new input/output) is introduced in JDK 1.4 and it defines **buffers** (**containers for data**) and other structures, mechanisms to support buffers. This Java NIO is an *alternative to standard IO and networking API's*. Java NIO offers a different I/O programming model than the traditional I/O APIs. Buffers are the foundation for NIO operations.

- In the Java standard I/O API, we work with **byte streams and character streams**. In NIO, we work with **channels and buffers**. In NIO, Data is always read from a channel into a buffer, or written from a buffer to a channel.
- The Java NIO enable us to do non-blocking I/O. For instance, a thread can ask a channel to read data into a buffer. While the channel reads data into the buffer, the thread can do something else. Once data is read into the buffer, the thread can then continue processing it. The same is true for writing data to channels.
- Java NIO contains the concept of **selectors**. **A selector is an object that can monitor multiple channels for events** (e.g. connection opened, data arrived etc.). Thus, a single thread **can monitor multiple channels for data**.

The Java NIO (New I/O) API support the following:

- **Buffers** (`java.nio`): They are containers for fixed amount of data of a specific primitive data type.
- **Charsets** (`java.nio.charset`): They are **named mappings** between **16-bit unicode characters and sequences of bytes**. Support for charsets include **encoders and decoders** (translate between bytes and unicode characters).
- **Channels** (`java.nio.channels`): They represent **an open connection to an entity** (such as hardware device, a file, a network socket, a program) that is capable of performing one or more distinct I/O operations (e.g. reading or writing).
- **Multi-plexed and non-blocking I/O** (`SelectableChannel`): Multiplexing is **the ability to process multiple I/O operations in one channel**. Multiplexing is supported by **Selectable channels**. **A selectable channel can be put into blocking or non-blocking mode**. In **blocking mode**, every I/O operation on a channel will block until it completes. In **non-blocking mode**, an I/O operation will never block but it may transfer fewer bytes than requested or possibly no bytes at all.

## **Buffers**

---

Essentially **NIO is all about moving data into and out of buffers**.

- They are **containers for fixed amount of data** of a specific primitive data type.
- Data is always read from a **channel into a buffer, or written from a buffer to a channel**.  
- It sits between an application and a channel that writes the buffered data to the service or reads the data from the service an.d deposits it into the buffer.
- All buffers are derived from an abstract class `java.nio.Buffer`.
- Buffers are **not thread-safe**. We must employ synchronization when we want to access a buffer from multiple threads.
- Every buffer is readable but not every buffer is writable e.g. a buffer backed by a memory-mapped file that’s read-only. You must not write to a read-only buffer. Otherwise, `ReadOnlyBufferException` is thrown.
- Buffer methods can be chained i.e. `b.flip().position(2).limit(32)`.
- Buffers support **absolute operations** (require an *index* value e.g. `put(int index,byte b)`) and **relative operations** (do not require an *index* value e.g. `put(byte b)`) by using `put()` and `get()` methods.

`java.nio.Buffer` has the following abstract sub classes except *Boolean* primitive data type.

- `ByteBuffer` and `MappedByteBuffer` (sub class of `ByteBuffer`)
- `CharBuffer`
- `DoubleBuffer`
- `FloatBuffer`
- `IntBuffer`
- `LongBuffer`
- `ShortBuffer`

Buffer has **four properties**:

These four properties are related as follows: **0 <= mark <= position <= limit <= capacity**

- **Capacity**: It is no. of elements it contains (The total number of data items that can be stored in the buffer). It is non-negative number that is specified during buffer creation time and cannot be changed later.
- **Limit**: It is the index of first element (It identifies number of live data items in the buffer). It is the zero-based (non-negative) index of first element that should not read or written. It is never greater than its ***capacity***.
- **Position**: The zero-based (non-negative) index of next data item that can be read or the location where the data item can be written. It is never greater than its ***limit***.
- **Mark**: A zero-based index to which the buffer’s position will be reset when the buffer’s reset() method is called. The mark is initially undefined.

The below diagram shows logical layout of a byte-oriented buffer with capacity 7. This below buffer can store 7 elements. mark is initially undefined, position is set to 0 and limit is initially set to the capacity i.e. 7 which specifies the maximum number of bytes that can be stored in the buffer.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/newio/buffer_diagram.png" alt="Java NIO Buffer Logical Representation" width="500" height="300"></img> </br>

### **||| Direct and Non-direct buffers**

---

An non-direct buffer is that memory allocated inside the **managed heap** of the java process and it is created by using `allocate()` method call. A direct buffer is that memory allocated outside of the **managed heap** of the java process and it is created by using `allocateDirect()` method call.

### **||| View buffers**

---

As buffers can manage data elements stored in external arrays (via `wrap()` method), they can also **manage data stored in other buffers**. When we create
a buffer that manages another buffer’s data, the created buffer is known as a **view buffer**. **Changes made in either buffer are reflected in the other**.

The resulting view buffer is equivalent to the original buffer. Both buffers share the same data items and have equivalent capacities. However, each
buffer has its own position, limit, and mark. When the buffer being duplicated is read-only or direct, the view buffer is also read-only or direct.

View buffers are created by calling `duplicate()` or `asxBuffer()` or `asLongBuffer()` (for LongBuffer) and Read-only buffers can be created using `asReadOnlyBuffer()`.

## **Channels**

---

Channels partner with buffers to achieve high-performance I/O.

- Channels represent **an open connection to an entity** (such as hardware device, a file, a network socket, a program) that is capable of performing one or more distinct I/O operations (e.g. reading or writing).
- Data is always read from a **channel into a buffer, or written from a buffer to a channel**.
- Typically, all I/O in NIO starts with a Channel. **A Channel is a bit like a stream**.
- Channels are either open or closed, and they are both asynchronously closeable and interruptible.
- Channels are gateways through which I/O services are accessed. Channels use byte buffers as the endpoints for sending and receiving data.
- All channels are instances of classes that ultimately implement the
`java.nio.channels.Channel` interface.

The following are different types of channels:

- Scatter and Gather Channels (**Scatter & Gather I/O or Vectored I/O**)
- File Channels


### **||| Scatter and Gather Channels**

---

Channels provide the ability to perform a **single I/O operation across multiple buffers**. This capability is known as **Scatter and Gather I/O** and it can also be called as **Vectored I/O**.

In the context of a write operation, the contents of several buffers are **gathered (drained)** in sequence and then sent through the channel to a destination. These buffers are not required to have identical capacities.

In the context of a read operation, the contents of a channel are **scattered (filled)** to multiple buffers in sequence. Each buffer is filled to its limit until the channel is empty or until the total buffer space is used.

### **||| File Channels**

---

- A Java NIO FileChannel is a channel that is connected to a file or used for reading, writing, mapping, and manipulating a file.
- File channels are **thread-safe** *unlike buffers*.
- A FileChannel cannot be set into non-blocking mode. It always runs in blocking mode.

## **Random Access Files**

---

Files can be created and/or opened for **random access** with a mixture of write and read operations at various locations can occur until the file is closed.

`RandomAccessFile` supports the following **modes**:


|Mode  |Description  |
|---------|---------|
|**r**     | Opens an existing file in Read only mode.        |
|**rw**     | (Create/open) a new file or open an existing file for reading & writing.         |
|**rwd**     | (Create/open) a new file or open an existing file for reading & writing. Further, each update to the **file's content** must be **written synchronously** to the **underlying storage device**.        |
|**rws**     | (Create/open) a new file or open an existing file for reading & writing. Further, each update to the **file's content or metadata** must be **written synchronously** to the **underlying storage device**. A file’s metadata is data about the file and not the actual file contents. e.g. file’s length, the time the file was last modified etc.         |

**Note**:

- The **rwd** and **rws** modes ensure that any writes to a file located on a local storage device are written to the device, which guarantees that critical data isn’t lost when the OS crashes. However no guarantee is made when the file doesn’t reside on a local device.

- Operations on a random access file opened in **rwd** or **rws** mode are slower than these same operations on a random access file opened in **rw** mode.

A random access file is associated with a *file pointer*, a *cursor* that identifies the location of the next byte to write or read. When an existing file is opened, the file pointer is set to its first byte at *offset* 0. The file pointer is also set to 0 when the file is created.

</div>