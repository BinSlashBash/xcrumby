package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.Plus.C1500a;
import com.google.android.gms.plus.internal.C1415e;

public final class hy implements Account {

    /* renamed from: com.google.android.gms.internal.hy.a */
    private static abstract class C1544a extends C1500a<Status> {
        private C1544a() {
        }

        public /* synthetic */ Result m3576d(Status status) {
            return m3577f(status);
        }

        public Status m3577f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.internal.hy.1 */
    class C16531 extends C1544a {
        final /* synthetic */ hy Uu;

        C16531(hy hyVar) {
            this.Uu = hyVar;
            super();
        }

        protected void m3839a(C1415e c1415e) {
            c1415e.m3202n(this);
        }
    }

    private static C1415e m2290a(GoogleApiClient googleApiClient, C0241c<C1415e> c0241c) {
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

    public void clearDefaultAccount(GoogleApiClient googleApiClient) {
        m2290a(googleApiClient, Plus.wx).clearDefaultAccount();
    }

    public String getAccountName(GoogleApiClient googleApiClient) {
        return m2290a(googleApiClient, Plus.wx).getAccountName();
    }

    public PendingResult<Status> revokeAccessAndDisconnect(GoogleApiClient googleApiClient) {
        return googleApiClient.m125b(new C16531(this));
    }
}
