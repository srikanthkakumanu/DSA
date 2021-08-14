package newio;

import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.channels.CompletionHandler;

public class NIO2Demo {
    public static void main(String[] args) throws Exception {
        // fileSystems();
        // pathsAndFiles();
        asyncFileChannelIO();
    }

    /**
     * Demonstrates usage of FileSystem, FileSystems, FileSystemProvider, FileStore
     * @throws Exception
     */
    private static void fileSystems() throws Exception {
        List<FileSystemProvider> providers = FileSystemProvider.installedProviders();
        System.out.println("Available/Installed File Systems: ------------ \n");
        providers.forEach(provider -> System.out.println(provider));

        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath("newio", "xanadu.txt");
        System.out.println(p.getFileName() + " " + p.getParent() + " " + p.getRoot());
        System.out.println(p.isAbsolute());

        // file stores
        FileStore fstore = Files.getFileStore(Paths.get("/home"));
        System.out.println("Total Space: " + fstore.getTotalSpace() + " Unallocated Space: " + fstore.getUnallocatedSpace() 
                            + " Usable Space: " + fstore.getUsableSpace() + " isReadOnly: " + fstore.isReadOnly() 
                            + " Name: " + fstore.name() + " Type: " + fstore.type());

    }

    /**
     * File I/O: Demonstrates usage of Path, Paths, Files operations
     * @throws IOException
     */
    private static void pathsAndFiles() throws IOException {
        Path dir = Paths.get("newio");
        Path file = Paths.get("newio/xanadu.txt");
        Path other = Paths.get("newio/words.txt");

        System.out.println("Path usage: ----------- \n");
        System.out.println("Path Exists: " + Files.exists(dir));
        System.out.println("Path Not Exists: " + Files.notExists(dir));
        // to check whether path is a file or a directory
        System.out.println("Is Regular File or Directory: " + Files.isRegularFile(file) + " and " + Files.isRegularFile(dir));
        // file permissions check
        System.out.println("Is Readable: " + Files.isReadable(dir) + " and " + Files.isReadable(file));
        System.out.println("Is Writable: " + Files.isWritable(dir) + " and " + Files.isWritable(file));
        System.out.println("Is Executable: " + Files.isExecutable(dir) + " and " + Files.isExecutable(file));
        System.out.println("Is Same File: " + Files.isSameFile(file, other));
        Path newFile = Paths.get("newio/testfile_" + UUID.randomUUID().toString() + ".txt");
        Path newDir = Paths.get("newio/testdir");
        // Files.createFile(newFile);
        // Files.createDirectory(newDir);
        System.out.println("Is Regular File or Directory: " + Files.isRegularFile(newFile) + " and " + Files.isDirectory(newDir));
        // create sub directories
        // Path dirs = Paths.get("newio/testdir2");
        // Path subDir = dirs.resolve("testdir3");
        // Files.createDirectories(subDir);
        // Path temp = Paths.get("newio");
        // Files.createTempFile(temp, "tempFile", ".txt");
        // Files.delete(temp); // delete file
        // Files.deleteIfExists(temp); // delete if exists

        // copy
        // Path dir1 = Paths.get("newio/firstdir_" + UUID.randomUUID().toString());
        // Path dir2 = Paths.get("newio/otherdir_" + UUID.randomUUID().toString());
        // Files.createDirectory(dir1);
        // Files.createDirectory(dir2);
        // Path file1 = dir1.resolve("filetocopy.txt");
        // Path file2 = dir2.resolve("filetocopy.txt");
        // Files.createFile(file1);
        // Files.copy(file1, file2);
        // The above approach copy fails if the target file exists unless the REPLACE_EXISTING option is specified
        // Files.copy(file1, file2, StandardCopyOption.REPLACE_EXISTING); 

        // move
        // Files.move(file1, file2);
        // The above approach move fails if the target file exists unless the REPLACE_EXISTING option is specified
        // Files.move(file1, file2, StandardCopyOption.REPLACE_EXISTING);

        // reading file contents
        List<String> lines = Files.readAllLines(Paths.get("newio/xanadu.txt"));
        lines.forEach(line -> System.out.println(line));

        // writing file contents
        // Path p = Paths.get("newio/xanadu_temp.txt");
        // Files.write(p, lines, StandardOpenOption.CREATE);
    }

    /**
     * Demonstrates usage of Async File Channel I/O (Asynchronous file channel).
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void asyncFileChannelIO() throws IOException, InterruptedException, ExecutionException {
        // Without callback handler i.e. CompletionHandler
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("newio/xanadu.txt"));
        ByteBuffer bbuff = ByteBuffer.allocate(1024);
        Future<Integer> result = channel.read(bbuff, 0);
        while(!result.isDone()) {
            System.out.println("We can perform other actions in the meanwhile...");
            TimeUnit.MILLISECONDS.sleep(500);
        }
        System.out.println("Finished = " + result.isDone());
        System.out.println("Bytes read = " + result.get());
        channel.close();

        // With callback handler i.e. CompletionHandler
        channel = AsynchronousFileChannel.open(Paths.get("newio/xanadu.txt"));
        bbuff = ByteBuffer.allocate(1024);
        Thread mainThread = Thread.currentThread();
        channel.read(bbuff, 0, null, 
            new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(Integer result, Void attachment) {
                    System.out.println("Bytes read: " + result);
                    mainThread.interrupt(); // intentionally interrupting the thread
                
                }
                @Override
                public void failed(Throwable exc, Void attachment) {
                    System.err.println("Failure: " + exc.toString());
                    mainThread.interrupt(); // intentionally interrupting the thread
                }
            }
        );
        System.out.println("Waiting for completion");
        try {
            mainThread.join();
        } catch (InterruptedException e) { System.err.println("Exception caught at asyncFileChannelIO() while mainThread.join(): " + e.getMessage()); }
        
        channel.close();
    }

}

