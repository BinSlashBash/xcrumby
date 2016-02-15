package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OnSyncMoreResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<OnSyncMoreResponse> CREATOR = new ag();
  final boolean Fg;
  final int xH;
  
  OnSyncMoreResponse(int paramInt, boolean paramBoolean)
  {
    this.xH = paramInt;
    this.Fg = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ag.a(this, paramParcel, paramInt);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/drive/internal/OnSyncMoreResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */