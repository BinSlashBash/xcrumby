package com.crumby;

import android.content.Context;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.crumby.HomeFragment;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders.AppViewBuilder;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.HitBuilders.TimingBuilder;
import com.google.android.gms.analytics.Tracker;
import java.lang.Thread.UncaughtExceptionHandler;

public enum Analytics {
    INSTANCE;
    
    private static final int NO_VALUE = -99999999;
    private final String PRODUCTION_TRACKER;
    private final String TESTING_TRACKER;
    long lastNavTime;
    String lastScreenName;
    String screenName;
    private Tracker tracker;

    /* renamed from: com.crumby.Analytics.1 */
    class C00521 implements UncaughtExceptionHandler {
        C00521() {
        }

        public void uncaughtException(Thread thread, Throwable ex) {
        }
    }

    public Tracker get() {
        return this.tracker;
    }

    public void newError(DisplayError error, String reason) {
        this.tracker.send(new ExceptionBuilder().setDescription(error.getErrorCode() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + reason).setFatal(false).build());
    }

    public void createTracker(Context context) {
        this.tracker = GoogleAnalytics.getInstance(context).newTracker("UA-27893558-2");
        this.tracker.send(((AppViewBuilder) new AppViewBuilder().setNewSession()).build());
        this.lastNavTime = System.currentTimeMillis();
        UncaughtExceptionHandler myHandler = new ExceptionReporter(this.tracker, Thread.getDefaultUncaughtExceptionHandler(), context);
        Thread.setDefaultUncaughtExceptionHandler(new C00521());
    }

    public void newTimingEvent(AnalyticsCategories category, long start, String variable, String label) {
        this.tracker.send(new TimingBuilder().setCategory(category.getName()).setValue(System.currentTimeMillis() - start).setVariable(variable).setLabel(label).build());
    }

    public void newEvent(AnalyticsCategories category, String action, String label) {
        newEvent(category, action, label, -99999999);
    }

    public synchronized void newNavigationEvent(String action, String label) {
        long newTime = System.currentTimeMillis();
        newEvent(AnalyticsCategories.NAVIGATION, action, label, -99999999);
        this.lastNavTime = newTime;
    }

    public void newEvent(AnalyticsCategories category, String action, String label, long value) {
        EventBuilder builder = new EventBuilder().setCategory(category.getName()).setLabel(label).setAction(action);
        if (value != -99999999) {
            builder.setValue(value);
        }
        this.tracker.send(builder.build());
    }

    public void newScreen(String name) {
        this.lastScreenName = this.screenName;
        this.screenName = name;
        this.tracker.setScreenName(name);
        this.tracker.send(new ScreenViewBuilder().build());
    }

    public void newPage(GalleryViewerFragment fragment) {
        String screenName = fragment.getUrl();
        if (fragment instanceof CrumbyGalleryFragment) {
            screenName = "s: " + fragment.getUrl();
        } else if (fragment instanceof HomeFragment) {
            screenName = HomeFragment.DISPLAY_NAME;
        }
        newScreen(screenName);
    }

    public void end() {
    }

    public void newImageDownloadEvent(ImageDownload imageDownload, String action) {
        newEvent(AnalyticsCategories.DOWNLOADS, action, imageDownload.getDownloadUri());
    }

    public void newImageDownloadEvent(ImageDownload imageDownload, String action, long startTime) {
        newTimingEvent(AnalyticsCategories.DOWNLOADS, startTime, action, imageDownload.getDownloadUri());
    }

    public void newException(Exception e) {
        this.tracker.send(new ExceptionBuilder().setDescription(e.getMessage() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e.getStackTrace()).setFatal(false).build());
    }
}
