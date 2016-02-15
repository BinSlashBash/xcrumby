/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.tapstream.sdk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Method;

public class AdvertisingIdFetcher
implements Runnable {
    private static final String UUID_KEY = "TapstreamSDKUUID";
    private Application app;

    public AdvertisingIdFetcher(Application application) {
        this.app = application;
    }

    @Override
    public void run() {
        try {
            Object object = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
            SharedPreferences.Editor editor = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            Object object2 = object.getMethod("getAdvertisingIdInfo", Context.class).invoke(object, new Object[]{this.app});
            object = (String)editor.getMethod("getId", new Class[0]).invoke(object2, new Object[0]);
            boolean bl2 = (Boolean)editor.getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(object2, new Object[0]);
            editor = this.app.getSharedPreferences("TapstreamSDKUUID", 0).edit();
            editor.putString("advertisingId", (String)object);
            editor.putBoolean("limitAdTracking", bl2);
            editor.commit();
            return;
        }
        catch (Exception var2_3) {
            return;
        }
    }
}

