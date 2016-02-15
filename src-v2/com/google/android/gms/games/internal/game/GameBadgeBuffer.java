/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.game.GameBadge;
import com.google.android.gms.games.internal.game.GameBadgeRef;

public final class GameBadgeBuffer
extends DataBuffer<GameBadge> {
    public GameBadge bf(int n2) {
        return new GameBadgeRef(this.BB, n2);
    }

    @Override
    public /* synthetic */ Object get(int n2) {
        return this.bf(n2);
    }
}

