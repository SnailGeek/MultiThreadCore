package Chapter2;

import util.Tools;

public class ThreadJoinVisibility {
    static int data = 0;

    public static void main(String[] args)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                Tools.randomPause(50);
                data = 1;
            }
        };

        thread.start();
        try
        {
            thread.join();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(data);
    }
}
