/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.games.multiplayer.realtime;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.Participatable;
import java.util.ArrayList;

public interface Room
extends Parcelable,
Freezable<Room>,
Participatable {
    public static final int ROOM_STATUS_ACTIVE = 3;
    public static final int ROOM_STATUS_AUTO_MATCHING = 1;
    public static final int ROOM_STATUS_CONNECTING = 2;
    public static final int ROOM_STATUS_INVITING = 0;
    public static final int ROOM_VARIANT_DEFAULT = -1;

    public Bundle getAutoMatchCriteria();

    public int getAutoMatchWaitEstimateSeconds();

    public long getCreationTimestamp();

    public String getCreatorId();

    public String getDescription();

    public void getDescription(CharArrayBuffer var1);

    public Participant getParticipant(String var1);

    public String getParticipantId(String var1);

    public ArrayList<String> getParticipantIds();

    public int getParticipantStatus(String var1);

    public String getRoomId();

    public int getStatus();

    public int getVariant();
}

