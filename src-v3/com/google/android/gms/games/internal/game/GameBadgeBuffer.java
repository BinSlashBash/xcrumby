package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataBuffer;

public final class GameBadgeBuffer extends DataBuffer<GameBadge> {
    public GameBadge bf(int i) {
        return new GameBadgeRef(this.BB, i);
    }

    public /* synthetic */ Object get(int x0) {
        return bf(x0);
    }
}
