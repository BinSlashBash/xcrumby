/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.sym;

public abstract class Name {
    protected final int _hashCode;
    protected final String _name;

    protected Name(String string2, int n2) {
        this._name = string2;
        this._hashCode = n2;
    }

    public abstract boolean equals(int var1);

    public abstract boolean equals(int var1, int var2);

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        return false;
    }

    public abstract boolean equals(int[] var1, int var2);

    public String getName() {
        return this._name;
    }

    public final int hashCode() {
        return this._hashCode;
    }

    public String toString() {
        return this._name;
    }
}

