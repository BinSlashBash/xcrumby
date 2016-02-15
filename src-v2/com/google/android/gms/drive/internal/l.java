/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.google.android.gms.drive.internal;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.internal.CloseContentsRequest;
import com.google.android.gms.drive.internal.CreateContentsRequest;
import com.google.android.gms.drive.internal.GetMetadataRequest;
import com.google.android.gms.drive.internal.OnContentsResponse;
import com.google.android.gms.drive.internal.OnListEntriesResponse;
import com.google.android.gms.drive.internal.OnMetadataResponse;
import com.google.android.gms.drive.internal.QueryRequest;
import com.google.android.gms.drive.internal.al;
import com.google.android.gms.drive.internal.m;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.o;
import com.google.android.gms.drive.internal.q;
import com.google.android.gms.drive.internal.u;
import com.google.android.gms.drive.internal.v;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Query;

public class l
implements DriveApi {
    @Override
    public PendingResult<Status> discardContents(GoogleApiClient googleApiClient, final Contents contents) {
        return googleApiClient.b(new j(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new CloseContentsRequest(contents, false), (v)new al(this));
            }
        });
    }

    @Override
    public PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.a(new d(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new GetMetadataRequest(DriveId.aw(string2)), (v)new b(this));
            }
        });
    }

    @Override
    public DriveFolder getAppFolder(GoogleApiClient object) {
        if (!object.isConnected()) {
            throw new IllegalStateException("Client must be connected");
        }
        if ((object = ((n)((Object)object.a(Drive.wx))).fG()) != null) {
            return new q((DriveId)object);
        }
        return null;
    }

    @Override
    public DriveFile getFile(GoogleApiClient googleApiClient, DriveId driveId) {
        if (driveId == null) {
            throw new IllegalArgumentException("Id must be provided.");
        }
        if (!googleApiClient.isConnected()) {
            throw new IllegalStateException("Client must be connected");
        }
        return new o(driveId);
    }

    @Override
    public DriveFolder getFolder(GoogleApiClient googleApiClient, DriveId driveId) {
        if (driveId == null) {
            throw new IllegalArgumentException("Id must be provided.");
        }
        if (!googleApiClient.isConnected()) {
            throw new IllegalStateException("Client must be connected");
        }
        return new q(driveId);
    }

    @Override
    public DriveFolder getRootFolder(GoogleApiClient googleApiClient) {
        if (!googleApiClient.isConnected()) {
            throw new IllegalStateException("Client must be connected");
        }
        return new q(((n)((Object)googleApiClient.a(Drive.wx))).fF());
    }

    @Override
    public PendingResult<DriveApi.ContentsResult> newContents(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new g(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new CreateContentsRequest(), (v)new f(this));
            }
        });
    }

    @Override
    public CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }

    @Override
    public OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    @Override
    public PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient googleApiClient, final Query query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must be provided.");
        }
        return googleApiClient.a(new i(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new QueryRequest(query), (v)new h(this));
            }
        });
    }

    @Override
    public PendingResult<Status> requestSync(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new l(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new al(this));
            }
        });
    }

    static class a
    implements DriveApi.ContentsResult {
        private final Contents EA;
        private final Status wJ;

        public a(Status status, Contents contents) {
            this.wJ = status;
            this.EA = contents;
        }

        @Override
        public Contents getContents() {
            return this.EA;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    private static class b
    extends com.google.android.gms.drive.internal.c {
        private final a.d<DriveApi.DriveIdResult> wH;

        public b(a.d<DriveApi.DriveIdResult> d2) {
            this.wH = d2;
        }

        @Override
        public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.wH.b(new c(Status.Bv, new com.google.android.gms.drive.internal.j(onMetadataResponse.fQ()).getDriveId()));
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new c(status, null));
        }
    }

    static class c
    implements DriveApi.DriveIdResult {
        private final DriveId Ew;
        private final Status wJ;

        public c(Status status, DriveId driveId) {
            this.wJ = status;
            this.Ew = driveId;
        }

        @Override
        public DriveId getDriveId() {
            return this.Ew;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    abstract class d
    extends m<DriveApi.DriveIdResult> {
        d() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.n(status);
        }

        public DriveApi.DriveIdResult n(Status status) {
            return new c(status, null);
        }
    }

    static class e
    implements DriveApi.MetadataBufferResult {
        private final MetadataBuffer Ff;
        private final boolean Fg;
        private final Status wJ;

        public e(Status status, MetadataBuffer metadataBuffer, boolean bl2) {
            this.wJ = status;
            this.Ff = metadataBuffer;
            this.Fg = bl2;
        }

        @Override
        public MetadataBuffer getMetadataBuffer() {
            return this.Ff;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    private static class f
    extends com.google.android.gms.drive.internal.c {
        private final a.d<DriveApi.ContentsResult> wH;

        public f(a.d<DriveApi.ContentsResult> d2) {
            this.wH = d2;
        }

        @Override
        public void a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.wH.b(new a(Status.Bv, onContentsResponse.fI()));
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new a(status, null));
        }
    }

    abstract class g
    extends m<DriveApi.ContentsResult> {
        g() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.o(status);
        }

        public DriveApi.ContentsResult o(Status status) {
            return new a(status, null);
        }
    }

    static class h
    extends com.google.android.gms.drive.internal.c {
        private final a.d<DriveApi.MetadataBufferResult> wH;

        public h(a.d<DriveApi.MetadataBufferResult> d2) {
            this.wH = d2;
        }

        @Override
        public void a(OnListEntriesResponse onListEntriesResponse) throws RemoteException {
            MetadataBuffer metadataBuffer = new MetadataBuffer(onListEntriesResponse.fN(), null);
            this.wH.b(new e(Status.Bv, metadataBuffer, onListEntriesResponse.fO()));
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new e(status, null, false));
        }
    }

    abstract class i
    extends m<DriveApi.MetadataBufferResult> {
        i() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.p(status);
        }

        public DriveApi.MetadataBufferResult p(Status status) {
            return new e(status, null, false);
        }
    }

    static abstract class j
    extends m<Status> {
        j() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

    static class k
    extends j {
        k(GoogleApiClient googleApiClient, Status status) {
            this.a(new a.c(((n)((Object)googleApiClient.a(Drive.wx))).getLooper()));
            this.a(status);
        }

        @Override
        protected void a(n n2) {
        }
    }

    abstract class l
    extends m<Status> {
        l() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

}

