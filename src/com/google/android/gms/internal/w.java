package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

class w
  implements y
{
  private dz kU;
  
  public w(dz paramdz)
  {
    this.kU = paramdz;
  }
  
  public void a(ab paramab, boolean paramBoolean)
  {
    HashMap localHashMap = new HashMap();
    if (paramBoolean) {}
    for (paramab = "1";; paramab = "0")
    {
      localHashMap.put("isVisible", paramab);
      this.kU.a("onAdVisibilityChanged", localHashMap);
      return;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/internal/w.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */