/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import com.tapstream.sdk.Logger;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class Logging {
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int WARN = 5;
    private static Method formatMethod;
    private static Logger logger;

    static {
        logger = new DefaultLogger();
        try {
            formatMethod = String.class.getDeclaredMethod("format", String.class, Object[].class);
        }
        catch (Exception var0) {
            var0.printStackTrace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static /* varargs */ void log(int n2, String string2, Object ... arrobject) {
        synchronized (Logging.class) {
            if (logger != null) {
                try {
                    void var2_3;
                    string2 = (String)formatMethod.invoke(null, string2, var2_3);
                }
                catch (Exception var1_2) {
                    var1_2.printStackTrace();
                    string2 = "";
                }
                logger.log(n2, string2);
            }
            return;
        }
    }

    public static void setLogger(Logger logger) {
        synchronized (Logging.class) {
            Logging.logger = logger;
            return;
        }
    }

    private static class DefaultLogger
    implements Logger {
        private DefaultLogger() {
        }

        @Override
        public void log(int n2, String string2) {
            System.out.println(string2);
        }
    }

}

