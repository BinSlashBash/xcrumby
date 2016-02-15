/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.view.View
 */
package com.google.android.gms.games;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.api.AchievementsImpl;
import com.google.android.gms.games.internal.api.AclsImpl;
import com.google.android.gms.games.internal.api.GamesMetadataImpl;
import com.google.android.gms.games.internal.api.InvitationsImpl;
import com.google.android.gms.games.internal.api.LeaderboardsImpl;
import com.google.android.gms.games.internal.api.MultiplayerImpl;
import com.google.android.gms.games.internal.api.NotificationsImpl;
import com.google.android.gms.games.internal.api.PlayersImpl;
import com.google.android.gms.games.internal.api.RealTimeMultiplayerImpl;
import com.google.android.gms.games.internal.api.RequestsImpl;
import com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl;
import com.google.android.gms.games.internal.game.Acls;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;

public final class Games {
    public static final Api<GamesOptions> API;
    public static final Achievements Achievements;
    public static final String EXTRA_PLAYER_IDS = "players";
    public static final GamesMetadata GamesMetadata;
    public static final Scope HV;
    public static final Api<GamesOptions> HW;
    public static final Multiplayer HX;
    public static final Acls HY;
    public static final Invitations Invitations;
    public static final Leaderboards Leaderboards;
    public static final Notifications Notifications;
    public static final Players Players;
    public static final RealTimeMultiplayer RealTimeMultiplayer;
    public static final Requests Requests;
    public static final Scope SCOPE_GAMES;
    public static final TurnBasedMultiplayer TurnBasedMultiplayer;
    static final Api.c<GamesClientImpl> wx;
    private static final Api.b<GamesClientImpl, GamesOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<GamesClientImpl, GamesOptions>(){

            @Override
            public GamesClientImpl a(Context context, Looper looper, fc fc2, GamesOptions gamesOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                GamesOptions gamesOptions2 = gamesOptions;
                if (gamesOptions == null) {
                    gamesOptions2 = new GamesOptions();
                }
                return new GamesClientImpl(context, looper, fc2.eG(), fc2.eC(), connectionCallbacks, onConnectionFailedListener, fc2.eF(), fc2.eD(), fc2.eH(), gamesOptions2.HZ, gamesOptions2.Ia, gamesOptions2.Ib, gamesOptions2.Ic, gamesOptions2.Id);
            }

            @Override
            public int getPriority() {
                return 1;
            }
        };
        SCOPE_GAMES = new Scope("https://www.googleapis.com/auth/games");
        API = new Api<GamesOptions>(wy, wx, SCOPE_GAMES);
        HV = new Scope("https://www.googleapis.com/auth/games.firstparty");
        HW = new Api<GamesOptions>(wy, wx, HV);
        GamesMetadata = new GamesMetadataImpl();
        Achievements = new AchievementsImpl();
        Leaderboards = new LeaderboardsImpl();
        Invitations = new InvitationsImpl();
        TurnBasedMultiplayer = new TurnBasedMultiplayerImpl();
        RealTimeMultiplayer = new RealTimeMultiplayerImpl();
        HX = new MultiplayerImpl();
        Players = new PlayersImpl();
        Notifications = new NotificationsImpl();
        Requests = new RequestsImpl();
        HY = new AclsImpl();
    }

    private Games() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static GamesClientImpl c(GoogleApiClient object) {
        boolean bl2 = true;
        boolean bl3 = object != null;
        fq.b(bl3, (Object)"GoogleApiClient parameter is required.");
        fq.a(object.isConnected(), "GoogleApiClient must be connected.");
        object = (GamesClientImpl)((Object)object.a(wx));
        bl3 = object != null ? bl2 : false;
        fq.a(bl3, "GoogleApiClient is not configured to use the Games Api. Pass Games.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return object;
    }

    public static String getAppId(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gz();
    }

    public static String getCurrentAccountName(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gl();
    }

    public static int getSdkVariant(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gy();
    }

    public static Intent getSettingsIntent(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gx();
    }

    public static void setGravityForPopups(GoogleApiClient googleApiClient, int n2) {
        Games.c(googleApiClient).aX(n2);
    }

    public static void setViewForPopups(GoogleApiClient googleApiClient, View view) {
        fq.f(view);
        Games.c(googleApiClient).f(view);
    }

    public static PendingResult<Status> signOut(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new SignOutImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this);
            }
        });
    }

    public static abstract class BaseGamesApiMethodImpl<R extends Result>
    extends a.b<R, GamesClientImpl> {
        public BaseGamesApiMethodImpl() {
            super(Games.wx);
        }
    }

    public static final class GamesOptions
    implements Api.ApiOptions.Optional {
        final boolean HZ;
        final boolean Ia;
        final int Ib;
        final boolean Ic;
        final int Id;

        private GamesOptions() {
            this.HZ = false;
            this.Ia = true;
            this.Ib = 17;
            this.Ic = false;
            this.Id = 4368;
        }

        private GamesOptions(Builder builder) {
            this.HZ = builder.HZ;
            this.Ia = builder.Ia;
            this.Ib = builder.Ib;
            this.Ic = builder.Ic;
            this.Id = builder.Id;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            boolean HZ = false;
            boolean Ia = true;
            int Ib = 17;
            boolean Ic = false;
            int Id = 4368;

            private Builder() {
            }

            public GamesOptions build() {
                return new GamesOptions(this);
            }

            public Builder setSdkVariant(int n2) {
                this.Id = n2;
                return this;
            }

            public Builder setShowConnectingPopup(boolean bl2) {
                this.Ia = bl2;
                this.Ib = 17;
                return this;
            }

            public Builder setShowConnectingPopup(boolean bl2, int n2) {
                this.Ia = bl2;
                this.Ib = n2;
                return this;
            }
        }

    }

    private static abstract class SignOutImpl
    extends BaseGamesApiMethodImpl<Status> {
        private SignOutImpl() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

}

