/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.TurnBasedMatchTurnStatus;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchBuffer;

public final class LoadMatchesResponse {
    private final InvitationBuffer MV;
    private final TurnBasedMatchBuffer MW;
    private final TurnBasedMatchBuffer MX;
    private final TurnBasedMatchBuffer MY;

    /*
     * Enabled aggressive block sorting
     */
    public LoadMatchesResponse(Bundle object) {
        DataHolder dataHolder = LoadMatchesResponse.a((Bundle)object, 0);
        this.MV = dataHolder != null ? new InvitationBuffer(dataHolder) : null;
        dataHolder = LoadMatchesResponse.a((Bundle)object, 1);
        this.MW = dataHolder != null ? new TurnBasedMatchBuffer(dataHolder) : null;
        dataHolder = LoadMatchesResponse.a((Bundle)object, 2);
        this.MX = dataHolder != null ? new TurnBasedMatchBuffer(dataHolder) : null;
        if ((object = LoadMatchesResponse.a((Bundle)object, 3)) != null) {
            this.MY = new TurnBasedMatchBuffer((DataHolder)object);
            return;
        }
        this.MY = null;
    }

    private static DataHolder a(Bundle bundle, int n2) {
        String string2 = TurnBasedMatchTurnStatus.bd(n2);
        if (!bundle.containsKey(string2)) {
            return null;
        }
        return (DataHolder)bundle.getParcelable(string2);
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

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasData() {
        if (this.MV != null && this.MV.getCount() > 0 || this.MW != null && this.MW.getCount() > 0 || this.MX != null && this.MX.getCount() > 0 || this.MY != null && this.MY.getCount() > 0) {
            return true;
        }
        return false;
    }
}

