/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name3
extends Name {
    private final int q1;
    private final int q2;
    private final int q3;

    Name3(String string2, int n2, int n3, int n4, int n5) {
        super(string2, n2);
        this.q1 = n3;
        this.q2 = n4;
        this.q3 = n5;
    }

    @Override
    public boolean equals(int n2) {
        return false;
    }

    @Override
    public boolean equals(int n2, int n3) {
        return false;
    }

    @Override
    public boolean equals(int[] arrn, int n2) {
        if (n2 == 3 && arrn[0] == this.q1 && arrn[1] == this.q2 && arrn[2] == this.q3) {
            return true;
        }
        return false;
    }
}

