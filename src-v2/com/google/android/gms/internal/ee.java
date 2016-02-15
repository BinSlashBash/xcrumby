/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.internal.ct;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import com.google.android.gms.internal.fo;
import java.net.URI;
import java.net.URISyntaxException;

public class ee
extends WebViewClient {
    private final dz lC;
    private final String rM;
    private boolean rN;
    private final ct rO;

    public ee(ct ct2, dz dz2, String string2) {
        this.rM = this.B(string2);
        this.rN = false;
        this.lC = dz2;
        this.rO = ct2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String B(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return string2;
        }
        try {
            if (!string2.endsWith("/")) return string2;
            return string2.substring(0, string2.length() - 1);
        }
        catch (IndexOutOfBoundsException var2_3) {
            dw.w(var2_3.getMessage());
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected boolean A(String string2) {
        if (TextUtils.isEmpty((CharSequence)(string2 = this.B(string2)))) {
            return false;
        }
        try {
            Object object = new URI(string2);
            if ("passback".equals(object.getScheme())) {
                dw.v("Passback received");
                this.rO.bb();
                return true;
            }
            if (TextUtils.isEmpty((CharSequence)this.rM)) return false;
            Object object2 = new URI(this.rM);
            string2 = object2.getHost();
            String string3 = object.getHost();
            object2 = object2.getPath();
            object = object.getPath();
            if (!fo.equal(string2, string3)) return false;
            if (!fo.equal(object2, object)) return false;
            dw.v("Passback received");
            this.rO.bb();
            return true;
        }
        catch (URISyntaxException var1_2) {
            dw.w(var1_2.getMessage());
            return false;
        }
    }

    public void onLoadResource(WebView webView, String string2) {
        dw.v("JavascriptAdWebViewClient::onLoadResource: " + string2);
        if (!this.A(string2)) {
            this.lC.bI().onLoadResource(this.lC, string2);
        }
    }

    public void onPageFinished(WebView webView, String string2) {
        dw.v("JavascriptAdWebViewClient::onPageFinished: " + string2);
        if (!this.rN) {
            this.rO.ba();
            this.rN = true;
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
        dw.v("JavascriptAdWebViewClient::shouldOverrideUrlLoading: " + string2);
        if (this.A(string2)) {
            dw.v("shouldOverrideUrlLoading: received passback url");
            return true;
        }
        return this.lC.bI().shouldOverrideUrlLoading(this.lC, string2);
    }
}

