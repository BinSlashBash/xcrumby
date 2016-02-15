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
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.Invitations.LoadInvitationsResult;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;

public final class InvitationsImpl implements Invitations {

    private static abstract class LoadInvitationsImpl extends BaseGamesApiMethodImpl<LoadInvitationsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.InvitationsImpl.LoadInvitationsImpl.1 */
        class C13331 implements LoadInvitationsResult {
            final /* synthetic */ LoadInvitationsImpl Kn;
            final /* synthetic */ Status wz;

            C13331(LoadInvitationsImpl loadInvitationsImpl, Status status) {
                this.Kn = loadInvitationsImpl;
                this.wz = status;
            }

            public InvitationBuffer getInvitations() {
                return new InvitationBuffer(DataHolder.empty(14));
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadInvitationsImpl() {
        }

        public LoadInvitationsResult m3517C(Status status) {
            return new C13331(this, status);
        }

        public /* synthetic */ Result m3518d(Status status) {
            return m3517C(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.InvitationsImpl.1 */
    class C16131 extends LoadInvitationsImpl {
        final /* synthetic */ int Kk;
        final /* synthetic */ InvitationsImpl Kl;

        C16131(InvitationsImpl invitationsImpl, int i) {
            this.Kl = invitationsImpl;
            this.Kk = i;
            super();
        }

        protected void m3723a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2841c((C0244d) this, this.Kk);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.InvitationsImpl.2 */
    class C16142 extends LoadInvitationsImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ int Kk;

        protected void m3725a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2844c((C0244d) this, this.JT, this.Kk);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.InvitationsImpl.3 */
    class C16153 extends LoadInvitationsImpl {
        final /* synthetic */ String Km;

        protected void m3727a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2869l((C0244d) this, this.Km);
        }
    }

    public Intent getInvitationInboxIntent(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gs();
    }

    public PendingResult<LoadInvitationsResult> loadInvitations(GoogleApiClient apiClient) {
        return loadInvitations(apiClient, 0);
    }

    public PendingResult<LoadInvitationsResult> loadInvitations(GoogleApiClient apiClient, int sortOrder) {
        return apiClient.m124a(new C16131(this, sortOrder));
    }

    public void registerInvitationListener(GoogleApiClient apiClient, OnInvitationReceivedListener listener) {
        Games.m361c(apiClient).m2820a(listener);
    }

    public void unregisterInvitationListener(GoogleApiClient apiClient) {
        Games.m361c(apiClient).gt();
    }
}
