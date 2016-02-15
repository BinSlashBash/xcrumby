/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.internal.CloseContentsAndUpdateMetadataRequest;
import com.google.android.gms.drive.internal.CloseContentsRequest;
import com.google.android.gms.drive.internal.OnContentsResponse;
import com.google.android.gms.drive.internal.OnDownloadProgressResponse;
import com.google.android.gms.drive.internal.OpenContentsRequest;
import com.google.android.gms.drive.internal.al;
import com.google.android.gms.drive.internal.l;
import com.google.android.gms.drive.internal.m;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.r;
import com.google.android.gms.drive.internal.u;
import com.google.android.gms.drive.internal.v;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class o
extends r
implements DriveFile {
    public o(DriveId driveId) {
        super(driveId);
    }

    @Override
    public PendingResult<Status> commitAndCloseContents(GoogleApiClient googleApiClient, final Contents contents) {
        if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        }
        return googleApiClient.b(new b(){

            @Override
            protected void a(n n2) throws RemoteException {
                contents.close();
                n2.fE().a(new CloseContentsRequest(contents, true), (v)new al(this));
            }
        });
    }

    @Override
    public PendingResult<Status> commitAndCloseContents(GoogleApiClient googleApiClient, final Contents contents, final MetadataChangeSet metadataChangeSet) {
        if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        }
        return googleApiClient.b(new a(){

            @Override
            protected void a(n n2) throws RemoteException {
                contents.close();
                n2.fE().a(new CloseContentsAndUpdateMetadataRequest(o.this.Ew, metadataChangeSet.fD(), contents), (v)new al(this));
            }
        });
    }

    @Override
    public PendingResult<Status> discardContents(GoogleApiClient googleApiClient, Contents contents) {
        return Drive.DriveApi.discardContents(googleApiClient, contents);
    }

    @Override
    public PendingResult<DriveApi.ContentsResult> openContents(GoogleApiClient googleApiClient, final int n2, final DriveFile.DownloadProgressListener downloadProgressListener) {
        if (n2 != 268435456 && n2 != 536870912 && n2 != 805306368) {
            throw new IllegalArgumentException("Invalid mode provided.");
        }
        return googleApiClient.a(new d(){

            @Override
            protected void a(n n22) throws RemoteException {
                n22.fE().a(new OpenContentsRequest(o.this.getDriveId(), n2), (v)new c(this, downloadProgressListener));
            }
        });
    }

    private abstract class a
    extends m<Status> {
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

    private abstract class b
    extends m<Status> {
        private b() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

    private static class c
    extends com.google.android.gms.drive.internal.c {
        private final DriveFile.DownloadProgressListener Ft;
        private final a.d<DriveApi.ContentsResult> wH;

        public c(a.d<DriveApi.ContentsResult> d2, DriveFile.DownloadProgressListener downloadProgressListener) {
            this.wH = d2;
            this.Ft = downloadProgressListener;
        }

        @Override
        public void a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.wH.b(new l.a(Status.Bv, onContentsResponse.fI()));
        }

        @Override
        public void a(OnDownloadProgressResponse onDownloadProgressResponse) throws RemoteException {
            if (this.Ft != null) {
                this.Ft.onProgress(onDownloadProgressResponse.fJ(), onDownloadProgressResponse.fK());
            }
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new l.a(status, null));
        }
    }

    private abstract class d
    extends m<DriveApi.ContentsResult> {
        private d() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.o(status);
        }

        public DriveApi.ContentsResult o(Status status) {
            return new l.a(status, null);
        }
    }

}

