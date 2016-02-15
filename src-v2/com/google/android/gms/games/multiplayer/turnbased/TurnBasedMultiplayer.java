/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.games.multiplayer.turnbased;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import java.util.List;

public interface TurnBasedMultiplayer {
    public PendingResult<InitiateMatchResult> acceptInvitation(GoogleApiClient var1, String var2);

    public PendingResult<CancelMatchResult> cancelMatch(GoogleApiClient var1, String var2);

    public PendingResult<InitiateMatchResult> createMatch(GoogleApiClient var1, TurnBasedMatchConfig var2);

    public void declineInvitation(GoogleApiClient var1, String var2);

    public void dismissInvitation(GoogleApiClient var1, String var2);

    public void dismissMatch(GoogleApiClient var1, String var2);

    public PendingResult<UpdateMatchResult> finishMatch(GoogleApiClient var1, String var2);

    public PendingResult<UpdateMatchResult> finishMatch(GoogleApiClient var1, String var2, byte[] var3, List<ParticipantResult> var4);

    public /* varargs */ PendingResult<UpdateMatchResult> finishMatch(GoogleApiClient var1, String var2, byte[] var3, ParticipantResult ... var4);

    public Intent getInboxIntent(GoogleApiClient var1);

    public int getMaxMatchDataSize(GoogleApiClient var1);

    public Intent getSelectOpponentsIntent(GoogleApiClient var1, int var2, int var3);

    public Intent getSelectOpponentsIntent(GoogleApiClient var1, int var2, int var3, boolean var4);

    public PendingResult<LeaveMatchResult> leaveMatch(GoogleApiClient var1, String var2);

    public PendingResult<LeaveMatchResult> leaveMatchDuringTurn(GoogleApiClient var1, String var2, String var3);

    public PendingResult<LoadMatchResult> loadMatch(GoogleApiClient var1, String var2);

    public PendingResult<LoadMatchesResult> loadMatchesByStatus(GoogleApiClient var1, int var2, int[] var3);

    public PendingResult<LoadMatchesResult> loadMatchesByStatus(GoogleApiClient var1, int[] var2);

    public void registerMatchUpdateListener(GoogleApiClient var1, OnTurnBasedMatchUpdateReceivedListener var2);

    public PendingResult<InitiateMatchResult> rematch(GoogleApiClient var1, String var2);

    public PendingResult<UpdateMatchResult> takeTurn(GoogleApiClient var1, String var2, byte[] var3, String var4);

    public PendingResult<UpdateMatchResult> takeTurn(GoogleApiClient var1, String var2, byte[] var3, String var4, List<ParticipantResult> var5);

    public /* varargs */ PendingResult<UpdateMatchResult> takeTurn(GoogleApiClient var1, String var2, byte[] var3, String var4, ParticipantResult ... var5);

    public void unregisterMatchUpdateListener(GoogleApiClient var1);

    public static interface CancelMatchResult
    extends Result {
        public String getMatchId();
    }

    public static interface InitiateMatchResult
    extends Result {
        public TurnBasedMatch getMatch();
    }

    public static interface LeaveMatchResult
    extends Result {
        public TurnBasedMatch getMatch();
    }

    public static interface LoadMatchResult
    extends Result {
        public TurnBasedMatch getMatch();
    }

    public static interface LoadMatchesResult
    extends Releasable,
    Result {
        public LoadMatchesResponse getMatches();
    }

    public static interface UpdateMatchResult
    extends Result {
        public TurnBasedMatch getMatch();
    }

}

