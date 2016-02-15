package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataBuffer;

public final class GameRequestSummaryBuffer
  extends DataBuffer<GameRequestSummary>
{
  public GameRequestSummary br(int paramInt)
  {
    return new GameRequestSummaryRef(this.BB, paramInt);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/games/request/GameRequestSummaryBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */