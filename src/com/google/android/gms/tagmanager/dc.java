package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.d.a;
import java.util.Map;

abstract class dc
  extends cc
{
  public dc(String paramString)
  {
    super(paramString);
  }
  
  protected boolean a(d.a parama1, d.a parama2, Map<String, d.a> paramMap)
  {
    parama1 = dh.j(parama1);
    parama2 = dh.j(parama2);
    if ((parama1 == dh.lS()) || (parama2 == dh.lS())) {
      return false;
    }
    return a(parama1, parama2, paramMap);
  }
  
  protected abstract boolean a(String paramString1, String paramString2, Map<String, d.a> paramMap);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/tagmanager/dc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */