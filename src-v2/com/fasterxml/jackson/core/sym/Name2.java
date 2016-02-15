/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name2
extends Name {
    private final int q1;
    private final int q2;

    Name2(String string2, int n2, int n3, int n4) {
        super(string2, n2);
        this.q1 = n3;
        this.q2 = n4;
    }

    @Override
    public boolean equals(int n2) {
        return false;
    }

    @Override
    public boolean equals(int n2, int n3) {
        if (n2 == this.q1 && n3 == this.q2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(int[] arrn, int n2) {
        if (n2 == 2 && arrn[0] == this.q1 && arrn[1] == this.q2) {
            return true;
        }
        return false;
    }
}

