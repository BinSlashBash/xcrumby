package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.C0238a;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.C0418t;
import com.google.android.gms.internal.C0418t.C0938a;
import com.google.android.gms.internal.fq;
import java.io.IOException;

public final class AdvertisingIdClient {

    public static final class Info {
        private final String kw;
        private final boolean kx;

        public Info(String advertisingId, boolean limitAdTrackingEnabled) {
            this.kw = advertisingId;
            this.kx = limitAdTrackingEnabled;
        }

        public String getId() {
            return this.kw;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.kx;
        }
    }

    static Info m8a(Context context, C0238a c0238a) throws IOException {
        try {
            C0418t b = C0938a.m2335b(c0238a.dV());
            Info info = new Info(b.getId(), b.m1207a(true));
            try {
                context.unbindService(c0238a);
            } catch (Throwable e) {
                Log.i("AdvertisingIdClient", "getAdvertisingIdInfo unbindService failed.", e);
            }
            return info;
        } catch (Throwable e2) {
            Log.i("AdvertisingIdClient", "GMS remote exception ", e2);
            throw new IOException("Remote exception");
        } catch (InterruptedException e3) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            try {
                context.unbindService(c0238a);
            } catch (Throwable e4) {
                Log.i("AdvertisingIdClient", "getAdvertisingIdInfo unbindService failed.", e4);
            }
        }
    }

    static C0238a m9g(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            try {
                GooglePlayServicesUtil.m117s(context);
                Object c0238a = new C0238a();
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                if (context.bindService(intent, c0238a, 1)) {
                    return c0238a;
                }
                throw new IOException("Connection failure");
            } catch (Throwable e) {
                throw new IOException(e);
            }
        } catch (NameNotFoundException e2) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        fq.ak("Calling this from your main thread can lead to deadlock");
        return m8a(context, m9g(context));
    }
}
