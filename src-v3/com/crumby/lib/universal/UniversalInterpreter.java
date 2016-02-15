package com.crumby.lib.universal;

import android.content.Context;
import android.net.http.SslError;
import android.os.Looper;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.crumby.CrDb;

public class UniversalInterpreter extends WebView {
    private UniversalInterpreterInterface javascriptInterface;

    /* renamed from: com.crumby.lib.universal.UniversalInterpreter.1 */
    class C01101 extends WebViewClient {
        C01101() {
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            UniversalInterpreter.this.javascriptInterface.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    public UniversalInterpreter(Context context) {
        super(context);
    }

    public UniversalInterpreter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UniversalInterpreter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public UniversalInterpreter(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
    }

    public void setInterface(UniversalInterpreterInterface anInterface) {
        if (this.javascriptInterface != null) {
            removeJavascriptInterface("Crumby");
        }
        if (anInterface != null) {
            this.javascriptInterface = anInterface;
            setWebViewClient(new C01101());
            addJavascriptInterface(this.javascriptInterface, "Crumby");
        }
    }

    public UniversalInterpreterInterface getInterface() {
        return this.javascriptInterface;
    }

    private void invalidateInterface() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            CrDb.m0d(getClass().getSimpleName(), "INVALIDATE");
        }
        this.javascriptInterface = null;
        removeJavascriptInterface("Crumby");
    }

    public void removeInterface(UniversalInterpreterInterface anInterface) {
        if (anInterface == this.javascriptInterface) {
            invalidateInterface();
        }
    }

    public void forceInvalidateInterface() {
        if (this.javascriptInterface != null) {
            this.javascriptInterface.onInterfaceInvalidated();
            invalidateInterface();
        }
    }
}
