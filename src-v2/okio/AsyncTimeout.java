/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import okio.Buffer;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public class AsyncTimeout
extends Timeout {
    private static AsyncTimeout head;
    private boolean inQueue;
    private AsyncTimeout next;
    private long timeoutAt;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static AsyncTimeout awaitTimeout() throws InterruptedException {
        synchronized (AsyncTimeout.class) {
            AsyncTimeout asyncTimeout;
            block10 : {
                block9 : {
                    asyncTimeout = AsyncTimeout.head.next;
                    if (asyncTimeout != null) break block9;
                    AsyncTimeout.class.wait();
                    return null;
                }
                long l2 = asyncTimeout.remainingNanos(System.nanoTime());
                if (l2 <= 0) break block10;
                long l3 = l2 / 1000000;
                AsyncTimeout.class.wait(l3, (int)(l2 - l3 * 1000000));
                return null;
            }
            AsyncTimeout.head.next = asyncTimeout.next;
            asyncTimeout.next = null;
            return asyncTimeout;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean cancelScheduledTimeout(AsyncTimeout asyncTimeout) {
        synchronized (AsyncTimeout.class) {
            AsyncTimeout asyncTimeout2 = head;
            while (asyncTimeout2 != null) {
                block6 : {
                    if (asyncTimeout2.next != asyncTimeout) break block6;
                    asyncTimeout2.next = asyncTimeout.next;
                    asyncTimeout.next = null;
                    return false;
                }
                asyncTimeout2 = asyncTimeout2.next;
                continue;
            }
            return true;
        }
    }

    private long remainingNanos(long l2) {
        return this.timeoutAt - l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void scheduleTimeout(AsyncTimeout asyncTimeout, long l2, boolean bl2) {
        synchronized (AsyncTimeout.class) {
            if (head == null) {
                head = new AsyncTimeout();
                new Watchdog().start();
            }
            long l3 = System.nanoTime();
            if (l2 != 0 && bl2) {
                asyncTimeout.timeoutAt = Math.min(l2, asyncTimeout.deadlineNanoTime() - l3) + l3;
            } else if (l2 != 0) {
                asyncTimeout.timeoutAt = l3 + l2;
            } else {
                if (!bl2) {
                    throw new AssertionError();
                }
                asyncTimeout.timeoutAt = asyncTimeout.deadlineNanoTime();
            }
            l2 = asyncTimeout.remainingNanos(l3);
            AsyncTimeout asyncTimeout2 = head;
            do {
                if (asyncTimeout2.next == null || l2 < asyncTimeout2.next.remainingNanos(l3)) {
                    asyncTimeout.next = asyncTimeout2.next;
                    asyncTimeout2.next = asyncTimeout;
                    if (asyncTimeout2 == head) {
                        AsyncTimeout.class.notify();
                    }
                    return;
                }
                asyncTimeout2 = asyncTimeout2.next;
            } while (true);
        }
    }

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long l2 = this.timeoutNanos();
        boolean bl2 = this.hasDeadline();
        if (l2 == 0 && !bl2) {
            return;
        }
        this.inQueue = true;
        AsyncTimeout.scheduleTimeout(this, l2, bl2);
    }

    final IOException exit(IOException iOException) throws IOException {
        if (!this.exit()) {
            return iOException;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        interruptedIOException.initCause(iOException);
        return interruptedIOException;
    }

    final void exit(boolean bl2) throws IOException {
        if (this.exit() && bl2) {
            throw new InterruptedIOException("timeout");
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return AsyncTimeout.cancelScheduledTimeout(this);
    }

    public final Sink sink(final Sink sink) {
        return new Sink(){

            @Override
            public void close() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    sink.close();
                }
                catch (IOException var1_1) {
                    try {
                        throw AsyncTimeout.this.exit(var1_1);
                    }
                    catch (Throwable var1_2) {
                        AsyncTimeout.this.exit(false);
                        throw var1_2;
                    }
                }
                AsyncTimeout.this.exit(true);
                return;
            }

            @Override
            public void flush() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    sink.flush();
                }
                catch (IOException var1_1) {
                    try {
                        throw AsyncTimeout.this.exit(var1_1);
                    }
                    catch (Throwable var1_2) {
                        AsyncTimeout.this.exit(false);
                        throw var1_2;
                    }
                }
                AsyncTimeout.this.exit(true);
                return;
            }

            @Override
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.sink(" + sink + ")";
            }

            @Override
            public void write(Buffer buffer, long l2) throws IOException {
                AsyncTimeout.this.enter();
                try {
                    sink.write(buffer, l2);
                }
                catch (IOException var1_2) {
                    try {
                        throw AsyncTimeout.this.exit(var1_2);
                    }
                    catch (Throwable var1_3) {
                        AsyncTimeout.this.exit(false);
                        throw var1_3;
                    }
                }
                AsyncTimeout.this.exit(true);
                return;
            }
        };
    }

    public final Source source(final Source source) {
        return new Source(){

            @Override
            public void close() throws IOException {
                try {
                    source.close();
                }
                catch (IOException var1_1) {
                    try {
                        throw AsyncTimeout.this.exit(var1_1);
                    }
                    catch (Throwable var1_2) {
                        AsyncTimeout.this.exit(false);
                        throw var1_2;
                    }
                }
                AsyncTimeout.this.exit(true);
                return;
            }

            @Override
            public long read(Buffer buffer, long l2) throws IOException {
                AsyncTimeout.this.enter();
                try {
                    l2 = source.read(buffer, l2);
                }
                catch (IOException var1_2) {
                    try {
                        throw AsyncTimeout.this.exit(var1_2);
                    }
                    catch (Throwable var1_3) {
                        AsyncTimeout.this.exit(false);
                        throw var1_3;
                    }
                }
                AsyncTimeout.this.exit(true);
                return l2;
            }

            @Override
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.source(" + source + ")";
            }
        };
    }

    protected void timedOut() {
    }

    private static final class Watchdog
    extends Thread {
        public Watchdog() {
            super("Okio Watchdog");
            this.setDaemon(true);
        }

        @Override
        public void run() {
            do {
                AsyncTimeout asyncTimeout = AsyncTimeout.awaitTimeout();
                if (asyncTimeout == null) continue;
                try {
                    asyncTimeout.timedOut();
                }
                catch (InterruptedException var1_2) {
                }
            } while (true);
        }
    }

}

