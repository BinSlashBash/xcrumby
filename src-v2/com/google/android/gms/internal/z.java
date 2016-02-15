/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.dx;
import org.json.JSONObject;

public final class z {
    private final String le;
    private final JSONObject lf;
    private final String lg;
    private final String lh;

    public z(String string2, dx dx2, String string3, JSONObject jSONObject) {
        this.lh = dx2.rq;
        this.lf = jSONObject;
        this.lg = string2;
        this.le = string3;
    }

    public String al() {
        return this.le;
    }

    public String am() {
        return this.lh;
    }

    public JSONObject an() {
        return this.lf;
    }

    public String ao() {
        return this.lg;
    }
}

