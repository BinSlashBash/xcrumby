/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.Iterator;

public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList2.add(((Freezable)arrayList.get(i2)).freeze());
        }
        return arrayList2;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] arrE) {
        ArrayList arrayList = new ArrayList(arrE.length);
        for (int i2 = 0; i2 < arrE.length; ++i2) {
            arrayList.add(arrE[i2].freeze());
        }
        return arrayList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> object) {
        ArrayList arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((Freezable)object.next()).freeze());
        }
        return arrayList;
    }
}

