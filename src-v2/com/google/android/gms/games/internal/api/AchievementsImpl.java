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
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class AchievementsImpl
implements Achievements {
    @Override
    public Intent getAchievementsIntent(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gq();
    }

    @Override
    public void increment(GoogleApiClient googleApiClient, final String string2, final int n2) {
        googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(null, string2, n2);
            }
        });
    }

    @Override
    public PendingResult<Achievements.UpdateAchievementResult> incrementImmediate(GoogleApiClient googleApiClient, final String string2, final int n2) {
        return googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2, n2);
            }
        });
    }

    @Override
    public PendingResult<Achievements.LoadAchievementsResult> load(GoogleApiClient googleApiClient, final boolean bl2) {
        return googleApiClient.a(new LoadImpl(){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(this, bl2);
            }
        });
    }

    @Override
    public void reveal(GoogleApiClient googleApiClient, final String string2) {
        googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b((a.d<Achievements.UpdateAchievementResult>)null, string2);
            }
        });
    }

    @Override
    public PendingResult<Achievements.UpdateAchievementResult> revealImmediate(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, string2);
            }
        });
    }

    @Override
    public void setSteps(GoogleApiClient googleApiClient, final String string2, final int n2) {
        googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(null, string2, n2);
            }
        });
    }

    @Override
    public PendingResult<Achievements.UpdateAchievementResult> setStepsImmediate(GoogleApiClient googleApiClient, final String string2, final int n2) {
        return googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, string2, n2);
            }
        });
    }

    @Override
    public void unlock(GoogleApiClient googleApiClient, final String string2) {
        googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(null, string2);
            }
        });
    }

    @Override
    public PendingResult<Achievements.UpdateAchievementResult> unlockImmediate(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.b(new UpdateImpl(string2){

            @Override
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(this, string2);
            }
        });
    }

    private static abstract class LoadImpl
    extends Games.BaseGamesApiMethodImpl<Achievements.LoadAchievementsResult> {
        private LoadImpl() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.t(status);
        }

        public Achievements.LoadAchievementsResult t(final Status status) {
            return new Achievements.LoadAchievementsResult(){

                @Override
                public AchievementBuffer getAchievements() {
                    return new AchievementBuffer(DataHolder.empty(14));
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

    }

    private static abstract class UpdateImpl
    extends Games.BaseGamesApiMethodImpl<Achievements.UpdateAchievementResult> {
        private final String wp;

        public UpdateImpl(String string2) {
            this.wp = string2;
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.u(status);
        }

        public Achievements.UpdateAchievementResult u(final Status status) {
            return new Achievements.UpdateAchievementResult(){

                @Override
                public String getAchievementId() {
                    return UpdateImpl.this.wp;
                }

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

    }

}

