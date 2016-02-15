/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class GamesMetadataImpl
implements GamesMetadata {
    @Override
    public Game getCurrentGame(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).go();
    }

    @Override
    public PendingResult<GamesMetadata.LoadGamesResult> loadGame(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new LoadGamesImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.g(this);
            }
        });
    }

    private static abstract class LoadExtendedGamesImpl
    extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadExtendedGamesResult> {
        private LoadExtendedGamesImpl() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.y(status);
        }

        public GamesMetadata.LoadExtendedGamesResult y(final Status status) {
            return new GamesMetadata.LoadExtendedGamesResult(){

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

    private static abstract class LoadGameInstancesImpl
    extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGameInstancesResult> {
        private LoadGameInstancesImpl() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.z(status);
        }

        public GamesMetadata.LoadGameInstancesResult z(final Status status) {
            return new GamesMetadata.LoadGameInstancesResult(){

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

    private static abstract class LoadGameSearchSuggestionsImpl
    extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGameSearchSuggestionsResult> {
        private LoadGameSearchSuggestionsImpl() {
        }

        public GamesMetadata.LoadGameSearchSuggestionsResult A(final Status status) {
            return new GamesMetadata.LoadGameSearchSuggestionsResult(){

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
            return this.A(status);
        }

    }

    private static abstract class LoadGamesImpl
    extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGamesResult> {
        private LoadGamesImpl() {
        }

        public GamesMetadata.LoadGamesResult B(final Status status) {
            return new GamesMetadata.LoadGamesResult(){

                @Override
                public GameBuffer getGames() {
                    return new GameBuffer(DataHolder.empty(14));
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
            return this.B(status);
        }

    }

}

