/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.a;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.t;
import java.io.IOException;

public final class AdvertisingIdClient {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Info a(Context context, a a2) throws IOException {
        try {
            Object object = t.a.b(a2.dV());
            object = new Info(object.getId(), object.a(true));
            return object;
        }
        catch (RemoteException var2_5) {
            Log.i((String)"AdvertisingIdClient", (String)"GMS remote exception ", (Throwable)var2_5);
            throw new IOException("Remote exception");
        }
        catch (InterruptedException var2_7) {
            throw new IOException("Interrupted exception");
        }
        finally {
            context.unbindService((ServiceConnection)a2);
        }
    }

    static a g(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        a a2;
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
        }
        catch (PackageManager.NameNotFoundException var0_1) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
        try {
            GooglePlayServicesUtil.s(context);
            a2 = new a();
        }
        catch (GooglePlayServicesNotAvailableException var0_2) {
            throw new IOException(var0_2);
        }
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        if (context.bindService(intent, (ServiceConnection)a2, 1)) {
            return a2;
        }
        throw new IOException("Connection failure");
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        fq.ak("Calling this from your main thread can lead to deadlock");
        return AdvertisingIdClient.a(context, AdvertisingIdClient.g(context));
    }

    public static final class Info {
        private final String kw;
        private final boolean kx;

        public Info(String string2, boolean bl2) {
            this.kw = string2;
            this.kx = bl2;
        }

        public String getId() {
            return this.kw;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.kx;
        }
    }

}

