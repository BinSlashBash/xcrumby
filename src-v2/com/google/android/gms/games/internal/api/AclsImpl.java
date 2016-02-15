/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.game.Acls;

public final class AclsImpl
implements Acls {
    private static Acls.LoadAclResult v(final Status status) {
        return new Acls.LoadAclResult(){

            @Override
            public Status getStatus() {
                return status;
            }

            @Override
            public void release() {
            }
        };
    }

    private static abstract class LoadNotifyAclImpl
    extends Games.BaseGamesApiMethodImpl<Acls.LoadAclResult> {
        private LoadNotifyAclImpl() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.x(status);
        }

        public Acls.LoadAclResult x(Status status) {
            return AclsImpl.v(status);
        }
    }

    private static abstract class UpdateNotifyAclImpl
    extends Games.BaseGamesApiMethodImpl<Status> {
        private UpdateNotifyAclImpl() {
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

