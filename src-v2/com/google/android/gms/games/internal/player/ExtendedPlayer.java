/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.games.internal.player;

import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Player;

public interface ExtendedPlayer
extends Freezable<ExtendedPlayer> {
    public Player getPlayer();

    public String hu();

    public long hv();

    public Uri hw();
}

