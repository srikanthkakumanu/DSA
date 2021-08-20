# **Networking and HTTP**

<div style="text-align: justify;">

## **Table Of Contents**

1. [Computer Network and Network types](#computer-network) </br>
2. [Network Communication](#network-communication) </br>
    2.1 [Protocol](#protocol) </br>
    2.2 [Packet](#Packet) </br>
    2.3 [Datagram](#datagram) </br>
    2.4 [MAC Address](#mac-address) </br>
    2.5 [Socket](#socket) </br>
    2.6 [Port](#port) </br>
3. [OSI - Open Systems Interconnection Model](#osi-model) </br>
    3.1 [Physical Layer](#physical-layer) </br>
    3.2 [DataLink Layer](#datalink-layer) </br>
    3.3 [Network Layer](#network-layer) </br>
    3.4 [Transport Layer](#transport-layer) </br>
    3.5 [Session Layer](#session-layer) </br>
    3.6 [Presentation Layer](#presentation-layer) </br>
    3.7 [Application Layer](#application-layer) </br>  
4. [Protocol Glossary](#protocol-glossary) </br>
5. [Internet Protocol (IP)](#internet-protocol) </br>
6. [Transmission Control Protocol](#transmission-control-protocol) </br>
7. [User Datagram Protocol](#user-datagram-protocol) </br>
8. [TCP vs. UDP](#tcp-vs-udp) </br>
9. [Hypertext Transfer Protocol (HTTP)](#http) </br>
10. [Altogether: HTTP + TCP + IP](#http-stack) </br>
11. [Google QUIC protocol](#quic-protocol) </br>
12. 

## **Computer Network**

---

[Table of contents](#table-of-contents) </br>

A Computer Network is a group of two or more interconnected computers that use common connection protocols for sharing various resources and files (send and receive information). We can establish a computer network connection using either **cable or wireless media**. Every network involves hardware and software that connects computers and tools.

Types of Computer Network:

- **Personal Area Network (PAN)**: is a computer network formed around a person and generally consists of a computer, mobile, or personal digital assistant. It can be used for establishing communication among the personal devices for connecting to a digital network and the internet.
- **Local Area Network (LAN)**: is a group of computer and peripheral devices which are connected in a limited area and widely useful network for sharing resources like files, printers, games, and other application.
- **Wide Area Network (WAN)**: is another important computer network that spreads across a large geographical area. It could be the connection of a LAN which connects with other LAN's using telephone lines and radio waves. It is mostly limited to an enterprise or an organization.
- **Metropolitan Area Network (MAN)**: is consisting of a computer network across an entire city, college campus, or a small region. This type of network is large than a LAN and depending upon the type of configuration, this type of network allow us to cover an area from several miles to tens of miles.

In addition to above there are some other types of networks such as Wireless Local Area Network (WLAN), Storage Area Network (SAN), System Area Network (SAN), Home Area Network (HAN), Passive Optical LAN (POLAN), Enterprise private network (EPN), Campus Area Network (CAN) and Virtual Area Network (VAN) etc.

Computer networks are logically classified into:

- **Peer-to-Peer (P2P)**: A P2P system is a **distributed collection of peer nodes** that act both as servers and as clients (provide services to other peers and consume services from other peers). It is very different from Client/Server model.
- **Client/Server**: A client makes a request to the server and the server responds by satisfying the client's request. In the client/server model new clients and servers can be added incrementally as more users come on-line and the demand for services increases.

## **Network Communication**

---

[Table of contents](#table-of-contents) </br>

The **Open Systems Interconnection (OSI)** Model is a logical and conceptual model that defines network communication (using protocols) used by systems open to interconnection and communication with other systems.

Note: *The TCP/IP model sometimes referred to as a protocol stack and it can be considered as a condensed version of OSI model.*

### **Protocol**

In network communication, a **protocol** is a set of rules and a standard for formatting and processing data. Protocols allows two entities to communicate across the network. Some standard protocols used for this purpose are IP, TCP, UDP, FTP, etc.

### **Packet**

A packet is a chunk of binary data that has a source host and a destination host or It is the **unit or block of a data** transaction between a computer and its network. A packet usually contains a network header, at least one high-level protocol header, and data blocks. Generally, the format of data blocks does not affect how packets are handled. Packets are the exchange medium used at the Internetwork layer to send data through the network.

### **Datagram**

The basic unit of information **consisting of one or more data packets**, which are passed across an Internet at the transport layer level.

### **MAC Address**

MAC (Media Access Control Address) is known as a physical address is a unique identifier of each host or device and is associated with the NIC (Network Interface Card). General **length of MAC address is : 12-digit/6-bytes/48-bits**.

### **Socket**

A socket can be thought of as ***an endpoint in a two-way communication channel*** or ***A socket is one end-point of a two-way communication link between two programs running on the network.*** Socket routines create the communication channel, and the channel is used to send data between application programs either locally or over networks. Each socket within the network has a **unique name** associated with it called a **socket descriptor** (a full word integer that designates a socket and allows application programs to refer to it when needed).

### **Port**

Port is a logical channel which allows network users to send or receive data to an application. Every host can have multiple applications running. Each of these applications are identified using the port number on which they are running. **Ports are assigned by Transport layer i.e. TCP or UDP**.

## **OSI model**

---

[Table of contents](#table-of-contents) </br>

Note: *The **TCP/IP model** sometimes referred to as a **protocol stack** and it can be considered as a **condensed version of OSI model**.*

The **Open Systems Interconnection (OSI)** model defines **a networking framework to implement protocols in layers with control passed from one layer to the next**. It conceptually divides computer network architecture into 7 layers in a logical progression.

The lower layers deal with electrical signals, chunks of binary data, and routing of these data across networks. Higher levels cover network requests and responses, representation of data, and network protocols, as seen from a user's point of view.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/nethttp/osi_1.gif" alt="OSI Layers" width="500" height="300"></img> </br>

### **Physical Layer**

---

[Table of contents](#table-of-contents) </br>

- It consists of the electronic circuit (physical structure/device) transmission technologies of a network. It plays a vital role in controlling the transmission rate.
- Data is transimitted using the type of signaling supported by physical medium such as electric voltages, radio frequencies, infrared pulses or ordinary light.
- Whenever some device sends data to the physical layer, it receives the data, **converts the data to bits**, and sends to data link layer. While transferring the bits, it defines the number of bits transmitted per second.
- Technologies include standard network devices such as Network adapters, Ethernet cables, hubs and repeaters.
- **Protocols**:  RS-232, UTP cables (twisted pair cables), DSL (Digital Subscriber Line - used in standard telephone lines), Wireless, Fiber etc.

### **DataLink Layer**

---

[Table of contents](#table-of-contents) </br>

- It is the **most complex layer in OSI model**. It is often divided into **two parts: the MAC (Media Access Control) sub-layer and the Logical Link Control sub-layer**.
- The data link layer is responsible for the data transfer between two directly connected nodes. It sends the data frame to another node using its **MAC address**.
- Data is represented as **Frames**. **Frame = [Header + Data + Trailer]**
- Packages data **bits into frames** when it is receiving from physical layer and divides the data from Network layer into frames.
- With a frame, it also adds a **header and trailer**. A header and trailer include information for hardware destination and source address. In this way, it also helps the packet to reach the destination.
- *Error control*: When obtaining data from the Physical layer, it checks for physical transmission errors.
- *Access control*: It also manages physical addressing schemes such as MAC addresses for Ethernet networks, controlling access of network devices to the physical medium.
- **Protocols**: Ethernet, PPP, Switch, Bridge etc.

### **Network Layer**

---

[Table of contents](#table-of-contents) </br>

- The network layer transmits data from one transmitting station to another.
- Data is represented as **Packet** (**also called as Datagram**).
- **Fragmentation**: Packets can often be larger than the maximum size, so each packet is also divided into smaller pieces of data called **fragments**. The network layer is responsible for fragmentation.
- It introduces concept called **Routing** above DataLink Layer. Routing means *select the shortest path from one host to another to deliver the data at the best possible time*.
- It also puts the IP addresses of the sender and receiver into the header (Refer Frame in DataLink Layer) to support Routing.
- It maintains logical addresses such as IP addresses for devices on the network.
- It also manages the mapping between these logical addresses and physical addresses.
- When data arrives at the Network layer, the source and destination addresses contained inside each frame are examined to determine if the data has reached its final destination. If the data has reached the final destination, It formats the data into packets delivered to the Transport layer. Otherwise, the Network layer updates the destination address and pushes the frame down to the lower layers.
- **Protocols**: Internet Protocols (IPv4 and IPv6), ARP, IPSec, IGMP, ICMP etc.

### **Transport Layer**

---

[Table of contents](#table-of-contents) </br>

- The transport layer **delivers data across network connections**. It ensures the end-to-end and complete transfer of the data.
- Data is represented as **Segments**.
- **Segmentation**: The transport layer receives the packets from the network layer and **divides the packets into smaller packets, known as segments. This process is called segmentation**.
- Transport layer protocols may support a range of optional capabilities, including error recovery, flow control, and support for re-transmission.
- **Re-transmission of data**: It also provides acknowledgment to the sender node when the data transmission is complete. If there is an error occurred during transmission, the transport layer re-transmit the data.
- It also controls the flow and error of the data and helps data to reach the receiver end successfully.
- At the receiver’s end, the transport layer receives the data in a *segment form*. It reassembles the segment and gives acknowledgment for the successful transformation of data.
- **Protocols**: TCP, UDP etc.

### **Session Layer**

---

[Table of contents](#table-of-contents) </br>

- The Session Layer manages the sequence and flow of events that initiate/establish and tear down network connections.
- It is built to support multiple types of connections that can be created dynamically and run over individual networks.
- It maintains and synchronizes sessions between communicating devices. It also provides authentication and ensures security.
- When transmitting data in the form of segments, It adds some synchronization points. If some error occurs, the transmission restarts from the last synchronization point. **This process is called synchronization and recovery**.
- **Protocols**: RPC, NetBIOS, PPTP, NFS, SQL etc.

Note: *Sockets are sometimes consider as part of Session Layer*.

### **Presentation Layer**

---

[Table of contents](#table-of-contents) </br>

- The Presentation layer has the simplest function of OSI model. Sometimes it is called as **Syntax Layer** or **Translation Layer**.
- In short, it gives syntax and semantics of the data exchanged between the two networking systems.
- It handles syntax processing of message data such as format conversions and encryption/decryption needed to support the Application layer above it.
- It does tasks like character code translation, data conversion and compression (data compression - reduction of the number of bits needed to represent data), data encryption/decryption, character set translation etc.
- **Protocols**: SSL, TLS, SSH, IMAP, FTP, MPEG, JPEG etc.

### **Application Layer**

---

[Table of contents](#table-of-contents) </br>

- It supplies network services to end-user applications.
- It acts as a window between a software application and an end-user. It helps to access the data from the network and display the information to the user.
- **Protocols**: HTTP, FTP, DNS, SNMP etc.

## **Protocol Glossary**

---

[Table of contents](#table-of-contents) </br>

- DSL - Digital Subscriber Line
- UTP - Unshielded twisted pair cable
- STP - Shielded twisted pair cable
- LLC - Logical Link control
- MAC - Media Access Control (MAC address)
- ARP - Address resolution protocol
- ICMP - Internet control message protocol. ICMP is found on network devices like routers. It is also used in distributed denial-of-service(DDoS) attacks.
- PPTP - P2P tunneling protocol
- NetBIOS - Network basic input/output system
- TLS - Transport layer security
- DNS - Domain name system
- SNMP - Simple network management protocol
- DHCP - Dynamic host configuration protocol

## **Internet Protocol**

---

[Table of contents](#table-of-contents) </br>

The IP in TCP/IP is short for Internet Protocol. As the name suggests, it is one of the fundamental protocols of the Internet.

IP implements **packet-switched networking** (is a method of grouping data that is transmitted over a digital network into packets). It has a concept of *hosts*, which are machines. The IP protocol specifies how datagrams (packets) are sent between these hosts. An IP network will then simply transmit the packet from the source to the destination. One important aspect of IP is that packets are delivered using **best effort**. A packet may be lost along the way and never reach the destination. Or it may get duplicated and arrive multiple times at the destination.

**Internet Protocol (IP)** is responsible for **addressing and fragmenting data packets** in digital networks. **Its goal is to ensure the successful delivery of packets from source to destination**.

**Fragmentation** - Packets can often be larger than the maximum size, so each packet is also divided into smaller pieces of data called **fragments**. The network layer is responsible for **fragmentation**.

To ensure successful delivery of packets, IP specifies a format that defines **the type of description of data packets called IP datagrams**.

### **The IP Header**

An **IP packet** consists of a **header and a payload**.

The payload contains the actual data to be transmitted, while the header is metadata.

### **IP address**

An Internet protocol address is known as **an IP address**, and any device connected to the internet requires an IP address. The **IP address is a numeric label** that is attached to the device once it gets connected to the internet for communication and is unique on a network.  Each packet contains the source and destination hosts' addresses. The IP is responsible for routing datagrams - as the IP packet travels through the network, each node (host) that it travels through looks at the destination address in the packet to figure out in which direction the packet should be forwarded.

There are two approaches to assign an IP address to a device – **static and dynamic**.

- A **static IP address** is specified explicitly in the device’s network configuration. A static IP address is configured, and it does not change even if there are changes in the network configurations. 
- A **dynamic IP address** is assigned to the device by the network and changes over time. There are several mechanisms to assign an IP address to a device dynamically. One of the most frequently used mechanisms is Dynamic Host Configuration Protocol (DHCP). DHCP automatically assigns an IP address to a device while it is connected to a network.

IP has two major versions **IPv4 and IPv6**. The main difference between IPv4 and IPv6 is the **use of address space**. The difference between these two can be found in the header, where multiple fields are present in one header but absent from the other. The IPv4 format was reviewed and improved to IPv6, which is more efficient and less complicated.

Although both IPv4 and IPv6 provide IP addresses, **they can’t communicate with each other**. However, **both can co-exists in a network. This is known as a dual-stack**. **Tunneling is another approach in which a tunnel is created within an IPv4 region that allows encapsulated IPv6 packets are sent**.

|IP v4  |IP v6  |
|---------|---------|
|The first major version of IP is IPv4.  </br> It was first deployed on SATNET (1982) and ARPANET (1983). It is oldest and most widely used IP address format.     | It is most recent version of Internet protocol. </br> IPv6 was developed by the Internet Engineering Task Force (IETF) to deal with the long-anticipated problem of **IPv4 address exhaustion**. </br> It is also known as **Internet Protocol next generation (IPng)** and its use is still not widespread compared to IPv6.        |
|It uses 32-bit address space to create unique TCP/IP address identifiers.     | It uses 128-bit address space to create unique TCP/IP address identifiers. </br> </br> This large number of IPv6 addresses allows large chunks of addresses to be allocated for various purposes and aggregate for efficient routing.        |
|It is a numeric address type and uses dotted-octet (dotted-decimal) notation in XXX.XXX.XXX.XXX to express the address. </br> </br> The XXX (octet) could be a value ranging between 0 to 255 and each octet (XXX) is 8-bits i.e. 4 X 8-bits (32-bits). </br> e.g. 172.16.254.1    | It is a alpha-numeric address type and is 128-bit long. </br> Each 16-bits is separated with a colon(:). </br> e.g. 2001:0DB8:AC10:FE01:0000:0000:0000:0000           |
|**Fragmentation** - While sending data from source to destination, the actual data packet can be fragmented into multiple smaller packets. This fragmentation is done based on the maximum transmission unit (MTU) defined in the network layer. This process of splitting the data packets into smaller chunks is known as **IP fragmentation**. In the IPV4 addressing scheme, this fragmentation can be done by both the ***sender and the intermediate routers***. The fragmented packets are then assembled by the receiver.     |**Fragmentation** - performed by sender. </br> </br> The IPV6 addressing scheme follows a different principle for IP fragmentation. </br> </br> In IPV6, the intermediate routers don’t perform any fragmentation. It is **always done by the sender** host if at all fragmentation is required. </br></br> Besides, in IPV6, the fragmentation header is not part of the actual IPV6 header. It is inserted in the data packet as an extension header if there is a need to perform fragmentation.         |
|**IP address configuration** - Manually (static) or through DHCP. (dynamic)     | **IP address configuration** - Stateless address autoconfiguration through Internet Control Message Protocol version 6 (ICMPv6) or DHCPv6.        |

## **Transmission Control Protocol**

---

[Table of contents](#table-of-contents) </br>

***Note: TCP and UDP both assigns port numbers to their segments or datagrams at transport layer.***

The *IP protocol* allows for sending single packets (datagrams) between two hosts. Packets are delivered best effort and may: reach the destination in a **different order** than the one in which they were sent, reach the destination multiple times, or never reach the destination at all.

**TCP is a connection-oriented reliable protocol** and *stateful* in nature. **TCP (Transport Layer) is built on top of IP (Network Layer)** to provide reliable transmission of packets. The TCP provides **reliable, ordered, error-checked delivery of a stream of data** between programs. With TCP, an application running on one device can send data to an application on another device and be sure that the data arrives there in the same way that it was sent. This may seem trivial, but it's really a stark contrast to how the raw IP layer works.

With TCP, applications establish connections between each other. A TCP connection is **unicast (only one host or destination)** and **duplex** (***bi-directional***) and allows data to flow in both directions. TCP guarantees that the data will arrive at the other end in pristine condition.

TCP runs unmodified on top of both IPv4 and IPv6. The Protocol (IPv4) or Next Header (IPv6) field will be set to 6, which is the protocol number for TCP.

### **TCP Segments**

The data stream that flows between hosts is cut up into chunks, which are turned into **TCP segments**. *The TCP segment then becomes the payload of an IP packet*.

**Each TCP segment has a header and a payload**. The payload is the actual data chunk to be transmitted. The TCP segment header first and foremost contains the source and destination port number - the source and destination addresses are already present in the IP header.

The header also contains **sequence** (mechanism to give each segment a unique number) and **acknowledgement** (allow the other end to communicate back to the sender regarding which segments it has received so far) numbers and quite a few other fields which are all used by TCP to manage the connection.

Since TCP is *dulex*, this happens in both directions.

### **TCP Connection**

In TCP, a connection is always established from one host to another. The server performs a so-called *passive open* - it starts listening. The client performs a so-called *active open* toward the server.

TCP Connection setup happens through a three-way handshake as described below:

1. The client sends a SYN to the server with a random sequence number, *A*.

2. The server replies with a SYN-ACK with an acknowledgment number of *A+1* and a random sequence number, *B*.

3. The client sends an ACK to the server with an acknowledgement number of *B+1* and a sequence number of *A+1*.

**SYN** - Synchronize sequence numbers. Once data flows between both ends, each TCP segment has a sequence number. This is how TCP makes sure that all parts arrive at the other end, and that they're put together in the right order. Before communication can start, both ends need to synchronize the sequence number of the first segments.

**ACK** - Acknowledgment. When a segment arrives at one of the ends, that end will acknowledge the receipt of that segment by sending an acknowledgment for the sequence number of the received segment.

## **User Datagram Protocol**

---

[Table of contents](#table-of-contents) </br>

***Note: TCP and UDP both assigns port numbers to their segments or datagrams at transport layer.***

Applications can use UDP to send data to other hosts over Internet Protocol. Messages sent with UDP are called **datagrams**.

The UDP protocol is **connectionless** and *stateless* in nature. In simple words, it lacks the mechanisms of starting, maintaining, or ending the conversation. Moreover, there aren’t any techniques of flow control or retransmission. Thus, there is **no guarantee** that the receiver will obtain all datagrams or that they will come in a valid order. Hence, UDP is sometimes called an *unreliable delivery protocol*.

UDP’s lack of connections and reliability mechanisms has a specific reason. There is no need to store additional bytes of security data in the datagrams.

Therefore, **UDP transmission is lightweight and fast**. Consequently, UDP is often used in applications where transmission speed is crucial, e.g., online games, video conferencing, or music streaming software.

Furthermore, UDP is **stateless and enables multicast**.

Hence, it’s a good choice for sending messages to great numbers of hosts. It’s especially useful in streaming applications, such as IPTV (Internet Protocol Television) or live streaming services.

### **Datagram in UDP**

The **datagram** is a self-contained, basic unit of data transferred via a packet-switched network. Datagrams usually consist of a header and a payload. UDP treats each datagram as independent from all others. Each datagram must contain all the information required for its delivery.

Theoretically, the maximum length of a single datagram is 65527 bytes. However, there are ways to extend that limit, e.g., using IPv6 *jumbograms*.

## **TCP vs UDP**

---

[Table of contents](#table-of-contents) </br>

|UDP  |TCP  |
|---------|---------|
|**Reliability**: It is unreliable. It does not guarantee to deliver all datagrams (packets) neither they will come in the correct order. Furthermore, It does not provide error-checking or flow-control.     | **Reliability**: It is Reliable. It delivers all packets in correct order. Additionally, in case of failure, it will inform the sender about the problem. Provides error-checking, re-transmission and flow-control mechanisms.        |
|**Connection Management**: It is Connection-less. The sender doesn't need any acknowledgement from the receiver. It needs only a receiver port to start broadcasting.     | **Connection Management**: It is Connection-oriented. Uses three-way handshake to establish a connection between hosts. Without it, a transfer is not allowed.        |
|**Transmission**: Allows multi-cast transmission     | **Transmission**: Due to three-way handshake requirement, it allows only unicast transmission.        |
|**Speed**: Lightweight datagram structure and lack of security mechanisms result in fast transmission.     | **Speed**: It is slower than UDP because of handling multiple additional actions such as error-checking, flow-control and re-transmission.        |
|**Header size**: The header size is 8-bytes.     |**Header size**:  The header size is 20-bytes.        |
|**Usage**: It is used in applications where packet loss is acceptable e.g. audio and video streaming etc.     | **Usage**: Used in applications that require reliable transfer e.g. e-mail.        |
|**State**: It is stateful in nature.     | **State**: It is stateless in nature.        |

## **HTTP**

---

[Table of contents](#table-of-contents) </br>

**HTTP**

The Hypertext Transfer Protocol (HTTP) is an application layer protocol and **it is connection-less, state-less protocol**. It is the foundation of the World Wide Web (WWW) and is used to load web pages using hypertext links.

**HTTP Request Header**: HTTP version type + URL + HTTP method + Request Headers + Optional HTTP body

**HTTP Response Header**: HTTP status code + Response Headers + Optional HTTP body

**HTTPS (HTTP secure)**

Transport Layer Security (TLS) is a cryptographic protocol that runs on top of TCP. It allows for two things: both ends can verify the identity of the other end, and the data sent between both ends is encrypted. Using HTTP on top of TLS gives us HTTP Secure, or simply, HTTPS.

Simply using HTTPS instead of HTTP will gives us a huge improvement in security.

**TLS 1.2**: We should set the `TLSMinimumSupportedProtocol` to `kTLSProtocol12` to require TLS version 1.2 if your server supports that. This will make **man-in-the-middle** attacks more difficult.

## **HTTP stack**

---

[Table of contents](#table-of-contents) </br>

HTTP stack is combining HTTP/S + TCP + IP.

HTTP connections really are nothing more than TCP connections (TCP connections are the reliable connections of the Internet).

TCP gives HTTP a ***reliable bit pipe***. Bytes stuffed in one side of a TCP connection come out the other side correctly, and in the right order (TCP carries HTTP data in order, and without corruption).

TCP streams are segmented and shipped by IP packets - TCP sends its data in little chunks called IP *packets* (or IP *datagrams*). In this way, HTTP is the top layer in a **"protocol stack"** of **"HTTP over TCP over IP”**.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/nethttp/http_stack.png" alt="HTTP stack" width="500" height="300"></img> </br>

IP packets carry TCP segments, which carry chunks of the TCP data stream i.e. When HTTP wants to transmit a message:

- It streams the contents of the message data, in order, through an open TCP connection.
- TCP takes the stream of data, chops up the data stream into chunks called segments, and transports the segments across the Internet inside envelopes called IP packets.

Each TCP segment is carried by an IP packet from one IP address to another IP address.

Each of these IP packets contains:

- An IP packet header (usually 20 bytes).
- A TCP segment header (usually 20 bytes).
- A chunk of TCP data (0 or more bytes).

The IP header contains the source and destination IP addresses, the size, and other flags. The TCP segment header contains TCP port numbers, TCP control flags, and numeric values used for data ordering and integrity checking.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/nethttp/http_data_transfer.png" alt="HTTP data transfer" width="500" height="300"></img> </br>

A computer might have several TCP connections open at any one time. TCP keeps all these connections straight through *port numbers*.

A TCP connection is distinguished by four values: ``<source-IP-address, source-port, destination-IP-address, destination-port>``

Together, these four values uniquely define a connection. Two different TCP connections are not allowed to have the same values for all four address components (but different connections can have the same values for some of the components).

## **QUIC Protocol**

---

[Table of contents](#table-of-contents) </br>

Google developed the **QUIC protocol** (**Quick UDP Internet Connections**) is an entirely new protocol for the web developed on **top of UDP instead of TCP**.

The QUIC protocol can start a connection and negotiate all the TLS (HTTPs) parameters in 1 or 2 packets (depends on if it’s a new server you are connecting to or a known host).

It is a new encrypted transport layer network protocol. QUIC was designed to make HTTP traffic more secure, efficient, and faster. Theoretically, QUIC has taken all the best qualities of TCP connections and TLS encryption and implemented it on UDP.

## **Java Networking Overview**

---

[Table of contents](#table-of-contents) </br>

The `java.net` package loosely devided into:

- **Low-level API**: Deals with addresses, sockets, network interfaces. Addresses are network identifiers like IP addresses and Sockets are basic bi-directional data communication mechanism.
- **High-level API**: Deals with URIs, URLs and connections (to the resource pointed to by URLs).

### **URL and URI**

**Uniform Resource Locator (URL)** is a reference (an address) to a resource on the Internet. Hence every URL is a **Uniform Resource Identifier (URI)** but NOT every URI is a URL. Because there is another subcategory of URIs, **uniform resource names (URNs)**, which name resources but do not specify how to locate them. The mailto, news, and isbn URIs shown are examples of URNs. URLs are *write-once* objects. Once we've created a URL object, we cannot change any of its attributes (protocol, host name, filename, or port number).

### **Network Interface**

A network interface is the point of inter-connection between a device and any of its network connections.
</div>
