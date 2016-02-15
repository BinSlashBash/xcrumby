/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.panorama;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.hw;
import com.google.android.gms.internal.hx;
import com.google.android.gms.panorama.PanoramaApi;

public final class Panorama {
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final PanoramaApi PanoramaApi;
    public static final Api.c<hx> wx;
    static final Api.b<hx, Api.ApiOptions.NoOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<hx, Api.ApiOptions.NoOptions>(){

            @Override
            public /* synthetic */ Api.a a(Context context, Looper looper, fc fc2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return this.c(context, looper, fc2, (Api.ApiOptions.NoOptions)object, connectionCallbacks, onConnectionFailedListener);
            }

            public hx c(Context context, Looper looper, fc fc2, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new hx(context, looper, connectionCallbacks, onConnectionFailedListener);
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };
        API = new Api<Api.ApiOptions.NoOptions>(wy, wx, new Scope[0]);
        PanoramaApi = new hw();
    }

    private Panorama() {
    }

}

