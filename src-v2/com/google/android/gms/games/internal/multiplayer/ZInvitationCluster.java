/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.games.internal.multiplayer;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.internal.multiplayer.InvitationClusterCreator;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationEntity;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import java.util.ArrayList;
import java.util.Collection;

public final class ZInvitationCluster
implements SafeParcelable,
Invitation {
    public static final InvitationClusterCreator CREATOR = new InvitationClusterCreator();
    private final ArrayList<InvitationEntity> LG;
    private final int xH;

    ZInvitationCluster(int n2, ArrayList<InvitationEntity> arrayList) {
        this.xH = n2;
        this.LG = arrayList;
        this.hn();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void hn() {
        boolean bl2 = !this.LG.isEmpty();
        fb.x(bl2);
        Invitation invitation = this.LG.get(0);
        int n2 = this.LG.size();
        int n3 = 1;
        while (n3 < n2) {
            Invitation invitation2 = this.LG.get(n3);
            fb.a(invitation.getInviter().equals(invitation2.getInviter()), "All the invitations must be from the same inviter");
            ++n3;
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ZInvitationCluster)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (ZInvitationCluster)object;
        if (object.LG.size() != this.LG.size()) {
            return false;
        }
        int n2 = this.LG.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (((Invitation)this.LG.get(i2)).equals(object.LG.get(i2))) continue;
            return false;
        }
        return true;
    }

    @Override
    public Invitation freeze() {
        return this;
    }

    @Override
    public int getAvailableAutoMatchSlots() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public long getCreationTimestamp() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public Game getGame() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public String getInvitationId() {
        return this.LG.get(0).getInvitationId();
    }

    @Override
    public int getInvitationType() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public Participant getInviter() {
        return this.LG.get(0).getInviter();
    }

    @Override
    public ArrayList<Participant> getParticipants() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public int getVariant() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.LG.toArray());
    }

    public ArrayList<Invitation> ho() {
        return new ArrayList<Invitation>(this.LG);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        InvitationClusterCreator.a(this, parcel, n2);
    }
}

