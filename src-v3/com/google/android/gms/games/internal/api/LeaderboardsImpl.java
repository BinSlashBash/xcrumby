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
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.Leaderboards.LeaderboardMetadataResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult;
import com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;

public final class LeaderboardsImpl implements Leaderboards {

    private static abstract class LoadMetadataImpl extends BaseGamesApiMethodImpl<LeaderboardMetadataResult> {

        /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.LoadMetadataImpl.1 */
        class C13341 implements LeaderboardMetadataResult {
            final /* synthetic */ LoadMetadataImpl Kx;
            final /* synthetic */ Status wz;

            C13341(LoadMetadataImpl loadMetadataImpl, Status status) {
                this.Kx = loadMetadataImpl;
                this.wz = status;
            }

            public LeaderboardBuffer getLeaderboards() {
                return new LeaderboardBuffer(DataHolder.empty(14));
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadMetadataImpl() {
        }

        public LeaderboardMetadataResult m3519D(Status status) {
            return new C13341(this, status);
        }

        public /* synthetic */ Result m3520d(Status status) {
            return m3519D(status);
        }
    }

    private static abstract class LoadPlayerScoreImpl extends BaseGamesApiMethodImpl<LoadPlayerScoreResult> {

        /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.LoadPlayerScoreImpl.1 */
        class C13351 implements LoadPlayerScoreResult {
            final /* synthetic */ LoadPlayerScoreImpl Ky;
            final /* synthetic */ Status wz;

            C13351(LoadPlayerScoreImpl loadPlayerScoreImpl, Status status) {
                this.Ky = loadPlayerScoreImpl;
                this.wz = status;
            }

            public LeaderboardScore getScore() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private LoadPlayerScoreImpl() {
        }

        public LoadPlayerScoreResult m3521E(Status status) {
            return new C13351(this, status);
        }

        public /* synthetic */ Result m3522d(Status status) {
            return m3521E(status);
        }
    }

    private static abstract class LoadScoresImpl extends BaseGamesApiMethodImpl<LoadScoresResult> {

        /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.LoadScoresImpl.1 */
        class C13361 implements LoadScoresResult {
            final /* synthetic */ LoadScoresImpl Kz;
            final /* synthetic */ Status wz;

            C13361(LoadScoresImpl loadScoresImpl, Status status) {
                this.Kz = loadScoresImpl;
                this.wz = status;
            }

            public Leaderboard getLeaderboard() {
                return null;
            }

            public LeaderboardScoreBuffer getScores() {
                return new LeaderboardScoreBuffer(DataHolder.empty(14));
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadScoresImpl() {
        }

        public LoadScoresResult m3523F(Status status) {
            return new C13361(this, status);
        }

        public /* synthetic */ Result m3524d(Status status) {
            return m3523F(status);
        }
    }

    protected static abstract class SubmitScoreImpl extends BaseGamesApiMethodImpl<SubmitScoreResult> {

        /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.SubmitScoreImpl.1 */
        class C13371 implements SubmitScoreResult {
            final /* synthetic */ SubmitScoreImpl KA;
            final /* synthetic */ Status wz;

            C13371(SubmitScoreImpl submitScoreImpl, Status status) {
                this.KA = submitScoreImpl;
                this.wz = status;
            }

