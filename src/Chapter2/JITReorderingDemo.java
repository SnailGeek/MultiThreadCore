package Chapter2;

public class JITReorderingDemo
{
    private int externalData = 1;
    private Helper helper;
    public void creatHelper() {
        helper = new Helper(externalData);
    }
    public int consume() {
        int sum = 0;
        final Helper observedHelper = helper;
        if(null == observedHelper)
            sum = -1;
        else
            sum = observedHelper.payloadA + observedHelper.payloadD;
        return sum;
    }
    static class Helper {
        int payloadA, payloadD;
        public Helper(int externalData) {
            this.payloadA = externalData;
            this.payloadD = externalData;
        }
    }
    public static void main(String[] args) throws InterruptedException, IllegalAccessException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (long i = 0; i < 400000000; i++)
                {
                    JITReorderingDemo demo = new JITReorderingDemo();
                    demo.creatHelper();
                    if (demo.consume() != 4)
                        System.out.println(demo.consume());
                }

            }
        };
        thread.start();
    }
}
