/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.player;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.player.ExtendedPlayer;
import com.google.android.gms.games.internal.player.ExtendedPlayerRef;

public final class ExtendedPlayerBuffer
extends DataBuffer<ExtendedPlayer> {
    public ExtendedPlayerBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    public ExtendedPlayer bk(int n2) {
        return new ExtendedPlayerRef(this.BB, n2);
    }

    @Override
    public /* synthetic */ Object get(int n2) {
        return this.bk(n2);
    }
}

