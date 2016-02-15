package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.GamesMetadata.LoadExtendedGamesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameInstancesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameSearchSuggestionsResult;
import com.google.android.gms.games.GamesMetadata.LoadGamesResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class GamesMetadataImpl implements GamesMetadata {

    private static abstract class LoadExtendedGamesImpl extends BaseGamesApiMethodImpl<LoadExtendedGamesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadExtendedGamesImpl.1 */
        class C13291 implements LoadExtendedGamesResult {
            final /* synthetic */ LoadExtendedGamesImpl Kg;
            final /* synthetic */ Status wz;

            C13291(LoadExtendedGamesImpl loadExtendedGamesImpl, Status status) {
                this.Kg = loadExtendedGamesImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadExtendedGamesImpl() {
        }

        public /* synthetic */ Result m3509d(Status status) {
            return m3510y(status);
        }

        public LoadExtendedGamesResult m3510y(Status status) {
            return new C13291(this, status);
        }
    }

    private static abstract class LoadGameInstancesImpl extends BaseGamesApiMethodImpl<LoadGameInstancesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameInstancesImpl.1 */
        class C13301 implements LoadGameInstancesResult {
            final /* synthetic */ LoadGameInstancesImpl Kh;
            final /* synthetic */ Status wz;

            C13301(LoadGameInstancesImpl loadGameInstancesImpl, Status status) {
                this.Kh = loadGameInstancesImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadGameInstancesImpl() {
        }

        public /* synthetic */ Result m3511d(Status status) {
            return m3512z(status);
        }

        public LoadGameInstancesResult m3512z(Status status) {
            return new C13301(this, status);
        }
    }

    private static abstract class LoadGameSearchSuggestionsImpl extends BaseGamesApiMethodImpl<LoadGameSearchSuggestionsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameSearchSuggestionsImpl.1 */
        class C13311 implements LoadGameSearchSuggestionsResult {
            final /* synthetic */ LoadGameSearchSuggestionsImpl Ki;
            final /* synthetic */ Status wz;

            C13311(LoadGameSearchSuggestionsImpl loadGameSearchSuggestionsImpl, Status status) {
                this.Ki = loadGameSearchSuggestionsImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadGameSearchSuggestionsImpl() {
        }

        public LoadGameSearchSuggestionsResult m3513A(Status status) {
            return new C13311(this, status);
        }

        public /* synthetic */ Result m3514d(Status status) {
            return m3513A(status);
        }
    }

    private static abstract class LoadGamesImpl extends BaseGamesApiMethodImpl<LoadGamesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGamesImpl.1 */
        class C13321 implements LoadGamesResult {
            final /* synthetic */ LoadGamesImpl Kj;
            final /* synthetic */ Status wz;

            C13321(LoadGamesImpl loadGamesImpl, Status status) {
                this.Kj = loadGamesImpl;
                this.wz = status;
            }

            public GameBuffer getGames() {
                return new GameBuffer(DataHolder.empty(14));
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadGamesImpl() {
        }

        public LoadGamesResult m3515B(Status status) {
            return new C13321(this, status);
        }

        public /* synthetic */ Result m3516d(Status status) {
            return m3515B(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.10 */
    class AnonymousClass10 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        protected void m3693a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2803a((C0244d) this, this.Ka, this.Kb, false, true, false, this.Kc);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.11 */
    class AnonymousClass11 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        protected void m3695a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2803a((C0244d) this, this.Ka, this.Kb, true, false, this.JQ, this.Kc);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.12 */
    class AnonymousClass12 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        protected void m3697a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2803a((C0244d) this, this.Ka, this.Kb, true, true, false, this.Kc);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.13 */
    class AnonymousClass13 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        protected void m3699a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2845c(this, this.Kd, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.14 */
    class AnonymousClass14 extends LoadExtendedGamesImpl {
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        protected void m3701a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2845c(this, this.Kd, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.15 */
    class AnonymousClass15 extends LoadGameSearchSuggestionsImpl {
        final /* synthetic */ String Kd;

        protected void m3703a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2868k(this, this.Kd);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.1 */
    class C16041 extends LoadGamesImpl {
        final /* synthetic */ GamesMetadataImpl JZ;

        C16041(GamesMetadataImpl gamesMetadataImpl) {
            this.JZ = gamesMetadataImpl;
            super();
        }

        protected void m3705a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2859g(this);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.2 */
    class C16052 extends LoadExtendedGamesImpl {
        final /* synthetic */ String JT;

        protected void m3707a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2865i((C0244d) this, this.JT);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.3 */
    class C16063 extends LoadGameInstancesImpl {
        final /* synthetic */ String JT;

        protected void m3709a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2867j(this, this.JT);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.4 */
    class C16074 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        protected void m3711a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2832b(this, null, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.5 */
    class C16085 extends LoadExtendedGamesImpl {
        final /* synthetic */ int Kb;

        protected void m3713a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2832b(this, null, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.6 */
    class C16096 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JS;
        final /* synthetic */ int Kb;

        protected void m3715a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2832b(this, this.JS, this.Kb, false, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.7 */
    class C16107 extends LoadExtendedGamesImpl {
        final /* synthetic */ String JS;
        final /* synthetic */ int Kb;

        protected void m3717a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2832b(this, this.JS, this.Kb, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.8 */
    class C16118 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;
        final /* synthetic */ int Ke;
        final /* synthetic */ boolean Kf;

        protected void m3719a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2793a((C0244d) this, this.Kb, this.Ke, this.Kf, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.9 */
    class C16129 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        protected void m3721a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2803a((C0244d) this, this.Ka, this.Kb, false, false, this.JQ, this.Kc);
        }
    }

    public Game getCurrentGame(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).go();
    }

    public PendingResult<LoadGamesResult> loadGame(GoogleApiClient apiClient) {
        return apiClient.m124a(new C16041(this));
    }
}
