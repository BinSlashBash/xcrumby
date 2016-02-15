package com.google.android.gms.plus;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.hy;
import com.google.android.gms.internal.hz;
import com.google.android.gms.internal.ia;
import com.google.android.gms.internal.ib;
import com.google.android.gms.plus.internal.C1053h;
import com.google.android.gms.plus.internal.C1415e;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import java.util.HashSet;
import java.util.Set;

public final class Plus {
    public static final Api<PlusOptions> API;
    public static final Account AccountApi;
    public static final Moments MomentsApi;
    public static final People PeopleApi;
    public static final Scope SCOPE_PLUS_LOGIN;
    public static final Scope SCOPE_PLUS_PROFILE;
    public static final C0475a TI;
    public static final C0241c<C1415e> wx;
    static final C0240b<C1415e, PlusOptions> wy;

    /* renamed from: com.google.android.gms.plus.Plus.1 */
    static class C10341 implements C0240b<C1415e, PlusOptions> {
        C10341() {
        }

        public C1415e m2393a(Context context, Looper looper, fc fcVar, PlusOptions plusOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            if (plusOptions == null) {
                plusOptions = new PlusOptions();
            }
            return new C1415e(context, looper, connectionCallbacks, onConnectionFailedListener, new C1053h(fcVar.eC(), fcVar.eF(), (String[]) plusOptions.TK.toArray(new String[0]), new String[0], context.getPackageName(), context.getPackageName(), null, new PlusCommonExtras()));
        }

        public int getPriority() {
            return 2;
        }
    }

    public static final class PlusOptions implements Optional {
        final String TJ;
        final Set<String> TK;

        public static final class Builder {
            String TJ;
            final Set<String> TK;

            public Builder() {
                this.TK = new HashSet();
            }

            public Builder addActivityTypes(String... activityTypes) {
                fq.m982b((Object) activityTypes, (Object) "activityTypes may not be null.");
                for (Object add : activityTypes) {
                    this.TK.add(add);
                }
                return this;
            }

            public PlusOptions build() {
                return new PlusOptions();
            }

            public Builder setServerClientId(String clientId) {
                this.TJ = clientId;
                return this;
            }
        }

        private PlusOptions() {
            this.TJ = null;
            this.TK = new HashSet();
        }

        private PlusOptions(Builder builder) {
            this.TJ = builder.TJ;
            this.TK = builder.TK;
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    /* renamed from: com.google.android.gms.plus.Plus.a */
    public static abstract class C1500a<R extends Result> extends C1299b<R, C1415e> {
        public C1500a() {
            super(Plus.wx);
        }
    }

    static {
        wx = new C0241c();
        wy = new C10341();
        API = new Api(wy, wx, new Scope[0]);
        SCOPE_PLUS_LOGIN = new Scope(Scopes.PLUS_LOGIN);
        SCOPE_PLUS_PROFILE = new Scope(Scopes.PLUS_ME);
        MomentsApi = new ia();
        PeopleApi = new ib();
        AccountApi = new hy();
        TI = new hz();
    }

    private Plus() {
    }

    public static C1415e m1292a(GoogleApiClient googleApiClient, C0241c<C1415e> c0241c) {
        boolean z = true;
        fq.m984b(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        fq.m980a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        C1415e c1415e = (C1415e) googleApiClient.m123a((C0241c) c0241c);
        if (c1415e == null) {
            z = false;
        }
        fq.m980a(z, "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return c1415e;
    }
}
