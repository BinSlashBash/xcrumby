/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.http.SslError
 *  android.os.Looper
 *  android.util.AttributeSet
 *  android.webkit.SslErrorHandler
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 */
package com.crumby.lib.universal;

import android.content.Context;
import android.net.http.SslError;
import android.os.Looper;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.crumby.CrDb;
import com.crumby.lib.universal.UniversalInterpreterInterface;

public class UniversalInterpreter
extends WebView {
    private UniversalInterpreterInterface javascriptInterface;

    public UniversalInterpreter(Context context) {
        super(context);
    }

    public UniversalInterpreter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public UniversalInterpreter(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public UniversalInterpreter(Context context, AttributeSet attributeSet, int n2, boolean bl2) {
        super(context, attributeSet, n2, bl2);
    }

    private void invalidateInterface() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            CrDb.d(this.getClass().getSimpleName(), "INVALIDATE");
        }
        this.javascriptInterface = null;
        this.removeJavascriptInterface("Crumby");
    }

    public void forceInvalidateInterface() {
        if (this.javascriptInterface == null) {
            return;
        }
        this.javascriptInterface.onInterfaceInvalidated();
        this.invalidateInterface();
    }

    public UniversalInterpreterInterface getInterface() {
        return this.javascriptInterface;
    }

    public void removeInterface(UniversalInterpreterInterface universalInterpreterInterface) {
        if (universalInterpreterInterface == this.javascriptInterface) {
            this.invalidateInterface();
        }
    }

    public void setInterface(UniversalInterpreterInterface universalInterpreterInterface) {
        if (this.javascriptInterface != null) {
            this.removeJavascriptInterface("Crumby");
        }
        if (universalInterpreterInterface == null) {
            return;
        }
        this.javascriptInterface = universalInterpreterInterface;
        this.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView webView, int n2, String string2, String string3) {
                UniversalInterpreter.this.javascriptInterface.onReceivedError(webView, n2, string2, string3);
            }

            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }
        });
        this.addJavascriptInterface((Object)this.javascriptInterface, "Crumby");
    }

}

