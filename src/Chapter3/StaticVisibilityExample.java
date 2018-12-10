package Chapter3;
import util.Tools;

import java.util.*;

public class StaticVisibilityExample
{
    private static Map<String, String> taskConfig;
    static
    {
        System.out.println("TheClass being initialized..");
        taskConfig = new HashMap<>();
        taskConfig.put("url", "hhhhhhhhhhhh");
        taskConfig.put("timeout", "10000000");
    }
    public static void changeConfig(String url, int timeout)
    {
        taskConfig = new HashMap<>();
        taskConfig.put("url", url);
        taskConfig.put("timeout", String.valueOf(timeout));
    }
    public static void run()
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = taskConfig.get("url");
                String timeOut = taskConfig.get("timeout");
                doTask(url, Integer.valueOf(timeOut));
            }
        });
    }
    private static void doTask(String url, int timeOut)
    {
        Tools.randomPause(500);
    }
}
