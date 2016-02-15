package com.google.android.gms.internal;

import android.text.TextUtils;
import java.io.IOException;

public abstract class em {
    protected final er yY;
    private final String yZ;
    private et za;

    protected em(String str, String str2, String str3) {
        eo.m872W(str);
        this.yZ = str;
        this.yY = new er(str2);
        if (!TextUtils.isEmpty(str3)) {
            this.yY.ab(str3);
        }
    }

    public void m868U(String str) {
    }

    public void m869a(long j, int i) {
    }

    public final void m870a(et etVar) {
        this.za = etVar;
        if (this.za == null) {
            dF();
        }
    }

    protected final void m871a(String str, long j, String str2) throws IOException {
        this.yY.m896a("Sending text message: %s to: %s", str, str2);
        this.za.m901a(this.yZ, str, j, str2);
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
