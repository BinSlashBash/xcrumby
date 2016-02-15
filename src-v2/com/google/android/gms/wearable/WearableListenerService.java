/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.kh;
import com.google.android.gms.internal.ki;
import com.google.android.gms.internal.kk;
import com.google.android.gms.wearable.b;
import com.google.android.gms.wearable.e;
import com.google.android.gms.wearable.f;

public abstract class WearableListenerService
extends Service {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private IBinder DB;
    private volatile int adu = -1;
    private String adv;
    private Handler adw;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean cM(int n2) {
        boolean bl2 = false;
        String[] arrstring = this.getPackageManager().getPackagesForUid(n2);
        boolean bl3 = bl2;
        if (arrstring == null) return bl3;
        n2 = 0;
        do {
            bl3 = bl2;
            if (n2 >= arrstring.length) return bl3;
            if ("com.google.android.gms".equals(arrstring[n2])) {
                return true;
            }
            ++n2;
        } while (true);
    }

    private void md() throws SecurityException {
        int n2 = Binder.getCallingUid();
        if (n2 == this.adu) {
            return;
        }
        if (GooglePlayServicesUtil.b(this.getPackageManager(), "com.google.android.gms") && this.cM(n2)) {
            this.adu = n2;
            return;
        }
        throw new SecurityException("Caller is not GooglePlayServices");
    }

    public IBinder onBind(Intent intent) {
        if ("com.google.android.gms.wearable.BIND_LISTENER".equals(intent.getAction())) {
            return this.DB;
        }
        return null;
    }

    public void onCreate() {
        super.onCreate();
        if (Log.isLoggable((String)"WearableLS", (int)3)) {
            Log.d((String)"WearableLS", (String)("onCreate: " + this.getPackageName()));
        }
        this.adv = this.getPackageName();
        HandlerThread handlerThread = new HandlerThread("WearableListenerService");
        handlerThread.start();
        this.adw = new Handler(handlerThread.getLooper());
        this.DB = new a();
    }

    public void onDataChanged(b b2) {
    }

    public void onDestroy() {
        this.adw.getLooper().quit();
        super.onDestroy();
    }

    public void onMessageReceived(e e2) {
    }

    public void onPeerConnected(f f2) {
    }

    public void onPeerDisconnected(f f2) {
    }

    private class a
    extends kh.a {
        private a() {
        }

        @Override
        public void M(final DataHolder dataHolder) {
            if (Log.isLoggable((String)"WearableLS", (int)3)) {
                Log.d((String)"WearableLS", (String)("onDataItemChanged: " + WearableListenerService.this.adv + ": " + dataHolder));
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable(){

                @Override
                public void run() {
                    b b2 = new b(dataHolder);
                    try {
                        WearableListenerService.this.onDataChanged(b2);
                        return;
                    }
                    finally {
                        b2.close();
                    }
                }
            });
        }

        @Override
        public void a(final ki ki2) {
            if (Log.isLoggable((String)"WearableLS", (int)3)) {
                Log.d((String)"WearableLS", (String)("onMessageReceived: " + ki2));
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onMessageReceived(ki2);
                }
            });
        }

        @Override
        public void a(final kk kk2) {
            if (Log.isLoggable((String)"WearableLS", (int)3)) {
                Log.d((String)"WearableLS", (String)("onPeerConnected: " + WearableListenerService.this.adv + ": " + kk2));
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onPeerConnected(kk2);
                }
            });
        }

        @Override
        public void b(final kk kk2) {
            if (Log.isLoggable((String)"WearableLS", (int)3)) {
                Log.d((String)"WearableLS", (String)("onPeerDisconnected: " + WearableListenerService.this.adv + ": " + kk2));
            }
            WearableListenerService.this.md();
            WearableListenerService.this.adw.post(new Runnable(){

                @Override
                public void run() {
                    WearableListenerService.this.onPeerDisconnected(kk2);
                }
            });
        }

    }

}

