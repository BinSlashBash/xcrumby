/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.maps.model;

import com.google.android.gms.maps.model.Tile;

public interface TileProvider {
    public static final Tile NO_TILE = new Tile(-1, -1, null);

    public Tile getTile(int var1, int var2, int var3);
}

