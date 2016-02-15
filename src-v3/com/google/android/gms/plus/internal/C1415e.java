package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.ff.C0391b;
import com.google.android.gms.internal.ff.C0888c;
import com.google.android.gms.internal.ff.C0889d;
import com.google.android.gms.internal.ff.C1374e;
import com.google.android.gms.internal.ff.C1375g;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.gg;
import com.google.android.gms.internal.ie;
import com.google.android.gms.internal.ih;
import com.google.android.gms.plus.Moments.LoadMomentsResult;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.internal.C0478d.C1050a;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/* renamed from: com.google.android.gms.plus.internal.e */
public class C1415e extends ff<C0478d> implements GooglePlayServicesClient {
    private Person Ub;
    private final C1053h Uc;

    /* renamed from: com.google.android.gms.plus.internal.e.d */
    final class C1051d extends C0391b<C0244d<Status>> {
        final /* synthetic */ C1415e Ud;
        private final Status wJ;

        public C1051d(C1415e c1415e, C0244d<Status> c0244d, Status status) {
            this.Ud = c1415e;
            super(c1415e, c0244d);
            this.wJ = status;
        }

        protected /* synthetic */ void m2433a(Object obj) {
            m2434c((C0244d) obj);
        }

        protected void m2434c(C0244d<Status> c0244d) {
            if (c0244d != null) {
                c0244d.m132b(this.wJ);
            }
        }

        protected void dx() {
        }
    }

    /* renamed from: com.google.android.gms.plus.internal.e.h */
    final class C1052h extends C0391b<C0244d<Status>> {
        final /* synthetic */ C1415e Ud;
        private final Status wJ;

        public C1052h(C1415e c1415e, C0244d<Status> c0244d, Status status) {
            this.Ud = c1415e;
            super(c1415e, c0244d);
            this.wJ = status;
        }

        protected /* synthetic */ void m2435a(Object obj) {
            m2436c((C0244d) obj);
        }

        protected void m2436c(C0244d<Status> c0244d) {
            this.Ud.disconnect();
            if (c0244d != null) {
                c0244d.m132b(this.wJ);
            }
        }

        protected void dx() {
        }
    }

    /* renamed from: com.google.android.gms.plus.internal.e.c */
    final class C1413c extends C0889d<C0244d<LoadMomentsResult>> implements LoadMomentsResult {
        private final String EM;
        final /* synthetic */ C1415e Ud;
        private final String Ue;
        private MomentBuffer Uf;
        private final Status wJ;

        public C1413c(C1415e c1415e, C0244d<LoadMomentsResult> c0244d, Status status, DataHolder dataHolder, String str, String str2) {
            this.Ud = c1415e;
            super(c1415e, c0244d, dataHolder);
            this.wJ = status;
            this.EM = str;
            this.Ue = str2;
        }

        protected void m3189a(C0244d<LoadMomentsResult> c0244d, DataHolder dataHolder) {
            this.Uf = dataHolder != null ? new MomentBuffer(dataHolder) : null;
            c0244d.m132b(this);
        }

        public MomentBuffer getMomentBuffer() {
            return this.Uf;
        }

        public String getNextPageToken() {
            return this.EM;
        }

        public Status getStatus() {
            return this.wJ;
        }

        public String getUpdated() {
            return this.Ue;
        }

        public void release() {
            if (this.Uf != null) {
                this.Uf.close();
            }
        }
    }

    /* renamed from: com.google.android.gms.plus.internal.e.f */
    final class C1414f extends C0889d<C0244d<LoadPeopleResult>> implements LoadPeopleResult {
        private final String EM;
        final /* synthetic */ C1415e Ud;
        private PersonBuffer Ug;
        private final Status wJ;

        public C1414f(C1415e c1415e, C0244d<LoadPeopleResult> c0244d, Status status, DataHolder dataHolder, String str) {
            this.Ud = c1415e;
            super(c1415e, c0244d, dataHolder);
            this.wJ = status;
            this.EM = str;
        }

        protected void m3191a(C0244d<LoadPeopleResult> c0244d, DataHolder dataHolder) {
            this.Ug = dataHolder != null ? new PersonBuffer(dataHolder) : null;
            c0244d.m132b(this);
        }

        public String getNextPageToken() {
            return this.EM;
        }

        public PersonBuffer getPersonBuffer() {
            return this.Ug;
        }

        public Status getStatus() {
            return this.wJ;
        }

        public void release() {
            if (this.Ug != null) {
                this.Ug.close();
            }
        }
    }

    /* renamed from: com.google.android.gms.plus.internal.e.a */
    final class C1501a extends C1412a {
        private final C0244d<Status> TG;
        final /* synthetic */ C1415e Ud;

        public C1501a(C1415e c1415e, C0244d<Status> c0244d) {
            this.Ud = c1415e;
            this.TG = c0244d;
        }

        public void m3435Z(Status status) {
            this.Ud.m2175a(new C1051d(this.Ud, this.TG, status));
        }
    }

    /* renamed from: com.google.android.gms.plus.internal.e.b */
    final class C1502b extends C1412a {
        private final C0244d<LoadMomentsResult> TG;
        final /* synthetic */ C1415e Ud;

        public C1502b(C1415e c1415e, C0244d<LoadMomentsResult> c0244d) {
            this.Ud = c1415e;
            this.TG = c0244d;
        }

