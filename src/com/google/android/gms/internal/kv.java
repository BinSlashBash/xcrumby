package com.google.android.gms.internal;

import java.util.Arrays;

public final class kv
{
  final byte[] adZ;
  final int tag;
  
  kv(int paramInt, byte[] paramArrayOfByte)
  {
    this.tag = paramInt;
    this.adZ = paramArrayOfByte;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (!(paramObject instanceof kv)) {
        return false;
      }
      paramObject = (kv)paramObject;
    } while ((this.tag == ((kv)paramObject).tag) && (Arrays.equals(this.adZ, ((kv)paramObject).adZ)));
    return false;
  }
  
  public int hashCode()
  {
    return (this.tag + 527) * 31 + Arrays.hashCode(this.adZ);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/internal/kv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */