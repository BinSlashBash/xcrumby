/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.drive;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.c;
import com.google.android.gms.drive.internal.l;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.p;
import com.google.android.gms.internal.fc;
import java.util.List;

public final class Drive {
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final DriveApi DriveApi;
    public static final Scope EE;
    public static final Scope EF;
    public static final c EG;
    public static final Scope SCOPE_APPFOLDER;
    public static final Scope SCOPE_FILE;
    public static final Api.c<n> wx;
    public static final Api.b<n, Api.ApiOptions.NoOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<n, Api.ApiOptions.NoOptions>(){

            @Override
            public /* synthetic */ Api.a a(Context context, Looper looper, fc fc2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return this.b(context, looper, fc2, (Api.ApiOptions.NoOptions)object, connectionCallbacks, onConnectionFailedListener);
            }

            public n b(Context context, Looper looper, fc fc2, Api.ApiOptions.NoOptions object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                object = fc2.eE();
                return new n(context, looper, fc2, connectionCallbacks, onConnectionFailedListener, object.toArray(new String[object.size()]));
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };
        SCOPE_FILE = new Scope("https://www.googleapis.com/auth/drive.file");
        SCOPE_APPFOLDER = new Scope("https://www.googleapis.com/auth/drive.appdata");
        EE = new Scope("https://www.googleapis.com/auth/drive");
        EF = new Scope("https://www.googleapis.com/auth/drive.apps");
        API = new Api<Api.ApiOptions.NoOptions>(wy, wx, new Scope[0]);
        DriveApi = new l();
        EG = new p();
    }

    private Drive() {
    }

}

