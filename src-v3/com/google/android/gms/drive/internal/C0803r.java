package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.DriveResource.MetadataResult;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.DriveEvent.Listener;
import com.google.android.gms.drive.internal.C0801l.C1307e;

/* renamed from: com.google.android.gms.drive.internal.r */
public class C0803r implements DriveResource {
    protected final DriveId Ew;

    /* renamed from: com.google.android.gms.drive.internal.r.e */
    private static class C1313e implements MetadataResult {
        private final Metadata Fy;
        private final Status wJ;

        public C1313e(Status status, Metadata metadata) {
            this.wJ = status;
            this.Fy = metadata;
        }

        public Metadata getMetadata() {
            return this.Fy;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.b */
    private static class C1479b extends C1303c {
        private final C0244d<MetadataBufferResult> wH;

        public C1479b(C0244d<MetadataBufferResult> c0244d) {
            this.wH = c0244d;
        }

        public void m3272a(OnListParentsResponse onListParentsResponse) throws RemoteException {
            this.wH.m132b(new C1307e(Status.Bv, new MetadataBuffer(onListParentsResponse.fP(), null), false));
        }

        public void m3273m(Status status) throws RemoteException {
            this.wH.m132b(new C1307e(status, null, false));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.d */
    private static class C1480d extends C1303c {
        private final C0244d<MetadataResult> wH;

        public C1480d(C0244d<MetadataResult> c0244d) {
            this.wH = c0244d;
        }

        public void m3274a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.wH.m132b(new C1313e(Status.Bv, new C1304j(onMetadataResponse.fQ())));
        }

        public void m3275m(Status status) throws RemoteException {
            this.wH.m132b(new C1313e(status, null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.a */
    private abstract class C1529a extends C1475m<MetadataResult> {
        final /* synthetic */ C0803r Fx;

        private C1529a(C0803r c0803r) {
            this.Fx = c0803r;
        }

        public /* synthetic */ Result m3482d(Status status) {
            return m3483s(status);
        }

        public MetadataResult m3483s(Status status) {
            return new C1313e(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.c */
    private abstract class C1530c extends C1475m<MetadataBufferResult> {
        final /* synthetic */ C0803r Fx;

        private C1530c(C0803r c0803r) {
            this.Fx = c0803r;
        }

        public /* synthetic */ Result m3484d(Status status) {
            return m3485p(status);
        }

        public MetadataBufferResult m3485p(Status status) {
            return new C1307e(status, null, false);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.f */
    private abstract class C1531f extends C1475m<MetadataResult> {
        final /* synthetic */ C0803r Fx;

        private C1531f(C0803r c0803r) {
            this.Fx = c0803r;
        }

        public /* synthetic */ Result m3486d(Status status) {
            return m3487s(status);
        }

        public MetadataResult m3487s(Status status) {
            return new C1313e(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.1 */
    class C15891 extends C1529a {
        final /* synthetic */ C0803r Fx;

        C15891(C0803r c0803r) {
            this.Fx = c0803r;
            super(null);
        }

        protected void m3661a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m304a(new GetMetadataRequest(this.Fx.Ew), new C1480d(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.2 */
    class C15902 extends C1530c {
        final /* synthetic */ C0803r Fx;

        C15902(C0803r c0803r) {
            this.Fx = c0803r;
            super(null);
        }

        protected void m3663a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m305a(new ListParentsRequest(this.Fx.Ew), new C1479b(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.r.3 */
    class C15913 extends C1531f {
        final /* synthetic */ MetadataChangeSet Fs;
        final /* synthetic */ C0803r Fx;

        C15913(C0803r c0803r, MetadataChangeSet metadataChangeSet) {
            this.Fx = c0803r;
            this.Fs = metadataChangeSet;
            super(null);
        }

        protected void m3665a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m310a(new UpdateMetadataRequest(this.Fx.Ew, this.Fs.fD()), new C1480d(this));
        }
    }

    protected C0803r(DriveId driveId) {
        this.Ew = driveId;
    }

    public PendingResult<Status> addChangeListener(GoogleApiClient apiClient, Listener<ChangeEvent> listener) {
        return ((C1308n) apiClient.m123a(Drive.wx)).m2665a(apiClient, this.Ew, 1, listener);
    }

    public DriveId getDriveId() {
        return this.Ew;
    }

    public PendingResult<MetadataResult> getMetadata(GoogleApiClient apiClient) {
        return apiClient.m124a(new C15891(this));
    }

    public PendingResult<MetadataBufferResult> listParents(GoogleApiClient apiClient) {
        return apiClient.m124a(new C15902(this));
    }

    public PendingResult<Status> removeChangeListener(GoogleApiClient apiClient, Listener<ChangeEvent> listener) {
        return ((C1308n) apiClient.m123a(Drive.wx)).m2668b(apiClient, this.Ew, 1, listener);
    }

    public PendingResult<MetadataResult> updateMetadata(GoogleApiClient apiClient, MetadataChangeSet changeSet) {
        if (changeSet != null) {
            return apiClient.m125b(new C15913(this, changeSet));
        }
        throw new IllegalArgumentException("ChangeSet must be provided.");
    }
}
