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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchEntityCreator;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gm;
import java.util.ArrayList;
import java.util.Collection;

public final class TurnBasedMatchEntity
implements SafeParcelable,
TurnBasedMatch {
    public static final TurnBasedMatchEntityCreator CREATOR = new TurnBasedMatchEntityCreator();
    private final String HD;
    private final String Jb;
    private final GameEntity Lt;
    private final Bundle MO;
    private final String MS;
    private final long Mu;
    private final ArrayList<ParticipantEntity> Mx;
    private final int My;
    private final String Na;
    private final long Nb;
    private final String Nc;
    private final int Nd;
    private final int Ne;
    private final byte[] Nf;
    private final String Ng;
    private final byte[] Nh;
    private final int Ni;
    private final int Nj;
    private final boolean Nk;
    private final String Nl;
    private final int xH;

    TurnBasedMatchEntity(int n2, GameEntity gameEntity, String string2, String string3, long l2, String string4, long l3, String string5, int n3, int n4, int n5, byte[] arrby, ArrayList<ParticipantEntity> arrayList, String string6, byte[] arrby2, int n6, Bundle bundle, int n7, boolean bl2, String string7, String string8) {
        this.xH = n2;
        this.Lt = gameEntity;
        this.Jb = string2;
        this.MS = string3;
        this.Mu = l2;
        this.Na = string4;
        this.Nb = l3;
        this.Nc = string5;
        this.Nd = n3;
        this.Nj = n7;
        this.My = n4;
        this.Ne = n5;
        this.Nf = arrby;
        this.Mx = arrayList;
        this.Ng = string6;
        this.Nh = arrby2;
        this.Ni = n6;
        this.MO = bundle;
        this.Nk = bl2;
        this.HD = string7;
        this.Nl = string8;
    }

    /*
     * Enabled aggressive block sorting
     */
    public TurnBasedMatchEntity(TurnBasedMatch object) {
        this.xH = 2;
        this.Lt = new GameEntity(object.getGame());
        this.Jb = object.getMatchId();
        this.MS = object.getCreatorId();
        this.Mu = object.getCreationTimestamp();
        this.Na = object.getLastUpdaterId();
        this.Nb = object.getLastUpdatedTimestamp();
        this.Nc = object.getPendingParticipantId();
        this.Nd = object.getStatus();
        this.Nj = object.getTurnStatus();
        this.My = object.getVariant();
        this.Ne = object.getVersion();
        this.Ng = object.getRematchId();
        this.Ni = object.getMatchNumber();
        this.MO = object.getAutoMatchCriteria();
        this.Nk = object.isLocallyModified();
        this.HD = object.getDescription();
        this.Nl = object.getDescriptionParticipantId();
        byte[] arrby = object.getData();
        if (arrby == null) {
            this.Nf = null;
        } else {
            this.Nf = new byte[arrby.length];
            System.arraycopy(arrby, 0, this.Nf, 0, arrby.length);
        }
        if ((arrby = object.getPreviousMatchData()) == null) {
            this.Nh = null;
        } else {
            this.Nh = new byte[arrby.length];
            System.arraycopy(arrby, 0, this.Nh, 0, arrby.length);
        }
        object = object.getParticipants();
        int n2 = object.size();
        this.Mx = new ArrayList(n2);
        int n3 = 0;
        while (n3 < n2) {
            this.Mx.add((ParticipantEntity)((Participant)object.get(n3)).freeze());
            ++n3;
        }
    }

    static int a(TurnBasedMatch turnBasedMatch) {
        return fo.hashCode(new Object[]{turnBasedMatch.getGame(), turnBasedMatch.getMatchId(), turnBasedMatch.getCreatorId(), turnBasedMatch.getCreationTimestamp(), turnBasedMatch.getLastUpdaterId(), turnBasedMatch.getLastUpdatedTimestamp(), turnBasedMatch.getPendingParticipantId(), turnBasedMatch.getStatus(), turnBasedMatch.getTurnStatus(), turnBasedMatch.getDescription(), turnBasedMatch.getVariant(), turnBasedMatch.getVersion(), turnBasedMatch.getParticipants(), turnBasedMatch.getRematchId(), turnBasedMatch.getMatchNumber(), turnBasedMatch.getAutoMatchCriteria(), turnBasedMatch.getAvailableAutoMatchSlots(), turnBasedMatch.isLocallyModified()});
    }

    static int a(TurnBasedMatch turnBasedMatch, String string2) {
        ArrayList arrayList = turnBasedMatch.getParticipants();
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Participant participant = (Participant)arrayList.get(i2);
            if (!participant.getParticipantId().equals(string2)) continue;
            return participant.getStatus();
        }
        throw new IllegalStateException("Participant " + string2 + " is not in match " + turnBasedMatch.getMatchId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(TurnBasedMatch turnBasedMatch, Object object) {
        boolean bl2 = true;
        if (!(object instanceof TurnBasedMatch)) {
            return false;
        }
        boolean bl3 = bl2;
        if (turnBasedMatch == object) return bl3;
        if (!fo.equal((object = (TurnBasedMatch)object).getGame(), turnBasedMatch.getGame())) return false;
        if (!fo.equal(object.getMatchId(), turnBasedMatch.getMatchId())) return false;
        if (!fo.equal(object.getCreatorId(), turnBasedMatch.getCreatorId())) return false;
        if (!fo.equal(object.getCreationTimestamp(), turnBasedMatch.getCreationTimestamp())) return false;
        if (!fo.equal(object.getLastUpdaterId(), turnBasedMatch.getLastUpdaterId())) return false;
        if (!fo.equal(object.getLastUpdatedTimestamp(), turnBasedMatch.getLastUpdatedTimestamp())) return false;
        if (!fo.equal(object.getPendingParticipantId(), turnBasedMatch.getPendingParticipantId())) return false;
        if (!fo.equal(object.getStatus(), turnBasedMatch.getStatus())) return false;
        if (!fo.equal(object.getTurnStatus(), turnBasedMatch.getTurnStatus())) return false;
        if (!fo.equal(object.getDescription(), turnBasedMatch.getDescription())) return false;
        if (!fo.equal(object.getVariant(), turnBasedMatch.getVariant())) return false;
        if (!fo.equal(object.getVersion(), turnBasedMatch.getVersion())) return false;
        if (!fo.equal(object.getParticipants(), turnBasedMatch.getParticipants())) return false;
        if (!fo.equal(object.getRematchId(), turnBasedMatch.getRematchId())) return false;
        if (!fo.equal(object.getMatchNumber(), turnBasedMatch.getMatchNumber())) return false;
        if (!fo.equal((Object)object.getAutoMatchCriteria(), (Object)turnBasedMatch.getAutoMatchCriteria())) return false;
        if (!fo.equal(object.getAvailableAutoMatchSlots(), turnBasedMatch.getAvailableAutoMatchSlots())) return false;
        bl3 = bl2;
        if (fo.equal(object.isLocallyModified(), turnBasedMatch.isLocallyModified())) return bl3;
        return false;
    }

    static String b(TurnBasedMatch turnBasedMatch) {
        return fo.e(turnBasedMatch).a("Game", turnBasedMatch.getGame()).a("MatchId", turnBasedMatch.getMatchId()).a("CreatorId", turnBasedMatch.getCreatorId()).a("CreationTimestamp", turnBasedMatch.getCreationTimestamp()).a("LastUpdaterId", turnBasedMatch.getLastUpdaterId()).a("LastUpdatedTimestamp", turnBasedMatch.getLastUpdatedTimestamp()).a("PendingParticipantId", turnBasedMatch.getPendingParticipantId()).a("MatchStatus", turnBasedMatch.getStatus()).a("TurnStatus", turnBasedMatch.getTurnStatus()).a("Description", turnBasedMatch.getDescription()).a("Variant", turnBasedMatch.getVariant()).a("Data", turnBasedMatch.getData()).a("Version", turnBasedMatch.getVersion()).a("Participants", turnBasedMatch.getParticipants()).a("RematchId", turnBasedMatch.getRematchId()).a("PreviousData", turnBasedMatch.getPreviousMatchData()).a("MatchNumber", turnBasedMatch.getMatchNumber()).a("AutoMatchCriteria", (Object)turnBasedMatch.getAutoMatchCriteria()).a("AvailableAutoMatchSlots", turnBasedMatch.getAvailableAutoMatchSlots()).a("LocallyModified", turnBasedMatch.isLocallyModified()).a("DescriptionParticipantId", turnBasedMatch.getDescriptionParticipantId()).toString();
    }

    static String b(TurnBasedMatch object, String string2) {
        object = object.getParticipants();
        int n2 = object.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Participant participant = (Participant)object.get(i2);
            Player player = participant.getPlayer();
            if (player == null || !player.getPlayerId().equals(string2)) continue;
            return participant.getParticipantId();
        }
        return null;
    }

    static Participant c(TurnBasedMatch turnBasedMatch, String string2) {
        ArrayList arrayList = turnBasedMatch.getParticipants();
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Participant participant = (Participant)arrayList.get(i2);
            if (!participant.getParticipantId().equals(string2)) continue;
            return participant;
        }
        throw new IllegalStateException("Participant " + string2 + " is not in match " + turnBasedMatch.getMatchId());
    }

    static ArrayList<String> c(TurnBasedMatch object) {
        object = object.getParticipants();
        int n2 = object.size();
        ArrayList<String> arrayList = new ArrayList<String>(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(((Participant)object.get(i2)).getParticipantId());
        }
        return arrayList;
    }

    @Override
    public boolean canRematch() {
        if (this.Nd == 2 && this.Ng == null) {
            return true;
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return TurnBasedMatchEntity.a((TurnBasedMatch)this, object);
    }

    @Override
    public TurnBasedMatch freeze() {
        return this;
    }

    @Override
    public Bundle getAutoMatchCriteria() {
        return this.MO;
    }

    @Override
    public int getAvailableAutoMatchSlots() {
        if (this.MO == null) {
            return 0;
        }
        return this.MO.getInt("max_automatch_players");
    }

    @Override
    public long getCreationTimestamp() {
        return this.Mu;
    }

    @Override
    public String getCreatorId() {
        return this.MS;
    }

    @Override
    public byte[] getData() {
        return this.Nf;
    }

    @Override
    public String getDescription() {
        return this.HD;
    }

    @Override
    public void getDescription(CharArrayBuffer charArrayBuffer) {
        gm.b(this.HD, charArrayBuffer);
    }

    @Override
    public Participant getDescriptionParticipant() {
        return this.getParticipant(this.getDescriptionParticipantId());
    }

    @Override
    public String getDescriptionParticipantId() {
        return this.Nl;
    }

    @Override
    public Game getGame() {
        return this.Lt;
    }

    @Override
    public long getLastUpdatedTimestamp() {
        return this.Nb;
    }

    @Override
    public String getLastUpdaterId() {
        return this.Na;
    }

    @Override
    public String getMatchId() {
        return this.Jb;
    }

    @Override
    public int getMatchNumber() {
        return this.Ni;
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
        return new ArrayList<Participant>(this.Mx);
    }

    @Override
    public String getPendingParticipantId() {
        return this.Nc;
    }

    @Override
    public byte[] getPreviousMatchData() {
        return this.Nh;
    }

    @Override
    public String getRematchId() {
        return this.Ng;
    }

    @Override
    public int getStatus() {
        return this.Nd;
    }

    @Override
    public int getTurnStatus() {
        return this.Nj;
    }

    @Override
    public int getVariant() {
        return this.My;
    }

    @Override
    public int getVersion() {
        return this.Ne;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return TurnBasedMatchEntity.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public boolean isLocallyModified() {
        return this.Nk;
    }

    public String toString() {
        return TurnBasedMatchEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        TurnBasedMatchEntityCreator.a(this, parcel, n2);
    }
}

