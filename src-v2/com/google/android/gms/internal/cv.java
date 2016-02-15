/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.ax;
import com.google.android.gms.internal.bg;
import com.google.android.gms.internal.cu;
import com.google.android.gms.internal.cw;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.db;
import com.google.android.gms.internal.dc;
import com.google.android.gms.internal.do;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;

public abstract class cv
extends do {
    private final cx mQ;
    private final cu.a pc;

    public cv(cx cx2, cu.a a2) {
        this.mQ = cx2;
        this.pc = a2;
    }

    private static cz a(db object, cx cx2) {
        try {
            object = object.b(cx2);
            return object;
        }
        catch (RemoteException var0_1) {
            dw.c("Could not fetch ad response from ad request service.", (Throwable)var0_1);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void aY() {
        try {
            Object object = this.bf();
            if (object == null) {
                object = new cz(0);
            } else {
                cz cz2;
                object = cz2 = cv.a((db)object, this.mQ);
                if (cz2 == null) {
                    object = new cz(0);
                }
            }
            this.pc.a((cz)object);
            return;
        }
        finally {
            this.be();
        }
    }

    public abstract void be();

    public abstract db bf();

    @Override
    public final void onStop() {
        this.be();
    }

    public static final class a
    extends cv {
        private final Context mContext;

        public a(Context context, cx cx2, cu.a a2) {
            super(cx2, a2);
            this.mContext = context;
        }

        @Override
        public void be() {
        }

        @Override
        public db bf() {
            ax ax2 = new ax();
            return dc.a(this.mContext, ax2, new bg());
        }
    }

    public static final class b
    extends cv
    implements GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener {
        private final Object li = new Object();
        private final cu.a pc;
        private final cw pd;

        public b(Context context, cx cx2, cu.a a2) {
            super(cx2, a2);
            this.pc = a2;
            this.pd = new cw(context, this, this, cx2.kK.rs);
            this.pd.connect();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void be() {
            Object object = this.li;
            synchronized (object) {
                if (this.pd.isConnected() || this.pd.isConnecting()) {
                    this.pd.disconnect();
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
        public db bf() {
            Object object = this.li;
            synchronized (object) {
                try {
                    return this.pd.bi();
                }
                catch (IllegalStateException var2_3) {
                    return null;
                }
            }
        }

        @Override
        public void onConnected(Bundle bundle) {
            this.start();
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            this.pc.a(new cz(0));
        }

        @Override
        public void onDisconnected() {
            dw.v("Disconnected from remote ad request service.");
        }
    }

}

