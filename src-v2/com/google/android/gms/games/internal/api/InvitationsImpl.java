/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;

public final class InvitationsImpl
implements Invitations {
    @Override
    public Intent getInvitationInboxIntent(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gs();
    }

    @Override
    public PendingResult<Invitations.LoadInvitationsResult> loadInvitations(GoogleApiClient googleApiClient) {
        return this.loadInvitations(googleApiClient, 0);
    }

    @Override
    public PendingResult<Invitations.LoadInvitationsResult> loadInvitations(GoogleApiClient googleApiClient, final int n2) {
        return googleApiClient.a(new LoadInvitationsImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(this, n2);
            }
        });
    }

    @Override
    public void registerInvitationListener(GoogleApiClient googleApiClient, OnInvitationReceivedListener onInvitationReceivedListener) {
        Games.c(googleApiClient).a(onInvitationReceivedListener);
    }

    @Override
    public void unregisterInvitationListener(GoogleApiClient googleApiClient) {
        Games.c(googleApiClient).gt();
    }

    private static abstract class LoadInvitationsImpl
    extends Games.BaseGamesApiMethodImpl<Invitations.LoadInvitationsResult> {
        private LoadInvitationsImpl() {
        }

        public Invitations.LoadInvitationsResult C(final Status status) {
            return new Invitations.LoadInvitationsResult(){

                @Override
                public InvitationBuffer getInvitations() {
                    return new InvitationBuffer(DataHolder.empty(14));
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.C(status);
        }

    }

}

