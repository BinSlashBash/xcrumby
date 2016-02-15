/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 *  android.os.Parcelable
 */
package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;

public interface Player
extends Parcelable,
Freezable<Player> {
    public String getDisplayName();

    public void getDisplayName(CharArrayBuffer var1);

    public Uri getHiResImageUri();

    @Deprecated
    public String getHiResImageUrl();

    public Uri getIconImageUri();

    @Deprecated
    public String getIconImageUrl();

    public long getLastPlayedWithTimestamp();

    public String getPlayerId();

    public long getRetrievedTimestamp();

    public int gh();

    public boolean hasHiResImage();

    public boolean hasIconImage();
}

