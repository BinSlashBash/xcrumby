package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class js
  implements SafeParcelable
{
  public static final Parcelable.Creator<js> CREATOR = new jt();
  String adn;
  String pm;
  private final int xH;
  
  js()
  {
    this.xH = 1;
  }
  
  js(int paramInt, String paramString1, String paramString2)
  {
    this.xH = paramInt;
    this.adn = paramString1;
    this.pm = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getVersionCode()
  {
    return this.xH;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    jt.a(this, paramParcel, paramInt);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/internal/js.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */