/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.internal.ff;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public final class fh
implements Handler.Callback {
    private static final Object Du = new Object();
    private static fh Dv;
    private final HashMap<String, a> Dw;
    private final Context lp;
    private final Handler mHandler;

    private fh(Context context) {
        this.mHandler = new Handler(context.getMainLooper(), (Handler.Callback)this);
        this.Dw = new HashMap();
        this.lp = context.getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static fh x(Context context) {
        Object object = Du;
        synchronized (object) {
            if (Dv == null) {
                Dv = new fh(context.getApplicationContext());
            }
            return Dv;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean a(String object, ff<?> intent) {
        HashMap<String, a> hashMap = this.Dw;
        synchronized (hashMap) {
            a a2 = this.Dw.get(object);
            if (a2 == null) {
                a2 = new a((String)object);
                a2.a(intent);
                intent = new Intent((String)object).setPackage("com.google.android.gms");
                a2.y(this.lp.bindService(intent, (ServiceConnection)a2.eP(), 129));
                this.Dw.put((String)object, a2);
                object = a2;
                return object.isBound();
            } else {
                this.mHandler.removeMessages(0, (Object)a2);
                if (a2.c(intent)) {
                    throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  startServiceAction=" + (String)object);
                }
                a2.a(intent);
                switch (a2.getState()) {
                    case 1: {
                        intent.onServiceConnected(a2.getComponentName(), a2.getBinder());
                        object = a2;
                        return object.isBound();
                    }
                    case 2: {
                        object = new Intent((String)object).setPackage("com.google.android.gms");
                        a2.y(this.lp.bindService((Intent)object, (ServiceConnection)a2.eP(), 129));
                        object = a2;
                        return object.isBound();
                    }
                    default: {
                        object = a2;
                    }
                }
            }
            return object.isBound();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(String string2, ff<?> ff2) {
        HashMap<String, a> hashMap = this.Dw;
        synchronized (hashMap) {
            a a2 = this.Dw.get(string2);
            if (a2 == null) {
                throw new IllegalStateException("Nonexistent connection status for service action: " + string2);
            }
            if (!a2.c(ff2)) {
                throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  startServiceAction=" + string2);
            }
            a2.b(ff2);
            if (a2.eR()) {
                string2 = this.mHandler.obtainMessage(0, (Object)a2);
                this.mHandler.sendMessageDelayed((Message)string2, 5000);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleMessage(Message object) {
        switch (object.what) {
            default: {
                return false;
            }
            case 0: 
        }
        a a2 = (a)object.obj;
        object = this.Dw;
        synchronized (object) {
            if (a2.eR()) {
                this.lp.unbindService((ServiceConnection)a2.eP());
                this.Dw.remove(a2.eQ());
            }
            return true;
        }
    }

    final class com.google.android.gms.internal.fh$a {
        private boolean DA;
        private IBinder DB;
        private ComponentName DC;
        private final String Dx;
        private final a Dy;
        private final HashSet<ff<?>> Dz;
        private int mState;

        public com.google.android.gms.internal.fh$a(String string2) {
            this.Dx = string2;
            this.Dy = new a();
            this.Dz = new HashSet();
            this.mState = 0;
        }

        public void a(ff<?> ff2) {
            this.Dz.add(ff2);
        }

        public void b(ff<?> ff2) {
            this.Dz.remove(ff2);
        }

        public boolean c(ff<?> ff2) {
            return this.Dz.contains(ff2);
        }

        public a eP() {
            return this.Dy;
        }

        public String eQ() {
            return this.Dx;
        }

        public boolean eR() {
            return this.Dz.isEmpty();
        }

        public IBinder getBinder() {
            return this.DB;
        }

        public ComponentName getComponentName() {
            return this.DC;
        }

        public int getState() {
            return this.mState;
        }

        public boolean isBound() {
            return this.DA;
        }

        public void y(boolean bl2) {
            this.DA = bl2;
        }

        public class a
        implements ServiceConnection {
            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                HashMap hashMap = fh.this.Dw;
                synchronized (hashMap) {
                    a.this.DB = iBinder;
                    a.this.DC = componentName;
                    Iterator iterator = a.this.Dz.iterator();
                    do {
                        if (!iterator.hasNext()) {
                            a.this.mState = 1;
                            return;
                        }
                        ((ff.f)iterator.next()).onServiceConnected(componentName, iBinder);
                    } while (true);
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceDisconnected(ComponentName componentName) {
                HashMap hashMap = fh.this.Dw;
                synchronized (hashMap) {
                    a.this.DB = null;
                    a.this.DC = componentName;
                    Iterator iterator = a.this.Dz.iterator();
                    do {
                        if (!iterator.hasNext()) {
                            a.this.mState = 2;
                            return;
                        }
                        ((ff.f)iterator.next()).onServiceDisconnected(componentName);
                    } while (true);
                }
            }
        }

    }

}

