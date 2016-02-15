package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.Moments.LoadMomentsResult;
import com.google.android.gms.plus.Plus.C1500a;
import com.google.android.gms.plus.internal.C1415e;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;

public final class ia implements Moments {

    /* renamed from: com.google.android.gms.internal.ia.a */
    private static abstract class C1545a extends C1500a<LoadMomentsResult> {

        /* renamed from: com.google.android.gms.internal.ia.a.1 */
        class C13821 implements LoadMomentsResult {
            final /* synthetic */ C1545a UC;
            final /* synthetic */ Status wz;

            C13821(C1545a c1545a, Status status) {
                this.UC = c1545a;
                this.wz = status;
            }

            public MomentBuffer getMomentBuffer() {
                return null;
            }

            public String getNextPageToken() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }

            public String getUpdated() {
                return null;
            }

            public void release() {
            }
        }

        private C1545a() {
        }

        public LoadMomentsResult aa(Status status) {
            return new C13821(this, status);
        }

        public /* synthetic */ Result m3578d(Status status) {
            return aa(status);
        }
    }

    /* renamed from: com.google.android.gms.internal.ia.b */
    private static abstract class C1546b extends C1500a<Status> {
        private C1546b() {
        }

        public /* synthetic */ Result m3579d(Status status) {
            return m3580f(status);
        }

        public Status m3580f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.internal.ia.c */
    private static abstract class C1547c extends C1500a<Status> {
        private C1547c() {
        }

        public /* synthetic */ Result m3581d(Status status) {
            return m3582f(status);
        }

        public Status m3582f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.internal.ia.1 */
    class C16541 extends C1545a {
        final /* synthetic */ ia Uv;

        C16541(ia iaVar) {
            this.Uv = iaVar;
            super();
        }

        protected void m3841a(C1415e c1415e) {
            c1415e.m3200l(this);
        }
    }

    /* renamed from: com.google.android.gms.internal.ia.2 */
    class C16552 extends C1545a {
        final /* synthetic */ int Ks;
        final /* synthetic */ ia Uv;
        final /* synthetic */ String Uw;
        final /* synthetic */ Uri Ux;
        final /* synthetic */ String Uy;
        final /* synthetic */ String Uz;

        C16552(ia iaVar, int i, String str, Uri uri, String str2, String str3) {
            this.Uv = iaVar;
            this.Ks = i;
            this.Uw = str;
            this.Ux = uri;
            this.Uy = str2;
            this.Uz = str3;
            super();
        }

        protected void m3843a(C1415e c1415e) {
            c1415e.m3195a(this, this.Ks, this.Uw, this.Ux, this.Uy, this.Uz);
        }
    }

    /* renamed from: com.google.android.gms.internal.ia.3 */
    class C16563 extends C1547c {
        final /* synthetic */ Moment UA;
        final /* synthetic */ ia Uv;

        C16563(ia iaVar, Moment moment) {
            this.Uv = iaVar;
            this.UA = moment;
            super();
        }

        protected void m3845a(C1415e c1415e) {
            c1415e.m3196a((C0244d) this, this.UA);
        }
    }

    /* renamed from: com.google.android.gms.internal.ia.4 */
    class C16574 extends C1546b {
        final /* synthetic */ String UB;
        final /* synthetic */ ia Uv;

        C16574(ia iaVar, String str) {
            this.Uv = iaVar;
            this.UB = str;
            super();
        }

        protected void m3847a(C1415e c1415e) {
            c1415e.removeMoment(this.UB);
            m1659a(Status.Bv);
        }
    }

    public PendingResult<LoadMomentsResult> load(GoogleApiClient googleApiClient) {
        return googleApiClient.m124a(new C16541(this));
    }

    public PendingResult<LoadMomentsResult> load(GoogleApiClient googleApiClient, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
        return googleApiClient.m124a(new C16552(this, maxResults, pageToken, targetUrl, type, userId));
    }

    public PendingResult<Status> remove(GoogleApiClient googleApiClient, String momentId) {
        return googleApiClient.m125b(new C16574(this, momentId));
    }

    public PendingResult<Status> write(GoogleApiClient googleApiClient, Moment moment) {
        return googleApiClient.m125b(new C16563(this, moment));
    }
}
