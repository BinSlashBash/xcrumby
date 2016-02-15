/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.math.BigDecimal;

public final class LazilyParsedNumber
extends Number {
    private final String value;

    public LazilyParsedNumber(String string2) {
        this.value = string2;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new BigDecimal(this.value);
    }

    @Override
    public double doubleValue() {
        return Double.parseDouble(this.value);
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(this.value);
    }

    @Override
    public int intValue() {
        try {
            int n2 = Integer.parseInt(this.value);
            return n2;
        }
        catch (NumberFormatException var4_2) {
            long l2;
            try {
                l2 = Long.parseLong(this.value);
            }
            catch (NumberFormatException var4_3) {
                return new BigDecimal(this.value).intValue();
            }
            return (int)l2;
        }
    }

    @Override
    public long longValue() {
        try {
            long l2 = Long.parseLong(this.value);
            return l2;
        }
        catch (NumberFormatException var3_2) {
            return new BigDecimal(this.value).longValue();
        }
    }

    public String toString() {
        return this.value;
    }
}

