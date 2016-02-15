/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer.turnbased;

import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;

public interface OnTurnBasedMatchUpdateReceivedListener {
    public void onTurnBasedMatchReceived(TurnBasedMatch var1);

    public void onTurnBasedMatchRemoved(String var1);
}

