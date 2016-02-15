/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.internal.eo;
import com.google.android.gms.internal.er;
import com.google.android.gms.internal.et;
import java.io.IOException;

public abstract class em {
    protected final er yY;
    private final String yZ;
    private et za;

    protected em(String string2, String string3, String string4) {
        eo.W(string2);
        this.yZ = string2;
        this.yY = new er(string3);
        if (!TextUtils.isEmpty((CharSequence)string4)) {
            this.yY.ab(string4);
        }
    }

    public void U(String string2) {
    }

    public void a(long l2, int n2) {
    }

    public final void a(et et2) {
        this.za = et2;
        if (this.za == null) {
            this.dF();
        }
    }

    protected final void a(String string2, long l2, String string3) throws IOException {
        this.yY.a("Sending text message: %s to: %s", string2, string3);
        this.za.a(this.yZ, string2, l2, string3);
    }

    protected final long dE() {
        return this.za.dD();
    }

    public void dF() {
    }

    public final String getNamespace() {
        return this.yZ;
    }
}

