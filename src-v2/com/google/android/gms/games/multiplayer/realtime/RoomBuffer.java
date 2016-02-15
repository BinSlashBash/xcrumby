/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomRef;

public final class RoomBuffer
extends d<Room> {
    public RoomBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    protected /* synthetic */ Object c(int n2, int n3) {
        return this.e(n2, n3);
    }

    protected Room e(int n2, int n3) {
        return new RoomRef(this.BB, n2, n3);
    }

    @Override
    protected String getPrimaryDataMarkerColumn() {
        return "external_match_id";
    }
}

