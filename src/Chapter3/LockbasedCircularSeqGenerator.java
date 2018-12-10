package Chapter3;

import java.util.concurrent.locks.*;

public class LockbasedCircularSeqGenerator
{
    private short sequence = -1;
    private final Lock lock = new ReentrantLock();

    public short nextSequence()
    {
        lock.lock();
        try
        {
            if (sequence >= 999)
                sequence = 0;
            else
                sequence++;
            return sequence;
        }
        finally {
            lock.unlock();
        }
    }
}
