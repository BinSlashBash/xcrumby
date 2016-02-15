package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveFolder.DriveFileResult;
import com.google.android.gms.drive.DriveFolder.DriveFolderResult;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.Query.Builder;
import com.google.android.gms.drive.query.SearchableField;

/* renamed from: com.google.android.gms.drive.internal.q */
public class C1312q extends C0803r implements DriveFolder {

    /* renamed from: com.google.android.gms.drive.internal.q.d */
    private static class C1310d implements DriveFileResult {
        private final DriveFile Fv;
        private final Status wJ;

        public C1310d(Status status, DriveFile driveFile) {
            this.wJ = status;
            this.Fv = driveFile;
        }

        public DriveFile getDriveFile() {
            return this.Fv;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.q.e */
    private static class C1311e implements DriveFolderResult {
        private final DriveFolder Fw;
        private final Status wJ;

        public C1311e(Status status, DriveFolder driveFolder) {
            this.wJ = status;
            this.Fw = driveFolder;
        }

        public DriveFolder getDriveFolder() {
            return this.Fw;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.q.a */
    private static class C1477a extends C1303c {
        private final C0244d<DriveFileResult> wH;

        public C1477a(C0244d<DriveFileResult> c0244d) {
            this.wH = c0244d;
        }

        public void m3268a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.wH.m132b(new C1310d(Status.Bv, new C1309o(onDriveIdResponse.getDriveId())));
        }

        public void m3269m(Status status) throws RemoteException {
            this.wH.m132b(new C1310d(status, null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.q.b */
    private static class C1478b extends C1303c {
        private final C0244d<DriveFolderResult> wH;

        public C1478b(C0244d<DriveFolderResult> c0244d) {
            this.wH = c0244d;
        }

        public void m3270a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.wH.m132b(new C1311e(Status.Bv, new C1312q(onDriveIdResponse.getDriveId())));
        }

        public void m3271m(Status status) throws RemoteException {
            this.wH.m132b(new C1311e(status, null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.q.1 */
    class C15271 extends C1475m<DriveFileResult> {
        final /* synthetic */ Contents Fd;
        final /* synthetic */ MetadataChangeSet Fs;
        final /* synthetic */ C1312q Fu;

        C15271(C1312q c1312q, Contents contents, MetadataChangeSet metadataChangeSet) {
            this.Fu = c1312q;
            this.Fd = contents;
            this.Fs = metadataChangeSet;
        }

        protected void m3477a(C1308n c1308n) throws RemoteException {
            this.Fd.close();
            c1308n.fE().m301a(new CreateFileRequest(this.Fu.getDriveId(), this.Fs.fD(), this.Fd), new C1477a(this));
        }

        public /* synthetic */ Result m3478d(Status status) {
            return m3479q(status);
        }

        public DriveFileResult m3479q(Status status) {
            return new C1310d(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.q.c */
    private abstract class C1528c extends C1475m<DriveFolderResult> {
        final /* synthetic */ C1312q Fu;

        private C1528c(C1312q c1312q) {
            this.Fu = c1312q;
        }

        public /* synthetic */ Result m3480d(Status status) {
            return m3481r(status);
        }

        public DriveFolderResult m3481r(Status status) {
            return new C1311e(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.q.2 */
    class C15882 extends C1528c {
        final /* synthetic */ MetadataChangeSet Fs;
        final /* synthetic */ C1312q Fu;

        C15882(C1312q c1312q, MetadataChangeSet metadataChangeSet) {
            this.Fu = c1312q;
            this.Fs = metadataChangeSet;
            super(null);
        }

        protected void m3659a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m302a(new CreateFolderRequest(this.Fu.getDriveId(), this.Fs.fD()), new C1478b(this));
        }
    }

    public C1312q(DriveId driveId) {
        super(driveId);
    }

    public PendingResult<DriveFileResult> createFile(GoogleApiClient apiClient, MetadataChangeSet changeSet, Contents contents) {
        if (changeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        } else if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        } else if (!DriveFolder.MIME_TYPE.equals(changeSet.getMimeType())) {
            return apiClient.m125b(new C15271(this, contents, changeSet));
        } else {
            throw new IllegalArgumentException("May not create folders (mimetype: application/vnd.google-apps.folder) using this method. Use DriveFolder.createFolder() instead.");
        }
    }

    public PendingResult<DriveFolderResult> createFolder(GoogleApiClient apiClient, MetadataChangeSet changeSet) {
        if (changeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        } else if (changeSet.getMimeType() == null || changeSet.getMimeType().equals(DriveFolder.MIME_TYPE)) {
            return apiClient.m125b(new C15882(this, changeSet));
        } else {
            throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
        }
    }

    public PendingResult<MetadataBufferResult> listChildren(GoogleApiClient apiClient) {
        return queryChildren(apiClient, null);
    }

    public PendingResult<MetadataBufferResult> queryChildren(GoogleApiClient apiClient, Query query) {
        Builder addFilter = new Builder().addFilter(Filters.in(SearchableField.PARENTS, getDriveId()));
        if (query != null) {
            if (query.getFilter() != null) {
                addFilter.addFilter(query.getFilter());
            }
            addFilter.setPageToken(query.getPageToken());
            addFilter.m332a(query.fV());
        }
        return new C0801l().query(apiClient, addFilter.build());
    }
}
