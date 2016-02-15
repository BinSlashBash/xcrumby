/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

class dg
extends Number
implements Comparable<dg> {
    private double aaC;
    private long aaD;
    private boolean aaE;

    private dg(double d2) {
        this.aaC = d2;
        this.aaE = false;
    }

    private dg(long l2) {
        this.aaD = l2;
        this.aaE = true;
    }

    public static dg a(Double d2) {
        return new dg(d2);
    }

    public static dg bW(String string2) throws NumberFormatException {
        try {
            dg dg2 = new dg(Long.parseLong(string2));
            return dg2;
        }
        catch (NumberFormatException var1_2) {
            try {
                dg dg3 = new dg(Double.parseDouble(string2));
                return dg3;
            }
            catch (NumberFormatException var1_4) {
                throw new NumberFormatException(string2 + " is not a valid TypedNumber");
            }
        }
    }

    public static dg w(long l2) {
        return new dg(l2);
    }

    public int a(dg dg2) {
        if (this.lJ() && dg2.lJ()) {
            return new Long(this.aaD).compareTo(dg2.aaD);
        }
        return Double.compare(this.doubleValue(), dg2.doubleValue());
    }

    @Override
    public byte byteValue() {
        return (byte)this.longValue();
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.a((dg)object);
    }

    @Override
    public double doubleValue() {
        if (this.lJ()) {
            return this.aaD;
        }
        return this.aaC;
    }

    public boolean equals(Object object) {
        if (object instanceof dg && this.a((dg)object) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public float floatValue() {
        return (float)this.doubleValue();
    }

    public int hashCode() {
        return new Long(this.longValue()).hashCode();
    }

    @Override
    public int intValue() {
        return this.lL();
    }

    public boolean lI() {
        if (!this.lJ()) {
            return true;
        }
        return false;
    }

    public boolean lJ() {
        return this.aaE;
    }

    public long lK() {
        if (this.lJ()) {
            return this.aaD;
        }
        return (long)this.aaC;
    }

    public int lL() {
        return (int)this.longValue();
    }

    public short lM() {
        return (short)this.longValue();
    }

    @Override
    public long longValue() {
        return this.lK();
    }

    @Override
    public short shortValue() {
        return this.lM();
    }

    public String toString() {
        if (this.lJ()) {
            return Long.toString(this.aaD);
        }
        return Double.toString(this.aaC);
    }
}

