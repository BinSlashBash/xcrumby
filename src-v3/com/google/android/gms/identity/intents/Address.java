package com.google.android.gms.identity.intents;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.gw;

public final class Address {
    public static final Api<AddressOptions> API;
    static final C0241c<gw> wx;
    private static final C0240b<gw, AddressOptions> wy;

    /* renamed from: com.google.android.gms.identity.intents.Address.1 */
    static class C08221 implements C0240b<gw, AddressOptions> {
        C08221() {
        }

        public gw m1993a(Context context, Looper looper, fc fcVar, AddressOptions addressOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            fq.m984b(context instanceof Activity, (Object) "An Activity must be used for Address APIs");
            if (addressOptions == null) {
                addressOptions = new AddressOptions();
            }
            return new gw((Activity) context, looper, connectionCallbacks, onConnectionFailedListener, fcVar.getAccountName(), addressOptions.theme);
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    }

    public static final class AddressOptions implements HasOptions {
        public final int theme;

        public AddressOptions() {
            this.theme = 0;
        }

        public AddressOptions(int theme) {
            this.theme = theme;
        }
    }

    /* renamed from: com.google.android.gms.identity.intents.Address.a */
    private static abstract class C1484a extends C1299b<Status, gw> {
        public C1484a() {
            super(Address.wx);
        }

        public /* synthetic */ Result m3413d(Status status) {
            return m3414f(status);
        }

        public Status m3414f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.identity.intents.Address.2 */
    static class C15352 extends C1484a {
        final /* synthetic */ UserAddressRequest Nw;
        final /* synthetic */ int Nx;

        C15352(UserAddressRequest userAddressRequest, int i) {
            this.Nw = userAddressRequest;
            this.Nx = i;
        }

        protected void m3571a(gw gwVar) throws RemoteException {
            gwVar.m3075a(this.Nw, this.Nx);
            m1659a(Status.Bv);
        }
    }

    static {
        wx = new C0241c();
        wy = new C08221();
        API = new Api(wy, wx, new Scope[0]);
    }

    public static void requestUserAddress(GoogleApiClient googleApiClient, UserAddressRequest request, int requestCode) {
        googleApiClient.m124a(new C15352(request, requestCode));
    }
}
