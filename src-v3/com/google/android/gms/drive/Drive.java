package com.google.android.gms.drive;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.C0239a;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.internal.C0801l;
import com.google.android.gms.drive.internal.C0802p;
import com.google.android.gms.drive.internal.C1308n;
import com.google.android.gms.internal.fc;
import java.util.List;

public final class Drive {
    public static final Api<NoOptions> API;
    public static final DriveApi DriveApi;
    public static final Scope EE;
    public static final Scope EF;
    public static final C0266c EG;
    public static final Scope SCOPE_APPFOLDER;
    public static final Scope SCOPE_FILE;
    public static final C0241c<C1308n> wx;
    public static final C0240b<C1308n, NoOptions> wy;

    /* renamed from: com.google.android.gms.drive.Drive.1 */
    static class C08001 implements C0240b<C1308n, NoOptions> {
        C08001() {
        }

        public /* synthetic */ C0239a m1700a(Context context, Looper looper, fc fcVar, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return m1701b(context, looper, fcVar, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public C1308n m1701b(Context context, Looper looper, fc fcVar, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            List eE = fcVar.eE();
            return new C1308n(context, looper, fcVar, connectionCallbacks, onConnectionFailedListener, (String[]) eE.toArray(new String[eE.size()]));
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    }

    static {
        wx = new C0241c();
        wy = new C08001();
        SCOPE_FILE = new Scope(Scopes.DRIVE_FILE);
        SCOPE_APPFOLDER = new Scope(Scopes.DRIVE_APPFOLDER);
        EE = new Scope(Scopes.DRIVE_FULL);
        EF = new Scope(Scopes.DRIVE_APPS);
        API = new Api(wy, wx, new Scope[0]);
        DriveApi = new C0801l();
        EG = new C0802p();
    }

    private Drive() {
    }
}
