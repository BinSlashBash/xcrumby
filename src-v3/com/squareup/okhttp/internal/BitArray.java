package com.squareup.okhttp.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface BitArray {

    public static final class FixedCapacity implements BitArray {
        long data;

        public FixedCapacity() {
            this.data = 0;
        }

        public void clear() {
            this.data = 0;
        }

        public void set(int index) {
            this.data |= 1 << checkInput(index);
        }

        public void toggle(int index) {
            this.data ^= 1 << checkInput(index);
        }

        public boolean get(int index) {
            return ((this.data >> checkInput(index)) & 1) == 1;
        }

        public void shiftLeft(int count) {
            this.data <<= checkInput(count);
        }

        public String toString() {
            return Long.toBinaryString(this.data);
        }

        public BitArray toVariableCapacity() {
            return new VariableCapacity();
        }

        private static int checkInput(int index) {
            if (index >= 0 && index <= 63) {
                return index;
            }
            throw new IllegalArgumentException(String.format("input must be between 0 and 63: %s", new Object[]{Integer.valueOf(index)}));
        }
    }

    public static final class VariableCapacity implements BitArray {
        long[] data;
        private int start;

        public VariableCapacity() {
            this.data = new long[1];
        }

        private VariableCapacity(FixedCapacity small) {
            this.data = new long[]{small.data, 0};
        }

        private void growToSize(int size) {
            long[] newData = new long[size];
            if (this.data != null) {
                System.arraycopy(this.data, 0, newData, 0, this.data.length);
            }
            this.data = newData;
        }

        private int offsetOf(int index) {
            int offset = (index + this.start) / 64;
            if (offset > this.data.length - 1) {
                growToSize(offset + 1);
            }
            return offset;
        }

        private int shiftOf(int index) {
            return (this.start + index) % 64;
        }

        public void clear() {
            Arrays.fill(this.data, 0);
        }

        public void set(int index) {
            checkInput(index);
            int offset = offsetOf(index);
            long[] jArr = this.data;
            jArr[offset] = jArr[offset] | (1 << shiftOf(index));
        }

        public void toggle(int index) {
            checkInput(index);
            int offset = offsetOf(index);
            long[] jArr = this.data;
            jArr[offset] = jArr[offset] ^ (1 << shiftOf(index));
        }

        public boolean get(int index) {
            checkInput(index);
            return (this.data[offsetOf(index)] & (1 << shiftOf(index))) != 0;
        }

        public void shiftLeft(int count) {
            this.start -= checkInput(count);
            if (this.start < 0) {
                int arrayShift = (this.start / -64) + 1;
                long[] newData = new long[(this.data.length + arrayShift)];
                System.arraycopy(this.data, 0, newData, arrayShift, this.data.length);
                this.data = newData;
                this.start = (this.start % 64) + 64;
            }
        }

        public String toString() {
            StringBuilder builder = new StringBuilder("{");
            List<Integer> ints = toIntegerList();
            int count = ints.size();
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    builder.append(',');
                }
                builder.append(ints.get(i));
            }
            return builder.append('}').toString();
        }

        List<Integer> toIntegerList() {
            List<Integer> ints = new ArrayList();
            int count = (this.data.length * 64) - this.start;
            for (int i = 0; i < count; i++) {
                if (get(i)) {
                    ints.add(Integer.valueOf(i));
                }
            }
            return ints;
        }

        private static int checkInput(int index) {
            if (index >= 0) {
                return index;
            }
            throw new IllegalArgumentException(String.format("input must be a positive number: %s", new Object[]{Integer.valueOf(index)}));
        }
    }

    void clear();

    boolean get(int i);

    void set(int i);

    void shiftLeft(int i);

    void toggle(int i);
}
