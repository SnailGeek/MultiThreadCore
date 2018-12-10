package Chapter4;

import util.Tools;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

public class Storage implements Closeable, AutoCloseable
{
    private final RandomAccessFile storeFile;
    private final FileChannel storeChannel;
    protected final AtomicLong totalWrites = new AtomicLong(0);
    public Storage(long fileSize, String fileShortName) throws IOException
    {
        String fullFileName = System.getProperty("java.io.tmpdir") + "/" + fileShortName;
        String localFileName;
        localFileName = createStoreFile(fileSize, fullFileName);
        storeFile = new RandomAccessFile(localFileName, "rw");
        storeChannel = storeFile.getChannel();
    }

    public int store(long offset, ByteBuffer byteBuf) throws IOException
    {
        int length;
        storeChannel.write(byteBuf, offset);
        length = byteBuf.limit();
        totalWrites.addAndGet(length);
        return length;
    }
    public long getTotalwrites()
    {
        return totalWrites.get();
    }
    public String createStoreFile(final long fileSize, String fullFileName) throws IOException
    {
        File file = new File(fullFileName);
        System.out.printf("create local file:%s", fullFileName);
        RandomAccessFile raf;
        raf = new RandomAccessFile(file, "rw");
        try
        {
            raf.setLength(fileSize);
        }
        finally {
            Tools.silentClose(raf);
        }
        return fullFileName;
    }
    public synchronized void close() throws IOException
    {
        if(storeChannel.isOpen())
        {
            Tools.silentClose(storeChannel, storeFile);
        }
    }
}
