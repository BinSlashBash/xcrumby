/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.google.android.gms.internal;

import android.util.Base64;
import com.google.android.gms.internal.n;

class e
implements n {
    e() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String a(byte[] arrby, boolean bl2) {
        int n2;
        if (bl2) {
            n2 = 11;
            do {
                return Base64.encodeToString((byte[])arrby, (int)n2);
                break;
            } while (true);
        }
        n2 = 2;
        return Base64.encodeToString((byte[])arrby, (int)n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] a(String string2, boolean bl2) throws IllegalArgumentException {
        int n2;
        if (bl2) {
            n2 = 11;
            do {
                return Base64.decode((String)string2, (int)n2);
                break;
            } while (true);
        }
        n2 = 2;
        return Base64.decode((String)string2, (int)n2);
    }
}

