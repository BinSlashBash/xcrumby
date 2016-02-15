package com.google.android.gms.games.internal.constants;

import com.google.android.gms.games.internal.GamesLog;

public final class RequestType
{
  public static String bd(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      GamesLog.h("RequestType", "Unknown request type: " + paramInt);
      return "UNKNOWN_TYPE";
    case 1: 
      return "GIFT";
    }
    return "WISH";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/games/internal/constants/RequestType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */