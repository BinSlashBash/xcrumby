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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.internal.GetMetadataRequest;
import com.google.android.gms.drive.internal.ListParentsRequest;
import com.google.android.gms.drive.internal.OnListParentsResponse;
import com.google.android.gms.drive.internal.OnMetadataResponse;
import com.google.android.gms.drive.internal.UpdateMetadataRequest;
import com.google.android.gms.drive.internal.j;
import com.google.android.gms.drive.internal.l;
import com.google.android.gms.drive.internal.m;
import com.google.android.gms.drive.internal.n;
import com.google.android.gms.drive.internal.u;
import com.google.android.gms.drive.internal.v;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class r
implements DriveResource {
    protected final DriveId Ew;

    protected r(DriveId driveId) {
        this.Ew = driveId;
    }

    @Override
    public PendingResult<Status> addChangeListener(GoogleApiClient googleApiClient, DriveEvent.Listener<ChangeEvent> listener) {
        return ((n)((Object)googleApiClient.a(Drive.wx))).a(googleApiClient, this.Ew, 1, listener);
    }

    @Override
    public DriveId getDriveId() {
        return this.Ew;
    }

    @Override
    public PendingResult<DriveResource.MetadataResult> getMetadata(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new GetMetadataRequest(r.this.Ew), (v)new d(this));
            }
        });
    }

    @Override
    public PendingResult<DriveApi.MetadataBufferResult> listParents(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new c(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new ListParentsRequest(r.this.Ew), (v)new b(this));
            }
        });
    }

    @Override
    public PendingResult<Status> removeChangeListener(GoogleApiClient googleApiClient, DriveEvent.Listener<ChangeEvent> listener) {
        return ((n)((Object)googleApiClient.a(Drive.wx))).b(googleApiClient, this.Ew, 1, listener);
    }

    @Override
    public PendingResult<DriveResource.MetadataResult> updateMetadata(GoogleApiClient googleApiClient, final MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("ChangeSet must be provided.");
        }
        return googleApiClient.b(new f(){

            @Override
            protected void a(n n2) throws RemoteException {
                n2.fE().a(new UpdateMetadataRequest(r.this.Ew, metadataChangeSet.fD()), (v)new d(this));
            }
        });
    }

    private abstract class a
    extends m<DriveResource.MetadataResult> {
        private a() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.s(status);
        }

        public DriveResource.MetadataResult s(Status status) {
            return new e(status, null);
        }
    }

    private static class b
    extends com.google.android.gms.drive.internal.c {
        private final a.d<DriveApi.MetadataBufferResult> wH;

        public b(a.d<DriveApi.MetadataBufferResult> d2) {
            this.wH = d2;
        }

        @Override
        public void a(OnListParentsResponse object) throws RemoteException {
            object = new MetadataBuffer(object.fP(), null);
            this.wH.b(new l.e(Status.Bv, (MetadataBuffer)object, false));
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new l.e(status, null, false));
        }
    }

    private abstract class c
    extends m<DriveApi.MetadataBufferResult> {
        private c() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.p(status);
        }

        public DriveApi.MetadataBufferResult p(Status status) {
            return new l.e(status, null, false);
        }
    }

    private static class d
    extends com.google.android.gms.drive.internal.c {
        private final a.d<DriveResource.MetadataResult> wH;

        public d(a.d<DriveResource.MetadataResult> d2) {
            this.wH = d2;
        }

        @Override
        public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.wH.b(new e(Status.Bv, new j(onMetadataResponse.fQ())));
        }

        @Override
        public void m(Status status) throws RemoteException {
            this.wH.b(new e(status, null));
        }
    }

    private static class e
    implements DriveResource.MetadataResult {
        private final Metadata Fy;
        private final Status wJ;

        public e(Status status, Metadata metadata) {
            this.wJ = status;
            this.Fy = metadata;
        }

        @Override
        public Metadata getMetadata() {
            return this.Fy;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    private abstract class f
    extends m<DriveResource.MetadataResult> {
        private f() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.s(status);
        }

        public DriveResource.MetadataResult s(Status status) {
            return new e(status, null);
        }
    }

}

