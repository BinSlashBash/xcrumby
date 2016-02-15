/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 */
package com.google.android.gms.games.internal.game;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.internal.game.GameBadge;
import java.util.ArrayList;

public interface ExtendedGame
extends Parcelable,
Freezable<ExtendedGame> {
    public ArrayList<GameBadge> gW();

    public int gX();

    public boolean gY();

    public int gZ();

    public Game getGame();

    public long ha();

    public long hb();

    public String hc();

    public long hd();

    public String he();
}

