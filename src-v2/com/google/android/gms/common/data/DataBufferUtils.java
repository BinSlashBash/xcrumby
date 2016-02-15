/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataBufferUtils {
    private DataBufferUtils() {
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> dataBuffer) {
        ArrayList arrayList;
        arrayList = new ArrayList(dataBuffer.getCount());
        try {
            Iterator<E> iterator = dataBuffer.iterator();
            while (iterator.hasNext()) {
                arrayList.add(((Freezable)iterator.next()).freeze());
            }
        }
        finally {
            dataBuffer.close();
        }
        return arrayList;
    }

    public static boolean hasNextPage(DataBuffer<?> bundle) {
        if ((bundle = bundle.getMetadata()) != null && bundle.getString("next_page_token") != null) {
            return true;
        }
        return false;
    }

    public static boolean hasPrevPage(DataBuffer<?> bundle) {
        if ((bundle = bundle.getMetadata()) != null && bundle.getString("prev_page_token") != null) {
            return true;
        }
        return false;
    }
}

