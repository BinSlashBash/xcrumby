package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.TurnBasedMatchTurnStatus;
import com.google.android.gms.games.multiplayer.InvitationBuffer;

public final class LoadMatchesResponse {
    private final InvitationBuffer MV;
    private final TurnBasedMatchBuffer MW;
    private final TurnBasedMatchBuffer MX;
    private final TurnBasedMatchBuffer MY;

    public LoadMatchesResponse(Bundle matchData) {
        DataHolder a = m579a(matchData, 0);
        if (a != null) {
            this.MV = new InvitationBuffer(a);
        } else {
            this.MV = null;
        }
        a = m579a(matchData, 1);
        if (a != null) {
            this.MW = new TurnBasedMatchBuffer(a);
        } else {
            this.MW = null;
        }
        a = m579a(matchData, 2);
        if (a != null) {
            this.MX = new TurnBasedMatchBuffer(a);
        } else {
            this.MX = null;
        }
        a = m579a(matchData, 3);
        if (a != null) {
            this.MY = new TurnBasedMatchBuffer(a);
        } else {
            this.MY = null;
        }
    }

    private static DataHolder m579a(Bundle bundle, int i) {
        String bd = TurnBasedMatchTurnStatus.bd(i);
        return !bundle.containsKey(bd) ? null : (DataHolder) bundle.getParcelable(bd);
    }

    public void close() {
        if (this.MV != null) {
            this.MV.close();
        }
        if (this.MW != null) {
            this.MW.close();
        }
        if (this.MX != null) {
            this.MX.close();
        }
        if (this.MY != null) {
            this.MY.close();
        }
    }

    public TurnBasedMatchBuffer getCompletedMatches() {
        return this.MY;
    }

    public InvitationBuffer getInvitations() {
        return this.MV;
    }

    public TurnBasedMatchBuffer getMyTurnMatches() {
        return this.MW;
    }

    public TurnBasedMatchBuffer getTheirTurnMatches() {
        return this.MX;
    }

    public boolean hasData() {
        return (this.MV == null || this.MV.getCount() <= 0) ? (this.MW == null || this.MW.getCount() <= 0) ? (this.MX == null || this.MX.getCount() <= 0) ? this.MY != null && this.MY.getCount() > 0 : true : true : true;
    }
}
