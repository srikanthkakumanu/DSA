package newio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * NIO2 Aynchronous Socket Channel Client
 */
public class NIO2AsyncSocketChannelClient {
    private final static Charset CSUTF8 = StandardCharsets.UTF_8;
    private final static int PORT = 9090;
    private final static String HOST = "localhost";

    public static void main(String[] args) {
        AsynchronousSocketChannel channel;
        
        try {
            channel = AsynchronousSocketChannel.open();
        }
        catch (IOException ioe) {
            System.err.println("Unable to open client socket channel");
            return;
        }
        
        try {
            channel.connect(new InetSocketAddress(HOST, PORT)).get();
            System.out.printf("Client at %s connected%n", channel.getLocalAddress());
        }
        catch (ExecutionException | InterruptedException eie) {
            System.err.println("Server not responding");
            return;
        }
        catch (IOException ioe) {
            System.err.println("Unable to obtain client socket channel's " + "local address");
            return;
        }

        ClientAttachment att = new ClientAttachment();
        att.setChannel(channel);
        att.setReadMode(false);
        att.setBuffer(ByteBuffer.allocate(2048));
        att.setMainThread(Thread.currentThread());
        byte[] data = "Hello".getBytes(CSUTF8);
        att.getBuffer().put(data);
        att.getBuffer().flip();
        channel.write(att.getBuffer(), att, new ClientReadWriteHandler()); // callback handler i.e. CompletionHandler

        try {
            att.getMainThread().join();
        }
        catch (InterruptedException ie) {
            System.out.println("Client terminating");
        }
    }
}

class ClientReadWriteHandler implements CompletionHandler<Integer, ClientAttachment> {
    private final static Charset CSUTF8 = StandardCharsets.UTF_8;
    private BufferedReader conReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void completed(Integer result, ClientAttachment att) {
        if (att.isReadMode()) {
            att.getBuffer().flip();
            int limit = att.getBuffer().limit();
            byte[] bytes = new byte[limit];
            att.getBuffer().get(bytes, 0, limit);
            String msg = new String(bytes, CSUTF8);
            System.out.printf("Server responded: %s%n", msg);
            try {
                msg = "";
                while (msg.length() == 0) {
                    System.out.print("Enter message (\"end\" to quit): ");
                    msg = conReader.readLine();
                }
                if (msg.equalsIgnoreCase("end")) {
                    att.getMainThread().interrupt();
                    return;
                }
            }
            catch (IOException ioe) {
                System.err.println("Unable to read from console");
            }
            att.setReadMode(false);
            att.getBuffer().clear();
            byte[] data = msg.getBytes(CSUTF8);
            att.getBuffer().put(data);
            att.getBuffer().flip();
            att.getChannel().write(att.getBuffer(), att, this);
        }
        else {
            att.setReadMode(true);
            att.getBuffer().clear();
            att.getChannel().read(att.getBuffer(), att, this);
        }
    }

    @Override
    public void failed(Throwable t, ClientAttachment att) {
        System.err.println("Server not responding");
        System.exit(1);
    }
}

/**
 * Bundling fields for communication
 */
class ClientAttachment {
    private AsynchronousSocketChannel channel;
    private boolean isReadMode;
    private ByteBuffer buffer;
    private Thread mainThread;

    public AsynchronousSocketChannel getChannel() {
        return channel;
    }
    public void setChannel(AsynchronousSocketChannel channel) {
        this.channel = channel;
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
    public Thread getMainThread() {
        return mainThread;
    }
    public void setMainThread(Thread mainThread) {
        this.mainThread = mainThread;
    }
}