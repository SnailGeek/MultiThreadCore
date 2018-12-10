package Chapter3;

import java.util.concurrent.locks.*;

public class ExplicitLockInfo
{
    private static final Lock lock = new ReentrantLock();
    private static int sharData = 0;

    public static void main(String[] args) throws Exception
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try
                {
                    try
                    {
                        Thread.sleep(22200000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                finally {
                    lock.unlock();
                }
            }
        });
        t.start();
        Thread.sleep(100);
        lock.lock();
        try
        {
            System.out.println("sharedData: " + sharData);
        }
        finally {
            lock.unlock();
        }
    }
}
