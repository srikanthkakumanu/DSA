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

- Peer-to-Peer (P2P)
- Client/Server

## **Network Communication**

---

[Table of contents](#table-of-contents) </br>

The **Open Systems Interconnection (OSI)** Model is a logical and conceptual model that defines network communication (using protocols) used by systems open to interconnection and communication with other systems.

### **Protocol**

In network communication, a **protocol** is a set of rules and a standard for formatting and processing data. Protocols allows two entities to communicate across the network. Some standard protocols used for this purpose are IP, TCP, UDP, FTP, etc.

### **Packet**

The **unit or block of a data** transaction between a computer and its network. A packet usually contains a network header, at least one high-level protocol header, and data blocks. Generally, the format of data blocks does not affect how packets are handled. Packets are the exchange medium used at the Internetwork layer to send data through the network.

### **Datagram**

The basic unit of information **consisting of one or more data packets**, which are passed across an Internet at the transport layer level.

### **MAC Address**

MAC (Media Access Control Address) is known as a physical address is a unique identifier of each host and is associated with the NIC (Network Interface Card). General **length of MAC address is : 12-digit/6-bytes/48-bits**.

### **Socket**

A socket can be thought of as **an endpoint in a two-way communication channel**. Socket routines create the communication channel, and the channel is used to send data between application programs either locally or over networks. Each socket within the network has a **unique name** associated with it called a **socket descriptor** (a full word integer that designates a socket and allows application programs to refer to it when needed).

### **Port**

Port is a logical channel which allows network users to send or receive data to an application. Every host can have multiple applications running. Each of these applications are identified using the port number on which they are running.

## **OSI model**

---

[Table of contents](#table-of-contents) </br>

The **Open Systems Interconnection (OSI)** model defines **a networking framework to implement protocols in layers with control passed from one layer to the next**. It conceptually divides computer network architecture into 7 layers in a logical progression.

The lower layers deal with electrical signals, chunks of binary data, and routing of these data across networks. Higher levels cover network requests and responses, representation of data, and network protocols, as seen from a user's point of view.

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/nethttp/osi_1.gif" alt="OSI Layers" width="500" height="300"></img> </br>













</div>



Transport protocols for sockets
A protocol is a set of rules or standards that each host must follow to allow other hosts to receive and interpret messages sent to them. There are two general types of transport protocols:

A connectionless protocol is a protocol that treats each datagram as independent from all others. Each datagram must contain all the information required for its delivery.
An example of such a protocol is User Datagram Protocol (UDP). UDP is a datagram-level protocol built directly on the IP layer and used for application-to-application programs on a TCP/IP host. UDP does not guarantee data delivery, and is therefore considered unreliable. Application programs that require reliable delivery of streams of data should use TCP.

A connection-oriented protocol requires that hosts establish a logical connection with each other before communication can take place. This connection is sometimes called a virtual circuit, although the actual data flow uses a packet-switching network. A connection-oriented exchange includes three phases:
Start the connection
Transfer data
End the connection
An example of such a protocol is Transmission Control Protocol (TCP). TCP provides a reliable vehicle for delivering packets between hosts on an Internet. TCP breaks a stream of data into datagrams, sends each one individually using IP, and reassembles the datagrams at the destination node. If any datagrams are lost or damaged during transmission, TCP detects this and retransmits the missing datagrams. The data stream that is received is therefore a reliable copy of the original.



