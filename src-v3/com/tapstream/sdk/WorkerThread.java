package com.tapstream.sdk;

import java.util.concurrent.ThreadFactory;
import org.apache.http.impl.client.DefaultHttpClient;

public class WorkerThread extends Thread {
    public DefaultHttpClient client;

    public static class Factory implements ThreadFactory {
        public Thread newThread(Runnable runnable) {
            return new WorkerThread(runnable);
        }
    }

    public WorkerThread(Runnable runnable) {
        super(runnable);
        this.client = new DefaultHttpClient();
    }
}
