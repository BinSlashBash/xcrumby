package com.google.android.gms.common.api;

import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.Api.C0239a;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0793b.C0246a;
import com.google.android.gms.common.api.C0793b.C0248c;
import com.google.android.gms.common.api.PendingResult.C0242a;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* renamed from: com.google.android.gms.common.api.a */
public class C0245a {

    /* renamed from: com.google.android.gms.common.api.a.c */
    public static class C0243c<R extends Result> extends Handler {
        public C0243c() {
            this(Looper.getMainLooper());
        }

        public C0243c(Looper looper) {
            super(looper);
        }

        public void m129a(ResultCallback<R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }

        public void m130a(C0789a<R> c0789a, long j) {
            sendMessageDelayed(obtainMessage(2, c0789a), j);
        }

        protected void m131b(ResultCallback<R> resultCallback, R r) {
            resultCallback.onResult(r);
        }

        public void eg() {
            removeMessages(2);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Std.STD_FILE /*1*/:
                    Pair pair = (Pair) msg.obj;
                    m131b((ResultCallback) pair.first, (Result) pair.second);
                case Std.STD_URL /*2*/:
                    ((C0789a) msg.obj).ee();
                default:
                    Log.wtf("GoogleApi", "Don't know how to handle this message.");
            }
        }
    }

    /* renamed from: com.google.android.gms.common.api.a.d */
    public interface C0244d<R> {
        void m132b(R r);
    }

    /* renamed from: com.google.android.gms.common.api.a.a */
    public static abstract class C0789a<R extends Result> implements PendingResult<R>, C0244d<R> {
        private final Object AB;
        private C0243c<R> AC;
        private final CountDownLatch AD;
        private final ArrayList<C0242a> AE;
        private ResultCallback<R> AF;
        private volatile R AG;
        private volatile boolean AH;
        private boolean AI;
        private boolean AJ;
        private fk AK;

        C0789a() {
            this.AB = new Object();
            this.AD = new CountDownLatch(1);
            this.AE = new ArrayList();
        }

        public C0789a(Looper looper) {
            this.AB = new Object();
            this.AD = new CountDownLatch(1);
            this.AE = new ArrayList();
            this.AC = new C0243c(looper);
        }

        public C0789a(C0243c<R> c0243c) {
            this.AB = new Object();
            this.AD = new CountDownLatch(1);
            this.AE = new ArrayList();
            this.AC = c0243c;
        }

        private void m1656b(R r) {
            this.AG = r;
            this.AK = null;
            this.AD.countDown();
            Status status = this.AG.getStatus();
            if (this.AF != null) {
                this.AC.eg();
                if (!this.AI) {
                    this.AC.m129a(this.AF, eb());
                }
            }
            Iterator it = this.AE.iterator();
            while (it.hasNext()) {
                ((C0242a) it.next()).m126l(status);
            }
            this.AE.clear();
        }

        private void m1657c(Result result) {
            if (result instanceof Releasable) {
                try {
                    ((Releasable) result).release();
                } catch (Throwable e) {
                    Log.w("AbstractPendingResult", "Unable to release " + this, e);
                }
            }
        }

        private R eb() {
            R r;
            synchronized (this.AB) {
                fq.m980a(!this.AH, "Result has already been consumed.");
                fq.m980a(isReady(), "Result is not ready.");
                r = this.AG;
                ec();
            }
            return r;
        }

        private void ed() {
            synchronized (this.AB) {
                if (!isReady()) {
                    m1659a(m1663d(Status.Bw));
                    this.AJ = true;
                }
            }
        }

        private void ee() {
            synchronized (this.AB) {
                if (!isReady()) {
                    m1659a(m1663d(Status.By));
                    this.AJ = true;
                }
            }
        }

        public final void m1658a(C0242a c0242a) {
            fq.m980a(!this.AH, "Result has already been consumed.");
            synchronized (this.AB) {
                if (isReady()) {
                    c0242a.m126l(this.AG.getStatus());
                } else {
                    this.AE.add(c0242a);
                }
            }
        }

        public final void m1659a(R r) {
            boolean z = true;
            synchronized (this.AB) {
                if (this.AJ || this.AI) {
                    m1657c(r);
                    return;
                }
                fq.m980a(!isReady(), "Results have already been set");
                if (this.AH) {
                    z = false;
                }
                fq.m980a(z, "Result has already been consumed");
                m1656b((Result) r);
            }
        }

        protected void m1660a(C0243c<R> c0243c) {
            this.AC = c0243c;
        }

        protected final void m1661a(fk fkVar) {
            synchronized (this.AB) {
                this.AK = fkVar;
            }
        }

        public final R await() {
            boolean z = false;
            fq.m980a(!this.AH, "Result has already been consumed");
            if (isReady() || Looper.myLooper() != Looper.getMainLooper()) {
                z = true;
            }
            fq.m980a(z, "await must not be called on the UI thread");
            try {
                this.AD.await();
            } catch (InterruptedException e) {
                ed();
            }
            fq.m980a(isReady(), "Result is not ready.");
            return eb();
        }

        public final R await(long time, TimeUnit units) {
            boolean z = false;
            fq.m980a(!this.AH, "Result has already been consumed.");
            if (isReady() || Looper.myLooper() != Looper.getMainLooper()) {
                z = true;
            }
            fq.m980a(z, "await must not be called on the UI thread");
            try {
                if (!this.AD.await(time, units)) {
                    ee();
                }
            } catch (InterruptedException e) {
                ed();
            }
            fq.m980a(isReady(), "Result is not ready.");
            return eb();
        }

        public /* synthetic */ void m1662b(Object obj) {
            m1659a((Result) obj);
        }

        public void cancel() {
            synchronized (this.AB) {
                if (this.AI) {
                    return;
                }
                if (this.AK != null) {
                    try {
                        this.AK.cancel();
                    } catch (RemoteException e) {
                    }
                }
                m1657c(this.AG);
                this.AF = null;
                this.AI = true;
                m1656b(m1663d(Status.Bz));
            }
        }

        protected abstract R m1663d(Status status);

        protected void ec() {
            this.AH = true;
            this.AG = null;
            this.AF = null;
        }

        public boolean isCanceled() {
            boolean z;
            synchronized (this.AB) {
                z = this.AI;
            }
            return z;
        }

        public final boolean isReady() {
            return this.AD.getCount() == 0;
        }

        public final void setResultCallback(ResultCallback<R> callback) {
            fq.m980a(!this.AH, "Result has already been consumed.");
            synchronized (this.AB) {
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.AC.m129a((ResultCallback) callback, eb());
                } else {
                    this.AF = callback;
                }
            }
        }

        public final void setResultCallback(ResultCallback<R> callback, long time, TimeUnit units) {
            fq.m980a(!this.AH, "Result has already been consumed.");
            synchronized (this.AB) {
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.AC.m129a((ResultCallback) callback, eb());
                } else {
                    this.AF = callback;
                    this.AC.m130a(this, units.toMillis(time));
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.common.api.a.b */
    public static abstract class C1299b<R extends Result, A extends C0239a> extends C0789a<R> implements C0248c<A> {
        private C0246a AL;
        private final C0241c<A> Az;

        protected C1299b(C0241c<A> c0241c) {
            this.Az = (C0241c) fq.m985f(c0241c);
        }

        private void m2647a(RemoteException remoteException) {
            m2651k(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        protected abstract void m2648a(A a) throws RemoteException;

        public void m2649a(C0246a c0246a) {
            this.AL = c0246a;
        }

        public final void m2650b(A a) throws DeadObjectException {
            m1660a(new C0243c(a.getLooper()));
            try {
                m2648a((C0239a) a);
            } catch (RemoteException e) {
                m2647a(e);
                throw e;
            } catch (RemoteException e2) {
                m2647a(e2);
            }
        }

        public final C0241c<A> ea() {
            return this.Az;
        }

        protected void ec() {
            super.ec();
            if (this.AL != null) {
                this.AL.m133b(this);
                this.AL = null;
            }
        }

        public int ef() {
            return 0;
        }

        public final void m2651k(Status status) {
            fq.m984b(!status.isSuccess(), (Object) "Failed result must not be success");
            m1659a(m1663d(status));
        }
    }
}
