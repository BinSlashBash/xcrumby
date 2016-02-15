package com.google.android.gms.games.multiplayer.turnbased;

public abstract interface OnTurnBasedMatchUpdateReceivedListener
{
  public abstract void onTurnBasedMatchReceived(TurnBasedMatch paramTurnBasedMatch);
  
  public abstract void onTurnBasedMatchRemoved(String paramString);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/games/multiplayer/turnbased/OnTurnBasedMatchUpdateReceivedListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */