package newio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * NIO2 Asynchronous Server Socket Channel - Server
 */
public class NIO2AsyncSocketChannelServer {
    private static final int PORT = 9090;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        AsynchronousServerSocketChannel channel;
        
        try {
            channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(HOST, PORT));
            System.out.printf("Server listening at %s%n", channel.getLocalAddress());
        }
        catch(IOException e) {
            System.err.println("Unable to open or bind server socket channel");
            return;
        }
        // ServerAttachment is user defined class
        ServerAttachment a = new ServerAttachment();
        a.setChannelServer(channel);

        channel.accept(a, new ConnectionHandler());
    }
}

/**
 * Callback handler i.e. CompletionHandler for async call
 */
class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, ServerAttachment>
{
    @Override
    public void completed(AsynchronousSocketChannel channelClient, ServerAttachment att) {
        try {
            SocketAddress clientAddr = channelClient.getRemoteAddress();
            System.out.printf("Accepted connection from %s%n", clientAddr);
            att.getChannelServer().accept(att, this);
            
            ServerAttachment newAtt = new ServerAttachment();
            newAtt.setChannelServer(att.getChannelServer());
            newAtt.setChannelClient(channelClient);
            newAtt.setReadMode(true);
            newAtt.setBuffer(ByteBuffer.allocate(2048));
            newAtt.setClientAddr(clientAddr);
            // ReadWriteHandler is user defined
            ServerReadWriteHandler rwh = new ServerReadWriteHandler();
            channelClient.read(newAtt.getBuffer(), newAtt, rwh);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable t, ServerAttachment att) {
        System.out.println("Failed to accept connection");
    }
}

class ServerReadWriteHandler implements CompletionHandler<Integer, ServerAttachment> {
    private final static Charset CSUTF8 = StandardCharsets.UTF_8;

    @Override
    public void completed(Integer result, ServerAttachment att) {
        if (result == -1) {
            try {
                att.getChannelClient().close();
                System.out.printf("Stopped listening to client %s%n", att.getClientAddr());
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }

        if (att.isReadMode()) {
            att.getBuffer().flip();
            int limit = att.getBuffer().limit();
            byte bytes[] = new byte[limit];
            att.getBuffer().get(bytes, 0, limit);
            System.out.printf("Client at %s sends message: %s%n", att.getClientAddr(), new String(bytes, CSUTF8));
            att.setReadMode(false);
            att.getBuffer().rewind();
            att.getChannelClient().write(att.getBuffer(), att, this);
        }
        else {
            att.setReadMode(true);
            att.getBuffer().clear();
            att.getChannelClient().read(att.getBuffer(), att, this);
        }
    }

    @Override
    public void failed(Throwable t, ServerAttachment att) {
        System.out.println("Connection with client broken");
    }
}

/**
 * Bundling fields that the server and clients use to communicate.
 */
class ServerAttachment
{
    private AsynchronousServerSocketChannel channelServer;
    private AsynchronousSocketChannel channelClient;
    private boolean isReadMode;
    private ByteBuffer buffer;
    private SocketAddress clientAddr;
    public AsynchronousServerSocketChannel getChannelServer() {
        return channelServer;
    }
    public void setChannelServer(AsynchronousServerSocketChannel channelServer) {
        this.channelServer = channelServer;
    }
    public AsynchronousSocketChannel getChannelClient() {
        return channelClient;
    }
    public void setChannelClient(AsynchronousSocketChannel channelClient) {
        this.channelClient = channelClient;
    }
    public boolean isReadMode() {
        return isReadMode;
    }
    public void setReadMode(boolean isReadMode) {
        this.isReadMode = isReadMode;
    }
    public ByteBuffer getBuffer() {
        return buffer;
    }
    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
    public SocketAddress getClientAddr() {
        return clientAddr;
    }
    public void setClientAddr(SocketAddress clientAddr) {
        this.clientAddr = clientAddr;
    }
}
