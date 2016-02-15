package com.google.android.gms.games.internal.player;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;

public final class ExtendedPlayerBuffer extends DataBuffer<ExtendedPlayer> {
    public ExtendedPlayerBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    public ExtendedPlayer bk(int i) {
        return new ExtendedPlayerRef(this.BB, i);
    }

    public /* synthetic */ Object get(int x0) {
        return bk(x0);
    }
}
