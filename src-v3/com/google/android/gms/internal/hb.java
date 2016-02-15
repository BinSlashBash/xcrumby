package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.location.C0428a;
import com.google.android.gms.location.C0428a.C0941a;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import java.util.HashMap;

public class hb {
    private final hf<ha> Ok;
    private ContentProviderClient Ol;
    private boolean Om;
    private HashMap<LocationListener, C1377b> On;
    private final Context mContext;

    /* renamed from: com.google.android.gms.internal.hb.a */
    private static class C0402a extends Handler {
        private final LocationListener Oo;

        public C0402a(LocationListener locationListener) {
            this.Oo = locationListener;
        }

        public C0402a(LocationListener locationListener, Looper looper) {
            super(looper);
            this.Oo = locationListener;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Std.STD_FILE /*1*/:
                    this.Oo.onLocationChanged(new Location((Location) msg.obj));
                default:
                    Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.hb.b */
    private static class C1377b extends C0941a {
        private Handler Op;

        C1377b(LocationListener locationListener, Looper looper) {
            this.Op = looper == null ? new C0402a(locationListener) : new C0402a(locationListener, looper);
        }

        public void onLocationChanged(Location location) {
            if (this.Op == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = location;
            this.Op.sendMessage(obtain);
        }

        public void release() {
            this.Op = null;
        }
    }

    public hb(Context context, hf<ha> hfVar) {
        this.Ol = null;
        this.Om = false;
        this.On = new HashMap();
        this.mContext = context;
        this.Ok = hfVar;
    }

    public Location getLastLocation() {
        this.Ok.bT();
        try {
            return ((ha) this.Ok.eM()).aW(this.mContext.getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void hQ() {
        if (this.Om) {
            setMockMode(false);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.On) {
                for (C0428a c0428a : this.On.values()) {
                    if (c0428a != null) {
                        ((ha) this.Ok.eM()).m1057a(c0428a);
                    }
                }
                this.On.clear();
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(PendingIntent callbackIntent) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).m1045a(callbackIntent);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(LocationListener listener) {
        this.Ok.bT();
        fq.m982b((Object) listener, (Object) "Invalid null listener");
        synchronized (this.On) {
            C0428a c0428a = (C1377b) this.On.remove(listener);
            if (this.Ol != null && this.On.isEmpty()) {
                this.Ol.release();
                this.Ol = null;
            }
            if (c0428a != null) {
                c0428a.release();
                try {
                    ((ha) this.Ok.eM()).m1057a(c0428a);
                } catch (Throwable e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).m1054a(request, callbackIntent);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
        this.Ok.bT();
        if (looper == null) {
            fq.m982b(Looper.myLooper(), (Object) "Can't create handler inside thread that has not called Looper.prepare()");
        }
        synchronized (this.On) {
            C0428a c1377b;
            C1377b c1377b2 = (C1377b) this.On.get(listener);
            if (c1377b2 == null) {
                c1377b = new C1377b(listener, looper);
            } else {
                Object obj = c1377b2;
            }
            this.On.put(listener, c1377b);
            try {
                ((ha) this.Ok.eM()).m1056a(request, c1377b, this.mContext.getPackageName());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void setMockLocation(Location mockLocation) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).setMockLocation(mockLocation);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void setMockMode(boolean isMockMode) {
        this.Ok.bT();
        try {
            ((ha) this.Ok.eM()).setMockMode(isMockMode);
            this.Om = isMockMode;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }
}
