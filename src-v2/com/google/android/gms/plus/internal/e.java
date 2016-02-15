/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  com.google.android.gms.internal.ff.b
 *  com.google.android.gms.internal.ff.d
 */
package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.gg;
import com.google.android.gms.internal.ie;
import com.google.android.gms.internal.ih;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.internal.d;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class e
extends ff<com.google.android.gms.plus.internal.d>
implements GooglePlayServicesClient {
    private Person Ub;
    private final com.google.android.gms.plus.internal.h Uc;

    public e(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, com.google.android.gms.plus.internal.h h2) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, h2.iP());
        this.Uc = h2;
    }

    @Deprecated
    public e(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, com.google.android.gms.plus.internal.h h2) {
        this(context, context.getMainLooper(), new ff.c(connectionCallbacks), new ff.g(onConnectionFailedListener), h2);
    }

    public fk a(a.d<People.LoadPeopleResult> object, int n2, String object2) {
        this.bT();
        object = new e((a.d<People.LoadPeopleResult>)object);
        try {
            object2 = ((com.google.android.gms.plus.internal.d)this.eM()).a((com.google.android.gms.plus.internal.b)object, 1, n2, -1, (String)object2);
            return object2;
        }
        catch (RemoteException var3_4) {
            object.a(DataHolder.empty(8), null);
            return null;
        }
    }

    @Override
    protected void a(int n2, IBinder iBinder, Bundle bundle) {
        if (n2 == 0 && bundle != null && bundle.containsKey("loaded_person")) {
            this.Ub = ih.i(bundle.getByteArray("loaded_person"));
        }
        super.a(n2, iBinder, bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(a.d<Moments.LoadMomentsResult> object, int n2, String string2, Uri uri, String string3, String string4) {
        this.bT();
        object = object != null ? new b((a.d<Moments.LoadMomentsResult>)object) : null;
        try {
            ((com.google.android.gms.plus.internal.d)this.eM()).a((com.google.android.gms.plus.internal.b)object, n2, string2, uri, string3, string4);
            return;
        }
        catch (RemoteException var3_4) {
            object.a(DataHolder.empty(8), null, null);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(a.d<Status> object, Moment object2) {
        this.bT();
        object = object != null ? new a((a.d<Status>)object) : null;
        try {
            object2 = gg.a((ie)object2);
            ((com.google.android.gms.plus.internal.d)this.eM()).a((com.google.android.gms.plus.internal.b)object, (gg)object2);
            return;
        }
        catch (RemoteException var2_3) {
            if (object == null) {
                throw new IllegalStateException((Throwable)var2_3);
            }
            object.Z(new Status(8, null, null));
            return;
        }
    }

    public void a(a.d<People.LoadPeopleResult> object, Collection<String> collection) {
        this.bT();
        object = new e((a.d<People.LoadPeopleResult>)object);
        try {
            ((com.google.android.gms.plus.internal.d)this.eM()).a((com.google.android.gms.plus.internal.b)object, new ArrayList<String>(collection));
            return;
        }
        catch (RemoteException var2_3) {
            object.a(DataHolder.empty(8), null);
            return;
        }
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        Bundle bundle = this.Uc.iX();
        bundle.putStringArray("request_visible_actions", this.Uc.iQ());
        fm2.a(e2, 4452000, this.Uc.iT(), this.Uc.iS(), this.eL(), this.Uc.getAccountName(), bundle);
    }

    protected com.google.android.gms.plus.internal.d aR(IBinder iBinder) {
        return d.a.aQ(iBinder);
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.plus.service.START";
    }

    public boolean bg(String string2) {
        return Arrays.asList(this.eL()).contains(string2);
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    public void clearDefaultAccount() {
        this.bT();
        try {
            this.Ub = null;
            ((com.google.android.gms.plus.internal.d)this.eM()).clearDefaultAccount();
            return;
        }
        catch (RemoteException var1_1) {
            throw new IllegalStateException((Throwable)var1_1);
        }
    }

    public void d(a.d<People.LoadPeopleResult> d2, String[] arrstring) {
        this.a(d2, Arrays.asList(arrstring));
    }

    public String getAccountName() {
        this.bT();
        try {
            String string2 = ((com.google.android.gms.plus.internal.d)this.eM()).getAccountName();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    public Person getCurrentPerson() {
        this.bT();
        return this.Ub;
    }

    public void l(a.d<Moments.LoadMomentsResult> d2) {
        this.a(d2, 20, null, null, null, "me");
    }

    public void m(a.d<People.LoadPeopleResult> object) {
        this.bT();
        object = new e((a.d<People.LoadPeopleResult>)object);
        try {
            ((com.google.android.gms.plus.internal.d)this.eM()).a((com.google.android.gms.plus.internal.b)object, 2, 1, -1, null);
            return;
        }
        catch (RemoteException var2_2) {
            object.a(DataHolder.empty(8), null);
            return;
        }
    }

    public void n(a.d<Status> object) {
        this.bT();
        this.clearDefaultAccount();
        object = new g((a.d<Status>)object);
        try {
            ((com.google.android.gms.plus.internal.d)this.eM()).b((com.google.android.gms.plus.internal.b)object);
            return;
        }
        catch (RemoteException var2_2) {
            object.e(8, null);
            return;
        }
    }

    public fk o(a.d<People.LoadPeopleResult> d2, String string2) {
        return this.a(d2, 0, string2);
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.aR(iBinder);
    }

    public void removeMoment(String string2) {
        this.bT();
        try {
            ((com.google.android.gms.plus.internal.d)this.eM()).removeMoment(string2);
            return;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    final class a
    extends com.google.android.gms.plus.internal.a {
        private final a.d<Status> TG;

        public a(a.d<Status> d2) {
            this.TG = d2;
        }

        @Override
        public void Z(Status status) {
            e.this.a((ff.b)((Object)new d(this.TG, status)));
        }
    }

    final class b
    extends com.google.android.gms.plus.internal.a {
        private final a.d<Moments.LoadMomentsResult> TG;

        public b(a.d<Moments.LoadMomentsResult> d2) {
            this.TG = d2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(DataHolder dataHolder, String string2, String string3) {
            Object object = dataHolder.getMetadata() != null ? (PendingIntent)dataHolder.getMetadata().getParcelable("pendingIntent") : null;
            object = new Status(dataHolder.getStatusCode(), null, (PendingIntent)object);
            if (!object.isSuccess() && dataHolder != null) {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder = null;
            }
            e.this.a((ff.b)((Object)new c(this.TG, (Status)object, dataHolder, string2, string3)));
        }
    }

    final class c
    extends com.google.android.gms.internal.ff.d<a.d<Moments.LoadMomentsResult>>
    implements Moments.LoadMomentsResult {
        private final String EM;
        private final String Ue;
        private MomentBuffer Uf;
        private final Status wJ;

        public c(a.d<Moments.LoadMomentsResult> d2, Status status, DataHolder dataHolder, String string2, String string3) {
            super(d2, dataHolder);
            this.wJ = status;
            this.EM = string2;
            this.Ue = string3;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void a(a.d<Moments.LoadMomentsResult> d2, DataHolder object) {
            object = object != null ? new MomentBuffer((DataHolder)object) : null;
            this.Uf = object;
            d2.b(this);
        }

        @Override
        public MomentBuffer getMomentBuffer() {
            return this.Uf;
        }

        @Override
        public String getNextPageToken() {
            return this.EM;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }

        @Override
        public String getUpdated() {
            return this.Ue;
        }

        @Override
        public void release() {
            if (this.Uf != null) {
                this.Uf.close();
            }
        }
    }

    final class d
    extends com.google.android.gms.internal.ff.b<a.d<Status>> {
        private final Status wJ;

        public d(a.d<Status> d2, Status status) {
            super(d2);
            this.wJ = status;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Status> d2) {
            if (d2 != null) {
                d2.b(this.wJ);
            }
        }

        protected void dx() {
        }
    }

    final class e
    extends com.google.android.gms.plus.internal.a {
        private final a.d<People.LoadPeopleResult> TG;

        public e(a.d<People.LoadPeopleResult> d2) {
            this.TG = d2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void a(DataHolder dataHolder, String string2) {
            Object object = dataHolder.getMetadata() != null ? (PendingIntent)dataHolder.getMetadata().getParcelable("pendingIntent") : null;
            object = new Status(dataHolder.getStatusCode(), null, (PendingIntent)object);
            if (!object.isSuccess() && dataHolder != null) {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder = null;
            }
            e.this.a((ff.b)((Object)new f(this.TG, (Status)object, dataHolder, string2)));
        }
    }

    final class f
    extends com.google.android.gms.internal.ff.d<a.d<People.LoadPeopleResult>>
    implements People.LoadPeopleResult {
        private final String EM;
        private PersonBuffer Ug;
        private final Status wJ;

        public f(a.d<People.LoadPeopleResult> d2, Status status, DataHolder dataHolder, String string2) {
            super(d2, dataHolder);
            this.wJ = status;
            this.EM = string2;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void a(a.d<People.LoadPeopleResult> d2, DataHolder object) {
            object = object != null ? new PersonBuffer((DataHolder)object) : null;
            this.Ug = object;
            d2.b(this);
        }

        @Override
        public String getNextPageToken() {
            return this.EM;
        }

        @Override
        public PersonBuffer getPersonBuffer() {
            return this.Ug;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }

        @Override
        public void release() {
            if (this.Ug != null) {
                this.Ug.close();
            }
        }
    }

    final class g
    extends com.google.android.gms.plus.internal.a {
        private final a.d<Status> TG;

        public g(a.d<Status> d2) {
            this.TG = d2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void e(int n2, Bundle object) {
            object = object != null ? (PendingIntent)object.getParcelable("pendingIntent") : null;
            object = new Status(n2, null, (PendingIntent)object);
            e.this.a((ff.b)((Object)new h(this.TG, (Status)object)));
        }
    }

    final class h
    extends com.google.android.gms.internal.ff.b<a.d<Status>> {
        private final Status wJ;

        public h(a.d<Status> d2, Status status) {
            super(d2);
            this.wJ = status;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Status> d2) {
            e.this.disconnect();
            if (d2 != null) {
                d2.b(this.wJ);
            }
        }

        protected void dx() {
        }
    }

}

