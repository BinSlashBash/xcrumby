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
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class PlayersImpl
implements Players {
    @Override
    public Player getCurrentPlayer(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gn();
    }

    @Override
    public String getCurrentPlayerId(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gm();
    }

    @Override
    public Intent getPlayerSearchIntent(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gw();
    }

    @Override
    public PendingResult<Players.LoadPlayersResult> loadConnectedPlayers(GoogleApiClient googleApiClient, final boolean bl2) {
        return googleApiClient.a(new LoadPlayersImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, bl2);
            }
        });
    }

    @Override
    public PendingResult<Players.LoadPlayersResult> loadInvitablePlayers(GoogleApiClient googleApiClient, final int n2, final boolean bl2) {
        return googleApiClient.a(new LoadPlayersImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, n2, false, bl2);
            }
        });
    }

    @Override
    public PendingResult<Players.LoadPlayersResult> loadMoreInvitablePlayers(GoogleApiClient googleApiClient, final int n2) {
        return googleApiClient.a(new LoadPlayersImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, n2, true, false);
            }
        });
    }

    @Override
    public PendingResult<Players.LoadPlayersResult> loadMoreRecentlyPlayedWithPlayers(GoogleApiClient googleApiClient, final int n2) {
        return googleApiClient.a(new LoadPlayersImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, "playedWith", n2, true, false);
            }
        });
    }

    @Override
    public PendingResult<Players.LoadPlayersResult> loadPlayer(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.a(new LoadPlayersImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2);
            }
        });
    }

    @Override
    public PendingResult<Players.LoadPlayersResult> loadRecentlyPlayedWithPlayers(GoogleApiClient googleApiClient, final int n2, final boolean bl2) {
        return googleApiClient.a(new LoadPlayersImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, "playedWith", n2, false, bl2);
            }
        });
    }

    private static abstract class LoadExtendedPlayersImpl
    extends Games.BaseGamesApiMethodImpl<Players.LoadExtendedPlayersResult> {
        private LoadExtendedPlayersImpl() {
        }

        public Players.LoadExtendedPlayersResult K(final Status status) {
            return new Players.LoadExtendedPlayersResult(){

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
            return this.K(status);
        }

    }

    private static abstract class LoadOwnerCoverPhotoUrisImpl
    extends Games.BaseGamesApiMethodImpl<Players.LoadOwnerCoverPhotoUrisResult> {
        private LoadOwnerCoverPhotoUrisImpl() {
        }

        public Players.LoadOwnerCoverPhotoUrisResult L(final Status status) {
            return new Players.LoadOwnerCoverPhotoUrisResult(){

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.L(status);
        }

    }

    private static abstract class LoadPlayersImpl
    extends Games.BaseGamesApiMethodImpl<Players.LoadPlayersResult> {
        private LoadPlayersImpl() {
        }

        public Players.LoadPlayersResult M(final Status status) {
            return new Players.LoadPlayersResult(){

                @Override
                public PlayerBuffer getPlayers() {
                    return new PlayerBuffer(DataHolder.empty(14));
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
            return this.M(status);
        }

    }

}

