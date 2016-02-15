/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.webkit.WebResourceResponse
 *  android.webkit.WebView
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ec
extends ea {
    public ec(dz dz2, boolean bl2) {
        super(dz2, bl2);
    }

    protected WebResourceResponse d(Context context, String string2, String object) throws IOException {
        object = (HttpURLConnection)new URL((String)object).openConnection();
        try {
            dq.a(context, string2, true, (HttpURLConnection)object);
            object.connect();
            context = new WebResourceResponse("application/javascript", "UTF-8", (InputStream)new ByteArrayInputStream(dq.a(new InputStreamReader(object.getInputStream())).getBytes("UTF-8")));
            return context;
        }
        finally {
            object.disconnect();
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String string2) {
        try {
            if (!"mraid.js".equalsIgnoreCase(new File(string2).getName())) {
                return super.shouldInterceptRequest(webView, string2);
            }
            if (!(webView instanceof dz)) {
                dw.z("Tried to intercept request from a WebView that wasn't an AdWebView.");
                return super.shouldInterceptRequest(webView, string2);
            }
            dz dz2 = (dz)webView;
            dz2.bI().aM();
            if (dz2.R().lT) {
                dw.y("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_interstitial.js)");
                return this.d(dz2.getContext(), this.lC.bK().rq, "http://media.admob.com/mraid/v1/mraid_app_interstitial.js");
            }
            if (dz2.bL()) {
                dw.y("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js)");
                return this.d(dz2.getContext(), this.lC.bK().rq, "http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js");
            }
            dw.y("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_banner.js)");
            dz2 = this.d(dz2.getContext(), this.lC.bK().rq, "http://media.admob.com/mraid/v1/mraid_app_banner.js");
            return dz2;
        }
        catch (IOException var3_4) {
            dw.z("Could not fetching MRAID JS. " + var3_4.getMessage());
            return super.shouldInterceptRequest(webView, string2);
        }
    }
}

