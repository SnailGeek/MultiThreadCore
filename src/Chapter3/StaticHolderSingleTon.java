package Chapter3;

public class StaticHolderSingleTon
{
    private StaticHolderSingleTon() {}
    private static class InstanceHolder{
        final static StaticHolderSingleTon INSTANCE = new StaticHolderSingleTon();
    }
    public static StaticHolderSingleTon getInstance() {
        return  InstanceHolder.INSTANCE;
    }
}
