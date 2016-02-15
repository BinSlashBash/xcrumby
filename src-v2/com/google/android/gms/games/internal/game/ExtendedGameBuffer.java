/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;
import com.google.android.gms.games.internal.game.ExtendedGame;
import com.google.android.gms.games.internal.game.ExtendedGameRef;

public final class ExtendedGameBuffer
extends d<ExtendedGame> {
    public ExtendedGameBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override
    protected /* synthetic */ Object c(int n2, int n3) {
        return this.d(n2, n3);
    }

    protected ExtendedGame d(int n2, int n3) {
        return new ExtendedGameRef(this.BB, n2, n3);
    }

    @Override
    protected String getPrimaryDataMarkerColumn() {
        return "external_game_id";
    }
}

