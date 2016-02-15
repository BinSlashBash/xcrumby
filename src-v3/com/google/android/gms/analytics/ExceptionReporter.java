package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

public class ExceptionReporter implements UncaughtExceptionHandler {
    private final Context mContext;
    private final UncaughtExceptionHandler sA;
    private final Tracker sB;
    private ExceptionParser sC;

    public ExceptionReporter(Tracker tracker, UncaughtExceptionHandler originalHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context == null) {
            throw new NullPointerException("context cannot be null");
        } else {
            this.sA = originalHandler;
            this.sB = tracker;
            this.sC = new StandardExceptionParser(context, new ArrayList());
            this.mContext = context.getApplicationContext();
            aa.m34y("ExceptionReporter created, original handler is " + (originalHandler == null ? "null" : originalHandler.getClass().getName()));
        }
    }

    public ExceptionParser getExceptionParser() {
        return this.sC;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.sC = exceptionParser;
    }

    public void uncaughtException(Thread t, Throwable e) {
        String str = "UncaughtException";
        if (this.sC != null) {
            str = this.sC.getDescription(t != null ? t.getName() : null, e);
        }
        aa.m34y("Tracking Exception: " + str);
        this.sB.send(new ExceptionBuilder().setDescription(str).setFatal(true).build());
        GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
        if (this.sA != null) {
            aa.m34y("Passing exception to original handler.");
            this.sA.uncaughtException(t, e);
        }
    }
}
