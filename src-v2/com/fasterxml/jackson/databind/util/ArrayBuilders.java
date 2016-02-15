/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ArrayBuilders {
    private BooleanBuilder _booleanBuilder = null;
    private ByteBuilder _byteBuilder = null;
    private DoubleBuilder _doubleBuilder = null;
    private FloatBuilder _floatBuilder = null;
    private IntBuilder _intBuilder = null;
    private LongBuilder _longBuilder = null;
    private ShortBuilder _shortBuilder = null;

    public static <T> List<T> addToList(List<T> list, T t2) {
        List<T> list2 = list;
        if (list == null) {
            list2 = new ArrayList<T>();
        }
        list2.add(t2);
        return list2;
    }

    public static <T> ArrayList<T> arrayToList(T[] arrT) {
        ArrayList<T> arrayList = new ArrayList<T>();
        if (arrT != null) {
            int n2 = arrT.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                arrayList.add(arrT[i2]);
            }
        }
        return arrayList;
    }

    public static <T> HashSet<T> arrayToSet(T[] arrT) {
        HashSet<T> hashSet = new HashSet<T>();
        if (arrT != null) {
            int n2 = arrT.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                hashSet.add(arrT[i2]);
            }
        }
        return hashSet;
    }

    public static Object getArrayComparator(Object object) {
        int n2 = Array.getLength(object);
        return new Object(object.getClass(), n2, object){
            final /* synthetic */ Object val$defaultValue;
            final /* synthetic */ Class val$defaultValueType;
            final /* synthetic */ int val$length;

            /*
             * Enabled aggressive block sorting
             */
            public boolean equals(Object object) {
                if (object == this) {
                    return true;
                }
                if (object == null || object.getClass() != this.val$defaultValueType) {
                    return false;
                }
                if (Array.getLength(object) != this.val$length) {
                    return false;
                }
                int n2 = 0;
                while (n2 < this.val$length) {
                    Object object2;
                    Object object3 = Array.get(this.val$defaultValue, n2);
                    if (object3 != (object2 = Array.get(object, n2)) && object3 != null && !object3.equals(object2)) {
                        return false;
                    }
                    ++n2;
                }
                return true;
            }
        };
    }

    public static <T> T[] insertInList(T[] arrT, T t2) {
        int n2 = arrT.length;
        Object[] arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n2 + 1);
        if (n2 > 0) {
            System.arraycopy(arrT, 0, arrobject, 1, n2);
        }
        arrobject[0] = t2;
        return arrobject;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> T[] insertInListNoDup(T[] arrT, T arrobject) {
        int n2 = arrT.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (arrT[i2] != arrobject) continue;
            if (i2 == 0) {
                return arrT;
            }
            Object[] arrobject2 = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n2);
            System.arraycopy(arrT, 0, arrobject2, 1, i2);
            arrobject2[0] = arrobject;
            arrobject = arrobject2;
            if ((n2 -= ++i2) <= 0) return arrobject;
            System.arraycopy(arrT, i2, arrobject2, i2, n2);
            return arrobject2;
        }
        Object[] arrobject3 = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n2 + 1);
        if (n2 > 0) {
            System.arraycopy(arrT, 0, arrobject3, 1, n2);
        }
        arrobject3[0] = arrobject;
        return arrobject3;
    }

    public static <T> HashSet<T> setAndArray(Set<T> set, T[] arrT) {
        HashSet<T> hashSet = new HashSet<T>();
        if (set != null) {
            hashSet.addAll(set);
        }
        if (arrT != null) {
            int n2 = arrT.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                hashSet.add(arrT[i2]);
            }
        }
        return hashSet;
    }

    public BooleanBuilder getBooleanBuilder() {
        if (this._booleanBuilder == null) {
            this._booleanBuilder = new BooleanBuilder();
        }
        return this._booleanBuilder;
    }

    public ByteBuilder getByteBuilder() {
        if (this._byteBuilder == null) {
            this._byteBuilder = new ByteBuilder();
        }
        return this._byteBuilder;
    }

    public DoubleBuilder getDoubleBuilder() {
        if (this._doubleBuilder == null) {
            this._doubleBuilder = new DoubleBuilder();
        }
        return this._doubleBuilder;
    }

    public FloatBuilder getFloatBuilder() {
        if (this._floatBuilder == null) {
            this._floatBuilder = new FloatBuilder();
        }
        return this._floatBuilder;
    }

    public IntBuilder getIntBuilder() {
        if (this._intBuilder == null) {
            this._intBuilder = new IntBuilder();
        }
        return this._intBuilder;
    }

    public LongBuilder getLongBuilder() {
        if (this._longBuilder == null) {
            this._longBuilder = new LongBuilder();
        }
        return this._longBuilder;
    }

    public ShortBuilder getShortBuilder() {
        if (this._shortBuilder == null) {
            this._shortBuilder = new ShortBuilder();
        }
        return this._shortBuilder;
    }

    public static final class BooleanBuilder
    extends PrimitiveArrayBuilder<boolean[]> {
        @Override
        public final boolean[] _constructArray(int n2) {
            return new boolean[n2];
        }
    }

    public static final class ByteBuilder
    extends PrimitiveArrayBuilder<byte[]> {
        @Override
        public final byte[] _constructArray(int n2) {
            return new byte[n2];
        }
    }

    public static final class DoubleBuilder
    extends PrimitiveArrayBuilder<double[]> {
        @Override
        public final double[] _constructArray(int n2) {
            return new double[n2];
        }
    }

    public static final class FloatBuilder
    extends PrimitiveArrayBuilder<float[]> {
        @Override
        public final float[] _constructArray(int n2) {
            return new float[n2];
        }
    }

    public static final class IntBuilder
    extends PrimitiveArrayBuilder<int[]> {
        @Override
        public final int[] _constructArray(int n2) {
            return new int[n2];
        }
    }

    public static final class LongBuilder
    extends PrimitiveArrayBuilder<long[]> {
        @Override
        public final long[] _constructArray(int n2) {
            return new long[n2];
        }
    }

    public static final class ShortBuilder
    extends PrimitiveArrayBuilder<short[]> {
        @Override
        public final short[] _constructArray(int n2) {
            return new short[n2];
        }
    }

}

