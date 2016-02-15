package uk.co.senab.photoview.log;

import android.util.Log;

public class LoggerDefault implements Logger {
    public int m2625v(String tag, String msg) {
        return Log.v(tag, msg);
    }

    public int m2626v(String tag, String msg, Throwable tr) {
        return Log.v(tag, msg, tr);
    }

    public int m2619d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    public int m2620d(String tag, String msg, Throwable tr) {
        return Log.d(tag, msg, tr);
    }

    public int m2623i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    public int m2624i(String tag, String msg, Throwable tr) {
        return Log.i(tag, msg, tr);
    }

    public int m2627w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    public int m2628w(String tag, String msg, Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    public int m2621e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    public int m2622e(String tag, String msg, Throwable tr) {
        return Log.e(tag, msg, tr);
    }
}
