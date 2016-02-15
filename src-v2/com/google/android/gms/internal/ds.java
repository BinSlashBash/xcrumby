/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Paint
 *  android.view.View
 *  android.view.Window
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.io.File;

public final class ds {
    public static void a(Context context, WebSettings webSettings) {
        webSettings.setAppCachePath(context.getCacheDir().getAbsolutePath());
        webSettings.setAppCacheMaxSize(0);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabasePath(context.getDatabasePath("com.google.android.gms.ads.db").getAbsolutePath());
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
    }

    public static void a(Window window) {
        window.setFlags(16777216, 16777216);
    }

    public static void a(WebView webView) {
        webView.onPause();
    }

    public static void b(WebView webView) {
        webView.onResume();
    }

    public static void d(View view) {
        view.setLayerType(1, null);
    }

    public static void e(View view) {
        view.setLayerType(0, null);
    }
}

