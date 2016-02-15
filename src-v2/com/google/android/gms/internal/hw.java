/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.hx;
import com.google.android.gms.panorama.Panorama;
import com.google.android.gms.panorama.PanoramaApi;

public class hw
implements PanoramaApi {
    @Override
    public PendingResult<PanoramaApi.PanoramaResult> loadPanoramaInfo(GoogleApiClient googleApiClient, final Uri uri) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(hx hx2) {
                hx2.a(this, uri, false);
            }
        });
    }

    @Override
    public PendingResult<PanoramaApi.PanoramaResult> loadPanoramaInfoAndGrantAccess(GoogleApiClient googleApiClient, final Uri uri) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(hx hx2) {
                hx2.a(this, uri, true);
            }
        });
    }

    private static abstract class a
    extends a.b<PanoramaApi.PanoramaResult, hx> {
        public a() {
            super(Panorama.wx);
        }

        public PanoramaApi.PanoramaResult X(final Status status) {
            return new PanoramaApi.PanoramaResult(){

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public Intent getViewerIntent() {
                    return null;
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.X(status);
        }

    }

}

