package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFile.DownloadProgressListener;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.internal.C0801l.C1305a;

/* renamed from: com.google.android.gms.drive.internal.o */
public class C1309o extends C0803r implements DriveFile {

    /* renamed from: com.google.android.gms.drive.internal.o.c */
    private static class C1476c extends C1303c {
        private final DownloadProgressListener Ft;
        private final C0244d<ContentsResult> wH;

        public C1476c(C0244d<ContentsResult> c0244d, DownloadProgressListener downloadProgressListener) {
            this.wH = c0244d;
            this.Ft = downloadProgressListener;
        }

        public void m3265a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.wH.m132b(new C1305a(Status.Bv, onContentsResponse.fI()));
        }

        public void m3266a(OnDownloadProgressResponse onDownloadProgressResponse) throws RemoteException {
            if (this.Ft != null) {
                this.Ft.onProgress(onDownloadProgressResponse.fJ(), onDownloadProgressResponse.fK());
            }
        }

        public void m3267m(Status status) throws RemoteException {
            this.wH.m132b(new C1305a(status, null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.o.a */
    private abstract class C1524a extends C1475m<Status> {
        final /* synthetic */ C1309o Fr;

        private C1524a(C1309o c1309o) {
            this.Fr = c1309o;
        }

        public /* synthetic */ Result m3470d(Status status) {
            return m3471f(status);
        }

        public Status m3471f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.o.b */
    private abstract class C1525b extends C1475m<Status> {
        final /* synthetic */ C1309o Fr;

        private C1525b(C1309o c1309o) {
            this.Fr = c1309o;
        }

        public /* synthetic */ Result m3472d(Status status) {
            return m3473f(status);
        }

        public Status m3473f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.o.d */
    private abstract class C1526d extends C1475m<ContentsResult> {
        final /* synthetic */ C1309o Fr;

        private C1526d(C1309o c1309o) {
            this.Fr = c1309o;
        }

        public /* synthetic */ Result m3474d(Status status) {
            return m3475o(status);
        }

        public ContentsResult m3475o(Status status) {
            return new C1305a(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.o.1 */
    class C15851 extends C1526d {
        final /* synthetic */ int Fp;
        final /* synthetic */ DownloadProgressListener Fq;
        final /* synthetic */ C1309o Fr;

        C15851(C1309o c1309o, int i, DownloadProgressListener downloadProgressListener) {
            this.Fr = c1309o;
            this.Fp = i;
            this.Fq = downloadProgressListener;
            super(null);
        }

        protected void m3653a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m306a(new OpenContentsRequest(this.Fr.getDriveId(), this.Fp), new C1476c(this, this.Fq));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.o.2 */
    class C15862 extends C1525b {
        final /* synthetic */ Contents Fd;
        final /* synthetic */ C1309o Fr;

        C15862(C1309o c1309o, Contents contents) {
            this.Fr = c1309o;
            this.Fd = contents;
            super(null);
        }

        protected void m3655a(C1308n c1308n) throws RemoteException {
            this.Fd.close();
            c1308n.fE().m299a(new CloseContentsRequest(this.Fd, true), new al(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.o.3 */
    class C15873 extends C1524a {
        final /* synthetic */ Contents Fd;
        final /* synthetic */ C1309o Fr;
        final /* synthetic */ MetadataChangeSet Fs;

        C15873(C1309o c1309o, Contents contents, MetadataChangeSet metadataChangeSet) {
            this.Fr = c1309o;
            this.Fd = contents;
            this.Fs = metadataChangeSet;
            super(null);
        }

        protected void m3657a(C1308n c1308n) throws RemoteException {
            this.Fd.close();
            c1308n.fE().m298a(new CloseContentsAndUpdateMetadataRequest(this.Fr.Ew, this.Fs.fD(), this.Fd), new al(this));
        }
    }

    public C1309o(DriveId driveId) {
        super(driveId);
    }

    public PendingResult<Status> commitAndCloseContents(GoogleApiClient apiClient, Contents contents) {
        if (contents != null) {
            return apiClient.m125b(new C15862(this, contents));
        }
        throw new IllegalArgumentException("Contents must be provided.");
    }

    public PendingResult<Status> commitAndCloseContents(GoogleApiClient apiClient, Contents contents, MetadataChangeSet changeSet) {
        if (contents != null) {
            return apiClient.m125b(new C15873(this, contents, changeSet));
        }
        throw new IllegalArgumentException("Contents must be provided.");
    }

    public PendingResult<Status> discardContents(GoogleApiClient apiClient, Contents contents) {
        return Drive.DriveApi.discardContents(apiClient, contents);
    }

    public PendingResult<ContentsResult> openContents(GoogleApiClient apiClient, int mode, DownloadProgressListener listener) {
        if (mode == DriveFile.MODE_READ_ONLY || mode == DriveFile.MODE_WRITE_ONLY || mode == DriveFile.MODE_READ_WRITE) {
            return apiClient.m124a(new C15851(this, mode, listener));
        }
        throw new IllegalArgumentException("Invalid mode provided.");
    }
}
