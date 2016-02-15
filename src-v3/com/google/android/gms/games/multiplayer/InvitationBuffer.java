package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.C0796d;
import com.google.android.gms.common.data.DataHolder;

public final class InvitationBuffer extends C0796d<Invitation> {
    public InvitationBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object m2891c(int i, int i2) {
        return getEntry(i, i2);
    }

    protected Invitation getEntry(int rowIndex, int numChildren) {
        return new InvitationRef(this.BB, rowIndex, numChildren);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "external_invitation_id";
    }
}
