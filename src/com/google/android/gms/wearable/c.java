package com.google.android.gms.wearable;

import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import java.util.Map;
import java.util.Set;

public abstract interface c
  extends Freezable<c>
{
  public abstract byte[] getData();
  
  public abstract Uri getUri();
  
  public abstract Map<String, d> ma();
  
  @Deprecated
  public abstract Set<String> mb();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/wearable/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */