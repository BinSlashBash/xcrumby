package org.json.zip;

abstract class Keep implements None, PostMortem {
    protected int capacity;
    protected int length;
    protected int power;
    protected long[] uses;

    public abstract Object value(int i);

    public Keep(int bits) {
        this.capacity = JSONzip.twos[bits];
        this.length = 0;
        this.power = 0;
        this.uses = new long[this.capacity];
    }

    public static long age(long use) {
        return use >= 32 ? 16 : use / 2;
    }

    public int bitsize() {
        while (JSONzip.twos[this.power] < this.length) {
            this.power++;
        }
        return this.power;
    }

    public void tick(int integer) {
        long[] jArr = this.uses;
        jArr[integer] = jArr[integer] + 1;
    }
}
