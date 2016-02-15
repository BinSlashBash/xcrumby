package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.C0245a.C0243c;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveApi.DriveIdResult;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;

/* renamed from: com.google.android.gms.drive.internal.l */
public class C0801l implements DriveApi {

    /* renamed from: com.google.android.gms.drive.internal.l.a */
    static class C1305a implements ContentsResult {
        private final Contents EA;
        private final Status wJ;

        public C1305a(Status status, Contents contents) {
            this.wJ = status;
            this.EA = contents;
        }

        public Contents getContents() {
            return this.EA;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.c */
    static class C1306c implements DriveIdResult {
        private final DriveId Ew;
        private final Status wJ;

        public C1306c(Status status, DriveId driveId) {
            this.wJ = status;
            this.Ew = driveId;
        }

        public DriveId getDriveId() {
            return this.Ew;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.e */
    static class C1307e implements MetadataBufferResult {
        private final MetadataBuffer Ff;
        private final boolean Fg;
        private final Status wJ;

        public C1307e(Status status, MetadataBuffer metadataBuffer, boolean z) {
            this.wJ = status;
            this.Ff = metadataBuffer;
            this.Fg = z;
        }

        public MetadataBuffer getMetadataBuffer() {
            return this.Ff;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.b */
    private static class C1472b extends C1303c {
        private final C0244d<DriveIdResult> wH;

        public C1472b(C0244d<DriveIdResult> c0244d) {
            this.wH = c0244d;
        }

        public void m3259a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.wH.m132b(new C1306c(Status.Bv, new C1304j(onMetadataResponse.fQ()).getDriveId()));
        }

        public void m3260m(Status status) throws RemoteException {
            this.wH.m132b(new C1306c(status, null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.f */
    private static class C1473f extends C1303c {
        private final C0244d<ContentsResult> wH;

        public C1473f(C0244d<ContentsResult> c0244d) {
            this.wH = c0244d;
        }

        public void m3261a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.wH.m132b(new C1305a(Status.Bv, onContentsResponse.fI()));
        }

        public void m3262m(Status status) throws RemoteException {
            this.wH.m132b(new C1305a(status, null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.h */
    static class C1474h extends C1303c {
        private final C0244d<MetadataBufferResult> wH;

        public C1474h(C0244d<MetadataBufferResult> c0244d) {
            this.wH = c0244d;
        }

        public void m3263a(OnListEntriesResponse onListEntriesResponse) throws RemoteException {
            this.wH.m132b(new C1307e(Status.Bv, new MetadataBuffer(onListEntriesResponse.fN(), null), onListEntriesResponse.fO()));
        }

        public void m3264m(Status status) throws RemoteException {
            this.wH.m132b(new C1307e(status, null, false));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.d */
    abstract class C1519d extends C1475m<DriveIdResult> {
        final /* synthetic */ C0801l Fc;

        C1519d(C0801l c0801l) {
            this.Fc = c0801l;
        }

        public /* synthetic */ Result m3460d(Status status) {
            return m3461n(status);
        }

        public DriveIdResult m3461n(Status status) {
            return new C1306c(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.g */
    abstract class C1520g extends C1475m<ContentsResult> {
        final /* synthetic */ C0801l Fc;

        C1520g(C0801l c0801l) {
            this.Fc = c0801l;
        }

        public /* synthetic */ Result m3462d(Status status) {
            return m3463o(status);
        }

        public ContentsResult m3463o(Status status) {
            return new C1305a(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.i */
    abstract class C1521i extends C1475m<MetadataBufferResult> {
        final /* synthetic */ C0801l Fc;

        C1521i(C0801l c0801l) {
            this.Fc = c0801l;
        }

        public /* synthetic */ Result m3464d(Status status) {
            return m3465p(status);
        }

        public MetadataBufferResult m3465p(Status status) {
            return new C1307e(status, null, false);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.j */
    static abstract class C1522j extends C1475m<Status> {
        C1522j() {
        }

        public /* synthetic */ Result m3466d(Status status) {
            return m3467f(status);
        }

        public Status m3467f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.l */
    abstract class C1523l extends C1475m<Status> {
        final /* synthetic */ C0801l Fc;

        C1523l(C0801l c0801l) {
            this.Fc = c0801l;
        }

        public /* synthetic */ Result m3468d(Status status) {
            return m3469f(status);
        }

        public Status m3469f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.1 */
    class C15771 extends C1521i {
        final /* synthetic */ Query Fb;
        final /* synthetic */ C0801l Fc;

        C15771(C0801l c0801l, Query query) {
            this.Fc = c0801l;
            this.Fb = query;
            super(c0801l);
        }

        protected void m3637a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m307a(new QueryRequest(this.Fb), new C1474h(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.2 */
    class C15782 extends C1520g {
        final /* synthetic */ C0801l Fc;

        C15782(C0801l c0801l) {
            this.Fc = c0801l;
            super(c0801l);
        }

        protected void m3639a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m300a(new CreateContentsRequest(), new C1473f(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.3 */
    class C15793 extends C1522j {
        final /* synthetic */ C0801l Fc;
        final /* synthetic */ Contents Fd;

        C15793(C0801l c0801l, Contents contents) {
            this.Fc = c0801l;
            this.Fd = contents;
        }

        protected void m3641a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m299a(new CloseContentsRequest(this.Fd, false), new al(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.4 */
    class C15804 extends C1519d {
        final /* synthetic */ C0801l Fc;
        final /* synthetic */ String Fe;

        C15804(C0801l c0801l, String str) {
            this.Fc = c0801l;
            this.Fe = str;
            super(c0801l);
        }

        protected void m3643a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m304a(new GetMetadataRequest(DriveId.aw(this.Fe)), new C1472b(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.5 */
    class C15815 extends C1523l {
        final /* synthetic */ C0801l Fc;

        C15815(C0801l c0801l) {
            this.Fc = c0801l;
            super(c0801l);
        }

        protected void m3645a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m311a(new al(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.l.k */
    static class C1582k extends C1522j {
        C1582k(GoogleApiClient googleApiClient, Status status) {
            m1660a(new C0243c(((C1308n) googleApiClient.m123a(Drive.wx)).getLooper()));
            m1659a((Result) status);
        }

        protected void m3647a(C1308n c1308n) {
        }
    }

    public PendingResult<Status> discardContents(GoogleApiClient apiClient, Contents contents) {
        return apiClient.m125b(new C15793(this, contents));
    }

    public PendingResult<DriveIdResult> fetchDriveId(GoogleApiClient apiClient, String resourceId) {
        return apiClient.m124a(new C15804(this, resourceId));
    }

    public DriveFolder getAppFolder(GoogleApiClient apiClient) {
        if (apiClient.isConnected()) {
            DriveId fG = ((C1308n) apiClient.m123a(Drive.wx)).fG();
            return fG != null ? new C1312q(fG) : null;
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFile getFile(GoogleApiClient apiClient, DriveId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (apiClient.isConnected()) {
            return new C1309o(id);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFolder getFolder(GoogleApiClient apiClient, DriveId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (apiClient.isConnected()) {
            return new C1312q(id);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFolder getRootFolder(GoogleApiClient apiClient) {
        if (apiClient.isConnected()) {
            return new C1312q(((C1308n) apiClient.m123a(Drive.wx)).fF());
        }
        throw new IllegalStateException("Client must be connected");
    }

    public PendingResult<ContentsResult> newContents(GoogleApiClient apiClient) {
        return apiClient.m124a(new C15782(this));
    }

    public CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }

    public OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    public PendingResult<MetadataBufferResult> query(GoogleApiClient apiClient, Query query) {
        if (query != null) {
            return apiClient.m124a(new C15771(this, query));
        }
        throw new IllegalArgumentException("Query must be provided.");
    }

    public PendingResult<Status> requestSync(GoogleApiClient client) {
        return client.m125b(new C15815(this));
    }
}
