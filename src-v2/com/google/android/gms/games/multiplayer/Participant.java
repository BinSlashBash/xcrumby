/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 *  android.os.Parcelable
 */
package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.ParticipantResult;

public interface Participant
extends Parcelable,
Freezable<Participant> {
    public static final int STATUS_DECLINED = 3;
    public static final int STATUS_FINISHED = 5;
    public static final int STATUS_INVITED = 1;
    public static final int STATUS_JOINED = 2;
    public static final int STATUS_LEFT = 4;
    public static final int STATUS_NOT_INVITED_YET = 0;
    public static final int STATUS_UNRESPONSIVE = 6;

    public int getCapabilities();

    public String getDisplayName();

    public void getDisplayName(CharArrayBuffer var1);

    public Uri getHiResImageUri();

    @Deprecated
    public String getHiResImageUrl();

    public Uri getIconImageUri();

    @Deprecated
    public String getIconImageUrl();

    public String getParticipantId();

    public Player getPlayer();

    public ParticipantResult getResult();

    public int getStatus();

    public String gi();

    public boolean isConnectedToRoom();
}

