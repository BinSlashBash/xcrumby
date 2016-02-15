/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Dispatcher {
    private ExecutorService executorService;
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private final Deque<Call.AsyncCall> readyCalls = new ArrayDeque<Call.AsyncCall>();
    private final Deque<Call.AsyncCall> runningCalls = new ArrayDeque<Call.AsyncCall>();

    public Dispatcher() {
    }

    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void promoteCalls() {
        if (this.runningCalls.size() >= this.maxRequests) {
            return;
        }
        if (this.readyCalls.isEmpty()) return;
        Iterator<Call.AsyncCall> iterator = this.readyCalls.iterator();
        do {
            if (!iterator.hasNext()) return;
            Call.AsyncCall asyncCall = iterator.next();
            if (this.runningCallsForHost(asyncCall) >= this.maxRequestsPerHost) continue;
            iterator.remove();
            this.runningCalls.add(asyncCall);
            this.getExecutorService().execute(asyncCall);
        } while (this.runningCalls.size() < this.maxRequests);
    }

    private int runningCallsForHost(Call.AsyncCall asyncCall) {
        int n2 = 0;
        Iterator<Call.AsyncCall> iterator = this.runningCalls.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().host().equals(asyncCall.host())) continue;
            ++n2;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel(Object object) {
        synchronized (this) {
            Iterator<Call.AsyncCall> iterator = this.readyCalls.iterator();
            while (iterator.hasNext()) {
                if (!Util.equal(object, iterator.next().tag())) continue;
                iterator.remove();
            }
            iterator = this.runningCalls.iterator();
            while (iterator.hasNext()) {
                Object object2 = iterator.next();
                if (!Util.equal(object, object2.tag())) continue;
                object2.get().canceled = true;
                object2 = object2.get().engine;
                if (object2 == null) continue;
                object2.disconnect();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void enqueue(Call.AsyncCall asyncCall) {
        synchronized (this) {
            if (this.runningCalls.size() < this.maxRequests && this.runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
                this.runningCalls.add(asyncCall);
                this.getExecutorService().execute(asyncCall);
            } else {
                this.readyCalls.add(asyncCall);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void finished(Call.AsyncCall asyncCall) {
        synchronized (this) {
            if (!this.runningCalls.remove(asyncCall)) {
                throw new AssertionError((Object)"AsyncCall wasn't running!");
            }
            this.promoteCalls();
            return;
        }
    }

    public ExecutorService getExecutorService() {
        synchronized (this) {
            if (this.executorService == null) {
                this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
            }
            ExecutorService executorService = this.executorService;
            return executorService;
        }
    }

    public int getMaxRequests() {
        synchronized (this) {
            int n2 = this.maxRequests;
            return n2;
        }
    }

    public int getMaxRequestsPerHost() {
        synchronized (this) {
            int n2 = this.maxRequestsPerHost;
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMaxRequests(int n2) {
        synchronized (this) {
            if (n2 < 1) {
                throw new IllegalArgumentException("max < 1: " + n2);
            }
            this.maxRequests = n2;
            this.promoteCalls();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMaxRequestsPerHost(int n2) {
        synchronized (this) {
            if (n2 < 1) {
                throw new IllegalArgumentException("max < 1: " + n2);
            }
            this.maxRequestsPerHost = n2;
            this.promoteCalls();
            return;
        }
    }
}

