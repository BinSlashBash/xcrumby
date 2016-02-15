/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
    private static final int MAX_CONNECTIONS_TO_CLEANUP = 2;
    private static final ConnectionPool systemDefault;
    private final LinkedList<Connection> connections = new LinkedList();
    private final Runnable connectionsCleanupRunnable;
    private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory("OkHttp ConnectionPool", true));
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    /*
     * Enabled aggressive block sorting
     */
    static {
        String string2 = System.getProperty("http.keepAlive");
        String string3 = System.getProperty("http.keepAliveDuration");
        String string4 = System.getProperty("http.maxConnections");
        long l2 = string3 != null ? Long.parseLong(string3) : 300000;
        if (string2 != null && !Boolean.parseBoolean(string2)) {
            systemDefault = new ConnectionPool(0, l2);
            return;
        }
        if (string4 != null) {
            systemDefault = new ConnectionPool(Integer.parseInt(string4), l2);
            return;
        }
        systemDefault = new ConnectionPool(5, l2);
    }

    public ConnectionPool(int n2, long l2) {
        this.connectionsCleanupRunnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                ArrayList<Connection> arrayList = new ArrayList<Connection>(2);
                int n2 = 0;
                Object object = ConnectionPool.this;
                synchronized (object) {
                    ListIterator listIterator = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                    do {
                        Connection connection;
                        if (listIterator.hasPrevious()) {
                            connection = (Connection)listIterator.previous();
                            if (!connection.isAlive() || connection.isExpired(ConnectionPool.this.keepAliveDurationNs)) {
                                listIterator.remove();
                                arrayList.add(connection);
                                if (arrayList.size() != 2) continue;
                            }
                        } else {
                            listIterator = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                            while (listIterator.hasPrevious() && n2 > ConnectionPool.this.maxIdleConnections) {
                                connection = (Connection)listIterator.previous();
                                if (!connection.isIdle()) continue;
                                arrayList.add(connection);
                                listIterator.remove();
                                --n2;
                            }
                            break;
                        }
                        if (!connection.isIdle()) continue;
                        ++n2;
                    } while (true);
                }
                object = arrayList.iterator();
                while (object.hasNext()) {
                    Util.closeQuietly(((Connection)object.next()).getSocket());
                }
                return;
            }
        };
        this.maxIdleConnections = n2;
        this.keepAliveDurationNs = l2 * 1000 * 1000;
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    private void waitForCleanupCallableToRun() {
        try {
            this.executorService.submit(new Runnable(){

                @Override
                public void run() {
                }
            }).get();
            return;
        }
        catch (Exception var1_1) {
            throw new AssertionError();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void evictAll() {
        ArrayList<Connection> arrayList;
        synchronized (this) {
            arrayList = new ArrayList<Connection>(this.connections);
            this.connections.clear();
        }
        int n2 = 0;
        int n3 = arrayList.size();
        while (n2 < n3) {
            Util.closeQuietly(arrayList.get(n2).getSocket());
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Connection get(Address address) {
        synchronized (this) {
            Connection connection2;
            Connection connection = null;
            ListIterator<Connection> listIterator = this.connections.listIterator(this.connections.size());
            do {
                connection2 = connection;
                if (!listIterator.hasPrevious()) break;
                connection2 = listIterator.previous();
                if (!connection2.getRoute().getAddress().equals(address) || !connection2.isAlive() || System.nanoTime() - connection2.getIdleStartTimeNs() >= this.keepAliveDurationNs) continue;
                listIterator.remove();
                boolean bl2 = connection2.isSpdy();
                if (bl2) break;
                try {
                    Platform.get().tagSocket(connection2.getSocket());
                    break;
                }
                catch (SocketException socketException) {
                    Util.closeQuietly(connection2.getSocket());
                    Platform.get().logW("Unable to tagSocket(): " + socketException);
                    continue;
                }
                break;
            } while (true);
            if (connection2 != null && connection2.isSpdy()) {
                this.connections.addFirst(connection2);
            }
            this.executorService.execute(this.connectionsCleanupRunnable);
            return connection2;
        }
    }

    public int getConnectionCount() {
        synchronized (this) {
            int n2 = this.connections.size();
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    List<Connection> getConnections() {
        this.waitForCleanupCallableToRun();
        synchronized (this) {
            return new ArrayList<Connection>(this.connections);
        }
    }

    public int getHttpConnectionCount() {
        synchronized (this) {
            int n2 = 0;
            Iterator<Connection> iterator = this.connections.iterator();
            while (iterator.hasNext()) {
                boolean bl2 = iterator.next().isSpdy();
                if (bl2) continue;
            }
            {
                ++n2;
                continue;
            }
            return n2;
        }
    }

    public int getSpdyConnectionCount() {
        synchronized (this) {
            int n2 = 0;
            Iterator<Connection> iterator = this.connections.iterator();
            while (iterator.hasNext()) {
                boolean bl2 = iterator.next().isSpdy();
                if (!bl2) continue;
            }
            {
                ++n2;
                continue;
            }
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void recycle(Connection connection) {
        if (connection.isSpdy() || !connection.clearOwner()) {
            return;
        }
        if (!connection.isAlive()) {
            Util.closeQuietly(connection.getSocket());
            return;
        }
        try {
            Platform.get().untagSocket(connection.getSocket());
        }
        catch (SocketException var2_2) {
            Platform.get().logW("Unable to untagSocket(): " + var2_2);
            Util.closeQuietly(connection.getSocket());
            return;
        }
        synchronized (this) {
            this.connections.addFirst(connection);
            connection.incrementRecycleCount();
            connection.resetIdleStartTime();
        }
        this.executorService.execute(this.connectionsCleanupRunnable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void share(Connection connection) {
        if (!connection.isSpdy()) {
            throw new IllegalArgumentException();
        }
        this.executorService.execute(this.connectionsCleanupRunnable);
        if (connection.isAlive()) {
            synchronized (this) {
                this.connections.addFirst(connection);
                return;
            }
        }
    }

}

