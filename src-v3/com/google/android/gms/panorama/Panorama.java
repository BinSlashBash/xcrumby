package com.google.android.gms.panorama;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.C0239a;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.hw;
import com.google.android.gms.internal.hx;

public final class Panorama {
    public static final Api<NoOptions> API;
    public static final PanoramaApi PanoramaApi;
    public static final C0241c<hx> wx;
    static final C0240b<hx, NoOptions> wy;

    /* renamed from: com.google.android.gms.panorama.Panorama.1 */
    static class C10331 implements C0240b<hx, NoOptions> {
        C10331() {
        }

        public /* synthetic */ C0239a m2390a(Context context, Looper looper, fc fcVar, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return m2391c(context, looper, fcVar, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public hx m2391c(Context context, Looper looper, fc fcVar, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new hx(context, looper, connectionCallbacks, onConnectionFailedListener);
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    }

    static {
        wx = new C0241c();
        wy = new C10331();
        API = new Api(wy, wx, new Scope[0]);
        PanoramaApi = new hw();
    }

    private Panorama() {
    }
}
