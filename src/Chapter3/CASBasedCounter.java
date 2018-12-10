package Chapter3;

import util.Tools;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.*;
public class CASBasedCounter
{
    private volatile long count;
    /**
     * 这里使用AtomicLongFieldUpdater只是为了便于讲解和运行该实例，
     * 实际上更多的情况是我们不使用AtomicLongFieldUpdater，而是使用
     * java.util.concurrent.atomic包下的其他更为直接的类。
     */
    private final AtomicLongFieldUpdater<CASBasedCounter> fieldUpdater;
    public CASBasedCounter() throws SecurityException, NoSuchFieldException
    {
        fieldUpdater = AtomicLongFieldUpdater.newUpdater(CASBasedCounter.class, "count");
    }
    public long value()
    {
        return count;
    }

    boolean compareAndSwap(long oldValue, long newValue)
    {
        boolean isOK = fieldUpdater.compareAndSet(this, oldValue, newValue);
        return isOK;
    }

    public void increment()
    {
        long oldValue;
        long newValue;
        do{
            oldValue = count;
            newValue = oldValue + 1;
        }while(!compareAndSwap(oldValue, newValue));
    }
    public static void main(String[] args) throws Exception
    {
        final CASBasedCounter counter = new CASBasedCounter();
        Thread t;
        Set<Thread> threads = new HashSet<>();
        for(int i = 0; i < 20; i++)
        {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(50);
                    counter.increment();
                }
            });
            threads.add(t);
        }
        for (int i = 0; i < 8; i++)
        {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(50);
                    System.out.println(String.valueOf(counter.value()));
                }
            });
            threads.add(t);
        }
        Tools.startAndWaitTerminated(threads);
        System.out.println("final count: " + String.valueOf(counter.value()));
    }
}
