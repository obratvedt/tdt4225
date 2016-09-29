import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


public class IOCapacity {
    //Block size of 4KB
    private static final int BLOCKSIZE = 4096;
    //Number of blocks needed for 1GB with 4KB block size
    private static final long NBLOCKS = 262144;

    private static void calculateIO(int gb, String filename) throws IOException {
        //Variable to write multiple GB's of data
        long noOfBlocks = gb * NBLOCKS;

        Path file = Paths.get(System.getProperty("user.dir"), filename);
        SeekableByteChannel out = Files.newByteChannel(file, EnumSet.of(CREATE, APPEND));
        //Timer
        long start = System.currentTimeMillis();
        //Write blocks to file
        for (long i = 0; i < noOfBlocks; i++){
            ByteBuffer buff = ByteBuffer.allocate(BLOCKSIZE);
            out.write(buff);
        }
        long totalTime = System.currentTimeMillis() - start;

        System.out.printf("File size: %d GB \n", gb);
        System.out.printf("Throughput: %d MB/s \n", ((noOfBlocks* BLOCKSIZE)/1024/1024)/(totalTime/1000));
        System.out.printf("Time: %d \n\n" , totalTime);
    }

    public static void main(String[] args) throws IOException {
        calculateIO(1, "myjavadata1gb");
        calculateIO(2, "myjavadata2gb");
        calculateIO(4, "myjavadata4gb");
        calculateIO(8, "myjavadata8gb");
        calculateIO(16, "myjavadata16gb");
        calculateIO(32, "myjavadata32gb");
    }
}
