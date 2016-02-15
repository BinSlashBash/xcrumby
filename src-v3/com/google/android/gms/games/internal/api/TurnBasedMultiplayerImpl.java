package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.CancelMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LeaveMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LoadMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LoadMatchesResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.UpdateMatchResult;
import java.util.List;

public final class TurnBasedMultiplayerImpl implements TurnBasedMultiplayer {

    private static abstract class CancelMatchImpl extends BaseGamesApiMethodImpl<CancelMatchResult> {
        private final String wp;

        /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.CancelMatchImpl.1 */
        class C13491 implements CancelMatchResult {
            final /* synthetic */ CancelMatchImpl Lj;
            final /* synthetic */ Status wz;

            C13491(CancelMatchImpl cancelMatchImpl, Status status) {
                this.Lj = cancelMatchImpl;
                this.wz = status;
            }

            public String getMatchId() {
                return this.Lj.wp;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        public CancelMatchImpl(String id) {
            this.wp = id;
        }

        public CancelMatchResult m3558R(Status status) {
            return new C13491(this, status);
        }

        public /* synthetic */ Result m3559d(Status status) {
            return m3558R(status);
        }
    }

    private static abstract class InitiateMatchImpl extends BaseGamesApiMethodImpl<InitiateMatchResult> {

        /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.InitiateMatchImpl.1 */
        class C13501 implements InitiateMatchResult {
            final /* synthetic */ InitiateMatchImpl Lk;
            final /* synthetic */ Status wz;

            C13501(InitiateMatchImpl initiateMatchImpl, Status status) {
                this.Lk = initiateMatchImpl;
                this.wz = status;
            }

            public TurnBasedMatch getMatch() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private InitiateMatchImpl() {
        }

        public InitiateMatchResult m3560S(Status status) {
            return new C13501(this, status);
        }

        public /* synthetic */ Result m3561d(Status status) {
            return m3560S(status);
        }
    }

    private static abstract class LeaveMatchImpl extends BaseGamesApiMethodImpl<LeaveMatchResult> {

        /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.LeaveMatchImpl.1 */
        class C13511 implements LeaveMatchResult {
            final /* synthetic */ LeaveMatchImpl Ll;
            final /* synthetic */ Status wz;

            C13511(LeaveMatchImpl leaveMatchImpl, Status status) {
                this.Ll = leaveMatchImpl;
                this.wz = status;
            }

            public TurnBasedMatch getMatch() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private LeaveMatchImpl() {
        }

        public LeaveMatchResult m3562T(Status status) {
            return new C13511(this, status);
        }

        public /* synthetic */ Result m3563d(Status status) {
            return m3562T(status);
        }
    }

    private static abstract class LoadMatchImpl extends BaseGamesApiMethodImpl<LoadMatchResult> {

        /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.LoadMatchImpl.1 */
        class C13521 implements LoadMatchResult {
            final /* synthetic */ LoadMatchImpl Lm;
            final /* synthetic */ Status wz;

            C13521(LoadMatchImpl loadMatchImpl, Status status) {
                this.Lm = loadMatchImpl;
                this.wz = status;
            }

            public TurnBasedMatch getMatch() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private LoadMatchImpl() {
        }

        public LoadMatchResult m3564U(Status status) {
            return new C13521(this, status);
        }

        public /* synthetic */ Result m3565d(Status status) {
            return m3564U(status);
        }
    }

    private static abstract class LoadMatchesImpl extends BaseGamesApiMethodImpl<LoadMatchesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.LoadMatchesImpl.1 */
        class C13531 implements LoadMatchesResult {
            final /* synthetic */ LoadMatchesImpl Ln;
            final /* synthetic */ Status wz;

            C13531(LoadMatchesImpl loadMatchesImpl, Status status) {
                this.Ln = loadMatchesImpl;
                this.wz = status;
            }

            public LoadMatchesResponse getMatches() {
                return new LoadMatchesResponse(new Bundle());
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadMatchesImpl() {
        }

        public LoadMatchesResult m3566V(Status status) {
            return new C13531(this, status);
        }

        public /* synthetic */ Result m3567d(Status status) {
            return m3566V(status);
        }
    }

    private static abstract class UpdateMatchImpl extends BaseGamesApiMethodImpl<UpdateMatchResult> {

        /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.UpdateMatchImpl.1 */
        class C13541 implements UpdateMatchResult {
            final /* synthetic */ UpdateMatchImpl Lo;
            final /* synthetic */ Status wz;

