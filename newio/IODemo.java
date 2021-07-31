package newio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IODemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // fileByteStreamsUnbuffered();
        // fileCharStreamUnbuffered();
        // bufferByteStream();
        // bufferCharStream();
        // byteArrayByteStream();
        // dataByteStreams();
        objectByteStreams();
        // scanAndFormat();
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

    /**
     * Data streams support binary I/O of primitive data types and string values. 
     * Supported primitive data types are boolean, char, byte, short, int, long, float and double.
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void dataByteStreams() throws FileNotFoundException, IOException {
        String file = "newio/invoice.dat";
        double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
        int[] units = {12, 8, 13, 29, 50};
        String[] descs = { "Java T-shirt", "Java Mug", "Duke Juggling Dolls", "Java Pin", "Java Key Chain" };

        // Write some data into a file.
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            for(int i = 0; i < prices.length; i++) {
                dos.writeDouble(prices[i]); dos.writeInt(units[i]); dos.writeUTF(descs[i]);
            }
        } finally {
            if(dos != null) dos.close();
        }

        // Reading from file
        DataInputStream din = null;
        double total = 0.0;
        try {
            din = new DataInputStream(new FileInputStream("newio/invoice.dat"));
            
            double price; 
            int unit; 
            String desc;
            try {
                while(true) {
                    price = din.readDouble();
                    unit = din.readInt();
                    desc = din.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n", unit, desc, price);
                    total = unit * price;
                }
            } catch(EOFException e) { System.out.println("Exception caught at dataByteStreams() : " + e.getMessage()); }
            System.out.format("For a Total of: $%.2f%n", total);
        } finally { if(din != null) din.close(); }
    }
 
    /**
     * Object streams support I/O of objects (Serialization & De-serialization) just like data streams 
     * support I/O of primitive data types. All the primitive data I/O methods covered in data streams 
     * are also implemented in object streams. So an object stream can contain a mixture of primitive 
     * and object values. 
     * 
     * The biggest advantage of object streams is that while they are writing/reading an object, all 
     * corresponding references(inherent references or other objects that are stored within a object) 
     * of that object are also preserved.
     * 
     * e.g. If a -> (b, c) and b -> (d, e) then object stream preserves whole a, b, c, d, e while 
     * reading/writing object a.
     * 
     * Basically, the ObjectOutputStream converts Java objects into corresponding streams. This is known 
     * as Serialization. Those converted streams can be stored in files or transferred through networks. 
     * Now, if we need to read those objects, we will use the ObjectInputStream that will convert the 
     * streams back to corresponding objects. This is known as De-serialization.
     * 
     * If two objects on the same stream both contain references to a single object, and they both refer 
     * to a single object when they're read back as well. Because, A stream can only contain one copy of 
     * an object, though it can contain any number of references to it. Thus if you explicitly write an 
     * object to a stream twice, you're really writing only the reference twice. 
     * 
     * However, if a single object is written to two different streams, it is effectively duplicated — a single 
     * program reading both streams back will see two distinct objects.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void objectByteStreams() throws IOException, ClassNotFoundException {
        ObjectInputStream obin = null;
        ObjectOutputStream obout = null;
        int number = 4948;
        String text = "This is text!";
        try {
            obout = new ObjectOutputStream(new FileOutputStream("newio/obj_invoice.dat"));
            obout.writeInt(number); obout.writeObject(text);

            obin = new ObjectInputStream(new FileInputStream("newio/obj_invoice.dat"));
            System.out.format("Number is %d and Text is %s \n", obin.readInt(), obin.readObject());
        } finally {
            if(obin != null) obin.close();
            if(obout != null) obout.close();
        }
    }   

    /**
     * The Scanner API breaks input into individual tokens associated with bits of data. By default,
     * Scanner uses a whitespace to separate tokens. To use different separator, invoke useDelimiter()
     * and specify regular expression. 
     * 
     * The Formatting API assembles data into nicely formatted, human-readable form: System.out.format() method
     * 
     * Java supports the user to interact with CLI environment in two ways: Standard streams and Console.
     * Console is more advanced than standard streams and it has most of the features provided standard streams.
     * @throws IOException
     */
    private static void scanAndFormat() throws IOException {
        Scanner scan = null;
        try {
            scan = new Scanner(new BufferedReader(new FileReader("newio/xanadu.txt")));
            //scan.useDelimiter(","); 
            //scan.useDelimiter(",\\s*"); // ,\\s* is regex for comma
            while(scan.hasNext())
                System.out.println(scan.next());

            // Scanner: Reading from CLI
            Scanner sin = new Scanner(System.in);
            String first, last;
            System.out.print("Enter Your First Name: ");
            first = sin.nextLine();
            System.out.print("Enter Your Last Name: ");
            last = sin.nextLine();
            System.out.format("Your Full Name is: %s %s \n", first, last);
            sin.close();

            // CLI: Standard streams     
            // InputStreamReader isr = new InputStreamReader(System.in);
            // CLI: Console
            Console c = System.console();
            
            if(c == null) {
                System.err.println("No Console supported.");
                System.exit(1);
            }

            // Logic for password change.
            String login = c.readLine("Enter Your Login: ");
            char[] oldPassword = c.readPassword("Enter Your Old Password: ");
            // Here you can add if condition logic to verify login and password.
            // if(verify(login, oldPassword))
            boolean isMatch;
            do {
                char[] nPass1 = c.readPassword("Enter Your New Password: ");
                char[] nPass2 = c.readPassword("Re-Enter Your New Password again: ");
                isMatch = Arrays.equals(nPass1, nPass2);
                if(isMatch)
                    c.format("Password for %s changed.%n", login);
                else
                    c.format("Passwords don't match. Try Again.%n");
                Arrays.fill(nPass1,' '); Arrays.fill(nPass2,' '); // security pre-caution: flushing stored passwords
                Arrays.fill(oldPassword, ' ');
            } while(!isMatch);
            
        } finally {
            if(scan != null) scan.close();
        }
    }
}


