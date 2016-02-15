package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.common.data.C0796d;
import com.google.android.gms.common.data.DataHolder;

public final class RoomBuffer extends C0796d<Room> {
    public RoomBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object m2892c(int i, int i2) {
        return m2893e(i, i2);
    }

    protected Room m2893e(int i, int i2) {
        return new RoomRef(this.BB, i, i2);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "external_match_id";
    }
}
