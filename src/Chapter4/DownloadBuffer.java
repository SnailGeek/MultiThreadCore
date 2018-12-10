package Chapter4;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DownloadBuffer implements Closeable
{
    private long globalOffset;
    private long upperBound;
    private int offset = 0;
    public final ByteBuffer byteBuffer;
    private final Storage storage;

    public DownloadBuffer(long globalOffset, long upperBound, final Storage storage)
    {
        this.globalOffset = globalOffset;
        this.upperBound = upperBound;
        this.storage = storage;
        this. byteBuffer = ByteBuffer.allocate(1024*1024);
    }
    public void write(ByteBuffer buf) throws IOException
    {
        int length = buf.position();
        final int capacity = buf.capacity();
        if (offset + length > capacity || length == capacity)
            flush();
        byteBuffer.position(offset);
        buf.flip();
        byteBuffer.put(buf);
        offset += length;
    }
    public void flush() throws IOException
    {
        int length;
        byteBuffer.flip();
        length = storage.store(globalOffset, byteBuffer);
        byteBuffer.clear();
        globalOffset += length;
        offset = 0;
    }

    public void close() throws IOException
    {
        System.out.printf("globalOffset:%s, upperBound:%s", globalOffset, upperBound);
        if (globalOffset < upperBound)
        {
            flush();
        }
    }
}
