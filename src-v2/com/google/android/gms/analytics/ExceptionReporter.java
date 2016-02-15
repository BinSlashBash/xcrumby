/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.ExceptionParser;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.aa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ExceptionReporter
implements Thread.UncaughtExceptionHandler {
    private final Context mContext;
    private final Thread.UncaughtExceptionHandler sA;
    private final Tracker sB;
    private ExceptionParser sC;

    /*
     * Enabled aggressive block sorting
     */
    public ExceptionReporter(Tracker object, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Context object2) {
        if (object == null) {
            throw new NullPointerException("tracker cannot be null");
        }
        if (object2 == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.sA = uncaughtExceptionHandler;
        this.sB = object;
        this.sC = new StandardExceptionParser((Context)object2, new ArrayList<String>());
        this.mContext = object2.getApplicationContext();
        object2 = new StringBuilder().append("ExceptionReporter created, original handler is ");
        object = uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName();
        aa.y(object2.append((String)object).toString());
    }

    public ExceptionParser getExceptionParser() {
        return this.sC;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.sC = exceptionParser;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String string2 = "UncaughtException";
        if (this.sC != null) {
            string2 = thread != null ? thread.getName() : null;
            string2 = this.sC.getDescription(string2, throwable);
        }
        aa.y("Tracking Exception: " + string2);
        this.sB.send(new HitBuilders.ExceptionBuilder().setDescription(string2).setFatal(true).build());
        GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
        if (this.sA != null) {
            aa.y("Passing exception to original handler.");
            this.sA.uncaughtException(thread, throwable);
        }
    }
}

