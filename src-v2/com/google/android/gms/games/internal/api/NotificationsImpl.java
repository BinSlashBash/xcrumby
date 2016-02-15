/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal.api;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class NotificationsImpl
implements Notifications {
    @Override
    public void clear(GoogleApiClient googleApiClient, int n2) {
        Games.c(googleApiClient).aY(n2);
    }

    @Override
    public void clearAll(GoogleApiClient googleApiClient) {
        this.clear(googleApiClient, 7);
    }

    private static abstract class ContactSettingLoadImpl
    extends Games.BaseGamesApiMethodImpl<Notifications.ContactSettingLoadResult> {
        private ContactSettingLoadImpl() {
        }

        public Notifications.ContactSettingLoadResult J(final Status status) {
            return new Notifications.ContactSettingLoadResult(){

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.J(status);
        }

    }

    private static abstract class ContactSettingUpdateImpl
    extends Games.BaseGamesApiMethodImpl<Status> {
        private ContactSettingUpdateImpl() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

}

