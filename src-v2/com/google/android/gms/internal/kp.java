/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.kq;
import com.google.android.gms.internal.kt;
import com.google.android.gms.internal.kv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class kp<M extends kp<M>>
extends kt {
    protected List<kv> adU;

    public final <T> T a(kq<M, T> kq2) {
        return kq2.f(this.adU);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(ko ko2) throws IOException {
        int n2 = this.adU == null ? 0 : this.adU.size();
        int n3 = 0;
        while (n3 < n2) {
            kv kv2 = this.adU.get(n3);
            ko2.da(kv2.tag);
            ko2.p(kv2.adZ);
            ++n3;
        }
    }

    protected final boolean a(kn kn2, int n2) throws IOException {
        int n3 = kn2.getPosition();
        if (!kn2.cQ(n2)) {
            return false;
        }
        if (this.adU == null) {
            this.adU = new ArrayList<kv>();
        }
        kn2 = (kn)kn2.h(n3, kn2.getPosition() - n3);
        this.adU.add(new kv(n2, (byte[])kn2));
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected int mx() {
        int n2 = this.adU == null ? 0 : this.adU.size();
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2) {
            kv kv2 = this.adU.get(n3);
            n4 = n4 + ko.db(kv2.tag) + kv2.adZ.length;
            ++n3;
        }
        return n4;
    }
}

