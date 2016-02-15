package com.crumby;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;
import com.crumby.lib.fragment.GalleryListFragment;
import com.squareup.okhttp.OkHttpClient;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.concurrent.TimeUnit;

public class CrumbyApp extends Application {
    private static final OkHttpClient client;

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static float convertDpToPx(Resources r, float pixels) {
        return TypedValue.applyDimension(1, pixels, r.getDisplayMetrics());
    }

    static {
        client = new OkHttpClient();
    }

    public static OkHttpClient getHttpClient() {
        return client;
    }

    public void onCreate() {
        UserVoice.init(new Config("crumby.uservoice.com"), this);
        Analytics.INSTANCE.createTracker(getApplicationContext());
        client.setFollowSslRedirects(true);
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        GalleryListFragment.THUMBNAIL_HEIGHT = (int) getResources().getDimension(C0065R.dimen.thumbnail_height);
        GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH = (int) getResources().getDimension(C0065R.dimen.minimum_thumbnail_width);
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onTerminate() {
        super.onTerminate();
    }
}
