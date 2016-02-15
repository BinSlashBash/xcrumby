package com.google.android.gms.common.internal.safeparcel;

import android.os.Parcel;

public final class c
{
  public static <T extends SafeParcelable> byte[] a(T paramT)
  {
    Parcel localParcel = Parcel.obtain();
    paramT.writeToParcel(localParcel, 0);
    paramT = localParcel.marshall();
    localParcel.recycle();
    return paramT;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/common/internal/safeparcel/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */