package util;

import java.io.Closeable;
import java.util.*;
public class Tools
{
    public static void randomPause(int time)
    {
        Random r = new Random();
        try
        {
            Thread.currentThread().sleep(r.nextInt(time));
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void startAndWaitTerminated(Set<Thread> threads)
            throws InterruptedException {
        if (null == threads) {
            throw new IllegalArgumentException("threads is null!");
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
    public static void silentClose(Closeable... closeable) {
        if (null == closeable) {
            return;
        }
        for (Closeable c : closeable) {
            if (null == c) {
                continue;
            }
            try {
                c.close();
            } catch (Exception ignored) {
            }
        }
    }
}
