/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.google.android.gms.identity.intents;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.identity.intents.UserAddressRequest;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.gw;

public final class Address {
    public static final Api<AddressOptions> API;
    static final Api.c<gw> wx;
    private static final Api.b<gw, AddressOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<gw, AddressOptions>(){

            @Override
            public gw a(Context context, Looper looper, fc fc2, AddressOptions addressOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                fq.b(context instanceof Activity, (Object)"An Activity must be used for Address APIs");
                AddressOptions addressOptions2 = addressOptions;
                if (addressOptions == null) {
                    addressOptions2 = new AddressOptions();
                }
                return new gw((Activity)context, looper, connectionCallbacks, onConnectionFailedListener, fc2.getAccountName(), addressOptions2.theme);
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };
        API = new Api<AddressOptions>(wy, wx, new Scope[0]);
    }

    public static void requestUserAddress(GoogleApiClient googleApiClient, final UserAddressRequest userAddressRequest, final int n2) {
        googleApiClient.a(new a(){

            @Override
            protected void a(gw gw2) throws RemoteException {
                gw2.a(userAddressRequest, n2);
                this.a(Status.Bv);
            }
        });
    }

    public static final class AddressOptions
    implements Api.ApiOptions.HasOptions {
        public final int theme;

        public AddressOptions() {
            this.theme = 0;
        }

        public AddressOptions(int n2) {
            this.theme = n2;
        }
    }

    private static abstract class a
    extends a.b<Status, gw> {
        public a() {
            super(Address.wx);
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

}

