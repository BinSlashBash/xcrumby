/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.games.multiplayer.turnbased;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.Participatable;
import java.util.ArrayList;

public interface TurnBasedMatch
extends Parcelable,
Freezable<TurnBasedMatch>,
Participatable {
    public static final int MATCH_STATUS_ACTIVE = 1;
    public static final int MATCH_STATUS_AUTO_MATCHING = 0;
    public static final int MATCH_STATUS_CANCELED = 4;
    public static final int MATCH_STATUS_COMPLETE = 2;
    public static final int MATCH_STATUS_EXPIRED = 3;
    public static final int[] MATCH_TURN_STATUS_ALL = new int[]{0, 1, 2, 3};
    public static final int MATCH_TURN_STATUS_COMPLETE = 3;
    public static final int MATCH_TURN_STATUS_INVITED = 0;
    public static final int MATCH_TURN_STATUS_MY_TURN = 1;
    public static final int MATCH_TURN_STATUS_THEIR_TURN = 2;
    public static final int MATCH_VARIANT_DEFAULT = -1;

    public boolean canRematch();

    public Bundle getAutoMatchCriteria();

    public int getAvailableAutoMatchSlots();

    public long getCreationTimestamp();

    public String getCreatorId();

    public byte[] getData();

    public String getDescription();

    public void getDescription(CharArrayBuffer var1);

    public Participant getDescriptionParticipant();

    public String getDescriptionParticipantId();

    public Game getGame();

    public long getLastUpdatedTimestamp();

    public String getLastUpdaterId();

    public String getMatchId();

    public int getMatchNumber();

    public Participant getParticipant(String var1);

    public String getParticipantId(String var1);

    public ArrayList<String> getParticipantIds();

    public int getParticipantStatus(String var1);

    public String getPendingParticipantId();

    public byte[] getPreviousMatchData();

    public String getRematchId();

    public int getStatus();

    public int getTurnStatus();

    public int getVariant();

    public int getVersion();

    public boolean isLocallyModified();
}

