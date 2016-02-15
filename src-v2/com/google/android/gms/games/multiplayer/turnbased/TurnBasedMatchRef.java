/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.games.multiplayer.turnbased;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantRef;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchEntity;
import java.util.ArrayList;

public final class TurnBasedMatchRef
extends b
implements TurnBasedMatch {
    private final int LE;
    private final Game LS;

    TurnBasedMatchRef(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.LS = new GameRef(dataHolder, n2);
        this.LE = n3;
    }

    @Override
    public boolean canRematch() {
        if (this.getTurnStatus() == 3 && this.getRematchId() == null && this.getParticipants().size() > 1) {
            return true;
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return TurnBasedMatchEntity.a((TurnBasedMatch)this, object);
    }

    @Override
    public TurnBasedMatch freeze() {
        return new TurnBasedMatchEntity(this);
    }

    @Override
    public Bundle getAutoMatchCriteria() {
        if (!this.getBoolean("has_automatch_criteria")) {
            return null;
        }
        return TurnBasedMatchConfig.createAutoMatchCriteria(this.getInteger("automatch_min_players"), this.getInteger("automatch_max_players"), this.getLong("automatch_bit_mask"));
    }

    @Override
    public int getAvailableAutoMatchSlots() {
        if (!this.getBoolean("has_automatch_criteria")) {
            return 0;
        }
        return this.getInteger("automatch_max_players");
    }

    @Override
    public long getCreationTimestamp() {
        return this.getLong("creation_timestamp");
    }

    @Override
    public String getCreatorId() {
        return this.getString("creator_external");
    }

    @Override
    public byte[] getData() {
        return this.getByteArray("data");
    }

    @Override
    public String getDescription() {
        return this.getString("description");
    }

    @Override
    public void getDescription(CharArrayBuffer charArrayBuffer) {
        this.a("description", charArrayBuffer);
    }

    @Override
    public Participant getDescriptionParticipant() {
        return this.getParticipant(this.getDescriptionParticipantId());
    }

    @Override
    public String getDescriptionParticipantId() {
        return this.getString("description_participant_id");
    }

    @Override
    public Game getGame() {
        return this.LS;
    }

    @Override
    public long getLastUpdatedTimestamp() {
        return this.getLong("last_updated_timestamp");
    }

    @Override
    public String getLastUpdaterId() {
        return this.getString("last_updater_external");
    }

    @Override
    public String getMatchId() {
        return this.getString("external_match_id");
    }

    @Override
    public int getMatchNumber() {
        return this.getInteger("match_number");
    }

    @Override
    public Participant getParticipant(String string2) {
        return TurnBasedMatchEntity.c(this, string2);
    }

    @Override
    public String getParticipantId(String string2) {
        return TurnBasedMatchEntity.b(this, string2);
    }

    @Override
    public ArrayList<String> getParticipantIds() {
        return TurnBasedMatchEntity.c(this);
    }

    @Override
    public int getParticipantStatus(String string2) {
        return TurnBasedMatchEntity.a((TurnBasedMatch)this, string2);
    }

    @Override
    public ArrayList<Participant> getParticipants() {
        ArrayList<Participant> arrayList = new ArrayList<Participant>(this.LE);
        for (int i2 = 0; i2 < this.LE; ++i2) {
            arrayList.add(new ParticipantRef(this.BB, this.BD + i2));
        }
        return arrayList;
    }

    @Override
    public String getPendingParticipantId() {
        return this.getString("pending_participant_external");
    }

    @Override
    public byte[] getPreviousMatchData() {
        return this.getByteArray("previous_match_data");
    }

    @Override
    public String getRematchId() {
        return this.getString("rematch_id");
    }

    @Override
    public int getStatus() {
        return this.getInteger("status");
    }

    @Override
    public int getTurnStatus() {
        return this.getInteger("user_match_status");
    }

    @Override
    public int getVariant() {
        return this.getInteger("variant");
    }

    @Override
    public int getVersion() {
        return this.getInteger("version");
    }

    @Override
    public int hashCode() {
        return TurnBasedMatchEntity.a(this);
    }

    @Override
    public boolean isLocallyModified() {
        return this.getBoolean("upsync_required");
    }

    public String toString() {
        return TurnBasedMatchEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ((TurnBasedMatchEntity)this.freeze()).writeToParcel(parcel, n2);
    }
}

