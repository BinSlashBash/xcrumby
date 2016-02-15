package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import java.io.IOException;

/* renamed from: com.google.android.gms.internal.k */
public class C1499k extends C1394j {

    /* renamed from: com.google.android.gms.internal.k.a */
    class C0408a {
        private String ka;
        private boolean kb;
        final /* synthetic */ C1499k kc;

        public C0408a(C1499k c1499k, String str, boolean z) {
            this.kc = c1499k;
            this.ka = str;
            this.kb = z;
        }

        public String getId() {
            return this.ka;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.kb;
        }
    }

    private C1499k(Context context, C0412n c0412n, C0413o c0413o) {
        super(context, c0412n, c0413o);
    }

    public static C1499k m3432a(String str, Context context) {
        C0412n c0864e = new C0864e();
        C1394j.m3122a(str, context, c0864e);
        return new C1499k(context, c0864e, new C0934q(239));
    }

    protected void m3433b(Context context) {
        long j = 1;
        super.m3130b(context);
        try {
            C0408a f = m3434f(context);
            try {
                if (!f.isLimitAdTrackingEnabled()) {
                    j = 0;
                }
                m2298a(28, j);
                String id = f.getId();
                if (id != null) {
                    m2299a(30, id);
                }
            } catch (IOException e) {
            }
        } catch (GooglePlayServicesNotAvailableException e2) {
        } catch (IOException e3) {
            m2298a(28, 1);
        }
    }

    C0408a m3434f(Context context) throws IOException, GooglePlayServicesNotAvailableException {
        int i = 0;
        try {
            String str;
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            String id = advertisingIdInfo.getId();
            if (id == null || !id.matches("^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")) {
                str = id;
            } else {
                byte[] bArr = new byte[16];
                int i2 = 0;
                while (i < id.length()) {
                    if (i == 8 || i == 13 || i == 18 || i == 23) {
                        i++;
                    }
                    bArr[i2] = (byte) ((Character.digit(id.charAt(i), 16) << 4) + Character.digit(id.charAt(i + 1), 16));
                    i2++;
                    i += 2;
                }
                str = this.jP.m1184a(bArr, true);
            }
            return new C0408a(this, str, advertisingIdInfo.isLimitAdTrackingEnabled());
        } catch (Throwable e) {
            throw new IOException(e);
        }
    }
}
