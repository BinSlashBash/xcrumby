/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.games.multiplayer;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;

public interface Invitations {
    public Intent getInvitationInboxIntent(GoogleApiClient var1);

    public PendingResult<LoadInvitationsResult> loadInvitations(GoogleApiClient var1);

    public PendingResult<LoadInvitationsResult> loadInvitations(GoogleApiClient var1, int var2);

    public void registerInvitationListener(GoogleApiClient var1, OnInvitationReceivedListener var2);

    public void unregisterInvitationListener(GoogleApiClient var1);

    public static interface LoadInvitationsResult
    extends Releasable,
    Result {
        public InvitationBuffer getInvitations();
    }

}

