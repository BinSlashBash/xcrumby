/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Result
implements Serializable {
    private static final long serialVersionUID = 1;
    private AtomicInteger fCount = new AtomicInteger();
    private final List<Failure> fFailures = Collections.synchronizedList(new ArrayList());
    private AtomicInteger fIgnoreCount = new AtomicInteger();
    private long fRunTime = 0;
    private long fStartTime;

    static /* synthetic */ long access$114(Result result, long l2) {
        result.fRunTime = l2 = result.fRunTime + l2;
        return l2;
    }

    public RunListener createListener() {
        return new Listener();
    }

    public int getFailureCount() {
        return this.fFailures.size();
    }

    public List<Failure> getFailures() {
        return this.fFailures;
    }

    public int getIgnoreCount() {
        return this.fIgnoreCount.get();
    }

    public int getRunCount() {
        return this.fCount.get();
    }

    public long getRunTime() {
        return this.fRunTime;
    }

    public boolean wasSuccessful() {
        if (this.getFailureCount() == 0) {
            return true;
        }
        return false;
    }

    private class Listener
    extends RunListener {
        private Listener() {
        }

        @Override
        public void testAssumptionFailure(Failure failure) {
        }

        @Override
        public void testFailure(Failure failure) throws Exception {
            Result.this.fFailures.add(failure);
        }

        @Override
        public void testFinished(Description description) throws Exception {
            Result.this.fCount.getAndIncrement();
        }

        @Override
        public void testIgnored(Description description) throws Exception {
            Result.this.fIgnoreCount.getAndIncrement();
        }

        @Override
        public void testRunFinished(Result result) throws Exception {
            long l2 = System.currentTimeMillis();
            Result.access$114(Result.this, l2 - Result.this.fStartTime);
        }

        @Override
        public void testRunStarted(Description description) throws Exception {
            Result.this.fStartTime = System.currentTimeMillis();
        }
    }

}