        public void m3436a(DataHolder dataHolder, String str, String str2) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), null, dataHolder.getMetadata() != null ? (PendingIntent) dataHolder.getMetadata().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            this.Ud.m2175a(new C1413c(this.Ud, this.TG, status, dataHolder2, str, str2));
        }
    }

    /* renamed from: com.google.android.gms.plus.internal.e.e */
    final class C1503e extends C1412a {
        private final C0244d<LoadPeopleResult> TG;
        final /* synthetic */ C1415e Ud;

        public C1503e(C1415e c1415e, C0244d<LoadPeopleResult> c0244d) {
            this.Ud = c1415e;
            this.TG = c0244d;
        }

        public void m3437a(DataHolder dataHolder, String str) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), null, dataHolder.getMetadata() != null ? (PendingIntent) dataHolder.getMetadata().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            this.Ud.m2175a(new C1414f(this.Ud, this.TG, status, dataHolder2, str));
        }
    }

    /* renamed from: com.google.android.gms.plus.internal.e.g */
    final class C1504g extends C1412a {
        private final C0244d<Status> TG;
        final /* synthetic */ C1415e Ud;

        public C1504g(C1415e c1415e, C0244d<Status> c0244d) {
            this.Ud = c1415e;
            this.TG = c0244d;
        }

        public void m3438e(int i, Bundle bundle) {
            this.Ud.m2175a(new C1052h(this.Ud, this.TG, new Status(i, null, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null)));
        }
    }

    public C1415e(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, C1053h c1053h) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, c1053h.iP());
        this.Uc = c1053h;
    }

    @Deprecated
    public C1415e(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, C1053h c1053h) {
        this(context, context.getMainLooper(), new C0888c(connectionCallbacks), new C1375g(onConnectionFailedListener), c1053h);
    }

    public fk m3193a(C0244d<LoadPeopleResult> c0244d, int i, String str) {
        bT();
        Object c1503e = new C1503e(this, c0244d);
        try {
            return ((C0478d) eM()).m1307a(c1503e, 1, i, -1, str);
        } catch (RemoteException e) {
            c1503e.m3437a(DataHolder.empty(8), null);
            return null;
        }
    }

    protected void m3194a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null && bundle.containsKey("loaded_person")) {
            this.Ub = ih.m3112i(bundle.getByteArray("loaded_person"));
        }
        super.m2174a(i, iBinder, bundle);
    }

    public void m3195a(C0244d<LoadMomentsResult> c0244d, int i, String str, Uri uri, String str2, String str3) {
        bT();
        Object c1502b = c0244d != null ? new C1502b(this, c0244d) : null;
        try {
            ((C0478d) eM()).m1310a(c1502b, i, str, uri, str2, str3);
        } catch (RemoteException e) {
            c1502b.m3436a(DataHolder.empty(8), null, null);
        }
    }

    public void m3196a(C0244d<Status> c0244d, Moment moment) {
        bT();
        C0476b c1501a = c0244d != null ? new C1501a(this, c0244d) : null;
        try {
            ((C0478d) eM()).m1312a(c1501a, gg.m2238a((ie) moment));
        } catch (Throwable e) {
            if (c1501a == null) {
                throw new IllegalStateException(e);
            }
            c1501a.m3435Z(new Status(8, null, null));
        }
    }

    public void m3197a(C0244d<LoadPeopleResult> c0244d, Collection<String> collection) {
        bT();
        C0476b c1503e = new C1503e(this, c0244d);
        try {
            ((C0478d) eM()).m1315a(c1503e, new ArrayList(collection));
        } catch (RemoteException e) {
            c1503e.m3437a(DataHolder.empty(8), null);
        }
    }

    protected void m3198a(fm fmVar, C1374e c1374e) throws RemoteException {
        Bundle iX = this.Uc.iX();
        iX.putStringArray(GoogleAuthUtil.KEY_REQUEST_VISIBLE_ACTIVITIES, this.Uc.iQ());
        fmVar.m951a(c1374e, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, this.Uc.iT(), this.Uc.iS(), eL(), this.Uc.getAccountName(), iX);
    }

    protected C0478d aR(IBinder iBinder) {
        return C1050a.aQ(iBinder);
    }

    protected String bg() {
        return "com.google.android.gms.plus.service.START";
    }

    public boolean bg(String str) {
        return Arrays.asList(eL()).contains(str);
    }

    protected String bh() {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    public void clearDefaultAccount() {
        bT();
        try {
            this.Ub = null;
            ((C0478d) eM()).clearDefaultAccount();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void m3199d(C0244d<LoadPeopleResult> c0244d, String[] strArr) {
        m3197a((C0244d) c0244d, Arrays.asList(strArr));
    }

    public String getAccountName() {
        bT();
        try {
            return ((C0478d) eM()).getAccountName();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public Person getCurrentPerson() {
        bT();
        return this.Ub;
    }

    public void m3200l(C0244d<LoadMomentsResult> c0244d) {
        m3195a(c0244d, 20, null, null, null, "me");
    }

    public void m3201m(C0244d<LoadPeopleResult> c0244d) {
        bT();
        Object c1503e = new C1503e(this, c0244d);
        try {
            ((C0478d) eM()).m1307a(c1503e, 2, 1, -1, null);
        } catch (RemoteException e) {
            c1503e.m3437a(DataHolder.empty(8), null);
        }
    }

    public void m3202n(C0244d<Status> c0244d) {
        bT();
        clearDefaultAccount();
        Object c1504g = new C1504g(this, c0244d);
        try {
            ((C0478d) eM()).m1316b(c1504g);
        } catch (RemoteException e) {
            c1504g.m3438e(8, null);
        }
    }

    public fk m3203o(C0244d<LoadPeopleResult> c0244d, String str) {
        return m3193a((C0244d) c0244d, 0, str);
    }

    protected /* synthetic */ IInterface m3204r(IBinder iBinder) {
        return aR(iBinder);
    }

    public void removeMoment(String momentId) {
        bT();
        try {
            ((C0478d) eM()).removeMoment(momentId);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }
}
