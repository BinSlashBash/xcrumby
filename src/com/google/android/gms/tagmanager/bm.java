package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;

class bm
{
  int jZ()
  {
    return Build.VERSION.SDK_INT;
  }
  
  public bl kH()
  {
    if (jZ() < 8) {
      return new av();
    }
    return new aw();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/tagmanager/bm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */