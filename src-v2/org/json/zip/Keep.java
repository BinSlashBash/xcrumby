/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import org.json.zip.JSONzip;
import org.json.zip.None;
import org.json.zip.PostMortem;

abstract class Keep
implements None,
PostMortem {
    protected int capacity;
    protected int length;
    protected int power;
    protected long[] uses;

    public Keep(int n2) {
        this.capacity = JSONzip.twos[n2];
        this.length = 0;
        this.power = 0;
        this.uses = new long[this.capacity];
    }

    public static long age(long l2) {
        if (l2 >= 32) {
            return 16;
        }
        return l2 / 2;
    }

    public int bitsize() {
        while (JSONzip.twos[this.power] < this.length) {
            ++this.power;
        }
        return this.power;
    }

    public void tick(int n2) {
        long[] arrl = this.uses;
        arrl[n2] = arrl[n2] + 1;
    }

    public abstract Object value(int var1);
}

