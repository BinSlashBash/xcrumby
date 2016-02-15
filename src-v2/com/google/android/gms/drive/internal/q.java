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
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.internal.CreateFileRequest;
import com.google.android.gms.drive.internal.CreateFolderRequest;
import com.google.android.gms.drive.internal.OnDriveIdResponse;
import com.google.android.gms.drive.internal.l;
import com.google.android.gms.drive.internal.m;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.o;
import com.google.android.gms.drive.internal.r;
import com.google.android.gms.drive.internal.u;
import com.google.android.gms.drive.internal.v;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;

public class q
extends r
implements DriveFolder {
    public q(DriveId driveId) {
        super(driveId);
    }

    @Override
    public PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, final MetadataChangeSet metadataChangeSet, final Contents contents) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        }
        if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        }
        if ("application/vnd.google-apps.folder".equals(metadataChangeSet.getMimeType())) {
            throw new IllegalArgumentException("May not create folders (mimetype: application/vnd.google-apps.folder) using this method. Use DriveFolder.createFolder() instead.");
        }
        return googleApiClient.b(new m<DriveFolder.DriveFileResult>(){

            @Override
            protected void a(n n2) throws RemoteException {
                contents.close();
                n2.fE().a(new CreateFileRequest(q.this.getDriveId(), metadataChangeSet.fD(), contents), (v)new a(this));
            }

            @Override
            public /* synthetic */ Result d(Status status) {
                return this.q(status);
            }

            public DriveFolder.DriveFileResult q(Status status) {
                return new d(status, null);
            }
        });
    }

    @Override
    public PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient googleApiClient, final MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        }
        if (metadataChangeSet.getMimeType() != null && !metadataChangeSet.getMimeType().equals("application/vnd.google-apps.folder")) {
            throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
        }
        return googleApiClient.b(new c(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new CreateFolderRequest(q.this.getDriveId(), metadataChangeSet.fD()), (v)new b(this));
            }
        });
    }

    @Override
    public PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient googleApiClient) {
        return this.queryChildren(googleApiClient, null);
    }

    @Override
    public PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient googleApiClient, Query query) {
        Query.Builder builder = new Query.Builder().addFilter(Filters.in(SearchableField.PARENTS, this.getDriveId()));
        if (query != null) {
            if (query.getFilter() != null) {
                builder.addFilter(query.getFilter());
            }
            builder.setPageToken(query.getPageToken());
            builder.a(query.fV());
        }
        return new l().query(googleApiClient, builder.build());
    }

    private static class a
    extends com.google.android.gms.drive.internal.c {
        private final a.d<DriveFolder.DriveFileResult> wH;

        public a(a.d<DriveFolder.DriveFileResult> d2) {
            this.wH = d2;
        }

        @Override
        public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.wH.b(new d(Status.Bv, new o(onDriveIdResponse.getDriveId())));
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new d(status, null));
        }
    }

    private static class b
    extends com.google.android.gms.drive.internal.c {
        private final a.d<DriveFolder.DriveFolderResult> wH;

        public b(a.d<DriveFolder.DriveFolderResult> d2) {
            this.wH = d2;
        }

        @Override
        public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.wH.b(new e(Status.Bv, new q(onDriveIdResponse.getDriveId())));
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new e(status, null));
        }
    }

    private abstract class c
    extends m<DriveFolder.DriveFolderResult> {
        private c() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.r(status);
        }

        public DriveFolder.DriveFolderResult r(Status status) {
            return new e(status, null);
        }
    }

    private static class d
    implements DriveFolder.DriveFileResult {
        private final DriveFile Fv;
        private final Status wJ;

        public d(Status status, DriveFile driveFile) {
            this.wJ = status;
            this.Fv = driveFile;
        }

        @Override
        public DriveFile getDriveFile() {
            return this.Fv;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    private static class e
    implements DriveFolder.DriveFolderResult {
        private final DriveFolder Fw;
        private final Status wJ;

        public e(Status status, DriveFolder driveFolder) {
            this.wJ = status;
            this.Fw = driveFolder;
        }

        @Override
        public DriveFolder getDriveFolder() {
            return this.Fw;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

}

