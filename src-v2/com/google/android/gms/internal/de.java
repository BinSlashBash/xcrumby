/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.dn;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dz;
import java.util.Map;

public final class de {
    private dz lC;
    private final Object li = new Object();
    private int oS = -2;
    private String pI;
    private String pJ;
    public final bb pK;
    public final bb pL;

    public de(String string2) {
        this.pK = new bb(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void b(dz object, Map<String, String> object2) {
                object = de.this.li;
                synchronized (object) {
                    String string2 = object2.get("type");
                    object2 = object2.get("errors");
                    dw.z("Invalid " + string2 + " request error: " + (String)object2);
                    de.this.oS = 1;
                    de.this.li.notify();
                    return;
                }
            }
        };
        this.pL = new bb(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void b(dz dz2, Map<String, String> map) {
                Object object = de.this.li;
                synchronized (object) {
                    String string2 = map.get("url");
                    if (string2 == null) {
                        dw.z("URL missing in loadAdUrl GMSG.");
                        return;
                    }
                    String string3 = string2;
                    if (string2.contains("%40mediation_adapters%40")) {
                        string3 = string2.replaceAll("%40mediation_adapters%40", dn.b(dz2.getContext(), map.get("check_adapters"), de.this.pI));
                        dw.y("Ad request URL modified to " + string3);
                    }
                    de.this.pJ = string3;
                    de.this.li.notify();
                    return;
                }
            }
        };
        this.pI = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(dz dz2) {
        Object object = this.li;
        synchronized (object) {
            this.lC = dz2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String bj() {
        Object object = this.li;
        synchronized (object) {
            while (this.pJ == null) {
                int n2 = this.oS;
                if (n2 != -2) return this.pJ;
                try {
                    this.li.wait();
                    continue;
                }
                catch (InterruptedException var3_3) {
                    dw.z("Ad request service was interrupted.");
                    return null;
                }
            }
            return this.pJ;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getErrorCode() {
        Object object = this.li;
        synchronized (object) {
            return this.oS;
        }
    }

}

