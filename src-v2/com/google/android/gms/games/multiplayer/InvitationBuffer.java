/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationRef;

public final class InvitationBuffer
extends d<Invitation> {
    public InvitationBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    protected /* synthetic */ Object c(int n2, int n3) {
        return this.getEntry(n2, n3);
    }

    protected Invitation getEntry(int n2, int n3) {
        return new InvitationRef(this.BB, n2, n3);
    }

    @Override
    protected String getPrimaryDataMarkerColumn() {
        return "external_invitation_id";
    }
}