            C13541(UpdateMatchImpl updateMatchImpl, Status status) {
                this.Lo = updateMatchImpl;
                this.wz = status;
            }

            public TurnBasedMatch getMatch() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private UpdateMatchImpl() {
        }

        public UpdateMatchResult m3568W(Status status) {
            return new C13541(this, status);
        }

        public /* synthetic */ Result m3569d(Status status) {
            return m3568W(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.10 */
    class AnonymousClass10 extends LoadMatchImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ String Ld;

        AnonymousClass10(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String str) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Ld = str;
            super();
        }

        protected void m3813a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2862h(this, this.Ld);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.11 */
    class AnonymousClass11 extends InitiateMatchImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String Ld;

        protected void m3815a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2833b((C0244d) this, this.JT, this.Ld);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.12 */
    class AnonymousClass12 extends InitiateMatchImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String Ld;

        protected void m3817a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2846c((C0244d) this, this.JT, this.Ld);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.13 */
    class AnonymousClass13 extends LoadMatchesImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ int Le;
        final /* synthetic */ int[] Lf;

        protected void m3819a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2804a((C0244d) this, this.JT, this.Le, this.Lf);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.1 */
    class C16441 extends InitiateMatchImpl {
        final /* synthetic */ TurnBasedMatchConfig Lb;
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;

        C16441(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, TurnBasedMatchConfig turnBasedMatchConfig) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Lb = turnBasedMatchConfig;
            super();
        }

        protected void m3821a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2797a((C0244d) this, this.Lb);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.2 */
    class C16452 extends InitiateMatchImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ String Ld;

        C16452(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String str) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Ld = str;
            super();
        }

        protected void m3823a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2852d((C0244d) this, this.Ld);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.3 */
    class C16463 extends InitiateMatchImpl {
        final /* synthetic */ String Km;
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;

        C16463(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String str) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Km = str;
            super();
        }

        protected void m3825a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2856e(this, this.Km);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.4 */
    class C16474 extends UpdateMatchImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ String Ld;
        final /* synthetic */ byte[] Lg;
        final /* synthetic */ String Lh;
        final /* synthetic */ ParticipantResult[] Li;

        C16474(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Ld = str;
            this.Lg = bArr;
            this.Lh = str2;
            this.Li = participantResultArr;
            super();
        }

        protected void m3827a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2814a((C0244d) this, this.Ld, this.Lg, this.Lh, this.Li);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.5 */
    class C16485 extends UpdateMatchImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ String Ld;
        final /* synthetic */ byte[] Lg;
        final /* synthetic */ ParticipantResult[] Li;

        C16485(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Ld = str;
            this.Lg = bArr;
            this.Li = participantResultArr;
            super();
        }

        protected void m3829a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2815a((C0244d) this, this.Ld, this.Lg, this.Li);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.6 */
    class C16496 extends LeaveMatchImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ String Ld;

        C16496(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String str) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Ld = str;
            super();
        }

        protected void m3831a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2858f(this, this.Ld);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.7 */
    class C16507 extends LeaveMatchImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ String Ld;
        final /* synthetic */ String Lh;

        C16507(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String str, String str2) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Ld = str;
            this.Lh = str2;
            super();
        }

        protected void m3833a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2806a((C0244d) this, this.Ld, this.Lh);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.8 */
    class C16518 extends CancelMatchImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ String Ld;

        C16518(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, String x0, String str) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Ld = str;
            super(x0);
        }

        protected void m3835a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2860g(this, this.Ld);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.TurnBasedMultiplayerImpl.9 */
    class C16529 extends LoadMatchesImpl {
        final /* synthetic */ TurnBasedMultiplayerImpl Lc;
        final /* synthetic */ int Le;
        final /* synthetic */ int[] Lf;

        C16529(TurnBasedMultiplayerImpl turnBasedMultiplayerImpl, int i, int[] iArr) {
            this.Lc = turnBasedMultiplayerImpl;
            this.Le = i;
            this.Lf = iArr;
            super();
        }

        protected void m3837a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2795a((C0244d) this, this.Le, this.Lf);
        }
    }

    public PendingResult<InitiateMatchResult> acceptInvitation(GoogleApiClient apiClient, String invitationId) {
        return apiClient.m125b(new C16463(this, invitationId));
    }

    public PendingResult<CancelMatchResult> cancelMatch(GoogleApiClient apiClient, String matchId) {
        return apiClient.m125b(new C16518(this, matchId, matchId));
    }

