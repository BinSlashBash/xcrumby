package com.crumby;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class CrDb
{
  private static final String DEBUG_NAME = "crumby";
  private static Map<String, Long> timeKeeper = new HashMap();
  
  public static void d(String paramString1, String paramString2)
  {
    Log.d("crumby " + paramString1, paramString2);
  }
  
  public static void logTime(String paramString1, String paramString2, boolean paramBoolean)
  {
    long l1 = 0L;
    try
    {
      long l2 = ((Long)timeKeeper.get(paramString2)).longValue();
      l1 = l2;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;) {}
    }
    if (!paramBoolean)
    {
      d(paramString1, paramString2 + " time event:" + (System.nanoTime() - l1) / 1000000L + "ms");
      timeKeeper.put(paramString2, Long.valueOf(0L));
      return;
    }
    timeKeeper.put(paramString2, Long.valueOf(System.nanoTime()));
  }
  
  public static void newActivity() {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/CrDb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */