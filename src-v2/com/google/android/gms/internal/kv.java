/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import java.util.Arrays;

public final class kv {
    final byte[] adZ;
    final int tag;

    kv(int n2, byte[] arrby) {
        this.tag = n2;
        this.adZ = arrby;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof kv)) {
            return false;
        }
        object = (kv)object;
        if (this.tag != object.tag) return false;
        if (Arrays.equals(this.adZ, object.adZ)) return true;
        return false;
    }

    public int hashCode() {
        return (this.tag + 527) * 31 + Arrays.hashCode(this.adZ);
    }
}

