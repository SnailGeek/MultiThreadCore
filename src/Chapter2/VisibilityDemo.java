package Chapter2;
import util.Tools;

public class VisibilityDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        TimeConsumingTask timeConsumingTask = new TimeConsumingTask();
        Thread thread = new Thread(timeConsumingTask);
        thread.start();
        Thread.sleep(10000);
        timeConsumingTask.cancel();
    }

}

class TimeConsumingTask implements Runnable
{
    private volatile boolean toCancel = false;

    @Override
    public void run() {
        while(!toCancel)
        {
            if(doExecute())
            {
                break;
            }
        }
        if (toCancel)
        {
            System.out.println("Task was canceled");
        }
        else
        {
            System.out.println("Task done.");
        }
    }
    private boolean doExecute()
    {
        boolean isDone = false;
        System.out.println("executing...");
        Tools.randomPause(50);
        return isDone;
    }
    public void cancel()
    {
        toCancel = true;
        System.out.println(this + " canceled.");
    }
}
