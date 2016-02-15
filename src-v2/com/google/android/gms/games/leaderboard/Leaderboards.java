/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.games.leaderboard;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;

public interface Leaderboards {
    public Intent getAllLeaderboardsIntent(GoogleApiClient var1);

    public Intent getLeaderboardIntent(GoogleApiClient var1, String var2);

    public PendingResult<LoadPlayerScoreResult> loadCurrentPlayerLeaderboardScore(GoogleApiClient var1, String var2, int var3, int var4);

    public PendingResult<LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient var1, String var2, boolean var3);

    public PendingResult<LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient var1, boolean var2);

    public PendingResult<LoadScoresResult> loadMoreScores(GoogleApiClient var1, LeaderboardScoreBuffer var2, int var3, int var4);

    public PendingResult<LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient var1, String var2, int var3, int var4, int var5);

    public PendingResult<LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient var1, String var2, int var3, int var4, int var5, boolean var6);

    public PendingResult<LoadScoresResult> loadTopScores(GoogleApiClient var1, String var2, int var3, int var4, int var5);

    public PendingResult<LoadScoresResult> loadTopScores(GoogleApiClient var1, String var2, int var3, int var4, int var5, boolean var6);

    public void submitScore(GoogleApiClient var1, String var2, long var3);

    public void submitScore(GoogleApiClient var1, String var2, long var3, String var5);

    public PendingResult<SubmitScoreResult> submitScoreImmediate(GoogleApiClient var1, String var2, long var3);

    public PendingResult<SubmitScoreResult> submitScoreImmediate(GoogleApiClient var1, String var2, long var3, String var5);

    public static interface LeaderboardMetadataResult
    extends Releasable,
    Result {
        public LeaderboardBuffer getLeaderboards();
    }

    public static interface LoadPlayerScoreResult
    extends Result {
        public LeaderboardScore getScore();
    }

    public static interface LoadScoresResult
    extends Releasable,
    Result {
        public Leaderboard getLeaderboard();

        public LeaderboardScoreBuffer getScores();
    }

    public static interface SubmitScoreResult
    extends Releasable,
    Result {
        public ScoreSubmissionData getScoreData();
    }

}

