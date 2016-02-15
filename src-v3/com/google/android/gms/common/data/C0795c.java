package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* renamed from: com.google.android.gms.common.data.c */
public class C0795c<T extends SafeParcelable> extends DataBuffer<T> {
    private static final String[] BF;
    private final Creator<T> BG;

    static {
        BF = new String[]{"data"};
    }

    public C0795c(DataHolder dataHolder, Creator<T> creator) {
        super(dataHolder);
        this.BG = creator;
    }

    public T m1689F(int i) {
        byte[] byteArray = this.BB.getByteArray("data", i, 0);
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(byteArray, 0, byteArray.length);
        obtain.setDataPosition(0);
        SafeParcelable safeParcelable = (SafeParcelable) this.BG.createFromParcel(obtain);
        obtain.recycle();
        return safeParcelable;
    }

    public /* synthetic */ Object get(int x0) {
        return m1689F(x0);
    }
}
