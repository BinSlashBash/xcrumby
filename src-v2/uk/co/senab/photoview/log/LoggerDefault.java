/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package uk.co.senab.photoview.log;

import android.util.Log;
import uk.co.senab.photoview.log.Logger;

public class LoggerDefault
implements Logger {
    @Override
    public int d(String string2, String string3) {
        return Log.d((String)string2, (String)string3);
    }

    @Override
    public int d(String string2, String string3, Throwable throwable) {
        return Log.d((String)string2, (String)string3, (Throwable)throwable);
    }

    @Override
    public int e(String string2, String string3) {
        return Log.e((String)string2, (String)string3);
    }

    @Override
    public int e(String string2, String string3, Throwable throwable) {
        return Log.e((String)string2, (String)string3, (Throwable)throwable);
    }

    @Override
    public int i(String string2, String string3) {
        return Log.i((String)string2, (String)string3);
    }

    @Override
    public int i(String string2, String string3, Throwable throwable) {
        return Log.i((String)string2, (String)string3, (Throwable)throwable);
    }

    @Override
    public int v(String string2, String string3) {
        return Log.v((String)string2, (String)string3);
    }

    @Override
    public int v(String string2, String string3, Throwable throwable) {
        return Log.v((String)string2, (String)string3, (Throwable)throwable);
    }

    @Override
    public int w(String string2, String string3) {
        return Log.w((String)string2, (String)string3);
    }

    @Override
    public int w(String string2, String string3, Throwable throwable) {
        return Log.w((String)string2, (String)string3, (Throwable)throwable);
    }
}

