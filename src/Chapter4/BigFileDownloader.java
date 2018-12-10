package Chapter4;

import util.Tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class BigFileDownloader
{
    protected final URL requestURL;
    protected final long fileSize;

    protected final Storage storage;
    protected final AtomicBoolean taskCanceled = new AtomicBoolean(false);
    public BigFileDownloader(String strURL) throws Exception
    {
        requestURL = new URL(strURL);
        fileSize = retieveFileSize(requestURL);
        System.out.printf("file total size:%s", fileSize);
        String fileName = strURL.substring(strURL.lastIndexOf('/') + 1);
        storage = new Storage(fileSize, fileName);
    }
    public void download(int taskCount, long reportInterval) throws Exception
    {
        long chunkSizePerThread = fileSize / taskCount;
        long lowerBound = 0;
        long upperBound = 0;

        DownloadTask dt;
        for (int i = taskCount - 1; i >= 0; i--)
        {
            lowerBound = i*chunkSizePerThread;
            if (i == taskCount - 1)
                upperBound = fileSize;
            else
                upperBound = lowerBound+chunkSizePerThread-1;
            dt = new DownloadTask(lowerBound, upperBound, requestURL, storage, taskCanceled);
            dispatchWork(dt, i);
        }
        reporetProgress(reportInterval);
        doCleanup();
    }


    protected void doCleanup()
    {
        Tools.silentClose(storage);
    }
    protected void cancelDownload()
    {
        if(taskCanceled.compareAndSet(false, true))
        {
            doCleanup();
        }
    }
    public void dispatchWork(final DownloadTask dt, int workerIndex)
    {
        Thread workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    dt.run();
                }catch (Exception e)
                {
                    e.printStackTrace();
                    cancelDownload();
                }
            }
        });
        workerThread.setName("downloader-" + workerIndex);
        workerThread.start();
    }
    public static long retieveFileSize(URL requestURL) throws Exception
    {
        long size = -1;
        HttpURLConnection conn = null;
        try
        {
            conn = (HttpURLConnection)requestURL.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setRequestProperty("Connection", "Keep-alive");
            conn.connect();
            int statusCode = conn.getResponseCode();
            if (HttpURLConnection.HTTP_OK != statusCode)
            {
                throw new Exception("Serve exception, status code: " + statusCode);
            }
            String cl = conn.getHeaderField("Content-Length");
            size = Long.valueOf(cl);
        }finally {
            if (null != conn)
                conn.disconnect();
        }
        return size;
    }
    private void reporetProgress(long reportInterval) throws InterruptedException
    {
        float lastCompletion;
        int completion = 0;
        while(!taskCanceled.get())
        {
            lastCompletion = completion;
            completion = (int)(storage.getTotalwrites() * 100 / fileSize);
            if (completion == 100)
            {
                break;
            }
            else if(completion - lastCompletion >= 1)
            {
                System.out.printf("Completion:%s%s", completion);
                if (completion >= 90)
                    reportInterval = 1000;
            }
            Thread.sleep(reportInterval);
        }
        System.out.printf("Completion:%s%s", completion);
    }
}


