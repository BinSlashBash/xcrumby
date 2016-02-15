/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.webkit.WebView
 */
package com.crumby.lib.universal;

import android.webkit.WebView;

public interface UniversalInterpreterInterface {
    public void onInterfaceInvalidated();

    public void onReceivedError(WebView var1, int var2, String var3, String var4);
}

