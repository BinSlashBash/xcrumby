/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.webkit.WebSettings
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebSettings;
import com.google.android.gms.internal.ds;

public final class dt {
    public static void a(Context context, WebSettings webSettings) {
        ds.a(context, webSettings);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
    }

    public static String getDefaultUserAgent(Context context) {
        return WebSettings.getDefaultUserAgent((Context)context);
    }
}

