/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import java.util.List;

public final class TurnBasedMultiplayerImpl
implements TurnBasedMultiplayer {
    @Override
    public PendingResult<TurnBasedMultiplayer.InitiateMatchResult> acceptInvitation(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.b(new InitiateMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.e(this, string2);
            }
        });
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.CancelMatchResult> cancelMatch(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.b(new CancelMatchImpl(string2){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.g(this, string2);
            }
        });
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.InitiateMatchResult> createMatch(GoogleApiClient googleApiClient, final TurnBasedMatchConfig turnBasedMatchConfig) {
        return googleApiClient.b(new InitiateMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, turnBasedMatchConfig);
            }
        });
    }

    @Override
    public void declineInvitation(GoogleApiClient googleApiClient, String string2) {
        Games.c(googleApiClient).m(string2, 1);
    }

    @Override
    public void dismissInvitation(GoogleApiClient googleApiClient, String string2) {
        Games.c(googleApiClient).l(string2, 1);
    }

    @Override
    public void dismissMatch(GoogleApiClient googleApiClient, String string2) {
        Games.c(googleApiClient).aB(string2);
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> finishMatch(GoogleApiClient googleApiClient, String string2) {
        return this.finishMatch(googleApiClient, string2, null, (ParticipantResult[])null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> finishMatch(GoogleApiClient googleApiClient, String string2, byte[] arrby, List<ParticipantResult> arrparticipantResult) {
        if (arrparticipantResult == null) {
            arrparticipantResult = null;
            do {
                return this.finishMatch(googleApiClient, string2, arrby, arrparticipantResult);
                break;
            } while (true);
        }
        arrparticipantResult = arrparticipantResult.toArray(new ParticipantResult[arrparticipantResult.size()]);
        return this.finishMatch(googleApiClient, string2, arrby, arrparticipantResult);
    }

    @Override
    public /* varargs */ PendingResult<TurnBasedMultiplayer.UpdateMatchResult> finishMatch(GoogleApiClient googleApiClient, final String string2, final byte[] arrby, final ParticipantResult ... arrparticipantResult) {
        return googleApiClient.b(new UpdateMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2, arrby, arrparticipantResult);
            }
        });
    }

    @Override
    public Intent getInboxIntent(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gr();
    }

    @Override
    public int getMaxMatchDataSize(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gA();
    }

    @Override
    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int n2, int n3) {
        return Games.c(googleApiClient).a(n2, n3, true);
    }

    @Override
    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int n2, int n3, boolean bl2) {
        return Games.c(googleApiClient).a(n2, n3, bl2);
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.LeaveMatchResult> leaveMatch(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.b(new LeaveMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.f(this, string2);
            }
        });
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.LeaveMatchResult> leaveMatchDuringTurn(GoogleApiClient googleApiClient, final String string2, final String string3) {
        return googleApiClient.b(new LeaveMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2, string3);
            }
        });
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.LoadMatchResult> loadMatch(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.a(new LoadMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.h(this, string2);
            }
        });
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.LoadMatchesResult> loadMatchesByStatus(GoogleApiClient googleApiClient, final int n2, final int[] arrn) {
        return googleApiClient.a(new LoadMatchesImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, n2, arrn);
            }
        });
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.LoadMatchesResult> loadMatchesByStatus(GoogleApiClient googleApiClient, int[] arrn) {
        return this.loadMatchesByStatus(googleApiClient, 0, arrn);
    }

    @Override
    public void registerMatchUpdateListener(GoogleApiClient googleApiClient, OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
        Games.c(googleApiClient).a(onTurnBasedMatchUpdateReceivedListener);
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.InitiateMatchResult> rematch(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.b(new InitiateMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.d(this, string2);
            }
        });
    }

    @Override
    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> takeTurn(GoogleApiClient googleApiClient, String string2, byte[] arrby, String string3) {
        return this.takeTurn(googleApiClient, string2, arrby, string3, (ParticipantResult[])null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> takeTurn(GoogleApiClient googleApiClient, String string2, byte[] arrby, String string3, List<ParticipantResult> arrparticipantResult) {
        if (arrparticipantResult == null) {
            arrparticipantResult = null;
            do {
                return this.takeTurn(googleApiClient, string2, arrby, string3, arrparticipantResult);
                break;
            } while (true);
        }
        arrparticipantResult = arrparticipantResult.toArray(new ParticipantResult[arrparticipantResult.size()]);
        return this.takeTurn(googleApiClient, string2, arrby, string3, arrparticipantResult);
    }

    @Override
    public /* varargs */ PendingResult<TurnBasedMultiplayer.UpdateMatchResult> takeTurn(GoogleApiClient googleApiClient, final String string2, final byte[] arrby, final String string3, final ParticipantResult ... arrparticipantResult) {
        return googleApiClient.b(new UpdateMatchImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, string2, arrby, string3, arrparticipantResult);
            }
        });
    }

    @Override
    public void unregisterMatchUpdateListener(GoogleApiClient googleApiClient) {
        Games.c(googleApiClient).gu();
    }

    private static abstract class CancelMatchImpl
    extends Games.BaseGamesApiMethodImpl<TurnBasedMultiplayer.CancelMatchResult> {
        private final String wp;

        public CancelMatchImpl(String string2) {
            this.wp = string2;
        }

        public TurnBasedMultiplayer.CancelMatchResult R(final Status status) {
            return new TurnBasedMultiplayer.CancelMatchResult(){

                @Override
                public String getMatchId() {
                    return CancelMatchImpl.this.wp;
                }

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.R(status);
        }

    }

    private static abstract class InitiateMatchImpl
    extends Games.BaseGamesApiMethodImpl<TurnBasedMultiplayer.InitiateMatchResult> {
        private InitiateMatchImpl() {
        }

        public TurnBasedMultiplayer.InitiateMatchResult S(final Status status) {
            return new TurnBasedMultiplayer.InitiateMatchResult(){

                @Override
                public TurnBasedMatch getMatch() {
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
            return this.S(status);
        }

    }

    private static abstract class LeaveMatchImpl
    extends Games.BaseGamesApiMethodImpl<TurnBasedMultiplayer.LeaveMatchResult> {
        private LeaveMatchImpl() {
        }

        public TurnBasedMultiplayer.LeaveMatchResult T(final Status status) {
            return new TurnBasedMultiplayer.LeaveMatchResult(){

                @Override
                public TurnBasedMatch getMatch() {
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
            return this.T(status);
        }

    }

    private static abstract class LoadMatchImpl
    extends Games.BaseGamesApiMethodImpl<TurnBasedMultiplayer.LoadMatchResult> {
        private LoadMatchImpl() {
        }

        public TurnBasedMultiplayer.LoadMatchResult U(final Status status) {
            return new TurnBasedMultiplayer.LoadMatchResult(){

                @Override
                public TurnBasedMatch getMatch() {
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
            return this.U(status);
        }

    }

    private static abstract class LoadMatchesImpl
    extends Games.BaseGamesApiMethodImpl<TurnBasedMultiplayer.LoadMatchesResult> {
        private LoadMatchesImpl() {
        }

        public TurnBasedMultiplayer.LoadMatchesResult V(final Status status) {
            return new TurnBasedMultiplayer.LoadMatchesResult(){

                @Override
                public LoadMatchesResponse getMatches() {
                    return new LoadMatchesResponse(new Bundle());
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
            return this.V(status);
        }

    }

    private static abstract class UpdateMatchImpl
    extends Games.BaseGamesApiMethodImpl<TurnBasedMultiplayer.UpdateMatchResult> {
        private UpdateMatchImpl() {
        }

        public TurnBasedMultiplayer.UpdateMatchResult W(final Status status) {
            return new TurnBasedMultiplayer.UpdateMatchResult(){

                @Override
                public TurnBasedMatch getMatch() {
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
            return this.W(status);
        }

    }

}

