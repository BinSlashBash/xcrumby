/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crumby;

import android.content.Context;
import com.crumby.AnalyticsCategories;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.crumby.HomeFragment;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.util.Map;

public enum Analytics {
    INSTANCE;
    
    private static final int NO_VALUE = -99999999;
    private final String PRODUCTION_TRACKER = "UA-27893558-3";
    private final String TESTING_TRACKER = "UA-27893558-2";
    long lastNavTime;
    String lastScreenName;
    String screenName;
    private Tracker tracker;

    private Analytics() {
    }

    public void createTracker(Context context) {
        this.tracker = GoogleAnalytics.getInstance(context).newTracker("UA-27893558-2");
        this.tracker.send(((HitBuilders.AppViewBuilder)new HitBuilders.AppViewBuilder().setNewSession()).build());
        this.lastNavTime = System.currentTimeMillis();
        new ExceptionReporter(this.tracker, Thread.getDefaultUncaughtExceptionHandler(), context);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
            }
        });
    }

    public void end() {
    }

    public Tracker get() {
        return this.tracker;
    }

    public void newError(DisplayError displayError, String string2) {
        this.tracker.send(new HitBuilders.ExceptionBuilder().setDescription("" + displayError.getErrorCode() + " " + string2).setFatal(false).build());
    }

    public void newEvent(AnalyticsCategories analyticsCategories, String string2, String string3) {
        this.newEvent(analyticsCategories, string2, string3, -99999999);
    }

    public void newEvent(AnalyticsCategories object, String string2, String string3, long l2) {
        object = new HitBuilders.EventBuilder().setCategory(object.getName()).setLabel(string3).setAction(string2);
        if (l2 != -99999999) {
            object.setValue(l2);
        }
        this.tracker.send(object.build());
    }

    public void newException(Exception exception) {
        this.tracker.send(new HitBuilders.ExceptionBuilder().setDescription(exception.getMessage() + " " + exception.getStackTrace()).setFatal(false).build());
    }

    public void newImageDownloadEvent(ImageDownload imageDownload, String string2) {
        this.newEvent(AnalyticsCategories.DOWNLOADS, string2, imageDownload.getDownloadUri());
    }

    public void newImageDownloadEvent(ImageDownload imageDownload, String string2, long l2) {
        this.newTimingEvent(AnalyticsCategories.DOWNLOADS, l2, string2, imageDownload.getDownloadUri());
    }

    public void newNavigationEvent(String string2, String string3) {
        synchronized (this) {
            long l2 = System.currentTimeMillis();
            this.newEvent(AnalyticsCategories.NAVIGATION, string2, string3, -99999999);
            this.lastNavTime = l2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void newPage(GalleryViewerFragment galleryViewerFragment) {
        String string2 = galleryViewerFragment.getUrl();
        if (galleryViewerFragment instanceof CrumbyGalleryFragment) {
            string2 = "s: " + galleryViewerFragment.getUrl();
        } else if (galleryViewerFragment instanceof HomeFragment) {
            string2 = "home";
        }
        this.newScreen(string2);
    }

    public void newScreen(String string2) {
        this.lastScreenName = this.screenName;
        this.screenName = string2;
        this.tracker.setScreenName(string2);
        this.tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void newTimingEvent(AnalyticsCategories analyticsCategories, long l2, String string2, String string3) {
        long l3 = System.currentTimeMillis();
        this.tracker.send(new HitBuilders.TimingBuilder().setCategory(analyticsCategories.getName()).setValue(l3 - l2).setVariable(string2).setLabel(string3).build());
    }

}

