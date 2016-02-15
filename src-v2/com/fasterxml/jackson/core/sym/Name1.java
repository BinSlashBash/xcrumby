/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name1
extends Name {
    private static final Name1 EMPTY = new Name1("", 0, 0);
    private final int q;

    Name1(String string2, int n2, int n3) {
        super(string2, n2);
        this.q = n3;
    }

    public static Name1 getEmptyName() {
        return EMPTY;
    }

    @Override
    public boolean equals(int n2) {
        if (n2 == this.q) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(int n2, int n3) {
        if (n2 == this.q && n3 == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(int[] arrn, int n2) {
        if (n2 == 1 && arrn[0] == this.q) {
            return true;
        }
        return false;
    }
}

