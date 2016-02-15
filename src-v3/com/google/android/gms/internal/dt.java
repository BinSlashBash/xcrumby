package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebSettings;

public final class dt {
    public static void m807a(Context context, WebSettings webSettings) {
        ds.m801a(context, webSettings);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
    }

    public static String getDefaultUserAgent(Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }
}
