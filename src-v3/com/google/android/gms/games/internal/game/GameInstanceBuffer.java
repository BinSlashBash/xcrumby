package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;

public final class GameInstanceBuffer extends DataBuffer<GameInstance> {
    public GameInstanceBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    public GameInstance bh(int i) {
        return new GameInstanceRef(this.BB, i);
    }

    public /* synthetic */ Object get(int x0) {
        return bh(x0);
    }
}
