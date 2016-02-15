/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ContentProviderClient
 *  android.content.Context
 *  android.location.Location
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ha;
import com.google.android.gms.internal.hf;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.a;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class hb {
    private final hf<ha> Ok;
    private ContentProviderClient Ol = null;
    private boolean Om = false;
    private HashMap<LocationListener, b> On = new HashMap();
    private final Context mContext;

    public hb(Context context, hf<ha> hf2) {
        this.mContext = context;
        this.Ok = hf2;
    }

    public Location getLastLocation() {
        this.Ok.bT();
        try {
            Location location = this.Ok.eM().aW(this.mContext.getPackageName());
            return location;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    public void hQ() {
        if (this.Om) {
            this.setMockMode(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void removeAllListeners() {
        try {
            HashMap<LocationListener, b> hashMap = this.On;
            // MONITORENTER : hashMap
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
        Iterator<b> iterator = this.On.values().iterator();
        do {
            if (!iterator.hasNext()) {
                this.On.clear();
                // MONITOREXIT : hashMap
                return;
            }
            b b2 = iterator.next();
            if (b2 == null) continue;
            this.Ok.eM().a(b2);
        } while (true);
    }

    public void removeLocationUpdates(PendingIntent pendingIntent) {
        this.Ok.bT();
        try {
            this.Ok.eM().a(pendingIntent);
            return;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void removeLocationUpdates(LocationListener var1_1) {
        this.Ok.bT();
        fq.b(var1_1, (Object)"Invalid null listener");
        var2_3 = this.On;
        // MONITORENTER : var2_3
        var1_1 = this.On.remove(var1_1);
        if (this.Ol != null && this.On.isEmpty()) {
            this.Ol.release();
            this.Ol = null;
        }
        if (var1_1 == null) ** GOTO lbl13
        var1_1.release();
        try {
            this.Ok.eM().a((com.google.android.gms.location.a)var1_1);
lbl13: // 2 sources:
            // MONITOREXIT : var2_3
            return;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    public void requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.Ok.bT();
        try {
            this.Ok.eM().a(locationRequest, pendingIntent);
            return;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, Looper object) {
        this.Ok.bT();
        if (object == null) {
            fq.b(Looper.myLooper(), (Object)"Can't create handler inside thread that has not called Looper.prepare()");
        }
        HashMap<LocationListener, b> hashMap = this.On;
        synchronized (hashMap) {
            b b2 = this.On.get(locationListener);
            object = b2 == null ? new b(locationListener, (Looper)object) : b2;
            this.On.put(locationListener, (b)object);
            try {
                this.Ok.eM().a(locationRequest, (com.google.android.gms.location.a)object, this.mContext.getPackageName());
                return;
            }
            catch (RemoteException var1_2) {
                throw new IllegalStateException((Throwable)var1_2);
            }
        }
    }

    public void setMockLocation(Location location) {
        this.Ok.bT();
        try {
            this.Ok.eM().setMockLocation(location);
            return;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    public void setMockMode(boolean bl2) {
        this.Ok.bT();
        try {
            this.Ok.eM().setMockMode(bl2);
        }
        catch (RemoteException var2_2) {
            throw new IllegalStateException((Throwable)var2_2);
        }
        this.Om = bl2;
    }

    private static class a
    extends Handler {
        private final LocationListener Oo;

        public a(LocationListener locationListener) {
            this.Oo = locationListener;
        }

        public a(LocationListener locationListener, Looper looper) {
            super(looper);
            this.Oo = locationListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.e((String)"LocationClientHelper", (String)"unknown message in LocationHandler.handleMessage");
                    return;
                }
                case 1: 
            }
            message = new Location((Location)message.obj);
            this.Oo.onLocationChanged((Location)message);
        }
    }

    private static class b
    extends a.a {
        private Handler Op;

        /*
         * Enabled aggressive block sorting
         */
        b(LocationListener object, Looper looper) {
            object = looper == null ? new a((LocationListener)object) : new a((LocationListener)object, looper);
            this.Op = object;
        }

        @Override
        public void onLocationChanged(Location location) {
            if (this.Op == null) {
                Log.e((String)"LocationClientHelper", (String)"Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message message = Message.obtain();
            message.what = 1;
            message.obj = location;
            this.Op.sendMessage(message);
        }

        public void release() {
            this.Op = null;
        }
    }

}

