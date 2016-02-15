/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.analytics;

public interface Logger {
    public void error(Exception var1);

    public void error(String var1);

    public int getLogLevel();

    public void info(String var1);

    public void setLogLevel(int var1);

    public void verbose(String var1);

    public void warn(String var1);

    public static class LogLevel {
        public static final int ERROR = 3;
        public static final int INFO = 1;
        public static final int VERBOSE = 0;
        public static final int WARNING = 2;
    }

}

