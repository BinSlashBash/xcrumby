package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d.a;
import java.util.Map;

class u
  extends aj
{
  private static final String ID = a.C.toString();
  private static final String NAME = b.dc.toString();
  private static final String XA = b.cb.toString();
  private final DataLayer WK;
  
  public u(DataLayer paramDataLayer)
  {
    super(ID, new String[] { NAME });
    this.WK = paramDataLayer;
  }
  
  public boolean jX()
  {
    return false;
  }
  
  public d.a x(Map<String, d.a> paramMap)
  {
    Object localObject = this.WK.get(dh.j((d.a)paramMap.get(NAME)));
    if (localObject == null)
    {
      paramMap = (d.a)paramMap.get(XA);
      if (paramMap != null) {
        return paramMap;
      }
      return dh.lT();
    }
    return dh.r(localObject);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/tagmanager/u.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */