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
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult;
import com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class AchievementsImpl implements Achievements {

    private static abstract class LoadImpl extends BaseGamesApiMethodImpl<LoadAchievementsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.LoadImpl.1 */
        class C13261 implements LoadAchievementsResult {
            final /* synthetic */ LoadImpl JW;
            final /* synthetic */ Status wz;

            C13261(LoadImpl loadImpl, Status status) {
                this.JW = loadImpl;
                this.wz = status;
            }

            public AchievementBuffer getAchievements() {
                return new AchievementBuffer(DataHolder.empty(14));
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadImpl() {
        }

        public /* synthetic */ Result m3500d(Status status) {
            return m3501t(status);
        }

        public LoadAchievementsResult m3501t(Status status) {
            return new C13261(this, status);
        }
    }

    private static abstract class UpdateImpl extends BaseGamesApiMethodImpl<UpdateAchievementResult> {
        private final String wp;

        /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.UpdateImpl.1 */
        class C13271 implements UpdateAchievementResult {
            final /* synthetic */ UpdateImpl JX;
            final /* synthetic */ Status wz;

            C13271(UpdateImpl updateImpl, Status status) {
                this.JX = updateImpl;
                this.wz = status;
            }

            public String getAchievementId() {
                return this.JX.wp;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        public UpdateImpl(String id) {
            this.wp = id;
        }

        public /* synthetic */ Result m3503d(Status status) {
            return m3504u(status);
        }

        public UpdateAchievementResult m3504u(Status status) {
            return new C13271(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.10 */
    class AnonymousClass10 extends LoadImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JS;
        final /* synthetic */ String JT;

        public void m3669a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2835b((C0244d) this, this.JS, this.JT, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.1 */
    class C15931 extends LoadImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ AchievementsImpl JR;

        C15931(AchievementsImpl achievementsImpl, boolean z) {
            this.JR = achievementsImpl;
            this.JQ = z;
            super();
        }

        public void m3671a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2848c((C0244d) this, this.JQ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.2 */
    class C15942 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;

        C15942(AchievementsImpl achievementsImpl, String x0, String str) {
            this.JR = achievementsImpl;
            this.JU = str;
            super(x0);
        }

        public void m3673a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2829b(null, this.JU);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.3 */
    class C15953 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;

        C15953(AchievementsImpl achievementsImpl, String x0, String str) {
            this.JR = achievementsImpl;
            this.JU = str;
            super(x0);
        }

        public void m3675a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2829b((C0244d) this, this.JU);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.4 */
    class C15964 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;

        C15964(AchievementsImpl achievementsImpl, String x0, String str) {
            this.JR = achievementsImpl;
            this.JU = str;
            super(x0);
        }

        public void m3677a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2843c(null, this.JU);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.5 */
    class C15975 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;

        C15975(AchievementsImpl achievementsImpl, String x0, String str) {
            this.JR = achievementsImpl;
            this.JU = str;
            super(x0);
        }

        public void m3679a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2843c((C0244d) this, this.JU);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.6 */
    class C15986 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;
        final /* synthetic */ int JV;

        C15986(AchievementsImpl achievementsImpl, String x0, String str, int i) {
            this.JR = achievementsImpl;
            this.JU = str;
            this.JV = i;
            super(x0);
        }

        public void m3681a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2799a(null, this.JU, this.JV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.7 */
    class C15997 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;
        final /* synthetic */ int JV;

        C15997(AchievementsImpl achievementsImpl, String x0, String str, int i) {
            this.JR = achievementsImpl;
            this.JU = str;
            this.JV = i;
            super(x0);
        }

        public void m3683a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2799a((C0244d) this, this.JU, this.JV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.8 */
    class C16008 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;
        final /* synthetic */ int JV;

        C16008(AchievementsImpl achievementsImpl, String x0, String str, int i) {
            this.JR = achievementsImpl;
            this.JU = str;
            this.JV = i;
            super(x0);
        }

        public void m3685a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2830b(null, this.JU, this.JV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.9 */
    class C16019 extends UpdateImpl {
        final /* synthetic */ AchievementsImpl JR;
        final /* synthetic */ String JU;
        final /* synthetic */ int JV;

        C16019(AchievementsImpl achievementsImpl, String x0, String str, int i) {
            this.JR = achievementsImpl;
            this.JU = str;
            this.JV = i;
            super(x0);
        }

        public void m3687a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2830b((C0244d) this, this.JU, this.JV);
        }
    }

    public Intent getAchievementsIntent(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gq();
    }

    public void increment(GoogleApiClient apiClient, String id, int numSteps) {
        apiClient.m125b(new C15986(this, id, id, numSteps));
    }

    public PendingResult<UpdateAchievementResult> incrementImmediate(GoogleApiClient apiClient, String id, int numSteps) {
        return apiClient.m125b(new C15997(this, id, id, numSteps));
    }

    public PendingResult<LoadAchievementsResult> load(GoogleApiClient apiClient, boolean forceReload) {
        return apiClient.m124a(new C15931(this, forceReload));
    }

    public void reveal(GoogleApiClient apiClient, String id) {
        apiClient.m125b(new C15942(this, id, id));
    }

    public PendingResult<UpdateAchievementResult> revealImmediate(GoogleApiClient apiClient, String id) {
        return apiClient.m125b(new C15953(this, id, id));
    }

    public void setSteps(GoogleApiClient apiClient, String id, int numSteps) {
        apiClient.m125b(new C16008(this, id, id, numSteps));
    }

    public PendingResult<UpdateAchievementResult> setStepsImmediate(GoogleApiClient apiClient, String id, int numSteps) {
        return apiClient.m125b(new C16019(this, id, id, numSteps));
    }

    public void unlock(GoogleApiClient apiClient, String id) {
        apiClient.m125b(new C15964(this, id, id));
    }

    public PendingResult<UpdateAchievementResult> unlockImmediate(GoogleApiClient apiClient, String id) {
        return apiClient.m125b(new C15975(this, id, id));
    }
}
