/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;

public abstract class d<T>
extends DataBuffer<T> {
    private boolean BW = false;
    private ArrayList<Integer> BX;

    protected d(DataHolder dataHolder) {
        super(dataHolder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void eu() {
        synchronized (this) {
            if (!this.BW) {
                int n2 = this.BB.getCount();
                this.BX = new ArrayList();
                if (n2 > 0) {
                    this.BX.add(0);
                    String string2 = this.getPrimaryDataMarkerColumn();
                    int n3 = this.BB.G(0);
                    String string3 = this.BB.getString(string2, 0, n3);
                    for (n3 = 1; n3 < n2; ++n3) {
                        int n4 = this.BB.G(n3);
                        String string4 = this.BB.getString(string2, n3, n4);
                        if (string4.equals(string3)) continue;
                        this.BX.add(n3);
                        string3 = string4;
                    }
                }
                this.BW = true;
            }
            return;
        }
    }

    int H(int n2) {
        if (n2 < 0 || n2 >= this.BX.size()) {
            throw new IllegalArgumentException("Position " + n2 + " is out of bounds for this buffer");
        }
        return this.BX.get(n2);
    }

    protected int I(int n2) {
        if (n2 < 0 || n2 == this.BX.size()) {
            return 0;
        }
        if (n2 == this.BX.size() - 1) {
            return this.BB.getCount() - this.BX.get(n2);
        }
        return this.BX.get(n2 + 1) - this.BX.get(n2);
    }

    protected abstract T c(int var1, int var2);

    @Override
    public final T get(int n2) {
        this.eu();
        return this.c(this.H(n2), this.I(n2));
    }

    @Override
    public int getCount() {
        this.eu();
        return this.BX.size();
    }

    protected abstract String getPrimaryDataMarkerColumn();
}

