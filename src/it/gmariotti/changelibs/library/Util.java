package it.gmariotti.changelibs.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util
{
  public static boolean isConnected(Context paramContext)
  {
    if (paramContext == null) {}
    do
    {
      do
      {
        return false;
        paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
      } while (paramContext == null);
      paramContext = paramContext.getActiveNetworkInfo();
    } while ((paramContext == null) || (paramContext == null) || (!paramContext.isConnectedOrConnecting()));
    return true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */