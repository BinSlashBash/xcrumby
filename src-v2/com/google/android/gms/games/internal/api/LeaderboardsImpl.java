/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;

public final class LeaderboardsImpl
implements Leaderboards {
    @Override
    public Intent getAllLeaderboardsIntent(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gp();
    }

    @Override
    public Intent getLeaderboardIntent(GoogleApiClient googleApiClient, String string2) {
        return Games.c(googleApiClient).aA(string2);
    }

    @Override
    public PendingResult<Leaderboards.LoadPlayerScoreResult> loadCurrentPlayerLeaderboardScore(GoogleApiClient googleApiClient, final String string2, final int n2, final int n3) {
        return googleApiClient.a(new LoadPlayerScoreImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, null, string2, n2, n3);
            }
        });
    }

    @Override
    public PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient googleApiClient, final String string2, final boolean bl2) {
        return googleApiClient.a(new LoadMetadataImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2, bl2);
            }
        });
    }

    @Override
    public PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient googleApiClient, final boolean bl2) {
        return googleApiClient.a(new LoadMetadataImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, bl2);
            }
        });
    }

    @Override
    public PendingResult<Leaderboards.LoadScoresResult> loadMoreScores(GoogleApiClient googleApiClient, final LeaderboardScoreBuffer leaderboardScoreBuffer, final int n2, final int n3) {
        return googleApiClient.a(new LoadScoresImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, leaderboardScoreBuffer, n2, n3);
            }
        });
    }

    @Override
    public PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient googleApiClient, String string2, int n2, int n3, int n4) {
        return this.loadPlayerCenteredScores(googleApiClient, string2, n2, n3, n4, false);
    }

    @Override
    public PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient googleApiClient, final String string2, final int n2, final int n3, final int n4, final boolean bl2) {
        return googleApiClient.a(new LoadScoresImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, string2, n2, n3, n4, bl2);
            }
        });
    }

    @Override
    public PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient googleApiClient, String string2, int n2, int n3, int n4) {
        return this.loadTopScores(googleApiClient, string2, n2, n3, n4, false);
    }

    @Override
    public PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient googleApiClient, final String string2, final int n2, final int n3, final int n4, final boolean bl2) {
        return googleApiClient.a(new LoadScoresImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2, n2, n3, n4, bl2);
            }
        });
    }

    @Override
    public void submitScore(GoogleApiClient googleApiClient, String string2, long l2) {
        this.submitScore(googleApiClient, string2, l2, null);
    }

    @Override
    public void submitScore(GoogleApiClient googleApiClient, String string2, long l2, String string3) {
        Games.c(googleApiClient).a(null, string2, l2, string3);
    }

    @Override
    public PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient googleApiClient, String string2, long l2) {
        return this.submitScoreImmediate(googleApiClient, string2, l2, null);
    }

    @Override
    public PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient googleApiClient, final String string2, final long l2, final String string3) {
        return googleApiClient.b(new SubmitScoreImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2, l2, string3);
            }
        });
    }

    private static abstract class LoadMetadataImpl
    extends Games.BaseGamesApiMethodImpl<Leaderboards.LeaderboardMetadataResult> {
        private LoadMetadataImpl() {
        }

        public Leaderboards.LeaderboardMetadataResult D(final Status status) {
            return new Leaderboards.LeaderboardMetadataResult(){

                @Override
                public LeaderboardBuffer getLeaderboards() {
                    return new LeaderboardBuffer(DataHolder.empty(14));
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.D(status);
        }

    }

    private static abstract class LoadPlayerScoreImpl
    extends Games.BaseGamesApiMethodImpl<Leaderboards.LoadPlayerScoreResult> {
        private LoadPlayerScoreImpl() {
        }

        public Leaderboards.LoadPlayerScoreResult E(final Status status) {
            return new Leaderboards.LoadPlayerScoreResult(){

                @Override
                public LeaderboardScore getScore() {
                    return null;
                }

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.E(status);
        }

    }

    private static abstract class LoadScoresImpl
    extends Games.BaseGamesApiMethodImpl<Leaderboards.LoadScoresResult> {
        private LoadScoresImpl() {
        }

        public Leaderboards.LoadScoresResult F(final Status status) {
            return new Leaderboards.LoadScoresResult(){

                @Override
                public Leaderboard getLeaderboard() {
                    return null;
                }

                @Override
                public LeaderboardScoreBuffer getScores() {
                    return new LeaderboardScoreBuffer(DataHolder.empty(14));
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.F(status);
        }

    }

    protected static abstract class SubmitScoreImpl
    extends Games.BaseGamesApiMethodImpl<Leaderboards.SubmitScoreResult> {
        protected SubmitScoreImpl() {
        }

        public Leaderboards.SubmitScoreResult G(final Status status) {
            return new Leaderboards.SubmitScoreResult(){

                @Override
                public ScoreSubmissionData getScoreData() {
                    return new ScoreSubmissionData(DataHolder.empty(14));
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.G(status);
        }

    }

}

