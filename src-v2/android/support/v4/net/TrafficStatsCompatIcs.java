/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.TrafficStats
 */
package android.support.v4.net;

import android.net.TrafficStats;
import java.net.Socket;
import java.net.SocketException;

class TrafficStatsCompatIcs {
    TrafficStatsCompatIcs() {
    }

    public static void clearThreadStatsTag() {
        TrafficStats.clearThreadStatsTag();
    }

    public static int getThreadStatsTag() {
        return TrafficStats.getThreadStatsTag();
    }

    public static void incrementOperationCount(int n2) {
        TrafficStats.incrementOperationCount((int)n2);
    }

    public static void incrementOperationCount(int n2, int n3) {
        TrafficStats.incrementOperationCount((int)n2, (int)n3);
    }

    public static void setThreadStatsTag(int n2) {
        TrafficStats.setThreadStatsTag((int)n2);
    }

    public static void tagSocket(Socket socket) throws SocketException {
        TrafficStats.tagSocket((Socket)socket);
    }

    public static void untagSocket(Socket socket) throws SocketException {
        TrafficStats.untagSocket((Socket)socket);
    }
}

