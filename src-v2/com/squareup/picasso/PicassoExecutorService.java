/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.NetworkInfo
 */
package com.squareup.picasso;

import android.net.NetworkInfo;
import com.squareup.picasso.Utils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PicassoExecutorService
extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    PicassoExecutorService() {
        super(3, 3, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new Utils.PicassoThreadFactory());
    }

    private void setThreadCount(int n2) {
        this.setCorePoolSize(n2);
        this.setMaximumPoolSize(n2);
    }

    void adjustThreadCount(NetworkInfo networkInfo) {
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            this.setThreadCount(3);
            return;
        }
        switch (networkInfo.getType()) {
            default: {
                this.setThreadCount(3);
                return;
            }
            case 1: 
            case 6: 
            case 9: {
                this.setThreadCount(4);
                return;
            }
            case 0: 
        }
        switch (networkInfo.getSubtype()) {
            default: {
                this.setThreadCount(3);
                return;
            }
            case 13: 
            case 14: 
            case 15: {
                this.setThreadCount(3);
                return;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 12: {
                this.setThreadCount(2);
                return;
            }
            case 1: 
            case 2: 
        }
        this.setThreadCount(1);
    }
}

