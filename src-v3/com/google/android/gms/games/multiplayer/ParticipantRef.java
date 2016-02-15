package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;

public final class ParticipantRef extends C0251b implements Participant {
    private final PlayerRef LL;

    public ParticipantRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
        this.LL = new PlayerRef(holder, dataRow);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return ParticipantEntity.m3402a(this, obj);
    }

    public Participant freeze() {
        return new ParticipantEntity(this);
    }

    public int getCapabilities() {
        return getInteger("capabilities");
    }

    public String getDisplayName() {
        return ai("external_player_id") ? getString("default_display_name") : this.LL.getDisplayName();
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        if (ai("external_player_id")) {
            m142a("default_display_name", dataOut);
        } else {
            this.LL.getDisplayName(dataOut);
        }
    }

    public Uri getHiResImageUri() {
        return ai("external_player_id") ? ah("default_display_hi_res_image_uri") : this.LL.getHiResImageUri();
    }

    public String getHiResImageUrl() {
        return ai("external_player_id") ? getString("default_display_hi_res_image_url") : this.LL.getHiResImageUrl();
    }

    public Uri getIconImageUri() {
        return ai("external_player_id") ? ah("default_display_image_uri") : this.LL.getIconImageUri();
    }

    public String getIconImageUrl() {
        return ai("external_player_id") ? getString("default_display_image_url") : this.LL.getIconImageUrl();
    }

    public String getParticipantId() {
        return getString("external_participant_id");
    }

    public Player getPlayer() {
        return ai("external_player_id") ? null : this.LL;
    }

    public ParticipantResult getResult() {
        if (ai("result_type")) {
            return null;
        }
        return new ParticipantResult(getParticipantId(), getInteger("result_type"), getInteger("placing"));
    }

    public int getStatus() {
        return getInteger("player_status");
    }

    public String gi() {
        return getString("client_address");
    }

    public int hashCode() {
        return ParticipantEntity.m3401a(this);
    }

    public boolean isConnectedToRoom() {
        return getInteger("connected") > 0;
    }

    public String toString() {
        return ParticipantEntity.m3403b((Participant) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        ((ParticipantEntity) freeze()).writeToParcel(dest, flags);
    }
}
