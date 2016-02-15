package com.google.android.gms.games.internal;

import com.google.android.gms.internal.fe;
import com.google.android.gms.internal.go;

public abstract class GamesDowngradeableSafeParcel
  extends fe
{
  protected static boolean c(Integer paramInteger)
  {
    if (paramInteger == null) {
      return false;
    }
    return go.aa(paramInteger.intValue());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/games/internal/GamesDowngradeableSafeParcel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */