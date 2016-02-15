package com.google.android.gms.common.data;

import java.util.ArrayList;

/* renamed from: com.google.android.gms.common.data.d */
public abstract class C0796d<T> extends DataBuffer<T> {
    private boolean BW;
    private ArrayList<Integer> BX;

    protected C0796d(DataHolder dataHolder) {
        super(dataHolder);
        this.BW = false;
    }

    private void eu() {
        synchronized (this) {
            if (!this.BW) {
                int count = this.BB.getCount();
                this.BX = new ArrayList();
                if (count > 0) {
                    this.BX.add(Integer.valueOf(0));
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    String string = this.BB.getString(primaryDataMarkerColumn, 0, this.BB.m1687G(0));
                    int i = 1;
                    while (i < count) {
                        String string2 = this.BB.getString(primaryDataMarkerColumn, i, this.BB.m1687G(i));
                        if (string2.equals(string)) {
                            string2 = string;
                        } else {
                            this.BX.add(Integer.valueOf(i));
                        }
                        i++;
                        string = string2;
                    }
                }
                this.BW = true;
            }
        }
    }

    int m1690H(int i) {
        if (i >= 0 && i < this.BX.size()) {
            return ((Integer) this.BX.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    protected int m1691I(int i) {
        return (i < 0 || i == this.BX.size()) ? 0 : i == this.BX.size() + -1 ? this.BB.getCount() - ((Integer) this.BX.get(i)).intValue() : ((Integer) this.BX.get(i + 1)).intValue() - ((Integer) this.BX.get(i)).intValue();
    }

    protected abstract T m1692c(int i, int i2);

    public final T get(int position) {
        eu();
        return m1692c(m1690H(position), m1691I(position));
    }

    public int getCount() {
        eu();
        return this.BX.size();
    }

    protected abstract String getPrimaryDataMarkerColumn();
}
