package Chapter2;

public class Notfier implements Runnable
{
    private boolean goal = true;
    public boolean isGoal()
    {
        return goal;
    }
    public void setGoal(boolean goal)
    {
        this.goal = goal;
    }

    @Override
    public void run() {
        while(isGoal())
        {
            System.out.println("aaaaaaa");
            if (!isGoal())
            {
                System.out.println("hhhhh");
            }
        }
    }
    public static void main(String[] args) throws InterruptedException
    {
        Notfier n = new Notfier();
        Thread thread = new Thread(n);
        thread.start();
        Thread.sleep(333);
        n.setGoal(false);
    }
}
