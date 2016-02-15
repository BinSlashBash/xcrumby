package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.game.Acls;
import com.google.android.gms.games.internal.game.Acls.LoadAclResult;

public final class AclsImpl implements Acls {

    /* renamed from: com.google.android.gms.games.internal.api.AclsImpl.1 */
    static class C13281 implements LoadAclResult {
        final /* synthetic */ Status wz;

        C13281(Status status) {
            this.wz = status;
        }

        public Status getStatus() {
            return this.wz;
        }

        public void release() {
        }
    }

    private static abstract class LoadNotifyAclImpl extends BaseGamesApiMethodImpl<LoadAclResult> {
        private LoadNotifyAclImpl() {
        }

        public /* synthetic */ Result m3505d(Status status) {
            return m3506x(status);
        }

        public LoadAclResult m3506x(Status status) {
            return AclsImpl.m1990v(status);
        }
    }

    private static abstract class UpdateNotifyAclImpl extends BaseGamesApiMethodImpl<Status> {
        private UpdateNotifyAclImpl() {
        }

        public /* synthetic */ Result m3507d(Status status) {
            return m3508f(status);
        }

        public Status m3508f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AclsImpl.2 */
    class C16022 extends LoadNotifyAclImpl {
        protected void m3689a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2864i(this);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AclsImpl.3 */
    class C16033 extends UpdateNotifyAclImpl {
        final /* synthetic */ String JY;

        protected void m3691a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2871m((C0244d) this, this.JY);
        }
    }

    private static LoadAclResult m1990v(Status status) {
        return new C13281(status);
    }
}
