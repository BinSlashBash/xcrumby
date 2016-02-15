/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.m;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

class a
implements m {
    private static Object sf = new Object();
    private static a sg;
    private Context mContext;
    private AdvertisingIdClient.Info sh;
    private long si;

    a(Context context) {
        this.mContext = context;
    }

    private AdvertisingIdClient.Info bQ() {
        try {
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
            return info;
        }
        catch (IllegalStateException var1_2) {
            aa.z("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        }
        catch (GooglePlayServicesRepairableException var1_3) {
            aa.z("GooglePlayServicesRepairableException getting Ad Id Info");
            return null;
        }
        catch (IOException var1_4) {
            aa.z("IOException getting Ad Id Info");
            return null;
        }
        catch (GooglePlayServicesNotAvailableException var1_5) {
            aa.z("GooglePlayServicesNotAvailableException getting Ad Id Info");
            return null;
        }
        catch (Exception var1_6) {
            aa.z("Unknown exception. Could not get the ad Id.");
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static m m(Context context) {
        if (sg == null) {
            Object object = sf;
            synchronized (object) {
                if (sg == null) {
                    sg = new a(context);
                }
            }
        }
        return sg;
    }

    @Override
    public String getValue(String string2) {
        long l2 = System.currentTimeMillis();
        if (l2 - this.si > 1000) {
            this.sh = this.bQ();
            this.si = l2;
        }
        if (this.sh != null) {
            if ("&adid".equals(string2)) {
                return this.sh.getId();
            }
            if ("&ate".equals(string2)) {
                if (this.sh.isLimitAdTrackingEnabled()) {
                    return "0";
                }
                return "1";
            }
        }
        return null;
    }
}

