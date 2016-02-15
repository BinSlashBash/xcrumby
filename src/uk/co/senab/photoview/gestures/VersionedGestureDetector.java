package uk.co.senab.photoview.gestures;

import android.content.Context;
import android.os.Build.VERSION;

public final class VersionedGestureDetector
{
  public static GestureDetector newInstance(Context paramContext, OnGestureListener paramOnGestureListener)
  {
    int i = Build.VERSION.SDK_INT;
    if (i < 5) {
      paramContext = new CupcakeGestureDetector(paramContext);
    }
    for (;;)
    {
      paramContext.setOnGestureListener(paramOnGestureListener);
      return paramContext;
      if (i < 8) {
        paramContext = new EclairGestureDetector(paramContext);
      } else {
        paramContext = new FroyoGestureDetector(paramContext);
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/uk/co/senab/photoview/gestures/VersionedGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */