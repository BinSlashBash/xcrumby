/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.tapstream.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ReferrerReceiver
extends BroadcastReceiver {
    private static final String UUID_KEY = "TapstreamSDKUUID";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onReceive(Context context, Intent object) {
        String string2 = object.getStringExtra("referrer");
        if (string2 != null) {
            object = "";
            try {
                object = string2 = URLDecoder.decode(string2, "utf-8");
            }
            catch (UnsupportedEncodingException var3_4) {
                var3_4.printStackTrace();
            }
            if (object.length() > 0) {
                context = context.getApplicationContext().getSharedPreferences("TapstreamSDKUUID", 0).edit();
                context.putString("referrer", (String)object);
                context.commit();
            }
        }
    }
}

