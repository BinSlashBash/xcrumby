package com.crumby.lib.universal;

import android.webkit.WebView;

public interface UniversalInterpreterInterface {
    void onInterfaceInvalidated();

    void onReceivedError(WebView webView, int i, String str, String str2);
}
