package Chapter2;

public class SpeculativeLoadExample {
    private boolean ready = false;
    private int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
    public void writer(){
        int[] newData = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        for (int i = 0; i < newData.length; i++)
        {
            newData[i] = newData[i] - i;
        }
        data = newData;
        ready = true;
    }
    public void reader()
    {
        int[] snapshot;
        if(ready){
            snapshot = data;
            //dosomething();
        }
    }
}