            public ScoreSubmissionData getScoreData() {
                return new ScoreSubmissionData(DataHolder.empty(14));
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        protected SubmitScoreImpl() {
        }

        public SubmitScoreResult m3525G(Status status) {
            return new C13371(this, status);
        }

        public /* synthetic */ Result m3526d(Status status) {
            return m3525G(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.10 */
    class AnonymousClass10 extends LoadScoresImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ String Kp;
        final /* synthetic */ int Kq;
        final /* synthetic */ int Kr;
        final /* synthetic */ int Ks;

        protected void m3729a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2809a((C0244d) this, this.JT, this.Kp, this.Kq, this.Kr, this.Ks, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.11 */
    class AnonymousClass11 extends LoadScoresImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ String Kp;
        final /* synthetic */ int Kq;
        final /* synthetic */ int Kr;
        final /* synthetic */ int Ks;

        protected void m3731a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2834b(this, this.JT, this.Kp, this.Kq, this.Kr, this.Ks, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.1 */
    class C16161 extends LoadMetadataImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ LeaderboardsImpl Ko;

        C16161(LeaderboardsImpl leaderboardsImpl, boolean z) {
            this.Ko = leaderboardsImpl;
            this.JQ = z;
            super();
        }

        protected void m3733a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2837b((C0244d) this, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.2 */
    class C16172 extends LoadMetadataImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ LeaderboardsImpl Ko;
        final /* synthetic */ String Kp;

        C16172(LeaderboardsImpl leaderboardsImpl, String str, boolean z) {
            this.Ko = leaderboardsImpl;
            this.Kp = str;
            this.JQ = z;
            super();
        }

        protected void m3735a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2813a((C0244d) this, this.Kp, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.3 */
    class C16183 extends LoadPlayerScoreImpl {
        final /* synthetic */ LeaderboardsImpl Ko;
        final /* synthetic */ String Kp;
        final /* synthetic */ int Kq;
        final /* synthetic */ int Kr;

        C16183(LeaderboardsImpl leaderboardsImpl, String str, int i, int i2) {
            this.Ko = leaderboardsImpl;
            this.Kp = str;
            this.Kq = i;
            this.Kr = i2;
            super();
        }

        protected void m3737a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2807a((C0244d) this, null, this.Kp, this.Kq, this.Kr);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.4 */
    class C16194 extends LoadScoresImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ LeaderboardsImpl Ko;
        final /* synthetic */ String Kp;
        final /* synthetic */ int Kq;
        final /* synthetic */ int Kr;
        final /* synthetic */ int Ks;

        C16194(LeaderboardsImpl leaderboardsImpl, String str, int i, int i2, int i3, boolean z) {
            this.Ko = leaderboardsImpl;
            this.Kp = str;
            this.Kq = i;
            this.Kr = i2;
            this.Ks = i3;
            this.JQ = z;
            super();
        }

        protected void m3739a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2800a((C0244d) this, this.Kp, this.Kq, this.Kr, this.Ks, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.5 */
    class C16205 extends LoadScoresImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ LeaderboardsImpl Ko;
        final /* synthetic */ String Kp;
        final /* synthetic */ int Kq;
        final /* synthetic */ int Kr;
        final /* synthetic */ int Ks;

        C16205(LeaderboardsImpl leaderboardsImpl, String str, int i, int i2, int i3, boolean z) {
            this.Ko = leaderboardsImpl;
            this.Kp = str;
            this.Kq = i;
            this.Kr = i2;
            this.Ks = i3;
            this.JQ = z;
            super();
        }

        protected void m3741a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2831b(this, this.Kp, this.Kq, this.Kr, this.Ks, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.6 */
    class C16216 extends LoadScoresImpl {
        final /* synthetic */ LeaderboardsImpl Ko;
        final /* synthetic */ int Ks;
        final /* synthetic */ LeaderboardScoreBuffer Kt;
        final /* synthetic */ int Ku;

        C16216(LeaderboardsImpl leaderboardsImpl, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
            this.Ko = leaderboardsImpl;
            this.Kt = leaderboardScoreBuffer;
            this.Ks = i;
            this.Ku = i2;
            super();
        }

        protected void m3743a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2796a((C0244d) this, this.Kt, this.Ks, this.Ku);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.7 */
    class C16227 extends SubmitScoreImpl {
        final /* synthetic */ LeaderboardsImpl Ko;
        final /* synthetic */ String Kp;
        final /* synthetic */ long Kv;
        final /* synthetic */ String Kw;

        C16227(LeaderboardsImpl leaderboardsImpl, String str, long j, String str2) {
            this.Ko = leaderboardsImpl;
            this.Kp = str;
            this.Kv = j;
            this.Kw = str2;
        }

        protected void m3745a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2805a((C0244d) this, this.Kp, this.Kv, this.Kw);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.8 */
    class C16238 extends LoadMetadataImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;

        protected void m3747a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2836b((C0244d) this, this.JT, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl.9 */
    class C16249 extends LoadMetadataImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ String Kp;

        protected void m3749a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2811a((C0244d) this, this.JT, this.Kp, this.JQ);
        }
    }

    public Intent getAllLeaderboardsIntent(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gp();
    }

    public Intent getLeaderboardIntent(GoogleApiClient apiClient, String leaderboardId) {
        return Games.m361c(apiClient).aA(leaderboardId);
    }

    public PendingResult<LoadPlayerScoreResult> loadCurrentPlayerLeaderboardScore(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection) {
        return apiClient.m124a(new C16183(this, leaderboardId, span, leaderboardCollection));
    }

    public PendingResult<LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient apiClient, String leaderboardId, boolean forceReload) {
        return apiClient.m124a(new C16172(this, leaderboardId, forceReload));
    }

    public PendingResult<LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient apiClient, boolean forceReload) {
        return apiClient.m124a(new C16161(this, forceReload));
    }

    public PendingResult<LoadScoresResult> loadMoreScores(GoogleApiClient apiClient, LeaderboardScoreBuffer buffer, int maxResults, int pageDirection) {
        return apiClient.m124a(new C16216(this, buffer, maxResults, pageDirection));
    }

    public PendingResult<LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
        return loadPlayerCenteredScores(apiClient, leaderboardId, span, leaderboardCollection, maxResults, false);
    }

    public PendingResult<LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
        return apiClient.m124a(new C16205(this, leaderboardId, span, leaderboardCollection, maxResults, forceReload));
    }

    public PendingResult<LoadScoresResult> loadTopScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
        return loadTopScores(apiClient, leaderboardId, span, leaderboardCollection, maxResults, false);
    }

    public PendingResult<LoadScoresResult> loadTopScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
        return apiClient.m124a(new C16194(this, leaderboardId, span, leaderboardCollection, maxResults, forceReload));
    }

    public void submitScore(GoogleApiClient apiClient, String leaderboardId, long score) {
        submitScore(apiClient, leaderboardId, score, null);
    }

    public void submitScore(GoogleApiClient apiClient, String leaderboardId, long score, String scoreTag) {
        Games.m361c(apiClient).m2805a(null, leaderboardId, score, scoreTag);
    }

    public PendingResult<SubmitScoreResult> submitScoreImmediate(GoogleApiClient apiClient, String leaderboardId, long score) {
        return submitScoreImmediate(apiClient, leaderboardId, score, null);
    }

    public PendingResult<SubmitScoreResult> submitScoreImmediate(GoogleApiClient apiClient, String leaderboardId, long score, String scoreTag) {
        return apiClient.m125b(new C16227(this, leaderboardId, score, scoreTag));
    }
}
