/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;
import java.util.Arrays;

public final class NameN
extends Name {
    private final int[] q;
    private final int q1;
    private final int q2;
    private final int q3;
    private final int q4;
    private final int qlen;

    NameN(String string2, int n2, int n3, int n4, int n5, int n6, int[] arrn, int n7) {
        super(string2, n2);
        this.q1 = n3;
        this.q2 = n4;
        this.q3 = n5;
        this.q4 = n6;
        this.q = arrn;
        this.qlen = n7;
    }

    private final boolean _equals2(int[] arrn) {
        int n2 = this.qlen;
        for (int i2 = 0; i2 < n2 - 4; ++i2) {
            if (arrn[i2 + 4] == this.q[i2]) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static NameN construct(String string2, int n2, int[] object, int n3) {
        if (n3 < 4) {
            throw new IllegalArgumentException();
        }
        Object object2 = object[0];
        Object object3 = object[1];
        Object object4 = object[2];
        Object object5 = object[3];
        if (n3 - 4 > 0) {
            object = Arrays.copyOfRange((int[])object, 4, n3);
            do {
                return new NameN(string2, n2, (int)object2, (int)object3, (int)object4, (int)object5, (int[])object, n3);
                break;
            } while (true);
        }
        object = null;
        return new NameN(string2, n2, (int)object2, (int)object3, (int)object4, (int)object5, (int[])object, n3);
    }

    @Override
    public boolean equals(int n2) {
        return false;
    }

    @Override
    public boolean equals(int n2, int n3) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(int[] arrn, int n2) {
        if (n2 != this.qlen) {
            return false;
        }
        if (arrn[0] != this.q1) return false;
        if (arrn[1] != this.q2) return false;
        if (arrn[2] != this.q3) return false;
        if (arrn[3] != this.q4) return false;
        switch (n2) {
            default: {
                return this._equals2(arrn);
            }
            case 8: {
                if (arrn[7] != this.q[3]) return false;
            }
            case 7: {
                if (arrn[6] != this.q[2]) return false;
            }
            case 6: {
                if (arrn[5] != this.q[1]) return false;
            }
            case 5: {
                if (arrn[4] != this.q[0]) return false;
            }
            case 4: 
        }
        return true;
    }
}

