package Chapter5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import util.Tools;
public class ShootPractice
{
    final Soldier[][] rank;
    final int N;
    final int lasting;
    volatile boolean done = false;
    volatile int nextLine = 0;
    final CyclicBarrier shiftBarrier;
    final CyclicBarrier startBarrier;
    public ShootPractice(int N, final int lineCount, int lasting)
    {
        this.N = N;
        this.lasting = lasting;
        this.rank = new Soldier[lineCount][N];
        for (int i = 0; i < lineCount; i++)
        {
            for (int j = 0; j < N; j++)
            {
                rank[i][j] = new Soldier(i * N + j);
            }
        }
        shiftBarrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                nextLine = (nextLine + 1) % lineCount;
                System.out.printf("Next turn is : %d\n", nextLine);
            }
        });
        startBarrier = new CyclicBarrier(N);
    }
    public static void main(String[] args)throws InterruptedException
    {
        ShootPractice sp = new ShootPractice(4, 5, 24);
        sp.start();
    }
    public void start() throws InterruptedException{
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; ++i)
        {
            threads[i] = new Shooting(i);
            threads[i].start();
        }
        Thread.sleep(lasting * 1000);
        stop();
        for (Thread t : threads)
        {
            t.join();
        }
        System.out.println("Practice finished");
    }
    public void stop()
    {
        done = true;
    }
    class Shooting extends Thread{
        final int index;
        public Shooting(int index)
        {
            this.index = index;
        }

        @Override
        public void run() {
            Soldier soldier;
            try
            {
                while (!done)
                {
                    soldier = rank[nextLine][index];
                    startBarrier.await();
                    soldier.fire();
                    shiftBarrier.await();
                }
            } catch (InterruptedException e)
            {

            }catch (BrokenBarrierException e)
            {
                e.printStackTrace();
            }
        }
    }
    static class Soldier{
        private final int seqNo;

        public Soldier(int seqNo) {
            this.seqNo = seqNo;
        }

        public void fire() {
            System.out.println(this + " start firing...");
            Tools.randomPause(5000);
            System.out.println(this + " fired.");
        }

        @Override
        public String toString() {
            return "Soldier-" + seqNo;
        }
    }
}
