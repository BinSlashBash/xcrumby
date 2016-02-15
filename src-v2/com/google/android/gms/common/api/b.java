/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fg;
import com.google.android.gms.internal.fq;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class b
implements GoogleApiClient {
    private final a AL;
    private final Looper AS;
    private final Lock Ba = new ReentrantLock();
    private final Condition Bb = this.Ba.newCondition();
    private final fg Bc;
    final Queue<c<?>> Bd = new LinkedList();
    private ConnectionResult Be;
    private int Bf;
    private int Bg = 4;
    private int Bh = 0;
    private boolean Bi = false;
    private int Bj;
    private long Bk = 5000;
    final Handler Bl;
    private final Bundle Bm = new Bundle();
    private final Map<Api.c<?>, Api.a> Bn = new HashMap();
    private boolean Bo;
    final Set<c<?>> Bp = new HashSet();
    final GoogleApiClient.ConnectionCallbacks Bq;
    private final fg.b Br;

    public b(Context context, Looper looper, fc fc2, Map<Api<?>, Api.ApiOptions> map, Set<GoogleApiClient.ConnectionCallbacks> iterator, Set<GoogleApiClient.OnConnectionFailedListener> object3) {
        Object object;
        this.AL = new a(){

            @Override
            public void b(c<?> c2) {
                b.this.Ba.lock();
                try {
                    b.this.Bp.remove(c2);
                    return;
                }
                finally {
                    b.this.Ba.unlock();
                }
            }
        };
        this.Bq = new GoogleApiClient.ConnectionCallbacks(){

            @Override
            public void onConnected(Bundle bundle) {
                b.this.Ba.lock();
                try {
                    if (b.this.Bg == 1) {
                        if (bundle != null) {
                            b.this.Bm.putAll(bundle);
                        }
                        b.this.ei();
                    }
                    return;
                }
                finally {
                    b.this.Ba.unlock();
                }
            }

            /*
             * Exception decompiling
             */
            @Override
            public void onConnectionSuspended(int var1_1) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
                // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:486)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:334)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:527)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
                // org.benf.cfr.reader.Main.doJar(Main.java:128)
                // org.benf.cfr.reader.Main.main(Main.java:178)
                throw new IllegalStateException("Decompilation failed");
            }
        };
        this.Br = new fg.b(){

            @Override
            public Bundle dG() {
                return null;
            }

            @Override
            public boolean em() {
                return b.this.Bo;
            }

            @Override
            public boolean isConnected() {
                return b.this.isConnected();
            }
        };
        this.Bc = new fg(context, looper, this.Br);
        this.AS = looper;
        this.Bl = new b(looper);
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object = (GoogleApiClient.ConnectionCallbacks)iterator.next();
            this.Bc.registerConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)object);
        }
        iterator = object3.iterator();
        while (iterator.hasNext()) {
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)iterator.next();
            this.Bc.registerConnectionFailedListener(onConnectionFailedListener);
        }
        for (Api api : map.keySet()) {
            object = api.dY();
            Api.ApiOptions apiOptions = map.get(api);
            this.Bn.put(api.ea(), (Api.a)b.a(object, apiOptions, context, looper, fc2, this.Bq, new GoogleApiClient.OnConnectionFailedListener((Api.b)object){
                final /* synthetic */ Api.b Bt;

                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    b.this.Ba.lock();
                    try {
                        if (b.this.Be == null || this.Bt.getPriority() < b.this.Bf) {
                            b.this.Be = connectionResult;
                            b.this.Bf = this.Bt.getPriority();
                        }
                        b.this.ei();
                        return;
                    }
                    finally {
                        b.this.Ba.unlock();
                    }
                }
            }));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void E(int n2) {
        this.Ba.lock();
        try {
            if (this.Bg == 3) return;
            {
                if (n2 == -1) {
                    Iterator iterator;
                    if (this.isConnecting()) {
                        iterator = this.Bd.iterator();
                        while (iterator.hasNext()) {
                            Object object = iterator.next();
                            if (object.ef() == 1) continue;
                            object.cancel();
                            iterator.remove();
                        }
                    } else {
                        this.Bd.clear();
                    }
                    iterator = this.Bp.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().cancel();
                    }
                    this.Bp.clear();
                    if (this.Be == null && !this.Bd.isEmpty()) {
                        this.Bi = true;
                        return;
                    }
                }
                boolean bl2 = this.isConnecting();
                boolean bl3 = this.isConnected();
                this.Bg = 3;
                if (bl2) {
                    if (n2 == -1) {
                        this.Be = null;
                    }
                    this.Bb.signalAll();
                }
                this.Bo = false;
                for (Object object : this.Bn.values()) {
                    if (!object.isConnected()) continue;
                    object.disconnect();
                }
                this.Bo = true;
                this.Bg = 4;
                if (!bl3) return;
                {
                    if (n2 != -1) {
                        this.Bc.O(n2);
                    }
                    this.Bo = false;
                    return;
                }
            }
        }
        finally {
            this.Ba.unlock();
        }
    }

    private static <C extends Api.a, O> C a(Api.b<C, O> b2, Object object, Context context, Looper looper, fc fc2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return b2.a(context, looper, fc2, object, connectionCallbacks, onConnectionFailedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private <A extends Api.a> void a(c<A> c2) throws DeadObjectException {
        boolean bl2;
        boolean bl3;
        block6 : {
            block5 : {
                bl2 = true;
                this.Ba.lock();
                if (this.isConnected() || this.ek()) break block5;
                bl3 = false;
                break block6;
            }
            bl3 = true;
        }
        fq.a(bl3, "GoogleApiClient is not connected yet.");
        bl3 = c2.ea() != null ? bl2 : false;
        fq.b(bl3, (Object)"This task can not be executed or enqueued (it's probably a Batch or malformed)");
        this.Bp.add(c2);
        c2.a(this.AL);
        if (this.ek()) {
            c2.k(new Status(8));
            this.Ba.unlock();
            return;
        }
        c2.b(this.a((T)c2.ea()));
    }

    static /* synthetic */ void a(b b2, int n2) {
        b2.E(n2);
    }

    static /* synthetic */ int b(b b2, int n2) {
        b2.Bh = n2;
        return n2;
    }

    static /* synthetic */ boolean e(b b2) {
        return b2.ek();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void ei() {
        this.Ba.lock();
        --this.Bj;
        if (this.Bj != 0) return;
        if (this.Be != null) {
            this.Bi = false;
            this.E(3);
            if (this.ek()) {
                --this.Bh;
            }
            if (this.ek()) {
                this.Bl.sendMessageDelayed(this.Bl.obtainMessage(1), this.Bk);
            } else {
                this.Bc.a(this.Be);
            }
            this.Bo = false;
            return;
        }
        this.Bg = 2;
        this.el();
        this.Bb.signalAll();
        this.ej();
        if (this.Bi) {
            this.Bi = false;
            this.E(-1);
            return;
        }
        var1_2 = this.Bm.isEmpty() ? null : this.Bm;
        ** GOTO lbl29
        finally {
            this.Ba.unlock();
        }
lbl29: // 1 sources:
        this.Bc.b(var1_2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void ej() {
        boolean bl2 = this.isConnected() || this.ek();
        fq.a(bl2, "GoogleApiClient is not connected yet.");
        this.Ba.lock();
        try {
            while (!(bl2 = this.Bd.isEmpty())) {
                try {
                    this.a((T)this.Bd.remove());
                }
                catch (DeadObjectException var2_2) {
                    Log.w((String)"GoogleApiClientImpl", (String)"Service died while flushing queue", (Throwable)var2_2);
                }
            }
            return;
        }
        finally {
            this.Ba.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean ek() {
        this.Ba.lock();
        try {
            int n2 = this.Bh;
            boolean bl2 = n2 != 0;
            return bl2;
        }
        finally {
            this.Ba.unlock();
        }
    }

    private void el() {
        this.Ba.lock();
        try {
            this.Bh = 0;
            this.Bl.removeMessages(1);
            return;
        }
        finally {
            this.Ba.unlock();
        }
    }

    static /* synthetic */ long f(b b2) {
        return b2.Bk;
    }

    @Override
    public <C extends Api.a> C a(Api.c<C> object) {
        object = this.Bn.get(object);
        fq.b(object, (Object)"Appropriate Api was not requested.");
        return (C)object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public <A extends Api.a, T extends a.b<? extends Result, A>> T a(T t2) {
        this.Ba.lock();
        try {
            if (this.isConnected()) {
                this.b(t2);
                do {
                    return t2;
                    break;
                } while (true);
            }
            this.Bd.add(t2);
            return t2;
        }
        finally {
            this.Ba.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <A extends Api.a, T extends a.b<? extends Result, A>> T b(T t2) {
        boolean bl2 = this.isConnected() || this.ek();
        fq.a(bl2, "GoogleApiClient is not connected yet.");
        this.ej();
        try {
            this.a(t2);
        }
        catch (DeadObjectException var3_3) {
            this.E(1);
            return t2;
        }
        return t2;
    }

    /*
     * Exception decompiling
     */
    @Override
    public ConnectionResult blockingConnect(long var1_1, TimeUnit var3_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:371)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:449)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void connect() {
        block5 : {
            boolean bl2;
            this.Ba.lock();
            this.Bi = false;
            if (!this.isConnected() && !(bl2 = this.isConnecting())) break block5;
            this.Ba.unlock();
            return;
        }
        try {
            this.Bo = true;
            this.Be = null;
            this.Bg = 1;
            this.Bm.clear();
            this.Bj = this.Bn.size();
            Iterator<Api.a> iterator = this.Bn.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().connect();
            }
        }
        catch (Throwable var2_3) {
            throw var2_3;
        }
        finally {
            this.Ba.unlock();
        }
    }

    @Override
    public void disconnect() {
        this.el();
        this.E(-1);
    }

    @Override
    public Looper getLooper() {
        return this.AS;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isConnected() {
        this.Ba.lock();
        try {
            int n2 = this.Bg;
            boolean bl2 = n2 == 2;
            return bl2;
        }
        finally {
            this.Ba.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isConnecting() {
        boolean bl2 = true;
        this.Ba.lock();
        try {
            int n2 = this.Bg;
            if (n2 != 1) {
                bl2 = false;
            }
            return bl2;
        }
        finally {
            this.Ba.unlock();
        }
    }

    @Override
    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.Bc.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Override
    public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.Bc.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Override
    public void reconnect() {
        this.disconnect();
        this.connect();
    }

    @Override
    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.Bc.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Bc.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.Bc.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Bc.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    static interface a {
        public void b(c<?> var1);
    }

    class b
    extends Handler {
        b(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what == 1) {
                b.this.Ba.lock();
                try {
                    if (!b.this.isConnected() && !b.this.isConnecting()) {
                        b.this.connect();
                    }
                    return;
                }
                finally {
                    b.this.Ba.unlock();
                }
            }
            Log.wtf((String)"GoogleApiClientImpl", (String)"Don't know how to handle this message.");
        }
    }

    static interface c<A extends Api.a> {
        public void a(a var1);

        public void b(A var1) throws DeadObjectException;

        public void cancel();

        public Api.c<A> ea();

        public int ef();

        public void k(Status var1);
    }

}

