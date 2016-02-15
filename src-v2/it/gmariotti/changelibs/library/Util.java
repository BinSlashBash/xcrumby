/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package it.gmariotti.changelibs.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
    /*
     * Enabled aggressive block sorting
     */
    public static boolean isConnected(Context context) {
        if (context == null || (context = (ConnectivityManager)context.getSystemService("connectivity")) == null || (context = context.getActiveNetworkInfo()) == null || context == null || !context.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
}

