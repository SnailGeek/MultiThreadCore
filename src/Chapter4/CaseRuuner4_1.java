package Chapter4;

public class CaseRuuner4_1
{
    public static void main(String[] args) throws Exception
    {
        if (0 == args.length)
        {
            args = new String[]{"http://", "2", "3"};
        }
        main0(args);
    }

    public static void main0(String[] args) throws Exception
    {
        final int argc = args.length;
        BigFileDownloader downloader = new BigFileDownloader(args[0]);
        int workerThreadCount = argc >= 2 ? Integer.valueOf(args[1]) : 2;
        int reportInterval = argc >3 ? Integer.valueOf(args[2]) : 2;
        System.out.printf("downloading %s%nConfig:worker threads:%s, reportInterval:%s s.",
                args[0], workerThreadCount, reportInterval);
        downloader.download(workerThreadCount, reportInterval * 1000);
    }
}
