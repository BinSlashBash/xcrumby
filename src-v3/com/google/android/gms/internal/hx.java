package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.ff.C0391b;
import com.google.android.gms.internal.ff.C1374e;
import com.google.android.gms.internal.hu.C0917a;
import com.google.android.gms.internal.hv.C0919a;
import com.google.android.gms.panorama.PanoramaApi.C1411a;
import com.google.android.gms.panorama.PanoramaApi.PanoramaResult;

public class hx extends ff<hv> {

    /* renamed from: com.google.android.gms.internal.hx.b */
    final class C1380b extends C0917a {
        final /* synthetic */ hx TE;
        private final C0244d<C1411a> TF;
        private final C0244d<PanoramaResult> TG;
        private final Uri TH;

        public C1380b(hx hxVar, C0244d<C1411a> c0244d, C0244d<PanoramaResult> c0244d2, Uri uri) {
            this.TE = hxVar;
            this.TF = c0244d;
            this.TG = c0244d2;
            this.TH = uri;
        }

        public void m3083a(int i, Bundle bundle, int i2, Intent intent) {
            if (this.TH != null) {
                this.TE.getContext().revokeUriPermission(this.TH, 1);
            }
            Status status = new Status(i, null, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null);
            if (this.TG != null) {
                this.TE.m2175a(new C1381c(this.TE, this.TG, status, intent));
            } else if (this.TF != null) {
                this.TE.m2175a(new C1497a(this.TE, this.TF, status, i2, intent));
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.hx.c */
    final class C1381c extends C0391b<C0244d<PanoramaResult>> implements PanoramaResult {
        private final Status TC;
        private final Intent TD;
        final /* synthetic */ hx TE;

        public C1381c(hx hxVar, C0244d<PanoramaResult> c0244d, Status status, Intent intent) {
            this.TE = hxVar;
            super(hxVar, c0244d);
            this.TC = status;
            this.TD = intent;
        }

        protected /* synthetic */ void m3084a(Object obj) {
            m3085c((C0244d) obj);
        }

        protected void m3085c(C0244d<PanoramaResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public Status getStatus() {
            return this.TC;
        }

        public Intent getViewerIntent() {
            return this.TD;
        }
    }

    /* renamed from: com.google.android.gms.internal.hx.a */
    final class C1497a extends C0391b<C0244d<C1411a>> implements C1411a {
        public final Status TC;
        public final Intent TD;
        final /* synthetic */ hx TE;
        public final int type;

        public C1497a(hx hxVar, C0244d<C1411a> c0244d, Status status, int i, Intent intent) {
            this.TE = hxVar;
            super(hxVar, c0244d);
            this.TC = status;
            this.type = i;
            this.TD = intent;
        }

        protected /* synthetic */ void m3426a(Object obj) {
            m3427c((C0244d) obj);
        }

        protected void m3427c(C0244d<C1411a> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public Status getStatus() {
            return this.TC;
        }

        public Intent getViewerIntent() {
            return this.TD;
        }
    }

    public hx(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, (String[]) null);
    }

    public void m3086a(C0244d<PanoramaResult> c0244d, Uri uri, boolean z) {
        m3088a(new C1380b(this, null, c0244d, z ? uri : null), uri, null, z);
    }

    protected void m3087a(fm fmVar, C1374e c1374e) throws RemoteException {
        fmVar.m948a(c1374e, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), new Bundle());
    }

    public void m3088a(C1380b c1380b, Uri uri, Bundle bundle, boolean z) {
        bT();
        if (z) {
            getContext().grantUriPermission(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, uri, 1);
        }
        try {
            ((hv) eM()).m1076a(c1380b, uri, bundle, z);
        } catch (RemoteException e) {
            c1380b.m3083a(8, null, 0, null);
        }
    }

    public hv aN(IBinder iBinder) {
        return C0919a.aM(iBinder);
    }

    protected String bg() {
        return "com.google.android.gms.panorama.service.START";
    }

    protected String bh() {
        return "com.google.android.gms.panorama.internal.IPanoramaService";
    }

    public /* synthetic */ IInterface m3089r(IBinder iBinder) {
        return aN(iBinder);
    }
}
