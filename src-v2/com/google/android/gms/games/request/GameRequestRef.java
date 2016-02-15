/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.games.request;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestEntity;
import java.util.ArrayList;
import java.util.List;

public final class GameRequestRef
extends b
implements GameRequest {
    private final int LE;

    public GameRequestRef(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.LE = n3;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return GameRequestEntity.a(this, object);
    }

    @Override
    public GameRequest freeze() {
        return new GameRequestEntity(this);
    }

    @Override
    public long getCreationTimestamp() {
        return this.getLong("creation_timestamp");
    }

    @Override
    public byte[] getData() {
        return this.getByteArray("data");
    }

    @Override
    public long getExpirationTimestamp() {
        return this.getLong("expiration_timestamp");
    }

    @Override
    public Game getGame() {
        return new GameRef(this.BB, this.BD);
    }

    @Override
    public int getRecipientStatus(String string2) {
        for (int i2 = this.BD; i2 < this.BD + this.LE; ++i2) {
            int n2 = this.BB.G(i2);
            if (!this.BB.getString("recipient_external_player_id", i2, n2).equals(string2)) continue;
            return this.BB.getInteger("recipient_status", i2, n2);
        }
        return -1;
    }

    @Override
    public List<Player> getRecipients() {
        ArrayList<Player> arrayList = new ArrayList<Player>(this.LE);
        for (int i2 = 0; i2 < this.LE; ++i2) {
            arrayList.add(new PlayerRef(this.BB, this.BD + i2, "recipient_"));
        }
        return arrayList;
    }

    @Override
    public String getRequestId() {
        return this.getString("external_request_id");
    }

    @Override
    public Player getSender() {
        return new PlayerRef(this.BB, this.BD, "sender_");
    }

    @Override
    public int getStatus() {
        return this.getInteger("status");
    }

    @Override
    public int getType() {
        return this.getInteger("type");
    }

    @Override
    public int hashCode() {
        return GameRequestEntity.a(this);
    }

    @Override
    public boolean isConsumed(String string2) {
        if (this.getRecipientStatus(string2) == 1) {
            return true;
        }
        return false;
    }

    public String toString() {
        return GameRequestEntity.c(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ((GameRequestEntity)this.freeze()).writeToParcel(parcel, n2);
    }
}

