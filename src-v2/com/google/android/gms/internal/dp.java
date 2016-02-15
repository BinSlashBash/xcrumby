/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.internal.dw;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class dp {
    private static final ThreadFactory ra = new ThreadFactory(){
        private final AtomicInteger rd = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AdWorker #" + this.rd.getAndIncrement());
        }
    };
    private static final ThreadPoolExecutor rb = new ThreadPoolExecutor(0, 10, 65, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(true), ra);

    public static void execute(final Runnable runnable) {
        try {
            rb.execute(new Runnable(){

                @Override
                public void run() {
                    Process.setThreadPriority((int)10);
                    runnable.run();
                }
            });
            return;
        }
        catch (RejectedExecutionException var0_1) {
            dw.c("Too many background threads already running. Aborting task.  Current pool size: " + dp.getPoolSize(), var0_1);
            return;
        }
    }

    public static int getPoolSize() {
        return rb.getPoolSize();
    }

}

