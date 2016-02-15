package com.google.android.gms.games.request;

import com.google.android.gms.common.data.C0796d;
import com.google.android.gms.common.data.DataHolder;

public final class GameRequestBuffer extends C0796d<GameRequest> {
    public GameRequestBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object m2902c(int i, int i2) {
        return getEntry(i, i2);
    }

    protected GameRequest getEntry(int rowIndex, int numChildren) {
        return new GameRequestRef(this.BB, rowIndex, numChildren);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "external_request_id";
    }
}
