/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package com.crumby;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.crumby.Analytics;
import com.crumby.lib.fragment.GalleryListFragment;
import com.squareup.okhttp.OkHttpClient;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.concurrent.TimeUnit;

public class CrumbyApp
extends Application {
    private static final OkHttpClient client = new OkHttpClient();

    public static float convertDpToPx(Resources resources, float f2) {
        return TypedValue.applyDimension((int)1, (float)f2, (DisplayMetrics)resources.getDisplayMetrics());
    }

    public static OkHttpClient getHttpClient() {
        return client;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onCreate() {
        UserVoice.init(new Config("crumby.uservoice.com"), (Context)this);
        Analytics.INSTANCE.createTracker(this.getApplicationContext());
        client.setFollowSslRedirects(true);
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        GalleryListFragment.THUMBNAIL_HEIGHT = (int)this.getResources().getDimension(2131165191);
        GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH = (int)this.getResources().getDimension(2131165190);
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onTerminate() {
        super.onTerminate();
    }
}

