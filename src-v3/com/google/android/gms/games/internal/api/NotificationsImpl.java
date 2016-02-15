package com.google.android.gms.games.internal.api;

import android.os.Bundle;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.Notifications.ContactSettingLoadResult;
import com.google.android.gms.games.Notifications.GameMuteStatusChangeResult;
import com.google.android.gms.games.Notifications.GameMuteStatusLoadResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class NotificationsImpl implements Notifications {

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.1 */
    class C15321 extends BaseGamesApiMethodImpl<GameMuteStatusChangeResult> {
        final /* synthetic */ String KB;

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.1.1 */
        class C13381 implements GameMuteStatusChangeResult {
            final /* synthetic */ C15321 KC;
            final /* synthetic */ Status wz;

            C13381(C15321 c15321, Status status) {
                this.KC = c15321;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        public GameMuteStatusChangeResult m3527H(Status status) {
            return new C13381(this, status);
        }

        protected void m3529a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2847c((C0244d) this, this.KB, true);
        }

        public /* synthetic */ Result m3530d(Status status) {
            return m3527H(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.2 */
    class C15332 extends BaseGamesApiMethodImpl<GameMuteStatusChangeResult> {
        final /* synthetic */ String KB;

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.2.1 */
        class C13391 implements GameMuteStatusChangeResult {
            final /* synthetic */ C15332 KD;
            final /* synthetic */ Status wz;

            C13391(C15332 c15332, Status status) {
                this.KD = c15332;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        public GameMuteStatusChangeResult m3531H(Status status) {
            return new C13391(this, status);
        }

        protected void m3533a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2847c((C0244d) this, this.KB, false);
        }

        public /* synthetic */ Result m3534d(Status status) {
            return m3531H(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.3 */
    class C15343 extends BaseGamesApiMethodImpl<GameMuteStatusLoadResult> {
        final /* synthetic */ String KB;

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.3.1 */
        class C13401 implements GameMuteStatusLoadResult {
            final /* synthetic */ C15343 KE;
            final /* synthetic */ Status wz;

            C13401(C15343 c15343, Status status) {
                this.KE = c15343;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        public GameMuteStatusLoadResult m3535I(Status status) {
            return new C13401(this, status);
        }

        protected void m3537a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2873n(this, this.KB);
        }

        public /* synthetic */ Result m3538d(Status status) {
            return m3535I(status);
        }
    }

    private static abstract class ContactSettingLoadImpl extends BaseGamesApiMethodImpl<ContactSettingLoadResult> {

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.ContactSettingLoadImpl.1 */
        class C13411 implements ContactSettingLoadResult {
            final /* synthetic */ ContactSettingLoadImpl KH;
            final /* synthetic */ Status wz;

            C13411(ContactSettingLoadImpl contactSettingLoadImpl, Status status) {
                this.KH = contactSettingLoadImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private ContactSettingLoadImpl() {
        }

        public ContactSettingLoadResult m3539J(Status status) {
            return new C13411(this, status);
        }

        public /* synthetic */ Result m3540d(Status status) {
            return m3539J(status);
        }
    }

    private static abstract class ContactSettingUpdateImpl extends BaseGamesApiMethodImpl<Status> {
        private ContactSettingUpdateImpl() {
        }

        public /* synthetic */ Result m3541d(Status status) {
            return m3542f(status);
        }

        public Status m3542f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.4 */
    class C16254 extends ContactSettingLoadImpl {
        protected void m3751a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2866j(this);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.5 */
    class C16265 extends ContactSettingUpdateImpl {
        final /* synthetic */ boolean KF;
        final /* synthetic */ Bundle KG;

        protected void m3753a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2818a((C0244d) this, this.KF, this.KG);
        }
    }

    public void clear(GoogleApiClient apiClient, int notificationTypes) {
        Games.m361c(apiClient).aY(notificationTypes);
    }

    public void clearAll(GoogleApiClient apiClient) {
        clear(apiClient, 7);
    }
}
