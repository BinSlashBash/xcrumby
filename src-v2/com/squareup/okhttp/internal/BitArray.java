/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface BitArray {
    public void clear();

    public boolean get(int var1);

    public void set(int var1);

    public void shiftLeft(int var1);

    public void toggle(int var1);

    public static final class FixedCapacity
    implements BitArray {
        long data = 0;

        private static int checkInput(int n2) {
            if (n2 < 0 || n2 > 63) {
                throw new IllegalArgumentException(String.format("input must be between 0 and 63: %s", n2));
            }
            return n2;
        }

        @Override
        public void clear() {
            this.data = 0;
        }

        @Override
        public boolean get(int n2) {
            if ((this.data >> FixedCapacity.checkInput(n2) & 1) == 1) {
                return true;
            }
            return false;
        }

        @Override
        public void set(int n2) {
            this.data |= 1 << FixedCapacity.checkInput(n2);
        }

        @Override
        public void shiftLeft(int n2) {
            this.data <<= FixedCapacity.checkInput(n2);
        }

        public String toString() {
            return Long.toBinaryString(this.data);
        }

        public BitArray toVariableCapacity() {
            return new VariableCapacity(this);
        }

        @Override
        public void toggle(int n2) {
            this.data ^= 1 << FixedCapacity.checkInput(n2);
        }
    }

    public static final class VariableCapacity
    implements BitArray {
        long[] data;
        private int start;

        public VariableCapacity() {
            this.data = new long[1];
        }

        private VariableCapacity(FixedCapacity fixedCapacity) {
            this.data = new long[]{fixedCapacity.data, 0};
        }

        private static int checkInput(int n2) {
            if (n2 < 0) {
                throw new IllegalArgumentException(String.format("input must be a positive number: %s", n2));
            }
            return n2;
        }

        private void growToSize(int n2) {
            long[] arrl = new long[n2];
            if (this.data != null) {
                System.arraycopy(this.data, 0, arrl, 0, this.data.length);
            }
            this.data = arrl;
        }

        private int offsetOf(int n2) {
            if ((n2 = (n2 + this.start) / 64) > this.data.length - 1) {
                this.growToSize(n2 + 1);
            }
            return n2;
        }

        private int shiftOf(int n2) {
            return (this.start + n2) % 64;
        }

        @Override
        public void clear() {
            Arrays.fill(this.data, 0);
        }

        @Override
        public boolean get(int n2) {
            VariableCapacity.checkInput(n2);
            int n3 = this.offsetOf(n2);
            if ((this.data[n3] & 1 << this.shiftOf(n2)) != 0) {
                return true;
            }
            return false;
        }

        @Override
        public void set(int n2) {
            VariableCapacity.checkInput(n2);
            int n3 = this.offsetOf(n2);
            long[] arrl = this.data;
            arrl[n3] = arrl[n3] | 1 << this.shiftOf(n2);
        }

        @Override
        public void shiftLeft(int n2) {
            this.start -= VariableCapacity.checkInput(n2);
            if (this.start < 0) {
                n2 = this.start / -64 + 1;
                long[] arrl = new long[this.data.length + n2];
                System.arraycopy(this.data, 0, arrl, n2, this.data.length);
                this.data = arrl;
                this.start = this.start % 64 + 64;
            }
        }

        List<Integer> toIntegerList() {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            int n2 = this.data.length;
            int n3 = this.start;
            for (int i2 = 0; i2 < n2 * 64 - n3; ++i2) {
                if (!this.get(i2)) continue;
                arrayList.add(i2);
            }
            return arrayList;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("{");
            List<Integer> list = this.toIntegerList();
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                if (i2 > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(list.get(i2));
            }
            return stringBuilder.append('}').toString();
        }

        @Override
        public void toggle(int n2) {
            VariableCapacity.checkInput(n2);
            int n3 = this.offsetOf(n2);
            long[] arrl = this.data;
            arrl[n3] = arrl[n3] ^ 1 << this.shiftOf(n2);
        }
    }

}

