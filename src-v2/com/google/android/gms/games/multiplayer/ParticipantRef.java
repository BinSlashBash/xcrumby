/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 *  android.os.Parcel
 */
package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.games.multiplayer.ParticipantResult;

public final class ParticipantRef
extends b
implements Participant {
    private final PlayerRef LL;

    public ParticipantRef(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
        this.LL = new PlayerRef(dataHolder, n2);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return ParticipantEntity.a(this, object);
    }

    @Override
    public Participant freeze() {
        return new ParticipantEntity(this);
    }

    @Override
    public int getCapabilities() {
        return this.getInteger("capabilities");
    }

    @Override
    public String getDisplayName() {
        if (this.ai("external_player_id")) {
            return this.getString("default_display_name");
        }
        return this.LL.getDisplayName();
    }

    @Override
    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        if (this.ai("external_player_id")) {
            this.a("default_display_name", charArrayBuffer);
            return;
        }
        this.LL.getDisplayName(charArrayBuffer);
    }

    @Override
    public Uri getHiResImageUri() {
        if (this.ai("external_player_id")) {
            return this.ah("default_display_hi_res_image_uri");
        }
        return this.LL.getHiResImageUri();
    }

    @Override
    public String getHiResImageUrl() {
        if (this.ai("external_player_id")) {
            return this.getString("default_display_hi_res_image_url");
        }
        return this.LL.getHiResImageUrl();
    }

    @Override
    public Uri getIconImageUri() {
        if (this.ai("external_player_id")) {
            return this.ah("default_display_image_uri");
        }
        return this.LL.getIconImageUri();
    }

    @Override
    public String getIconImageUrl() {
        if (this.ai("external_player_id")) {
            return this.getString("default_display_image_url");
        }
        return this.LL.getIconImageUrl();
    }

    @Override
    public String getParticipantId() {
        return this.getString("external_participant_id");
    }

    @Override
    public Player getPlayer() {
        if (this.ai("external_player_id")) {
            return null;
        }
        return this.LL;
    }

    @Override
    public ParticipantResult getResult() {
        if (this.ai("result_type")) {
            return null;
        }
        int n2 = this.getInteger("result_type");
        int n3 = this.getInteger("placing");
        return new ParticipantResult(this.getParticipantId(), n2, n3);
    }

    @Override
    public int getStatus() {
        return this.getInteger("player_status");
    }

    @Override
    public String gi() {
        return this.getString("client_address");
    }

    @Override
    public int hashCode() {
        return ParticipantEntity.a(this);
    }

    @Override
    public boolean isConnectedToRoom() {
        if (this.getInteger("connected") > 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return ParticipantEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ((ParticipantEntity)this.freeze()).writeToParcel(parcel, n2);
    }
}

