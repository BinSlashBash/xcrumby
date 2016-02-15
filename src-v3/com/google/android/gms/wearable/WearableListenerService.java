package com.google.android.gms.wearable;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.kh.C0933a;
import com.google.android.gms.internal.ki;
import com.google.android.gms.internal.kk;

public abstract class WearableListenerService extends Service {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private IBinder DB;
    private volatile int adu;
    private String adv;
    private Handler adw;

    /* renamed from: com.google.android.gms.wearable.WearableListenerService.a */
    private class C1422a extends C0933a {
        final /* synthetic */ WearableListenerService adx;

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.a.1 */
        class C05641 implements Runnable {
            final /* synthetic */ DataHolder ady;
            final /* synthetic */ C1422a adz;

            C05641(C1422a c1422a, DataHolder dataHolder) {
                this.adz = c1422a;
                this.ady = dataHolder;
            }

            public void run() {
                C1423b c1423b = new C1423b(this.ady);
                try {
                    this.adz.adx.onDataChanged(c1423b);
                } finally {
                    c1423b.close();
                }
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.a.2 */
        class C05652 implements Runnable {
            final /* synthetic */ ki adA;
            final /* synthetic */ C1422a adz;

            C05652(C1422a c1422a, ki kiVar) {
                this.adz = c1422a;
                this.adA = kiVar;
            }

            public void run() {
                this.adz.adx.onMessageReceived(this.adA);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.a.3 */
        class C05663 implements Runnable {
            final /* synthetic */ kk adB;
            final /* synthetic */ C1422a adz;

            C05663(C1422a c1422a, kk kkVar) {
                this.adz = c1422a;
                this.adB = kkVar;
            }

            public void run() {
                this.adz.adx.onPeerConnected(this.adB);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.a.4 */
        class C05674 implements Runnable {
            final /* synthetic */ kk adB;
            final /* synthetic */ C1422a adz;

            C05674(C1422a c1422a, kk kkVar) {
                this.adz = c1422a;
                this.adB = kkVar;
            }

            public void run() {
                this.adz.adx.onPeerDisconnected(this.adB);
            }
        }

        private C1422a(WearableListenerService wearableListenerService) {
            this.adx = wearableListenerService;
        }

        public void m3250M(DataHolder dataHolder) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onDataItemChanged: " + this.adx.adv + ": " + dataHolder);
            }
            this.adx.md();
            this.adx.adw.post(new C05641(this, dataHolder));
        }

        public void m3251a(ki kiVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onMessageReceived: " + kiVar);
            }
            this.adx.md();
            this.adx.adw.post(new C05652(this, kiVar));
        }

        public void m3252a(kk kkVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerConnected: " + this.adx.adv + ": " + kkVar);
            }
            this.adx.md();
            this.adx.adw.post(new C05663(this, kkVar));
        }

        public void m3253b(kk kkVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerDisconnected: " + this.adx.adv + ": " + kkVar);
            }
            this.adx.md();
            this.adx.adw.post(new C05674(this, kkVar));
        }
    }

    public WearableListenerService() {
        this.adu = -1;
    }

    private boolean cM(int i) {
        String str = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE;
        String[] packagesForUid = getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        for (Object equals : packagesForUid) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private void md() throws SecurityException {
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.adu) {
            if (GooglePlayServicesUtil.m111b(getPackageManager(), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE) && cM(callingUid)) {
                this.adu = callingUid;
                return;
            }
            throw new SecurityException("Caller is not GooglePlayServices");
        }
    }

    public IBinder onBind(Intent intent) {
        return BIND_LISTENER_INTENT_ACTION.equals(intent.getAction()) ? this.DB : null;
    }

    public void onCreate() {
        super.onCreate();
        if (Log.isLoggable("WearableLS", 3)) {
            Log.d("WearableLS", "onCreate: " + getPackageName());
        }
        this.adv = getPackageName();
        HandlerThread handlerThread = new HandlerThread("WearableListenerService");
        handlerThread.start();
        this.adw = new Handler(handlerThread.getLooper());
        this.DB = new C1422a();
    }

    public void onDataChanged(C1423b dataEvents) {
    }

    public void onDestroy() {
        this.adw.getLooper().quit();
        super.onDestroy();
    }

    public void onMessageReceived(C0568e messageEvent) {
    }

    public void onPeerConnected(C0569f peer) {
    }

    public void onPeerDisconnected(C0569f peer) {
    }
}
