/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.fq;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.e;

public final class hy
implements Account {
    /*
     * Enabled aggressive block sorting
     */
    private static e a(GoogleApiClient object, Api.c<e> c2) {
        boolean bl2 = true;
        boolean bl3 = object != null;
        fq.b(bl3, (Object)"GoogleApiClient parameter is required.");
        fq.a(object.isConnected(), "GoogleApiClient must be connected.");
        object = (e)((Object)object.a(c2));
        bl3 = object != null ? bl2 : false;
        fq.a(bl3, "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return object;
    }

    @Override
    public void clearDefaultAccount(GoogleApiClient googleApiClient) {
        hy.a(googleApiClient, Plus.wx).clearDefaultAccount();
    }

    @Override
    public String getAccountName(GoogleApiClient googleApiClient) {
        return hy.a(googleApiClient, Plus.wx).getAccountName();
    }

    @Override
    public PendingResult<Status> revokeAccessAndDisconnect(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new a(){

            @Override
            protected void a(e e2) {
                e2.n(this);
            }
        });
    }

    private static abstract class a
    extends Plus.a<Status> {
        private a() {
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

