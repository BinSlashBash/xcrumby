/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 */
package com.google.android.gms.games.multiplayer;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.Participatable;

public interface Invitation
extends Parcelable,
Freezable<Invitation>,
Participatable {
    public static final int INVITATION_TYPE_REAL_TIME = 0;
    public static final int INVITATION_TYPE_TURN_BASED = 1;

    public int getAvailableAutoMatchSlots();

    public long getCreationTimestamp();

    public Game getGame();

    public String getInvitationId();

    public int getInvitationType();

    public Participant getInviter();

    public int getVariant();
}

