package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d.a;
import java.util.Map;

class ax
  extends aj
{
  private static final String ID = a.ab.toString();
  private static final String WA = b.bH.toString();
  private final Context kI;
  
  public ax(Context paramContext)
  {
    super(ID, new String[0]);
    this.kI = paramContext;
  }
  
  public boolean jX()
  {
    return true;
  }
  
  public d.a x(Map<String, d.a> paramMap)
  {
    if ((d.a)paramMap.get(WA) != null) {}
    for (paramMap = dh.j((d.a)paramMap.get(WA));; paramMap = null)
    {
      paramMap = ay.d(this.kI, paramMap);
      if (paramMap == null) {
        break;
      }
      return dh.r(paramMap);
    }
    return dh.lT();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/tagmanager/ax.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */