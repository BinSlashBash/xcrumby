/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.plus;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.hy;
import com.google.android.gms.internal.hz;
import com.google.android.gms.internal.ia;
import com.google.android.gms.internal.ib;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.internal.h;
import java.util.HashSet;
import java.util.Set;

public final class Plus {
    public static final Api<PlusOptions> API;
    public static final Account AccountApi;
    public static final Moments MomentsApi;
    public static final People PeopleApi;
    public static final Scope SCOPE_PLUS_LOGIN;
    public static final Scope SCOPE_PLUS_PROFILE;
    public static final com.google.android.gms.plus.a TI;
    public static final Api.c<e> wx;
    static final Api.b<e, PlusOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<e, PlusOptions>(){

            @Override
            public e a(Context context, Looper looper, fc arrstring, PlusOptions object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                String[] arrstring2 = object;
                if (object == null) {
                    arrstring2 = new PlusOptions();
                }
                object = arrstring.eC();
                arrstring = arrstring.eF();
                arrstring2 = arrstring2.TK.toArray(new String[0]);
                String string2 = context.getPackageName();
                String string3 = context.getPackageName();
                PlusCommonExtras plusCommonExtras = new PlusCommonExtras();
                return new e(context, looper, connectionCallbacks, onConnectionFailedListener, new h((String)object, arrstring, arrstring2, new String[0], string2, string3, null, plusCommonExtras));
            }

            @Override
            public int getPriority() {
                return 2;
            }
        };
        API = new Api<PlusOptions>(wy, wx, new Scope[0]);
        SCOPE_PLUS_LOGIN = new Scope("https://www.googleapis.com/auth/plus.login");
        SCOPE_PLUS_PROFILE = new Scope("https://www.googleapis.com/auth/plus.me");
        MomentsApi = new ia();
        PeopleApi = new ib();
        AccountApi = new hy();
        TI = new hz();
    }

    private Plus() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static e a(GoogleApiClient object, Api.c<e> c2) {
        boolean bl2 = true;
        boolean bl3 = object != null;
        fq.b(bl3, (Object)"GoogleApiClient parameter is required.");
        fq.a(object.isConnected(), "GoogleApiClient must be connected.");
        object = (e)((Object)object.a(c2));
        bl3 = object != null ? bl2 : false;
        fq.a(bl3, "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return object;
    }

    public static final class PlusOptions
    implements Api.ApiOptions.Optional {
        final String TJ;
        final Set<String> TK;

        private PlusOptions() {
            this.TJ = null;
            this.TK = new HashSet<String>();
        }

        private PlusOptions(Builder builder) {
            this.TJ = builder.TJ;
            this.TK = builder.TK;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            String TJ;
            final Set<String> TK = new HashSet<String>();

            public /* varargs */ Builder addActivityTypes(String ... arrstring) {
                fq.b(arrstring, (Object)"activityTypes may not be null.");
                for (int i2 = 0; i2 < arrstring.length; ++i2) {
                    this.TK.add(arrstring[i2]);
                }
                return this;
            }

            public PlusOptions build() {
                return new PlusOptions(this);
            }

            public Builder setServerClientId(String string2) {
                this.TJ = string2;
                return this;
            }
        }

    }

    public static abstract class a<R extends Result>
    extends a.b<R, e> {
        public a() {
            super(Plus.wx);
        }
    }

}

