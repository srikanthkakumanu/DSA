package newio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates NIO Socket Channel's (ServerSocketChannel and DatagramChannel) server
 */
public class NIOSocketChannelServer {
    private static final int PORT = 9999;
    public static void main(String[] args) throws IOException {
        // tcp();
        udp();
    }

    /**
     * stream-oriented TCP server socket channel
     * @throws IOException
     */
    private static void tcp() throws IOException {
        System.out.println("Starting NIO Socket Channel Server...");
        
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(PORT));
        ssc.configureBlocking(false); // sets server socket channel to non-blocking
        String msg = "Local Address: " + ssc.socket().getLocalSocketAddress();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        while(true) {
            System.out.print(".");
            SocketChannel sc = ssc.accept();
            if(sc != null) {
                System.out.println("\n Received connection from " + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            } else {
                try {
                    Thread.sleep(TimeUnit.MILLISECONDS.toMillis(1000));
                } catch (InterruptedException e) { assert false; }
            }
        }
    }

    /**
     * packet-oriented UDP server datagram channel.
     * @throws IOException
     */
    private static void udp() throws IOException {
        System.out.println("DatagramChannel server starting and listening on port " + PORT + " for incoming requests...");
        DatagramChannel dcServer = DatagramChannel.open();
        dcServer.socket().bind(new InetSocketAddress(PORT));
        ByteBuffer symbol = ByteBuffer.allocate(4);
        ByteBuffer payload = ByteBuffer.allocate(16);
        while (true) {
            payload.clear();
            symbol.clear();
            SocketAddress sa = dcServer.receive(symbol);
            if (sa == null)
                return;
            System.out.println("Received request from " + sa);
            String stockSymbol = new String(symbol.array(), 0, 4);
            System.out.println("Symbol: " + stockSymbol);
            if (stockSymbol.toUpperCase().equals("MSFT")) {
                payload.putFloat(0, 37.40f); // open share price
                payload.putFloat(4, 37.22f); // low share price
                payload.putFloat(8, 37.48f); // high share price
                payload.putFloat(12, 37.41f); // close share price
            } else {
                payload.putFloat(0, 0.0f);
                payload.putFloat(4, 0.0f);
                payload.putFloat(8, 0.0f);
                payload.putFloat(12, 0.0f);
            }
            dcServer.send(payload, sa);
        }
    }
}
