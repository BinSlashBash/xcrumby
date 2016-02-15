/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class c<T extends SafeParcelable>
extends DataBuffer<T> {
    private static final String[] BF = new String[]{"data"};
    private final Parcelable.Creator<T> BG;

    public c(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.BG = creator;
    }

    public T F(int n2) {
        Object object = this.BB.getByteArray("data", n2, 0);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall((byte[])object, 0, object.length);
        parcel.setDataPosition(0);
        object = (SafeParcelable)this.BG.createFromParcel(parcel);
        parcel.recycle();
        return (T)object;
    }

    @Override
    public /* synthetic */ Object get(int n2) {
        return this.F(n2);
    }
}

