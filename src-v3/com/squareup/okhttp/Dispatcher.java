package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Dispatcher {
    private ExecutorService executorService;
    private int maxRequests;
    private int maxRequestsPerHost;
    private final Deque<AsyncCall> readyCalls;
    private final Deque<AsyncCall> runningCalls;

    public Dispatcher(ExecutorService executorService) {
        this.maxRequests = 64;
        this.maxRequestsPerHost = 5;
        this.readyCalls = new ArrayDeque();
        this.runningCalls = new ArrayDeque();
        this.executorService = executorService;
    }

    public Dispatcher() {
        this.maxRequests = 64;
        this.maxRequestsPerHost = 5;
        this.readyCalls = new ArrayDeque();
        this.runningCalls = new ArrayDeque();
    }

    public synchronized ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp Dispatcher", false));
        }
        return this.executorService;
    }

    public synchronized void setMaxRequests(int maxRequests) {
        if (maxRequests < 1) {
            throw new IllegalArgumentException("max < 1: " + maxRequests);
        }
        this.maxRequests = maxRequests;
        promoteCalls();
    }

    public synchronized int getMaxRequests() {
        return this.maxRequests;
    }

    public synchronized void setMaxRequestsPerHost(int maxRequestsPerHost) {
        if (maxRequestsPerHost < 1) {
            throw new IllegalArgumentException("max < 1: " + maxRequestsPerHost);
        }
        this.maxRequestsPerHost = maxRequestsPerHost;
        promoteCalls();
    }

    public synchronized int getMaxRequestsPerHost() {
        return this.maxRequestsPerHost;
    }

    synchronized void enqueue(AsyncCall call) {
        if (this.runningCalls.size() >= this.maxRequests || runningCallsForHost(call) >= this.maxRequestsPerHost) {
            this.readyCalls.add(call);
        } else {
            this.runningCalls.add(call);
            getExecutorService().execute(call);
        }
    }

    public synchronized void cancel(Object tag) {
        Iterator<AsyncCall> i = this.readyCalls.iterator();
        while (i.hasNext()) {
            if (Util.equal(tag, ((AsyncCall) i.next()).tag())) {
                i.remove();
            }
        }
        for (AsyncCall call : this.runningCalls) {
            if (Util.equal(tag, call.tag())) {
                call.get().canceled = true;
                HttpEngine engine = call.get().engine;
                if (engine != null) {
                    engine.disconnect();
                }
            }
        }
    }

    synchronized void finished(AsyncCall call) {
        if (this.runningCalls.remove(call)) {
            promoteCalls();
        } else {
            throw new AssertionError("AsyncCall wasn't running!");
        }
    }

    private void promoteCalls() {
        if (this.runningCalls.size() < this.maxRequests && !this.readyCalls.isEmpty()) {
            Iterator<AsyncCall> i = this.readyCalls.iterator();
            while (i.hasNext()) {
                AsyncCall call = (AsyncCall) i.next();
                if (runningCallsForHost(call) < this.maxRequestsPerHost) {
                    i.remove();
                    this.runningCalls.add(call);
                    getExecutorService().execute(call);
                }
                if (this.runningCalls.size() >= this.maxRequests) {
                    return;
                }
            }
        }
    }

    private int runningCallsForHost(AsyncCall call) {
        int result = 0;
        for (AsyncCall c : this.runningCalls) {
            if (c.host().equals(call.host())) {
                result++;
            }
        }
        return result;
    }
}
