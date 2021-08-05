package newio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class NIODemo {
    public static void main(String[] args) {
        buffers();
    }

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