package com.google.android.gms.internal;

import java.util.Arrays;

public final class kv {
    final byte[] adZ;
    final int tag;

    kv(int i, byte[] bArr) {
        this.tag = i;
        this.adZ = bArr;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof kv)) {
            return false;
        }
        kv kvVar = (kv) o;
        return this.tag == kvVar.tag && Arrays.equals(this.adZ, kvVar.adZ);
    }

    public int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.adZ);
    }
}
