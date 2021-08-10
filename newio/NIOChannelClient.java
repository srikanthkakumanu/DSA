package newio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

/**
 * Demonstrates NIO's TCP socket channel and UDP datagram client
 */
public class NIOChannelClient {
    public static void main(String[] args) throws Exception {
        // tcp();
        udp();
    }

    private static void tcp() throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false); // // sets client socket channel to non-blocking
        InetSocketAddress address = new InetSocketAddress("localhost", 9999);
        sc.connect(address);

        while(!sc.finishConnect()) 
            System.out.println("Waiting to finish connection");
        
        ByteBuffer buffer = ByteBuffer.allocate(200);
        while(sc.read(buffer) >= 0) {
            buffer.flip();
            while(buffer.hasRemaining()) 
                System.out.print((char) buffer.get());
            buffer.clear();
        }
        sc.close();
    }

    private static void udp() throws Exception {
        DatagramChannel dcClient = DatagramChannel.open();
        ByteBuffer symbol = ByteBuffer.wrap("MSFT".getBytes());
        ByteBuffer response = ByteBuffer.allocate(16);
        InetSocketAddress sa = new InetSocketAddress("localhost", 9999);
        dcClient.send(symbol, sa);
        System.out.println("Receiving datagram from " + dcClient.receive(response));
        System.out.println("Open price: " + response.getFloat(0));
        System.out.println("Low price: " + response.getFloat(4));
        System.out.println("High price: " + response.getFloat(8));
        System.out.println("Close price: " + response.getFloat(12));
    }
}
