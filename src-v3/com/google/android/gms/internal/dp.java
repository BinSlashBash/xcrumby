package com.google.android.gms.internal;

import android.os.Process;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class dp {
    private static final ThreadFactory ra;
    private static final ThreadPoolExecutor rb;

    /* renamed from: com.google.android.gms.internal.dp.1 */
    static class C03601 implements Runnable {
        final /* synthetic */ Runnable rc;

        C03601(Runnable runnable) {
            this.rc = runnable;
        }

        public void run() {
            Process.setThreadPriority(10);
            this.rc.run();
        }
    }

    /* renamed from: com.google.android.gms.internal.dp.2 */
    static class C03612 implements ThreadFactory {
        private final AtomicInteger rd;

        C03612() {
            this.rd = new AtomicInteger(1);
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AdWorker #" + this.rd.getAndIncrement());
        }
    }

    static {
        ra = new C03612();
        rb = new ThreadPoolExecutor(0, 10, 65, TimeUnit.SECONDS, new SynchronousQueue(true), ra);
    }

    public static void execute(Runnable task) {
        try {
            rb.execute(new C03601(task));
        } catch (Throwable e) {
            dw.m817c("Too many background threads already running. Aborting task.  Current pool size: " + getPoolSize(), e);
        }
    }

    public static int getPoolSize() {
        return rb.getPoolSize();
    }
}
