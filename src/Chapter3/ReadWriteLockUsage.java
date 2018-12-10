package Chapter3;

import java.util.concurrent.locks.*;

public class ReadWriteLockUsage
{
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void reader() {
        readLock.lock();
        try {
            //在此区域读取共享变量
        }finally {
            readLock.unlock();
        }
    }
    public void writer(){
        writeLock.lock();
        try {
            //在此区域访问（读、写）共享变量
        }finally {
            writeLock.unlock();
        }
    }
}
