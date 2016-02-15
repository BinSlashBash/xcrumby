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

public interface Game
extends Parcelable,
Freezable<Game> {
    public boolean gb();

    public boolean gc();

    public boolean gd();

    public String ge();

    public int getAchievementTotalCount();

    public String getApplicationId();

    public String getDescription();

    public void getDescription(CharArrayBuffer var1);

    public String getDeveloperName();

    public void getDeveloperName(CharArrayBuffer var1);

    public String getDisplayName();

    public void getDisplayName(CharArrayBuffer var1);

    public Uri getFeaturedImageUri();

    @Deprecated
    public String getFeaturedImageUrl();

    public Uri getHiResImageUri();

    @Deprecated
    public String getHiResImageUrl();

    public Uri getIconImageUri();

    @Deprecated
    public String getIconImageUrl();

    public int getLeaderboardCount();

    public String getPrimaryCategory();

    public String getSecondaryCategory();

    public int gf();

    public boolean isMuted();

    public boolean isRealTimeMultiplayerEnabled();

    public boolean isTurnBasedMultiplayerEnabled();
}

