package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.panorama.Panorama;
import com.google.android.gms.panorama.PanoramaApi;
import com.google.android.gms.panorama.PanoramaApi.PanoramaResult;

public class hw implements PanoramaApi {

    /* renamed from: com.google.android.gms.internal.hw.a */
    private static abstract class C1496a extends C1299b<PanoramaResult, hx> {

        /* renamed from: com.google.android.gms.internal.hw.a.1 */
        class C13791 implements PanoramaResult {
            final /* synthetic */ C1496a TB;
            final /* synthetic */ Status wz;

            C13791(C1496a c1496a, Status status) {
                this.TB = c1496a;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }

            public Intent getViewerIntent() {
                return null;
            }
        }

        public C1496a() {
            super(Panorama.wx);
        }

        public PanoramaResult m3424X(Status status) {
            return new C13791(this, status);
        }

        public /* synthetic */ Result m3425d(Status status) {
            return m3424X(status);
        }
    }

    /* renamed from: com.google.android.gms.internal.hw.1 */
    class C15421 extends C1496a {
        final /* synthetic */ hw TA;
        final /* synthetic */ Uri Tz;

        C15421(hw hwVar, Uri uri) {
            this.TA = hwVar;
            this.Tz = uri;
        }

        protected void m3573a(hx hxVar) {
            hxVar.m3086a(this, this.Tz, false);
        }
    }

    /* renamed from: com.google.android.gms.internal.hw.2 */
    class C15432 extends C1496a {
        final /* synthetic */ hw TA;
        final /* synthetic */ Uri Tz;

        C15432(hw hwVar, Uri uri) {
            this.TA = hwVar;
            this.Tz = uri;
        }

        protected void m3575a(hx hxVar) {
            hxVar.m3086a(this, this.Tz, true);
        }
    }

    public PendingResult<PanoramaResult> loadPanoramaInfo(GoogleApiClient client, Uri uri) {
        return client.m124a(new C15421(this, uri));
    }

    public PendingResult<PanoramaResult> loadPanoramaInfoAndGrantAccess(GoogleApiClient client, Uri uri) {
        return client.m124a(new C15432(this, uri));
    }
}
