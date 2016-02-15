package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.ff.C0392f;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.json.zip.JSONzip;

public final class fh implements Callback {
    private static final Object Du;
    private static fh Dv;
    private final HashMap<String, C0396a> Dw;
    private final Context lp;
    private final Handler mHandler;

    /* renamed from: com.google.android.gms.internal.fh.a */
    final class C0396a {
        private boolean DA;
        private IBinder DB;
        private ComponentName DC;
        final /* synthetic */ fh DD;
        private final String Dx;
        private final C0395a Dy;
        private final HashSet<C0392f> Dz;
        private int mState;

        /* renamed from: com.google.android.gms.internal.fh.a.a */
        public class C0395a implements ServiceConnection {
            final /* synthetic */ C0396a DE;

            public C0395a(C0396a c0396a) {
                this.DE = c0396a;
            }

            public void onServiceConnected(ComponentName component, IBinder binder) {
                synchronized (this.DE.DD.Dw) {
                    this.DE.DB = binder;
                    this.DE.DC = component;
                    Iterator it = this.DE.Dz.iterator();
                    while (it.hasNext()) {
                        ((C0392f) it.next()).onServiceConnected(component, binder);
                    }
                    this.DE.mState = 1;
                }
            }

            public void onServiceDisconnected(ComponentName component) {
                synchronized (this.DE.DD.Dw) {
                    this.DE.DB = null;
                    this.DE.DC = component;
                    Iterator it = this.DE.Dz.iterator();
                    while (it.hasNext()) {
                        ((C0392f) it.next()).onServiceDisconnected(component);
                    }
                    this.DE.mState = 2;
                }
            }
        }

        public C0396a(fh fhVar, String str) {
            this.DD = fhVar;
            this.Dx = str;
            this.Dy = new C0395a(this);
            this.Dz = new HashSet();
            this.mState = 0;
        }

        public void m932a(C0392f c0392f) {
            this.Dz.add(c0392f);
        }

        public void m933b(C0392f c0392f) {
            this.Dz.remove(c0392f);
        }

        public boolean m934c(C0392f c0392f) {
            return this.Dz.contains(c0392f);
        }

        public C0395a eP() {
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

        public void m935y(boolean z) {
            this.DA = z;
        }
    }

    static {
        Du = new Object();
    }

    private fh(Context context) {
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.Dw = new HashMap();
        this.lp = context.getApplicationContext();
    }

    public static fh m937x(Context context) {
        synchronized (Du) {
            if (Dv == null) {
                Dv = new fh(context.getApplicationContext());
            }
        }
        return Dv;
    }

    public boolean m938a(String str, C0392f c0392f) {
        boolean isBound;
        synchronized (this.Dw) {
            C0396a c0396a = (C0396a) this.Dw.get(str);
            if (c0396a != null) {
                this.mHandler.removeMessages(0, c0396a);
                if (!c0396a.m934c(c0392f)) {
                    c0396a.m932a((C0392f) c0392f);
                    switch (c0396a.getState()) {
                        case Std.STD_FILE /*1*/:
                            c0392f.onServiceConnected(c0396a.getComponentName(), c0396a.getBinder());
                            break;
                        case Std.STD_URL /*2*/:
                            c0396a.m935y(this.lp.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), c0396a.eP(), 129));
                            break;
                        default:
                            break;
                    }
                }
                throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  startServiceAction=" + str);
            }
            c0396a = new C0396a(this, str);
            c0396a.m932a((C0392f) c0392f);
            c0396a.m935y(this.lp.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), c0396a.eP(), 129));
            this.Dw.put(str, c0396a);
            isBound = c0396a.isBound();
        }
        return isBound;
    }

    public void m939b(String str, C0392f c0392f) {
        synchronized (this.Dw) {
            C0396a c0396a = (C0396a) this.Dw.get(str);
            if (c0396a == null) {
                throw new IllegalStateException("Nonexistent connection status for service action: " + str);
            } else if (c0396a.m934c(c0392f)) {
                c0396a.m933b(c0392f);
                if (c0396a.eR()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, c0396a), 5000);
                }
            } else {
                throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  startServiceAction=" + str);
            }
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case JSONzip.zipEmptyObject /*0*/:
                C0396a c0396a = (C0396a) msg.obj;
                synchronized (this.Dw) {
                    if (c0396a.eR()) {
                        this.lp.unbindService(c0396a.eP());
                        this.Dw.remove(c0396a.eQ());
                    }
                    break;
                }
                return true;
            default:
                return false;
        }
    }
}
