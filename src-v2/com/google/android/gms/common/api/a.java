/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.b;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class a {

    public static abstract class a<R extends Result>
    implements PendingResult<R>,
    d<R> {
        private final Object AB = new Object();
        private c<R> AC;
        private final CountDownLatch AD = new CountDownLatch(1);
        private final ArrayList<PendingResult.a> AE = new ArrayList();
        private ResultCallback<R> AF;
        private volatile R AG;
        private volatile boolean AH;
        private boolean AI;
        private boolean AJ;
        private fk AK;

        a() {
        }

        public a(Looper looper) {
            this.AC = new c(looper);
        }

        public a(c<R> c2) {
            this.AC = c2;
        }

        @Override
        private void b(R object) {
            this.AG = object;
            this.AK = null;
            this.AD.countDown();
            object = this.AG.getStatus();
            if (this.AF != null) {
                this.AC.eg();
                if (!this.AI) {
                    this.AC.a(this.AF, this.eb());
                }
            }
            Iterator<PendingResult.a> iterator = this.AE.iterator();
            while (iterator.hasNext()) {
                iterator.next().l((Status)object);
            }
            this.AE.clear();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void c(Result result) {
            if (!(result instanceof Releasable)) return;
            try {
                ((Releasable)((Object)result)).release();
                return;
            }
            catch (Exception var1_2) {
                Log.w((String)"AbstractPendingResult", (String)("Unable to release " + this), (Throwable)var1_2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private R eb() {
            Object object = this.AB;
            synchronized (object) {
                boolean bl2 = !this.AH;
                fq.a(bl2, "Result has already been consumed.");
                fq.a(this.isReady(), "Result is not ready.");
                R r2 = this.AG;
                this.ec();
                return r2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void ed() {
            Object object = this.AB;
            synchronized (object) {
                if (!this.isReady()) {
                    this.a(this.d(Status.Bw));
                    this.AJ = true;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void ee() {
            Object object = this.AB;
            synchronized (object) {
                if (!this.isReady()) {
                    this.a(this.d(Status.By));
                    this.AJ = true;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void a(PendingResult.a a2) {
            boolean bl2 = !this.AH;
            fq.a(bl2, "Result has already been consumed.");
            Object object = this.AB;
            synchronized (object) {
                if (this.isReady()) {
                    a2.l(this.AG.getStatus());
                } else {
                    this.AE.add(a2);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void a(R r2) {
            boolean bl2 = true;
            Object object = this.AB;
            synchronized (object) {
                if (this.AJ || this.AI) {
                    this.c((Result)r2);
                    return;
                }
                boolean bl3 = !this.isReady();
                fq.a(bl3, "Results have already been set");
                bl3 = !this.AH ? bl2 : false;
                fq.a(bl3, "Result has already been consumed");
                this.b(r2);
                return;
            }
        }

        protected void a(c<R> c2) {
            this.AC = c2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected final void a(fk fk2) {
            Object object = this.AB;
            synchronized (object) {
                this.AK = fk2;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final R await() {
            boolean bl2;
            block4 : {
                boolean bl3 = false;
                bl2 = !this.AH;
                fq.a(bl2, "Result has already been consumed");
                if (!this.isReady()) {
                    bl2 = bl3;
                    if (Looper.myLooper() == Looper.getMainLooper()) break block4;
                }
                bl2 = true;
            }
            fq.a(bl2, "await must not be called on the UI thread");
            try {
                this.AD.await();
            }
            catch (InterruptedException var3_3) {
                this.ed();
            }
            fq.a(this.isReady(), "Result is not ready.");
            return this.eb();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final R await(long l2, TimeUnit timeUnit) {
            boolean bl2;
            block5 : {
                boolean bl3 = false;
                bl2 = !this.AH;
                fq.a(bl2, "Result has already been consumed.");
                if (!this.isReady()) {
                    bl2 = bl3;
                    if (Looper.myLooper() == Looper.getMainLooper()) break block5;
                }
                bl2 = true;
            }
            fq.a(bl2, "await must not be called on the UI thread");
            try {
                if (!this.AD.await(l2, timeUnit)) {
                    this.ee();
                }
            }
            catch (InterruptedException var3_3) {
                this.ed();
            }
            fq.a(this.isReady(), "Result is not ready.");
            return this.eb();
        }

        @Override
        public /* synthetic */ void b(Object object) {
            this.a((Result)object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void cancel() {
            Object object = this.AB;
            synchronized (object) {
                if (this.AI) {
                    return;
                }
                fk fk2 = this.AK;
                if (fk2 != null) {
                    try {
                        this.AK.cancel();
                    }
                    catch (RemoteException var2_3) {}
                }
                this.c((Result)this.AG);
                this.AF = null;
                this.AI = true;
                this.b(this.d(Status.Bz));
                return;
            }
        }

        protected abstract R d(Status var1);

        protected void ec() {
            this.AH = true;
            this.AG = null;
            this.AF = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean isCanceled() {
            Object object = this.AB;
            synchronized (object) {
                return this.AI;
            }
        }

        public final boolean isReady() {
            if (this.AD.getCount() == 0) {
                return true;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void setResultCallback(ResultCallback<R> resultCallback) {
            boolean bl2 = !this.AH;
            fq.a(bl2, "Result has already been consumed.");
            Object object = this.AB;
            synchronized (object) {
                if (this.isCanceled()) {
                    return;
                }
                if (this.isReady()) {
                    this.AC.a(resultCallback, this.eb());
                } else {
                    this.AF = resultCallback;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void setResultCallback(ResultCallback<R> resultCallback, long l2, TimeUnit timeUnit) {
            boolean bl2 = !this.AH;
            fq.a(bl2, "Result has already been consumed.");
            Object object = this.AB;
            synchronized (object) {
                if (this.isCanceled()) {
                    return;
                }
                if (this.isReady()) {
                    this.AC.a(resultCallback, this.eb());
                } else {
                    this.AF = resultCallback;
                    this.AC.a(this, timeUnit.toMillis(l2));
                }
                return;
            }
        }
    }

    public static abstract class b<R extends Result, A extends Api.a>
    extends a<R>
    implements b.c<A> {
        private b.a AL;
        private final Api.c<A> Az;

        protected b(Api.c<A> c2) {
            this.Az = fq.f(c2);
        }

        @Override
        private void a(RemoteException remoteException) {
            this.k(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        @Override
        protected abstract void a(A var1) throws RemoteException;

        @Override
        public void a(b.a a2) {
            this.AL = a2;
        }

        @Override
        public final void b(A a2) throws DeadObjectException {
            this.a((A)((Object)new c<R>(a2.getLooper())));
            try {
                this.a(a2);
                return;
            }
            catch (DeadObjectException var1_2) {
                this.a((RemoteException)var1_2);
                throw var1_2;
            }
            catch (RemoteException var1_3) {
                this.a(var1_3);
                return;
            }
        }

        @Override
        public final Api.c<A> ea() {
            return this.Az;
        }

        @Override
        protected void ec() {
            super.ec();
            if (this.AL != null) {
                this.AL.b(this);
                this.AL = null;
            }
        }

        @Override
        public int ef() {
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public final void k(Status status) {
            boolean bl2 = !status.isSuccess();
            fq.b(bl2, (Object)"Failed result must not be success");
            this.a(this.d(status));
        }
    }

    public static class c<R extends Result>
    extends Handler {
        public c() {
            this(Looper.getMainLooper());
        }

        public c(Looper looper) {
            super(looper);
        }

        public void a(ResultCallback<R> resultCallback, R r2) {
            this.sendMessage(this.obtainMessage(1, (Object)new Pair(resultCallback, r2)));
        }

        public void a(a<R> a2, long l2) {
            this.sendMessageDelayed(this.obtainMessage(2, a2), l2);
        }

        protected void b(ResultCallback<R> resultCallback, R r2) {
            resultCallback.onResult(r2);
        }

        public void eg() {
            this.removeMessages(2);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.wtf((String)"GoogleApi", (String)"Don't know how to handle this message.");
                    return;
                }
                case 1: {
                    message = (Pair)message.obj;
                    this.b((ResultCallback)message.first, (Result)message.second);
                    return;
                }
                case 2: 
            }
            ((a)message.obj).ee();
        }
    }

    public static interface d<R> {
        public void b(R var1);
    }

}

