package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.C0796d;
import com.google.android.gms.common.data.DataHolder;

public final class ExtendedGameBuffer extends C0796d<ExtendedGame> {
    public ExtendedGameBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object m2876c(int i, int i2) {
        return m2877d(i, i2);
    }

    protected ExtendedGame m2877d(int i, int i2) {
        return new ExtendedGameRef(this.BB, i, i2);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "external_game_id";
    }
}
