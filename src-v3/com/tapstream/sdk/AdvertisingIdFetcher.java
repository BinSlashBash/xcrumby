package com.tapstream.sdk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences.Editor;

public class AdvertisingIdFetcher implements Runnable {
    private static final String UUID_KEY = "TapstreamSDKUUID";
    private Application app;

    public AdvertisingIdFetcher(Application application) {
        this.app = application;
    }

    public void run() {
        try {
            Class cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
            Class cls2 = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            Object invoke = cls.getMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(cls, new Object[]{this.app});
            String str = (String) cls2.getMethod("getId", new Class[0]).invoke(invoke, new Object[0]);
            boolean booleanValue = ((Boolean) cls2.getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(invoke, new Object[0])).booleanValue();
            Editor edit = this.app.getSharedPreferences(UUID_KEY, 0).edit();
            edit.putString("advertisingId", str);
            edit.putBoolean("limitAdTracking", booleanValue);
            edit.commit();
        } catch (Exception e) {
        }
    }
}
