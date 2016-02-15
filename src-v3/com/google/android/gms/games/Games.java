package com.google.android.gms.games;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.view.View;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
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
    static final C0241c<GamesClientImpl> wx;
    private static final C0240b<GamesClientImpl, GamesOptions> wy;

    /* renamed from: com.google.android.gms.games.Games.1 */
    static class C08211 implements C0240b<GamesClientImpl, GamesOptions> {
        C08211() {
        }

        public GamesClientImpl m1757a(Context context, Looper looper, fc fcVar, GamesOptions gamesOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            if (gamesOptions == null) {
                GamesOptions gamesOptions2 = new GamesOptions();
            }
            return new GamesClientImpl(context, looper, fcVar.eG(), fcVar.eC(), connectionCallbacks, onConnectionFailedListener, fcVar.eF(), fcVar.eD(), fcVar.eH(), gamesOptions.HZ, gamesOptions.Ia, gamesOptions.Ib, gamesOptions.Ic, gamesOptions.Id);
        }

        public int getPriority() {
            return 1;
        }
    }

    public static abstract class BaseGamesApiMethodImpl<R extends Result> extends C1299b<R, GamesClientImpl> {
        public BaseGamesApiMethodImpl() {
            super(Games.wx);
        }
    }

    public static final class GamesOptions implements Optional {
        final boolean HZ;
        final boolean Ia;
        final int Ib;
        final boolean Ic;
        final int Id;

        public static final class Builder {
            boolean HZ;
            boolean Ia;
            int Ib;
            boolean Ic;
            int Id;

            private Builder() {
                this.HZ = false;
                this.Ia = true;
                this.Ib = 17;
                this.Ic = false;
                this.Id = 4368;
            }

            public GamesOptions build() {
                return new GamesOptions();
            }

            public Builder setSdkVariant(int variant) {
                this.Id = variant;
                return this;
            }

            public Builder setShowConnectingPopup(boolean showConnectingPopup) {
                this.Ia = showConnectingPopup;
                this.Ib = 17;
                return this;
            }

            public Builder setShowConnectingPopup(boolean showConnectingPopup, int gravity) {
                this.Ia = showConnectingPopup;
                this.Ib = gravity;
                return this;
            }
        }

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
    }

    private static abstract class SignOutImpl extends BaseGamesApiMethodImpl<Status> {
        private SignOutImpl() {
        }

        public /* synthetic */ Result m3488d(Status status) {
            return m3489f(status);
        }

        public Status m3489f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.games.Games.2 */
    static class C15922 extends SignOutImpl {
        C15922() {
            super();
        }

        protected void m3667a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2827b((C0244d) this);
        }
    }

    static {
        wx = new C0241c();
        wy = new C08211();
        SCOPE_GAMES = new Scope(Scopes.GAMES);
        API = new Api(wy, wx, SCOPE_GAMES);
        HV = new Scope("https://www.googleapis.com/auth/games.firstparty");
        HW = new Api(wy, wx, HV);
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

    public static GamesClientImpl m361c(GoogleApiClient googleApiClient) {
        boolean z = true;
        fq.m984b(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        fq.m980a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        GamesClientImpl gamesClientImpl = (GamesClientImpl) googleApiClient.m123a(wx);
        if (gamesClientImpl == null) {
            z = false;
        }
        fq.m980a(z, "GoogleApiClient is not configured to use the Games Api. Pass Games.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return gamesClientImpl;
    }

    public static String getAppId(GoogleApiClient apiClient) {
        return m361c(apiClient).gz();
    }

    public static String getCurrentAccountName(GoogleApiClient apiClient) {
        return m361c(apiClient).gl();
    }

    public static int getSdkVariant(GoogleApiClient apiClient) {
        return m361c(apiClient).gy();
    }

    public static Intent getSettingsIntent(GoogleApiClient apiClient) {
        return m361c(apiClient).gx();
    }

    public static void setGravityForPopups(GoogleApiClient apiClient, int gravity) {
        m361c(apiClient).aX(gravity);
    }

    public static void setViewForPopups(GoogleApiClient apiClient, View gamesContentView) {
        fq.m985f(gamesContentView);
        m361c(apiClient).m2857f(gamesContentView);
    }

    public static PendingResult<Status> signOut(GoogleApiClient apiClient) {
        return apiClient.m125b(new C15922());
    }
}
