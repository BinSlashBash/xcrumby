package com.google.android.gms.games.internal.api;

import android.content.Intent;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.Players.LoadExtendedPlayersResult;
import com.google.android.gms.games.Players.LoadOwnerCoverPhotoUrisResult;
import com.google.android.gms.games.Players.LoadPlayersResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class PlayersImpl implements Players {

    private static abstract class LoadExtendedPlayersImpl extends BaseGamesApiMethodImpl<LoadExtendedPlayersResult> {

        /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.LoadExtendedPlayersImpl.1 */
        class C13421 implements LoadExtendedPlayersResult {
            final /* synthetic */ LoadExtendedPlayersImpl KL;
            final /* synthetic */ Status wz;

            C13421(LoadExtendedPlayersImpl loadExtendedPlayersImpl, Status status) {
                this.KL = loadExtendedPlayersImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadExtendedPlayersImpl() {
        }

        public LoadExtendedPlayersResult m3543K(Status status) {
            return new C13421(this, status);
        }

        public /* synthetic */ Result m3544d(Status status) {
            return m3543K(status);
        }
    }

    private static abstract class LoadOwnerCoverPhotoUrisImpl extends BaseGamesApiMethodImpl<LoadOwnerCoverPhotoUrisResult> {

        /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.LoadOwnerCoverPhotoUrisImpl.1 */
        class C13431 implements LoadOwnerCoverPhotoUrisResult {
            final /* synthetic */ LoadOwnerCoverPhotoUrisImpl KM;
            final /* synthetic */ Status wz;

            C13431(LoadOwnerCoverPhotoUrisImpl loadOwnerCoverPhotoUrisImpl, Status status) {
                this.KM = loadOwnerCoverPhotoUrisImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private LoadOwnerCoverPhotoUrisImpl() {
        }

        public LoadOwnerCoverPhotoUrisResult m3545L(Status status) {
            return new C13431(this, status);
        }

        public /* synthetic */ Result m3546d(Status status) {
            return m3545L(status);
        }
    }

    private static abstract class LoadPlayersImpl extends BaseGamesApiMethodImpl<LoadPlayersResult> {

        /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.LoadPlayersImpl.1 */
        class C13441 implements LoadPlayersResult {
            final /* synthetic */ LoadPlayersImpl KN;
            final /* synthetic */ Status wz;

            C13441(LoadPlayersImpl loadPlayersImpl, Status status) {
                this.KN = loadPlayersImpl;
                this.wz = status;
            }

            public PlayerBuffer getPlayers() {
                return new PlayerBuffer(DataHolder.empty(14));
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadPlayersImpl() {
        }

        public LoadPlayersResult m3547M(Status status) {
            return new C13441(this, status);
        }

        public /* synthetic */ Result m3548d(Status status) {
            return m3547M(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.10 */
    class AnonymousClass10 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        protected void m3755a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2828b((C0244d) this, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.11 */
    class AnonymousClass11 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;

        protected void m3757a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2828b((C0244d) this, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.12 */
    class AnonymousClass12 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        protected void m3759a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2842c(this, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.13 */
    class AnonymousClass13 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;

        protected void m3761a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2842c(this, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.14 */
    class AnonymousClass14 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        protected void m3763a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2851d(this, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.15 */
    class AnonymousClass15 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;

        protected void m3765a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2851d(this, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.16 */
    class AnonymousClass16 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        protected void m3767a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2854d(this, this.Kd, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.17 */
    class AnonymousClass17 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        protected void m3769a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2854d(this, this.Kd, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.18 */
    class AnonymousClass18 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ int KJ;

        protected void m3771a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2801a((C0244d) this, this.JT, this.KJ, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.19 */
    class AnonymousClass19 extends LoadExtendedPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        protected void m3773a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2855e(this, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.1 */
    class C16271 extends LoadPlayersImpl {
        final /* synthetic */ String JS;
        final /* synthetic */ PlayersImpl KI;

        C16271(PlayersImpl playersImpl, String str) {
            this.KI = playersImpl;
            this.JS = str;
            super();
        }

        protected void m3775a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2798a((C0244d) this, this.JS);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.20 */
    class AnonymousClass20 extends LoadExtendedPlayersImpl {
        final /* synthetic */ int Kb;

        protected void m3777a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2855e(this, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.21 */
    class AnonymousClass21 extends LoadOwnerCoverPhotoUrisImpl {
        protected void m3779a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2861h(this);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.2 */
    class C16282 extends LoadPlayersImpl {
        final /* synthetic */ String[] KK;

        protected void m3781a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2819a((C0244d) this, this.KK);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.3 */
    class C16293 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ PlayersImpl KI;
        final /* synthetic */ int Kb;

        C16293(PlayersImpl playersImpl, int i, boolean z) {
            this.KI = playersImpl;
            this.Kb = i;
            this.JQ = z;
            super();
        }

        protected void m3783a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2794a((C0244d) this, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.4 */
    class C16304 extends LoadPlayersImpl {
        final /* synthetic */ PlayersImpl KI;
        final /* synthetic */ int Kb;

        C16304(PlayersImpl playersImpl, int i) {
            this.KI = playersImpl;
            this.Kb = i;
            super();
        }

        protected void m3785a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2794a((C0244d) this, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.5 */
    class C16315 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ PlayersImpl KI;
        final /* synthetic */ int Kb;

        C16315(PlayersImpl playersImpl, int i, boolean z) {
            this.KI = playersImpl;
            this.Kb = i;
            this.JQ = z;
            super();
        }

        protected void m3787a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2802a((C0244d) this, "playedWith", this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.6 */
    class C16326 extends LoadPlayersImpl {
        final /* synthetic */ PlayersImpl KI;
        final /* synthetic */ int Kb;

        C16326(PlayersImpl playersImpl, int i) {
            this.KI = playersImpl;
            this.Kb = i;
            super();
        }

        protected void m3789a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2802a((C0244d) this, "playedWith", this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.7 */
    class C16337 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ int Kb;

        protected void m3791a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2810a((C0244d) this, "playedWith", this.JT, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.8 */
    class C16348 extends LoadPlayersImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ int Kb;

        protected void m3793a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2810a((C0244d) this, "playedWith", this.JT, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.9 */
    class C16359 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ PlayersImpl KI;

        C16359(PlayersImpl playersImpl, boolean z) {
            this.KI = playersImpl;
            this.JQ = z;
            super();
        }

        protected void m3795a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2817a((C0244d) this, this.JQ);
        }
    }

    public Player getCurrentPlayer(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gn();
    }

    public String getCurrentPlayerId(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gm();
    }

    public Intent getPlayerSearchIntent(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gw();
    }

    public PendingResult<LoadPlayersResult> loadConnectedPlayers(GoogleApiClient apiClient, boolean forceReload) {
        return apiClient.m124a(new C16359(this, forceReload));
    }

    public PendingResult<LoadPlayersResult> loadInvitablePlayers(GoogleApiClient apiClient, int pageSize, boolean forceReload) {
        return apiClient.m124a(new C16293(this, pageSize, forceReload));
    }

    public PendingResult<LoadPlayersResult> loadMoreInvitablePlayers(GoogleApiClient apiClient, int pageSize) {
        return apiClient.m124a(new C16304(this, pageSize));
    }

    public PendingResult<LoadPlayersResult> loadMoreRecentlyPlayedWithPlayers(GoogleApiClient apiClient, int pageSize) {
        return apiClient.m124a(new C16326(this, pageSize));
    }

    public PendingResult<LoadPlayersResult> loadPlayer(GoogleApiClient apiClient, String playerId) {
        return apiClient.m124a(new C16271(this, playerId));
    }

    public PendingResult<LoadPlayersResult> loadRecentlyPlayedWithPlayers(GoogleApiClient apiClient, int pageSize, boolean forceReload) {
        return apiClient.m124a(new C16315(this, pageSize, forceReload));
    }
}
