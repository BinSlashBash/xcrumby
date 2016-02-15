/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.ads.mediation.customevent;

import com.google.ads.mediation.NetworkExtras;
import java.util.HashMap;

public final class CustomEventExtras
implements NetworkExtras {
    private final HashMap<String, Object> rQ = new HashMap();

    public Object getExtra(String string2) {
        return this.rQ.get(string2);
    }

    public void setExtra(String string2, Object object) {
        this.rQ.put(string2, object);
    }
}

