/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.games.internal.request;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.internal.request.GameRequestClusterCreator;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestEntity;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class GameRequestCluster
implements SafeParcelable,
GameRequest {
    public static final GameRequestClusterCreator CREATOR = new GameRequestClusterCreator();
    private final ArrayList<GameRequestEntity> LM;
    private final int xH;

    GameRequestCluster(int n2, ArrayList<GameRequestEntity> arrayList) {
        this.xH = n2;
        this.LM = arrayList;
        this.hn();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void hn() {
        boolean bl2 = !this.LM.isEmpty();
        fb.x(bl2);
        GameRequest gameRequest = this.LM.get(0);
        int n2 = this.LM.size();
        int n3 = 1;
        while (n3 < n2) {
            GameRequest gameRequest2 = this.LM.get(n3);
            bl2 = gameRequest.getType() == gameRequest2.getType();
            fb.a(bl2, "All the requests must be of the same type");
            fb.a(gameRequest.getSender().equals(gameRequest2.getSender()), "All the requests must be from the same sender");
            ++n3;
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof GameRequestCluster)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (GameRequestCluster)object;
        if (object.LM.size() != this.LM.size()) {
            return false;
        }
        int n2 = this.LM.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (((GameRequest)this.LM.get(i2)).equals(object.LM.get(i2))) continue;
            return false;
        }
        return true;
    }

    @Override
    public GameRequest freeze() {
        return this;
    }

    @Override
    public long getCreationTimestamp() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public byte[] getData() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public long getExpirationTimestamp() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public Game getGame() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public int getRecipientStatus(String string2) {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    public /* synthetic */ List getRecipients() {
        return this.hA();
    }

    @Override
    public String getRequestId() {
        return this.LM.get(0).getRequestId();
    }

    @Override
    public Player getSender() {
        return this.LM.get(0).getSender();
    }

    @Override
    public int getStatus() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public int getType() {
        return this.LM.get(0).getType();
    }

    public int getVersionCode() {
        return this.xH;
    }

    public ArrayList<Player> hA() {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    public int hashCode() {
        return fo.hashCode(this.LM.toArray());
    }

    public ArrayList<GameRequest> hz() {
        return new ArrayList<GameRequest>(this.LM);
    }

    @Override
    public boolean isConsumed(String string2) {
        throw new UnsupportedOperationException("Method not supported on a cluster");
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        GameRequestClusterCreator.a(this, parcel, n2);
    }
}

