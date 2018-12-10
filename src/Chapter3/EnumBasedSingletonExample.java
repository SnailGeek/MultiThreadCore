package Chapter3;

public class EnumBasedSingletonExample
{
    public static void main(String[] args)
    {
        Thread t = new Thread(){
            @Override
            public void run() {
                System.out.println(Singleton.class.getName());
                Singleton.INSTANCE.someService();
            }
        };
        t.start();
    }
    public static enum Singleton
    {
        INSTANCE;
        Singleton()
        {
            System.out.println("Singleton inited.");
        }
        public void someService()
        {
            System.out.println("someService invoked.");
        }
    }
}
