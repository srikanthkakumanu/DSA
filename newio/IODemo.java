package newio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IODemo {

    public static void main(String[] args) throws IOException {
        // fileByteStreamsUnbuffered();
        // fileCharStreamUnbuffered();
        // bufferByteStream();
        // bufferCharStream();
        byteArrayByteStream();
    }

    /**
     * Demonstrates FileInputStream and FileOutputStream. 
     * They are byte stream based and un-buffered.
     * They use file as source and it's data in bytes as stream.
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void fileByteStreamsUnbuffered() throws IOException, FileNotFoundException {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        
        try {
            fin = new FileInputStream("newio/xanadu.txt");
            fout = new FileOutputStream("newio/xanadu_bout.txt");
            // reads & writes all bytes at a time
            // byte[] data = fin.readAllBytes();
            // fout.write(data);        
    
            // reads & writes 1 byte at a time i.e. holds 8 bits in part variable.
            int part;
            while((part = fin.read()) != -1) 
                fout.write(part);
        } 
        finally {
            if(fin != null) fin.close(); // always close to avoid resource leaks
            if(fout != null) fout.close(); // always close to avoid resource leaks
        }
    }

    /**
     * Demonstrates FileReader and FileWriter.
     * They are char stream based and un-buffered.
     * They use file as source and it's data as stream.
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void fileCharStreamUnbuffered() throws FileNotFoundException, IOException {
        FileReader freader = null;
        FileWriter fwriter = null;

        try {
            freader = new FileReader("newio/xanadu.txt");
            fwriter = new FileWriter("newio/xanadu_cout.txt");
            // reads a single character at a time i.e. holds 16 bits in part variable
            // In Java, all characters are Unicode characters i.e. unsigned int 2 bytes (16 bits)
            int part; // In Java, all characters are unicode characters i.e. unsigned int
            while((part = freader.read()) != -1)
                fwriter.write(part);
            
        } finally {
            if(freader != null) freader.close(); // always close to avoid resource leaks
            if(fwriter != null) fwriter.close(); // always close to avoid resource leaks
        }
    }

    /**
     * Demonstrates BufferedInputStream and BufferedOutputStream. 
     * Un-buffered stream means that each read and write request is handled directly by the underlying OS. 
     * 
     * This can make a program much less efficient, since each such request often triggers disk access, network activity, 
     * or some other operation that is relatively expensive. Therefore, to reduce this overhead, Java platform implements 
     * buffered I/O streams. 
     * 
     * Buffered input streams read data from a memory area known as a buffer. The native input API is called only when the 
     * buffer is empty. Similarly, buffered output streams write data to a buffer, and the native output API is called only 
     * when the buffer is full. 
     * 
     * We can convert an unbuffered stream into a buffered stream by passing the unbuffered stream object to a buffered 
     * stream class constructor.
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void bufferByteStream() throws FileNotFoundException, IOException {
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;

        try {
            int bsize = 1024 * 8; // we can specify internal byte buffer size
            bin = new BufferedInputStream(new FileInputStream("newio/xanadu.txt"), bsize); 
            bout = new BufferedOutputStream(new FileOutputStream("newio/xanadu_bbout.txt"));
            System.out.println("Available Bytes: " + bin.available());

            // byte[] parts = bin.readAllBytes();
            // bout.write(parts);
            boolean isMarkSupported = bin.markSupported();
            
            if(isMarkSupported)
                bin.mark(bin.available());

            // bin.skip(12); // no. of bytes to skip
            int part; // reads 1 byte at a time
            while((part = bin.read()) != -1)
                bout.write(part);
            
        } finally {
            if(bin != null) bin.close(); // always close to avoid resource leaks
            if(bout != null) bout.close(); // always close to avoid resource leaks
        }
    }    

    /**
     * Demonstrates BufferedReader and BufferedWriter. 
     * Un-buffered stream means that each read and write request is handled directly by the underlying OS. 
     * 
     * This can make a program much less efficient, since each such request often triggers disk access, network activity, 
     * or some other operation that is relatively expensive. Therefore, to reduce this overhead, Java platform implements 
     * buffered I/O streams. 
     * 
     * Buffered input streams read data from a memory area known as a buffer. The native input API is called only when the 
     * buffer is empty. Similarly, buffered output streams write data to a buffer, and the native output API is called only 
     * when the buffer is full. 
     * 
     * We can convert an unbuffered stream into a buffered stream by passing the unbuffered stream object to a buffered 
     * stream class constructor.
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void bufferCharStream() throws FileNotFoundException, IOException {
        BufferedReader breader = null;
        BufferedWriter bwriter = null;

        try {
            int bsize = 1024 * 8; // we can specify internal byte buffer size
            breader = new BufferedReader(new FileReader("newio/xanadu.txt"), bsize);
            bwriter = new BufferedWriter(new FileWriter("newio/xanadu_bcout.txt"));

            // int part; // reads single character at a time
            // while((part = breader.read()) != -1)
            //     bwriter.write(part);

            // String line; // reads each line
            // while((line = breader.readLine()) != null) 
            //     bwriter.write(line + System.lineSeparator()); // by default it does not keep line separators

            // reads via stream from buffer
            bwriter.write(breader.lines().collect(Collectors.joining(System.lineSeparator())));
            
        } finally {
            if(breader != null) breader.close(); // always close to avoid resource leaks
            if(bwriter != null) bwriter.close(); // always close to avoid resource leaks
        }
    }

    /**
     * Demonstrates Byte Array Input and Output Stream.
     * ByteArray input stream is buffered. It has an internal buffer that contains bytes
     * that may be read from the stream.
     * @throws IOException
     */
    private static void byteArrayByteStream() throws IOException {
        byte[] data = { 100, 101, 102, 103, 104, 105, 106 };
        ByteArrayInputStream bain = new ByteArrayInputStream(data);
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        try {

            // reads all bytes at a time
            // baout.write(bain.readAllBytes());

            int part;
            byte[] holder = new byte[data.length];
            while((part = bain.readNBytes(holder, 0, holder.length)) != 0)
                baout.write(holder, 0, part);

            byte[] output = baout.toByteArray();
            for(int counter = 0; counter < output.length; counter++) 
                System.out.print(output[counter] + " ");

            baout.flush();
        } finally {
            if(bain != null) bain.close();
            if(baout != null) baout.close();
        }
    }
}


