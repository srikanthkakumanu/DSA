package newio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NIODemo {
    public static void main(String[] args) throws IOException {
        buffers();
        // channels();
        // ChannelScatterAndGatherIO();
        // fileChannels();
        charsets();
    }

    /**
     * Demonstrates NIO Buffers
     */
    private static void buffers() {
        Buffer buffer = ByteBuffer.allocate(7); // Allocates a buffer (non-direct) inside the 'managed heap' of the java process
        // Buffer buffer = ByteBuffer.allocateDirect(7); // Allocates a buffer (direct) outside of the 'managed heap' of the java process

        printBuffer(buffer);        
        buffer.limit(5);
        printBuffer(buffer);        
        buffer.position(3);
        printBuffer(buffer);

        ByteBuffer bbuff = ByteBuffer.allocate(10); // buffer with internal byte array of 10 bytes
        byte[] bytes = new byte[200]; // byte array of 200 bytes
        ByteBuffer bbuff2 = ByteBuffer.wrap(bytes); // buffer with internal byte array of 200 bytes
        
        // a byte buffer with position:10 and limit:50 but capacity: 200 (bytes length)
        // Although it appears that the buffer can only access a subrange of this array, it actually has
        // access to the entire array: values 10 and 50 are only the starting values for
        // the position and limit
        bbuff = ByteBuffer.wrap(bytes, 10, 50);
        printBuffer(bbuff);
        
        // non-direct byte buffers i.e. allocate() have backing arrays and they can be accessed via array()
        buffer = ByteBuffer.allocate(10);
        if(buffer.hasArray())
            System.out.println("Buffer Array: " + buffer.array() + " and Offset: " + buffer.arrayOffset());
        
        bbuff.put((byte) 10).put((byte) 20).put((byte) 30);
        for (int i = 0; i < bbuff.position(); i++)
            System.out.println(bbuff.get(i));
        
    }

    /**
     * Simple Channel for read & write of I/O
     */
    private static void channels() {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);

        try {
            /* Approach-1:
            Approach-1 goal is to minimize OS I/O calls (via the write() method calls), although more data may end up 
            being copied as a result of the compact() method calls.
            */
            ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
            while(source.read(buffer) != -1) {
                buffer.flip();
                dest.write(buffer);
                buffer.compact();
            }
            buffer.flip();
            while(buffer.hasRemaining())
                dest.write(buffer);
            
            /* Approach-2:
            Approach-2 goal is to eliminate data copying, although more OS I/O calls might occur.
            */
            // ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
            while(source.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining())
                    dest.write(buffer);
                buffer.clear();
            }
        } 
        catch (IOException e) { System.err.println("Exception caught at channels: " + e.getMessage()); }
        finally {
            try {
                source.close(); dest.close();
            } catch (IOException e) { System.err.println("Exception caught at channels: " + e.getMessage()); }
        }
    }

    /**
     * Demonstrates Scatter & Gather I/O (Vectored I/O) and reading & writing to/from multiple buffers
     */
    private static void ChannelScatterAndGatherIO() {
        ScatteringByteChannel source = null;
        GatheringByteChannel dest = null;
        FileInputStream fis = null;
        FileOutputStream fout = null;
        
        try {
            fis = new FileInputStream("newio/xanadu.txt");
            source = (ScatteringByteChannel) Channels.newChannel(fis);
            ByteBuffer buffer1 = ByteBuffer.allocateDirect(1048);
            ByteBuffer buffer2 = ByteBuffer.allocateDirect(1058);
            ByteBuffer[] buffers = {buffer1, buffer2};
            source.read(buffers);
            
            buffer1.flip();
            while(buffer1.hasRemaining())
                System.out.println(buffer1.get());
            System.out.println();

            buffer2.flip();
            while(buffer2.hasRemaining())
                System.out.println(buffer2.get());
            
            buffer1.rewind(); buffer2.rewind();

            fout = new FileOutputStream("newio/xanadu_out.txt");
            dest = (GatheringByteChannel) Channels.newChannel(fout);
            buffers[0] = buffer2; buffers[1] = buffer1;
            dest.write(buffers);
        } 
        catch (IOException e) { System.err.println("Exception caught at channels: " + e.getMessage()); }
        finally {
            try {
                source.close(); dest.close();
            } catch (IOException e) { System.err.println("Exception caught at channels: " + e.getMessage()); }
        }

    }

    /**
     * Demonstrates usage of FileChannel
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void fileChannels() throws FileNotFoundException, IOException {
        RandomAccessFile raf = new RandomAccessFile("newio/filechannel.txt", "rw");
        FileChannel fc = raf.getChannel();
        long position;
        System.out.println("Position = " + (position = fc.position()));
        System.out.println("size: " + fc.size());
        String msg = "This is a test message from file channel.";
        ByteBuffer buffer = ByteBuffer.allocateDirect(msg.length() * 2);
        buffer.asCharBuffer().put(msg);
        fc.write(buffer);
        fc.force(true);
        System.out.println("position: " + fc.position());
        System.out.println("size: " + fc.size());
        buffer.clear();
        fc.position(position);
        fc.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining())
            System.out.print(buffer.getChar());
    }

    /**
     * Demonstrates use of transferTo() and transferFrom() which transfer data between channels directly.
     * @throws IOException
     */
    private static void transferDataBtwChannels() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("From.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("To.txt", "rw");
        FileChannel toChannel = fromFile.getChannel();

        long position = 0, count = fromChannel.size();
        // transferFrom() method transfers data from a source channel into the FileChannel
        toChannel.transferFrom(fromChannel, position, count);

        // transferTo() method transfer from a FileChannel into some other channel
        fromChannel.transferTo(position, count, toChannel);
    }

    /**
     * Demonstrates use of charsets
     */
    private static void charsets() {
        Charset cset = StandardCharsets.US_ASCII;
        // Charset cset = Charset.forName("US-ASCII"); // alternative approach
        String text = "Sample text for convesion";

        System.out.println(cset.name());
        System.out.println(cset.displayName());
        System.out.println(cset.canEncode());

        //convert byte buffer in given charset to char buffer in unicode
        ByteBuffer bbuff = ByteBuffer.wrap(text.getBytes());
        CharBuffer cbuff = cset.decode(bbuff);        
        
        //convert char buffer in unicode to byte buffer in given charset
        ByteBuffer newByteBuffer = cset.encode(cbuff);
        while(newByteBuffer.hasRemaining())
            System.out.print("\t" + (char) newByteBuffer.get());

        byte[] encodedText =
        {
            0x66, 0x61, (byte) 0xc3, (byte) 0xa7, 0x61, 0x64, 0x65, 0x20, 0x74,
            0x6f, 0x75, 0x63, 0x68, (byte) 0xc3, (byte) 0xa9
        }; // fa??ade touch??

        String msg = new String(encodedText, StandardCharsets.UTF_8);
        System.out.println(msg);
        byte[] bytes = msg.getBytes();
        for (byte _byte: bytes)
            System.out.print(Integer.toHexString(_byte & 255) + " ");
        System.out.println();
    }

    /**
     * Simple utility method to print buffer information
     * @param buffer
     */
    private static void printBuffer(Buffer buffer) {
        System.out.format("Buffer=[Capacity: %d, Limit: %d, Position: %d, Remaining: %d]\n", 
                            buffer.capacity(), 
                            buffer.limit(), 
                            buffer.position(), 
                            buffer.remaining());
        System.out.println(buffer);
    }
}