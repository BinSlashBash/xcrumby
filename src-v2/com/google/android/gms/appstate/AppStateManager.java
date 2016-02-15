/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.google.android.gms.appstate;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.appstate.AppStateBuffer;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.ei;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import java.util.List;

public final class AppStateManager {
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final Scope SCOPE_APP_STATE;
    static final Api.c<ei> wx;
    private static final Api.b<ei, Api.ApiOptions.NoOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<ei, Api.ApiOptions.NoOptions>(){

            @Override
            public ei a(Context context, Looper looper, fc fc2, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new ei(context, looper, connectionCallbacks, onConnectionFailedListener, fc2.eC(), fc2.eE().toArray(new String[0]));
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };
        SCOPE_APP_STATE = new Scope("https://www.googleapis.com/auth/appstate");
        API = new Api<Api.ApiOptions.NoOptions>(wy, wx, SCOPE_APP_STATE);
    }

    private AppStateManager() {
    }

    private static StateResult a(final Status status) {
        return new StateResult(){

            @Override
            public StateConflictResult getConflictResult() {
                return null;
            }

            @Override
            public StateLoadedResult getLoadedResult() {
                return null;
            }

            @Override
            public Status getStatus() {
                return status;
            }

            @Override
            public void release() {
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ei a(GoogleApiClient object) {
        boolean bl2 = true;
        boolean bl3 = object != null;
        fq.b(bl3, (Object)"GoogleApiClient parameter is required.");
        fq.a(object.isConnected(), "GoogleApiClient must be connected.");
        object = (ei)((Object)object.a(wx));
        bl3 = object != null ? bl2 : false;
        fq.a(bl3, "GoogleApiClient is not configured to use the AppState API. Pass AppStateManager.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return object;
    }

    public static PendingResult<StateDeletedResult> delete(GoogleApiClient googleApiClient, final int n2) {
        return googleApiClient.b(new b(){

            @Override
            protected void a(ei ei2) {
                ei2.a(this, n2);
            }

            public StateDeletedResult c(final Status status) {
                return new StateDeletedResult(){

                    @Override
                    public int getStateKey() {
                        return n2;
                    }

                    @Override
                    public Status getStatus() {
                        return status;
                    }
                };
            }

            @Override
            public /* synthetic */ Result d(Status status) {
                return this.c(status);
            }

        });
    }

    public static int getMaxNumKeys(GoogleApiClient googleApiClient) {
        return AppStateManager.a(googleApiClient).dw();
    }

    public static int getMaxStateSize(GoogleApiClient googleApiClient) {
        return AppStateManager.a(googleApiClient).dv();
    }

    public static PendingResult<StateListResult> list(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new c(){

            @Override
            protected void a(ei ei2) {
                ei2.a(this);
            }
        });
    }

    public static PendingResult<StateResult> load(GoogleApiClient googleApiClient, final int n2) {
        return googleApiClient.a(new e(){

            @Override
            protected void a(ei ei2) {
                ei2.b(this, n2);
            }
        });
    }

    public static PendingResult<StateResult> resolve(GoogleApiClient googleApiClient, final int n2, final String string2, final byte[] arrby) {
        return googleApiClient.b(new e(){

            @Override
            protected void a(ei ei2) {
                ei2.a(this, n2, string2, arrby);
            }
        });
    }

    public static PendingResult<Status> signOut(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new d(){

            @Override
            protected void a(ei ei2) {
                ei2.b(this);
            }
        });
    }

    public static void update(GoogleApiClient googleApiClient, final int n2, final byte[] arrby) {
        googleApiClient.b(new e(){

            @Override
            protected void a(ei ei2) {
                ei2.a(null, n2, arrby);
            }
        });
    }

    public static PendingResult<StateResult> updateImmediate(GoogleApiClient googleApiClient, final int n2, final byte[] arrby) {
        return googleApiClient.b(new e(){

            @Override
            protected void a(ei ei2) {
                ei2.a(this, n2, arrby);
            }
        });
    }

    public static interface StateConflictResult
    extends Releasable,
    Result {
        public byte[] getLocalData();

        public String getResolvedVersion();

        public byte[] getServerData();

        public int getStateKey();
    }

    public static interface StateDeletedResult
    extends Result {
        public int getStateKey();
    }

    public static interface StateListResult
    extends Result {
        public AppStateBuffer getStateBuffer();
    }

    public static interface StateLoadedResult
    extends Releasable,
    Result {
        public byte[] getLocalData();

        public int getStateKey();
    }

    public static interface StateResult
    extends Releasable,
    Result {
        public StateConflictResult getConflictResult();

        public StateLoadedResult getLoadedResult();
    }

    public static abstract class a<R extends Result>
    extends a.b<R, ei> {
        public a() {
            super(AppStateManager.wx);
        }
    }

    private static abstract class b
    extends a<StateDeletedResult> {
        private b() {
        }
    }

    private static abstract class c
    extends a<StateListResult> {
        private c() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.e(status);
        }

        public StateListResult e(final Status status) {
            return new StateListResult(){

                @Override
                public AppStateBuffer getStateBuffer() {
                    return new AppStateBuffer(null);
                }

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

    }

    private static abstract class d
    extends a<Status> {
        private d() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

    private static abstract class e
    extends a<StateResult> {
        private e() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.g(status);
        }

        public StateResult g(Status status) {
            return AppStateManager.a(status);
        }
    }

}

