/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 */
package com.google.android.gms.games.request;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Player;
import java.util.List;

public interface GameRequest
extends Parcelable,
Freezable<GameRequest> {
    public static final int RECIPIENT_STATUS_ACCEPTED = 1;
    public static final int RECIPIENT_STATUS_PENDING = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_PENDING = 0;
    public static final int TYPE_ALL = 65535;
    public static final int TYPE_GIFT = 1;
    public static final int TYPE_WISH = 2;

    public long getCreationTimestamp();

    public byte[] getData();

    public long getExpirationTimestamp();

    public Game getGame();

    public int getRecipientStatus(String var1);

    public List<Player> getRecipients();

    public String getRequestId();

    public Player getSender();

    public int getStatus();

    public int getType();

    public boolean isConsumed(String var1);
}

