/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.http.impl.client.DefaultHttpClient
 */
package com.tapstream.sdk;

import java.util.concurrent.ThreadFactory;
import org.apache.http.impl.client.DefaultHttpClient;

public class WorkerThread
extends Thread {
    public DefaultHttpClient client = new DefaultHttpClient();

    public WorkerThread(Runnable runnable) {
        super(runnable);
    }

    public static class Factory
    implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            return new WorkerThread(runnable);
        }
    }

}

