# **Basic I/O and New I/O**

<div style="text-align: justify;">

## **Table Of Contents**

1. [Overview](#overview) </br>
2. [Basic I/O: Streams](#streams) </br>
3. [Basic I/O: Byte Streams](#byte-streams) </br>
4. [Basic I/O: Character Streams](#character-streams) </br>
5. [Basic I/O: Un-buffered Streams](#unbuffered-streams) </br>
6. [Basic I/O: Buffered Streams](#buffered-streams) </br>
7. [Basic I/O: Data Streams](#data-streams) </br>
8. [Basic I/O: Object Streams](#object-streams) </br>
9. [Basic I/O: Scanner + Format + Console](#scanner-and-console) </br>

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

**Note**: Both byte and characters streams are further categorized into **Buffered** and **unbuffered** streams.

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

</div>