    public PendingResult<InitiateMatchResult> createMatch(GoogleApiClient apiClient, TurnBasedMatchConfig config) {
        return apiClient.m125b(new C16441(this, config));
    }

    public void declineInvitation(GoogleApiClient apiClient, String invitationId) {
        Games.m361c(apiClient).m2872m(invitationId, 1);
    }

    public void dismissInvitation(GoogleApiClient apiClient, String invitationId) {
        Games.m361c(apiClient).m2870l(invitationId, 1);
    }

    public void dismissMatch(GoogleApiClient apiClient, String matchId) {
        Games.m361c(apiClient).aB(matchId);
    }

    public PendingResult<UpdateMatchResult> finishMatch(GoogleApiClient apiClient, String matchId) {
        return finishMatch(apiClient, matchId, null, (ParticipantResult[]) null);
    }

    public PendingResult<UpdateMatchResult> finishMatch(GoogleApiClient apiClient, String matchId, byte[] matchData, List<ParticipantResult> results) {
        return finishMatch(apiClient, matchId, matchData, results == null ? null : (ParticipantResult[]) results.toArray(new ParticipantResult[results.size()]));
    }

    public PendingResult<UpdateMatchResult> finishMatch(GoogleApiClient apiClient, String matchId, byte[] matchData, ParticipantResult... results) {
        return apiClient.m125b(new C16485(this, matchId, matchData, results));
    }

    public Intent getInboxIntent(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gr();
    }

    public int getMaxMatchDataSize(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gA();
    }

    public Intent getSelectOpponentsIntent(GoogleApiClient apiClient, int minPlayers, int maxPlayers) {
        return Games.m361c(apiClient).m2787a(minPlayers, maxPlayers, true);
    }

    public Intent getSelectOpponentsIntent(GoogleApiClient apiClient, int minPlayers, int maxPlayers, boolean allowAutomatch) {
        return Games.m361c(apiClient).m2787a(minPlayers, maxPlayers, allowAutomatch);
    }

    public PendingResult<LeaveMatchResult> leaveMatch(GoogleApiClient apiClient, String matchId) {
        return apiClient.m125b(new C16496(this, matchId));
    }

    public PendingResult<LeaveMatchResult> leaveMatchDuringTurn(GoogleApiClient apiClient, String matchId, String pendingParticipantId) {
        return apiClient.m125b(new C16507(this, matchId, pendingParticipantId));
    }

    public PendingResult<LoadMatchResult> loadMatch(GoogleApiClient apiClient, String matchId) {
        return apiClient.m124a(new AnonymousClass10(this, matchId));
    }

    public PendingResult<LoadMatchesResult> loadMatchesByStatus(GoogleApiClient apiClient, int invitationSortOrder, int[] matchTurnStatuses) {
        return apiClient.m124a(new C16529(this, invitationSortOrder, matchTurnStatuses));
    }

    public PendingResult<LoadMatchesResult> loadMatchesByStatus(GoogleApiClient apiClient, int[] matchTurnStatuses) {
        return loadMatchesByStatus(apiClient, 0, matchTurnStatuses);
    }

    public void registerMatchUpdateListener(GoogleApiClient apiClient, OnTurnBasedMatchUpdateReceivedListener listener) {
        Games.m361c(apiClient).m2823a(listener);
    }

    public PendingResult<InitiateMatchResult> rematch(GoogleApiClient apiClient, String matchId) {
        return apiClient.m125b(new C16452(this, matchId));
    }

    public PendingResult<UpdateMatchResult> takeTurn(GoogleApiClient apiClient, String matchId, byte[] matchData, String pendingParticipantId) {
        return takeTurn(apiClient, matchId, matchData, pendingParticipantId, (ParticipantResult[]) null);
    }

    public PendingResult<UpdateMatchResult> takeTurn(GoogleApiClient apiClient, String matchId, byte[] matchData, String pendingParticipantId, List<ParticipantResult> results) {
        return takeTurn(apiClient, matchId, matchData, pendingParticipantId, results == null ? null : (ParticipantResult[]) results.toArray(new ParticipantResult[results.size()]));
    }

    public PendingResult<UpdateMatchResult> takeTurn(GoogleApiClient apiClient, String matchId, byte[] matchData, String pendingParticipantId, ParticipantResult... results) {
        return apiClient.m125b(new C16474(this, matchId, matchData, pendingParticipantId, results));
    }

    public void unregisterMatchUpdateListener(GoogleApiClient apiClient) {
        Games.m361c(apiClient).gu();
    }
}
