package com.tapstream.sdk;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.lang.reflect.Method;

public class Logging {
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int WARN = 5;
    private static Method formatMethod;
    private static Logger logger;

    private static class DefaultLogger implements Logger {
        private DefaultLogger() {
        }

        public void log(int i, String str) {
            System.out.println(str);
        }
    }

    static {
        logger = new DefaultLogger();
        try {
            formatMethod = String.class.getDeclaredMethod("format", new Class[]{String.class, Object[].class});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void log(int i, String str, Object... objArr) {
        synchronized (Logging.class) {
            if (logger != null) {
                String str2;
                String str3 = UnsupportedUrlFragment.DISPLAY_NAME;
                try {
                    str2 = (String) formatMethod.invoke(null, new Object[]{str, objArr});
                } catch (Exception e) {
                    e.printStackTrace();
                    str2 = str3;
                }
                logger.log(i, str2);
            }
        }
    }

    public static synchronized void setLogger(Logger logger) {
        synchronized (Logging.class) {
            logger = logger;
        }
    }
}
