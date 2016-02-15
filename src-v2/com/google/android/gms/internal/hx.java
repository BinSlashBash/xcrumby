/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  com.google.android.gms.internal.ff.b
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.hu;
import com.google.android.gms.internal.hv;
import com.google.android.gms.panorama.PanoramaApi;

public class hx
extends ff<hv> {
    public hx(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, (String[])null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(a.d<PanoramaApi.PanoramaResult> d2, Uri uri, boolean bl2) {
        Uri uri2 = bl2 ? uri : null;
        this.a(new b(null, d2, uri2), uri, null, bl2);
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        Bundle bundle = new Bundle();
        fm2.a(e2, 4452000, this.getContext().getPackageName(), bundle);
    }

    public void a(b b2, Uri uri, Bundle bundle, boolean bl2) {
        this.bT();
        if (bl2) {
            this.getContext().grantUriPermission("com.google.android.gms", uri, 1);
        }
        try {
            ((hv)this.eM()).a(b2, uri, bundle, bl2);
            return;
        }
        catch (RemoteException var2_3) {
            b2.a(8, null, 0, null);
            return;
        }
    }

    public hv aN(IBinder iBinder) {
        return hv.a.aM(iBinder);
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.panorama.service.START";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.panorama.internal.IPanoramaService";
    }

    @Override
    public /* synthetic */ IInterface r(IBinder iBinder) {
        return this.aN(iBinder);
    }

    final class a
    extends com.google.android.gms.internal.ff.b<a.d<PanoramaApi.a>>
    implements PanoramaApi.a {
        public final Status TC;
        public final Intent TD;
        public final int type;

        public a(a.d<PanoramaApi.a> d2, Status status, int n2, Intent intent) {
            super(d2);
            this.TC = status;
            this.type = n2;
            this.TD = intent;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<PanoramaApi.a> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public Status getStatus() {
            return this.TC;
        }

        @Override
        public Intent getViewerIntent() {
            return this.TD;
        }
    }

    final class b
    extends hu.a {
        private final a.d<PanoramaApi.a> TF;
        private final a.d<PanoramaApi.PanoramaResult> TG;
        private final Uri TH;

        public b(a.d<PanoramaApi.a> d2, a.d<PanoramaApi.PanoramaResult> d3, Uri uri) {
            this.TF = d2;
            this.TG = d3;
            this.TH = uri;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(int n2, Bundle object, int n3, Intent intent) {
            if (this.TH != null) {
                hx.this.getContext().revokeUriPermission(this.TH, 1);
            }
            object = object != null ? (PendingIntent)object.getParcelable("pendingIntent") : null;
            object = new Status(n2, null, (PendingIntent)object);
            if (this.TG != null) {
                hx.this.a((ff.b)((Object)new c(this.TG, (Status)object, intent)));
                return;
            } else {
                if (this.TF == null) return;
                {
                    hx.this.a((ff.b)((Object)new a(this.TF, (Status)object, n3, intent)));
                    return;
                }
            }
        }
    }

    final class c
    extends com.google.android.gms.internal.ff.b<a.d<PanoramaApi.PanoramaResult>>
    implements PanoramaApi.PanoramaResult {
        private final Status TC;
        private final Intent TD;

        public c(a.d<PanoramaApi.PanoramaResult> d2, Status status, Intent intent) {
            super(d2);
            this.TC = status;
            this.TD = intent;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<PanoramaApi.PanoramaResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public Status getStatus() {
            return this.TC;
        }

        @Override
        public Intent getViewerIntent() {
            return this.TD;
        }
    }

}

