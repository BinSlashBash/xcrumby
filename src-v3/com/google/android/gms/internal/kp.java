package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class kp<M extends kp<M>> extends kt {
    protected List<kv> adU;

    public final <T> T m2323a(kq<M, T> kqVar) {
        return kqVar.m1164f(this.adU);
    }

    public void m2324a(ko koVar) throws IOException {
        int size = this.adU == null ? 0 : this.adU.size();
        for (int i = 0; i < size; i++) {
            kv kvVar = (kv) this.adU.get(i);
            koVar.da(kvVar.tag);
            koVar.m1159p(kvVar.adZ);
        }
    }

    protected final boolean m2325a(kn knVar, int i) throws IOException {
        int position = knVar.getPosition();
        if (!knVar.cQ(i)) {
            return false;
        }
        if (this.adU == null) {
            this.adU = new ArrayList();
        }
        this.adU.add(new kv(i, knVar.m1128h(position, knVar.getPosition() - position)));
        return true;
    }

    protected int mx() {
        int i = 0;
        for (int i2 = 0; i2 < (this.adU == null ? 0 : this.adU.size()); i2++) {
            kv kvVar = (kv) this.adU.get(i2);
            i = (i + ko.db(kvVar.tag)) + kvVar.adZ.length;
        }
        return i;
    }
}
