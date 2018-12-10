package Chapter2;

import util.Tools;

public class ThreadStartVisibility
{
    static int data = 0;
    public static void main(String[] args)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                Tools.randomPause(50);
                System.out.println(data);
            }
        };
        data = 1;
        thread.start();
        Tools.randomPause(50);
        data = 2;
    }
}
