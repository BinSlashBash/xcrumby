/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.os.SystemClock;
import com.google.android.gms.internal.er;
import com.google.android.gms.internal.eu;
import org.json.JSONObject;

public final class ev {
    public static final Object Ab;
    private static final er zb;
    private eu Aa;
    private long zX;
    private long zY;
    private long zZ;

    static {
        zb = new er("RequestTracker");
        Ab = new Object();
    }

    public ev(long l2) {
        this.zX = l2;
        this.zY = -1;
        this.zZ = 0;
    }

    private void dT() {
        this.zY = -1;
        this.Aa = null;
        this.zZ = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void a(long l2, eu eu2) {
        Object object = Ab;
        // MONITORENTER : object
        eu eu3 = this.Aa;
        long l3 = this.zY;
        this.zY = l2;
        this.Aa = eu2;
        this.zZ = SystemClock.elapsedRealtime();
        // MONITOREXIT : object
        if (eu3 == null) return;
        eu3.l(l3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean b(long l2, int n2, JSONObject jSONObject) {
        boolean bl2 = true;
        eu eu2 = null;
        Object object = Ab;
        // MONITORENTER : object
        if (this.zY != -1 && this.zY == l2) {
            zb.b("request %d completed", this.zY);
            eu2 = this.Aa;
            this.dT();
        } else {
            bl2 = false;
        }
        // MONITOREXIT : object
        if (eu2 == null) return bl2;
        eu2.a(l2, n2, jSONObject);
        return bl2;
    }

    public boolean c(long l2, int n2) {
        return this.b(l2, n2, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clear() {
        Object object = Ab;
        synchronized (object) {
            if (this.zY != -1) {
                this.dT();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean d(long l2, int n2) {
        eu eu2;
        boolean bl2 = true;
        long l3 = 0;
        Object object = Ab;
        // MONITORENTER : object
        if (this.zY != -1 && l2 - this.zZ >= this.zX) {
            zb.b("request %d timed out", this.zY);
            l2 = this.zY;
            eu2 = this.Aa;
            this.dT();
        } else {
            bl2 = false;
            eu2 = null;
            l2 = l3;
        }
        // MONITOREXIT : object
        if (eu2 == null) return bl2;
        eu2.a(l2, n2, null);
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean dU() {
        Object object = Ab;
        synchronized (object) {
            if (this.zY == -1) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean n(long l2) {
        Object object = Ab;
        synchronized (object) {
            if (this.zY == -1) return false;
            if (this.zY != l2) return false;
            return true;
        }
    }
}

