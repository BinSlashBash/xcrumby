/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.game.GameInstance;
import com.google.android.gms.games.internal.game.GameInstanceRef;

public final class GameInstanceBuffer
extends DataBuffer<GameInstance> {
    public GameInstanceBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    public GameInstance bh(int n2) {
        return new GameInstanceRef(this.BB, n2);
    }

    @Override
    public /* synthetic */ Object get(int n2) {
        return this.bh(n2);
    }
}

