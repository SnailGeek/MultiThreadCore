package Chapter3;
import java.util.*;

public class SafeObjPublishWhenStartingThread {
    private final Map<String, String> objectState;
    private SafeObjPublishWhenStartingThread(Map<String, String> objectState) {
        this.objectState = objectState;
    }
    private void init() {
        new Thread(){
            @Override
            public void run() {
                //doSomeThing();
            }
        }.start();
    }
    //工厂方法
    public static SafeObjPublishWhenStartingThread newInstance(
            Map<String, String> objState) {
        SafeObjPublishWhenStartingThread instance =
                new SafeObjPublishWhenStartingThread(objState);
        instance.init();
        return instance;
    }
}
