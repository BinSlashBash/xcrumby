/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class c {
    public static <T extends SafeParcelable> byte[] a(T object) {
        Parcel parcel = Parcel.obtain();
        object.writeToParcel(parcel, 0);
        object = parcel.marshall();
        parcel.recycle();
        return object;
    }
}

