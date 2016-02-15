package org.junit.runner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Result implements Serializable {
    private static final long serialVersionUID = 1;
    private AtomicInteger fCount;
    private final List<Failure> fFailures;
    private AtomicInteger fIgnoreCount;
    private long fRunTime;
    private long fStartTime;

    private class Listener extends RunListener {
        private Listener() {
        }

        public void testRunStarted(Description description) throws Exception {
            Result.this.fStartTime = System.currentTimeMillis();
        }

        public void testRunFinished(Result result) throws Exception {
            Result.access$114(Result.this, System.currentTimeMillis() - Result.this.fStartTime);
        }

        public void testFinished(Description description) throws Exception {
            Result.this.fCount.getAndIncrement();
        }

        public void testFailure(Failure failure) throws Exception {
            Result.this.fFailures.add(failure);
        }

        public void testIgnored(Description description) throws Exception {
            Result.this.fIgnoreCount.getAndIncrement();
        }

        public void testAssumptionFailure(Failure failure) {
        }
    }

    public Result() {
        this.fCount = new AtomicInteger();
        this.fIgnoreCount = new AtomicInteger();
        this.fFailures = Collections.synchronizedList(new ArrayList());
        this.fRunTime = 0;
    }

    static /* synthetic */ long access$114(Result x0, long x1) {
        long j = x0.fRunTime + x1;
        x0.fRunTime = j;
        return j;
    }

    public int getRunCount() {
        return this.fCount.get();
    }

    public int getFailureCount() {
        return this.fFailures.size();
    }

    public long getRunTime() {
        return this.fRunTime;
    }

    public List<Failure> getFailures() {
        return this.fFailures;
    }

    public int getIgnoreCount() {
        return this.fIgnoreCount.get();
    }

    public boolean wasSuccessful() {
        return getFailureCount() == 0;
    }

    public RunListener createListener() {
        return new Listener();
    }
}
